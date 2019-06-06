package com.moseeker.baseorm.dao.hrdb;

import com.alibaba.fastjson.JSON;
import com.google.common.primitives.Ints;
import com.moseeker.baseorm.base.EmptyExtThirdPartyPosition;
import com.moseeker.baseorm.base.IThirdPartyPositionDao;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.dao.hrdb.utils.ThirdPartyPositionDaoFactory;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyPositionRecord;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.Update;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import org.apache.commons.lang.time.FastDateFormat;
import org.joda.time.DateTime;
import org.jooq.impl.TableImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * HR帐号数据库持久类
 * 这是一个代理类，真正的HRThirdPartyPositionDao是这个类的私有类
 * 这个类代理了几个基础的类功能：getData、getDatas、addData、addAllData、updateExtPosition、updateExtPositions
 * 不同于原生方法，代理方法传入的参数都是TwoParam<HRThirdPartyPositionDO,第三方渠道职位类>，查询方法返回也是如此。
 * <p>
 * Company: MoSeeker
 * </P>
 * <p>
 * date: Nov 9, 2016
 * modifyDate: Dec 5,2017
 * </p>
 * <p>
 * Email: wjf2255@gmail.com
 * </p>
 *
 * @author wjf
 * @modify pyb
 */
@Service
public class HRThirdPartyPositionDao  {
    Logger logger= LoggerFactory.getLogger(HRThirdPartyPositionDao.class);

    @Autowired
    private InnerHRThirdPartyPositionDao thirdPartyPositionDao;

    @Autowired
    private ThirdPartyPositionDaoFactory daoFactory;


