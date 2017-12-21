package com.moseeker.baseorm.dao.hrdb;

import com.alibaba.fastjson.JSON;
import com.google.common.primitives.Ints;
import com.moseeker.baseorm.base.EmptyExtThirdPartyPosition;
import com.moseeker.baseorm.base.IThirdPartyPositionDao;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.dao.thirdpartydb.DefaultThirdPartyPositionDao;
import com.moseeker.baseorm.dao.thirdpartydb.ThirdpartyJob1001PositionDao;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyPositionRecord;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.Update;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import org.jooq.impl.TableImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * HR帐号数据库持久类
 * 这是一个代理类，真正的HRThirdPartyPositionDao是这个类的私有类
 * 这个类代理了几个基础的类功能：getData、getDatas、addData、addAllData、updateData、updateDatas
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

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    List<IThirdPartyPositionDao> daos;


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
    @Transactional
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
                throw new BIZException(-1, "更新状态时发生了错误，请重试！");
            }
            query = new Query.QueryBuilder().where("id", thirdPartyPosition.getId()).buildQuery();
            return getData(query);
        }
    }

    /**
     *
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
        return thirdPartyPositionDao(thirdPartyPositionDO.getChannel()).getData(thirdPartyPositionDO);
    }

    public <P> List<TwoParam<HrThirdPartyPositionDO,P>> getDatas(Query query) throws BIZException {
        List<HrThirdPartyPositionDO> list=thirdPartyPositionDao.getDatas(query);
        if(list==null || list.isEmpty()){
            return new ArrayList<>();
        }


        List<TwoParam<HrThirdPartyPositionDO,P>> results=new ArrayList<>();

        //需要根据渠道去对应的dao去调用获取对应的getdatas方法
        List<Integer> channels=list.stream().map(p->p.getChannel()).distinct().collect(Collectors.toList());
        for(int channel:channels){
            results.addAll(thirdPartyPositionDao(channel).getDatas(list));
        }

        return results;
    }

    @Transactional
    public <P> TwoParam<HrThirdPartyPositionDO,P> addData(HrThirdPartyPositionDO s,P p) throws BIZException {
        HrThirdPartyPositionDO result=thirdPartyPositionDao.addData(s);
        return thirdPartyPositionDao(result.getChannel()).addData(result,p);
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

    @Transactional
    public <P> int updateData(HrThirdPartyPositionDO s,P p) throws BIZException {
        int result=thirdPartyPositionDao.updateData(s);
        if(p== EmptyExtThirdPartyPosition.EMPTY){
            return result;
        }
        IThirdPartyPositionDao extDao=thirdPartyPositionDao(s.getChannel());
        //设置下ExtThirdpartyPosition的id
        extDao.setId(s,p);

        int extResult=extDao.updateData(p);
        if(result!=extResult){
            logger.error("update Position and ExtPosition not EQ HrThirdPartyPositionDO:{}，P:{}",s,p);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"update Position and ExtPosition not EQ ");
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
                int[] extResult = thirdPartyPositionDao(channel).updateDatas(tempExtPositions);

                if (!Arrays.equals(result, extResult)) {
                    logger.error("batch update update Position and ExtPosition not EQ channel:{} tempPositions:{}，tempExtPositions:{}", channel, tempPositions, tempExtPositions);
                    throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, "batch update update Position and ExtPosition not EQ");
                }
                results.addAll(Ints.asList(result));
            }
        }
        return Ints.toArray(results);
    }

    public int disable(List<Condition> conditions){
        Update.UpdateBuilder update=new Update.UpdateBuilder().set(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.POSITION_ID.getName(),0);
        if(!StringUtils.isEmptyList(conditions)){
            update=update.where(conditions.get(0));
            for(int i=1;i<conditions.size();i++){
                update=update.and(conditions.get(i));
            }
        }
        return thirdPartyPositionDao.update(update.buildUpdate());
    }

    /**
     * 工厂类，根据渠道获取对应的dao
     * @param channel
     * @return
     * @throws BIZException
     */
    public IThirdPartyPositionDao thirdPartyPositionDao(int channel) throws BIZException {
        ChannelPositionDao channelPositionDao=ChannelPositionDao.getInsance(channel);
        if(channelPositionDao==null){
            logger.error("no matched ChannelPositionDao! channel : {}",channel);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"no matched ChannelPositionDao channel");
        }
        for(IThirdPartyPositionDao dao:daos){
            if(dao.getClass()==channelPositionDao.dao){
                return dao;
            }
        }
        logger.error("no matched thirdPartyPositionDao! channel : {}",channel);
        throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS,"no matched thirdPartyPositionDao channel");
    }

    public enum ChannelPositionDao{
        JOB1001(ChannelType.JOB1001, ThirdpartyJob1001PositionDao.class),
        VERYEAST(ChannelType.VERYEAST, ThirdpartyJob1001PositionDao.class),
        JOB51(ChannelType.JOB51, DefaultThirdPartyPositionDao.class),
        LIEPIN(ChannelType.LIEPIN, DefaultThirdPartyPositionDao.class),
        ZHILIAN(ChannelType.ZHILIAN, DefaultThirdPartyPositionDao.class)
        ;

        ChannelPositionDao(ChannelType channelType, Class<? extends IThirdPartyPositionDao> dao) {
            this.channelType = channelType;
            this.dao = dao;
        }

        public static ChannelPositionDao getInsance(int channel){
            for(ChannelPositionDao dao:values()){
                if(dao.channelType.getValue()==channel){
                    return dao;
                }
            }
            return null;
        }

        ChannelType channelType;

        Class<? extends IThirdPartyPositionDao> dao;
    }

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