    public <P> TwoParam<HrThirdPartyPositionDO, P> getThirdPositionById(int id) throws BIZException {
        Query query = new Query.QueryBuilder().where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.ID.getName(), id).buildQuery();
        return getData(query);
    }

    /**
     * 批量插入或更新第三方职能
     *
     * @param positions
     * @return
     */
    public <P> Response upsertThirdPartyPositions(List<TwoParam<HrThirdPartyPositionDO, P> > positions) throws BIZException {
        if (positions == null || positions.size() == 0) return ResponseUtils.success(null);
        logger.info("HRThirdPartyPositionDao upsertThirdPartyPositions" + JSON.toJSONString(positions));
        for (TwoParam<HrThirdPartyPositionDO, P>  p : positions) {
            upsertThirdPartyPosition(p);
        }
        return ResponseUtils.success(null);

    }

    /**
     * 如果第三方职位数据存在，则修改，否则添加
     *
     * @param p
     * @return
     */
    @Transactional
    public <P> TwoParam<HrThirdPartyPositionDO, P> upsertThirdPartyPosition(TwoParam<HrThirdPartyPositionDO, P>  p) throws BIZException {

        HrThirdPartyPositionDO thirdPartyPositionDO=p.getR1();
        P extPosition=p.getR2();
        Query query = new Query.QueryBuilder()
                .where("third_party_account_id", thirdPartyPositionDO.getThirdPartyAccountId())
                .and("position_id", thirdPartyPositionDO.getPositionId())
                .buildQuery();
        HrThirdPartyPositionDO thirdPartyPosition = thirdPartyPositionDao.getData(query);
        if (thirdPartyPosition == null) {
            logger.info("添加一个第三方职位:channel:{},positionId:{}", thirdPartyPositionDO.getChannel(), thirdPartyPositionDO.getPositionId());
            return addData(thirdPartyPositionDO,extPosition);
        } else {
            logger.info("更新一个第三方职位:channel:{},positionId:{}", thirdPartyPositionDO.getChannel(), thirdPartyPositionDO.getPositionId());
            thirdPartyPositionDO.setId(thirdPartyPosition.getId());

            int updateResult = updateData(thirdPartyPositionDO,extPosition);

            if (updateResult < 1) {
                logger.error("更新第三方职位失败:{}", JSON.toJSONString(thirdPartyPositionDO));
                throw new RuntimeException("更新状态时发生了错误，请重试！");
            }
            query = new Query.QueryBuilder().where("id", thirdPartyPosition.getId()).buildQuery();
            return getData(query);
        }
    }

    /**
     *  查询完整的第三方职位数据
     * @param query
     * @param <P>
     * @return 返回<第三方职位表,渠道职位表数据>
     * @throws BIZException
     */
    public <P> TwoParam<HrThirdPartyPositionDO,P> getData(Query query) throws BIZException {
        HrThirdPartyPositionDO thirdPartyPositionDO=thirdPartyPositionDao.getData(query);
        if(thirdPartyPositionDO==null || thirdPartyPositionDO.getId()==0){
            return null;
        }
        return daoFactory.thirdPartyPositionDao(thirdPartyPositionDO.getChannel()).getData(thirdPartyPositionDO);
    }

    /**
     * 只获取第三方职位主表，有些操作只需要主表数据
     * @param query
     * @return 第三方职位-主表数据
     */
    public HrThirdPartyPositionDO getSimpleData(Query query){
        return thirdPartyPositionDao.getData(query);
    }

    /**
     * 批量只获取第三方职位主表，有些操作只需要主表数据
     * @param query
     * @return 第三方职位-主表数据
     */
    public List<HrThirdPartyPositionDO> getSimpleDatas(Query query){
        return thirdPartyPositionDao.getDatas(query);
    }


    /**
     * 获取正在绑定的第三方职位数据
     * @param positionId 职位ID
     * @param accountId 第三方账号ID
     * @return 第三方职位
     */
    public HrThirdPartyPositionDO getBindingData(int positionId,int accountId){
        Query query=new Query.QueryBuilder()
                .where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.POSITION_ID.getName(),positionId)
                .and(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.THIRD_PARTY_ACCOUNT_ID.getName(),accountId)
                .and(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.IS_SYNCHRONIZATION.getName(), PositionSync.binding.getValue()) //只有正在绑定才能改为3，重新同步
                .buildQuery();
        return getSimpleData(query);
    }

    /**
     * 获取完整的第三方职位数据，
     * 先查询出第三方职位-主表数据
     * 再调用策略模式查询出附表数据 {@link IThirdPartyPositionDao}
     *
     * @param query
     * @param <P>
     * @return
     * @throws BIZException
     */
    public <P> List<TwoParam<HrThirdPartyPositionDO,P>> getDatas(Query query) throws BIZException {
            List<HrThirdPartyPositionDO> list=thirdPartyPositionDao.getDatas(query);
        if(list==null || list.isEmpty()){
            return new ArrayList<>();
        }


        List<TwoParam<HrThirdPartyPositionDO,P>> results=new ArrayList<>();

        //需要根据渠道去对应的dao去调用获取对应的getdatas方法
        Map<Integer,List<HrThirdPartyPositionDO>> map=list.stream().collect(Collectors.groupingBy(p->p.getChannel()));
        for(Map.Entry<Integer,List<HrThirdPartyPositionDO>> entry:map.entrySet()){
            int channel=entry.getKey();
            List<HrThirdPartyPositionDO> listToBeSearch=entry.getValue();
            results.addAll(daoFactory.thirdPartyPositionDao(channel).getDatas(listToBeSearch));
        }

        return results;
    }

    /**
     * 添加完整第三方职位数据
     * 先添加第三方职位主表hr_third_party_position
     * 再调用策略模式添加第三方职位附表 {@link IThirdPartyPositionDao}
     *
     * @param s 第三方职位主表数据
     * @param p 第三方职位附表数据
     * @param <P>
     * @return
     * @throws BIZException
     */
    @Transactional
    public <P> TwoParam<HrThirdPartyPositionDO,P> addData(HrThirdPartyPositionDO s,P p) throws BIZException {
        HrThirdPartyPositionDO result=thirdPartyPositionDao.addData(s);
        return daoFactory.thirdPartyPositionDao(result.getChannel()).addData(result,p);
    }

    //批量更新应该很慢，慎用，或者找到可以防止批量插入HrThirdPartyPositionDO后批量对应P的问题
    @Transactional
    public <P> List<TwoParam<HrThirdPartyPositionDO,P>> addAllData(List<TwoParam<HrThirdPartyPositionDO,P>> ss) throws BIZException {
        List<TwoParam<HrThirdPartyPositionDO,P>> result=new ArrayList<>();
        for(TwoParam<HrThirdPartyPositionDO,P> param:ss){
            addData(param.getR1(),param.getR2());
        }
        return result;
    }

    public int getCount(Query query){
        return thirdPartyPositionDao.getCount(query);
    }

    /**
     * 更新完整的第三方职位数据
     * 先更新第三方职位-主表 hr_third_party_position
     * 再调用策略模式更新第三方职位-附表 {@link IThirdPartyPositionDao}
     * @param s 第三方职位-主表
     * @param p 第三方职位-附表
     * @param <P>
     * @return
     */
    @Transactional
    public <P> int updateData(HrThirdPartyPositionDO s,P p) {
        int result=thirdPartyPositionDao.updateData(s);
        if(p== EmptyExtThirdPartyPosition.EMPTY){
            return result;
        }
        IThirdPartyPositionDao extDao=daoFactory.thirdPartyPositionDao(s.getChannel());
        //设置下ExtThirdpartyPosition的id
        extDao.setId(s,p);

        int extResult=extDao.updateExtPosition(p);
        if(result!=extResult){
            logger.error("update Position and ExtPosition not EQ HrThirdPartyPositionDO:{}，P:{}",s,p);
            throw new RuntimeException("update Position and ExtPosition not EQ ");
        }
        return result;
    }

    @Transactional
    public <P> int[] updateDatas(List<TwoParam<HrThirdPartyPositionDO,P>> ss) throws BIZException {
        if(ss==null || ss.isEmpty()){
            return null;
        }
        List<Integer> results=new ArrayList<>(ss.size());

        //处理只需要更新HR_Third_Party_Position表
        List<HrThirdPartyPositionDO> onlyOneTable=ss.stream().filter(s->s.getR2()==EmptyExtThirdPartyPosition.EMPTY).map(s->s.getR1()).collect(Collectors.toList());
        int[] oneTableResult=thirdPartyPositionDao.updateDatas(onlyOneTable);
        results.addAll(Ints.asList(oneTableResult));


        //处理需要更新HR_Third_Party_Position和对应渠道职位表
        List<TwoParam<HrThirdPartyPositionDO,P>> twoTable=ss.stream().filter(s->s.getR2()!=EmptyExtThirdPartyPosition.EMPTY).collect(Collectors.toList());
        if(twoTable!=null && !twoTable.isEmpty()) {
            //按渠道更新
            List<Integer> channels = ss.stream().map(s -> s.getR1().getChannel()).distinct().collect(Collectors.toList());
            for (int channel : channels) {
                List<HrThirdPartyPositionDO> tempPositions = twoTable.stream().filter(p -> p.getR1().getChannel() == channel).map(p -> p.getR1()).collect(Collectors.toList());
                List<P> tempExtPositions = twoTable.stream().filter(p -> p.getR1().getChannel() == channel).map(p -> p.getR2()).collect(Collectors.toList());

                int[] result = thirdPartyPositionDao.updateDatas(tempPositions);
                int[] extResult = daoFactory.thirdPartyPositionDao(channel).updateExtPositions(tempExtPositions);

                if (!Arrays.equals(result, extResult)) {
                    logger.error("batch update update Position and ExtPosition not EQ channel:{} tempPositions:{}，tempExtPositions:{}", channel, tempPositions, tempExtPositions);
                    throw new RuntimeException("batch update update Position and ExtPosition not EQ");
                }
                results.addAll(Ints.asList(result));
            }
        }
        return Ints.toArray(results);
    }

    /**
     * 作废第三方职位
     * @param conditions 作废条件
     * @return
     */
    public int disable(List<Condition> conditions){
        Update.UpdateBuilder update=new Update.UpdateBuilder()
                .set(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.IS_SYNCHRONIZATION.getName(),0)
                .set(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.UPDATE_TIME.getName(),new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"))
                .where(new Condition(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.IS_SYNCHRONIZATION.getName(),0, ValueOp.NEQ))
                .and(new Condition(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.CHANNEL.getName(), ChannelType.TW104.getValue(), ValueOp.NEQ));
        if(!StringUtils.isEmptyList(conditions)){
            for(int i=0;i<conditions.size();i++){
                update=update.and(conditions.get(i));
            }
        }
        return thirdPartyPositionDao.update(update.buildUpdate());
    }



    /**
     * 通过职位id获取职位发布时的第三方表单数据
     * @param
     * @author  cjm
     * @date  2018/6/4
     * @return
     */
    public HrThirdPartyPositionDO getThirdPartyPositionById(int positionId, int positionChannel, int accountId) {
        Query query = new Query.QueryBuilder().where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.POSITION_ID.getName(), positionId)
                .and(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.CHANNEL.getName(), positionChannel)
                .and(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.THIRD_PARTY_ACCOUNT_ID.getName(), accountId)
                .buildQuery();
        return getSimpleData(query);
    }

    /**
     * 修改同步状态为未同步，要不然无法复用positionbs
     * @param
     * @author  cjm
     * @date  2018/6/11
     * @return
     */
    public void updateBindState(int positionId, int accountId, int channel, int state) {
        Update.UpdateBuilder update=new Update.UpdateBuilder()
                .set(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.IS_SYNCHRONIZATION.getName(), state)
                .where(new Condition(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.POSITION_ID.getName(),positionId))
                .and(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.THIRD_PARTY_ACCOUNT_ID.getName(), accountId)
                .and(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.CHANNEL.getName(), channel);
        thirdPartyPositionDao.update(update.buildUpdate());
    }

    /**
     * 当token失效时，修改同步状态和同步失败原
     * @param
     * @author  cjm
     * @date  2018/6/25
     * @return
     */
    public void updateErrmsg(String errMsg, int positionId, int channel, int state) {
        Update.UpdateBuilder update=new Update.UpdateBuilder()
                .set(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.IS_SYNCHRONIZATION.getName(), state)
                .set(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.SYNC_FAIL_REASON.getName(), errMsg)
                .where(new Condition(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.POSITION_ID.getName(),positionId))
                .and(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.CHANNEL.getName(), channel);
        thirdPartyPositionDao.update(update.buildUpdate());
    }

    public List<HrThirdPartyPositionDO> getAuditPositionData() {
        Query query = new Query.QueryBuilder()
                .where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.IS_SYNCHRONIZATION.getName(), PositionSync.binding.getValue())
                .buildQuery();
        return getSimpleDatas(query);
    }


    /**
     * 隐藏的内部第三方职位dao，
     * 因为第三方职位分成主表和附表(坑爹的附表还有可能是多个)
     * 插入和更新涉及多个dao，所以封装一下
     */
    @Repository
    private static class InnerHRThirdPartyPositionDao extends JooqCrudImpl<HrThirdPartyPositionDO, HrThirdPartyPositionRecord>{

        public InnerHRThirdPartyPositionDao() {
            super(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION, HrThirdPartyPositionDO.class);
        }

        public InnerHRThirdPartyPositionDao(TableImpl<HrThirdPartyPositionRecord> table, Class<HrThirdPartyPositionDO> hrThirdPartyPositionDOClass) {
            super(table, hrThirdPartyPositionDOClass);
        }
    }
}
