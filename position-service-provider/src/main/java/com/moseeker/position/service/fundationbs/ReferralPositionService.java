package com.moseeker.position.service.fundationbs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.config.HRAccountType;
import com.moseeker.baseorm.dao.referraldb.ReferralCompanyConfJooqDao;
import com.moseeker.baseorm.dao.referraldb.ReferralPositionBonusDao;
import com.moseeker.baseorm.dao.referraldb.ReferralPositionBonusStageDetailDao;
import com.moseeker.baseorm.db.referraldb.tables.ReferralPositionBonus;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralPositionBonusStageDetail;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.PositionEntity;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.struct.ReferralPositionBonusDO;
import com.moseeker.thrift.gen.position.struct.ReferralPositionBonusStageDetailDO;
import com.moseeker.thrift.gen.position.struct.ReferralPositionBonusVO;
import com.moseeker.thrift.gen.position.struct.ReferralPositionUpdateDataDO;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Date: 2018/9/4
 * @Author: JackYang
 */
@Service
public class ReferralPositionService {

    @Autowired
    PositionEntity positionEntity;

    @Autowired
    ReferralCompanyConfJooqDao referralCompanyConfJooqDao;

    @Autowired
    ReferralPositionBonusDao referralPositionBonusDao;

    @Autowired
    ReferralPositionBonusStageDetailDao referralPositionBonusStageDetailDao;


    SearchengineServices.Iface searchengineServices = ServiceManager.SERVICEMANAGER.getService(SearchengineServices.Iface.class);


    Logger logger = LoggerFactory.getLogger(this.getClass());

    private ThreadPool tp = ThreadPool.Instance;

    private static  final int RECORDNUM = 1500;

    @CounterIface
    @Transactional
    public void putReferralPositions(ReferralPositionUpdateDataDO dataDO) throws Exception{
        logger.info("putReferralPositions {}",JSON.toJSONString(dataDO) );

        this.positionUpdateHandler(dataDO,"add");

    }
    @CounterIface
    @Transactional
    public void delReferralPositions(ReferralPositionUpdateDataDO dataDO) throws Exception {
        logger.info("delReferralPositions {}",JSON.toJSONString(dataDO) );
        this.positionUpdateHandler(dataDO,"del");

    }

    @CounterIface
    @Transactional
    public void updatePointsConfig(Integer companyId,Integer flag)  {

        ReferralCompanyConf referralCompanyConf = referralCompanyConfJooqDao.findByCompnayId(companyId);
        if(referralCompanyConf != null) {
            referralCompanyConf.setPositionPointsFlag(flag.byteValue());
            referralCompanyConf.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            referralCompanyConfJooqDao.update(referralCompanyConf);

        }else {
            ReferralCompanyConf referralCompanyConf1  = new ReferralCompanyConf();
            referralCompanyConf1.setPositionPointsFlag(flag.byteValue());
            referralCompanyConf1.setCompanyId(companyId);
            referralCompanyConfJooqDao.insert(referralCompanyConf1);
        }


    }

    @CounterIface
    @Transactional
    public Response getPointsConfig(Integer companyId) {

        ReferralCompanyConf referralCompanyConf = referralCompanyConfJooqDao.findByCompnayId(companyId);

        if(referralCompanyConf != null) {
            return ResponseUtils.success(referralCompanyConf);

        } else {

            ReferralCompanyConf newReferralCompanyConf  = new ReferralCompanyConf();
            newReferralCompanyConf.setPositionPointsFlag((byte)0);
            return ResponseUtils.success(newReferralCompanyConf);
        }

    }

    @Transactional
    public void positionUpdateHandler(ReferralPositionUpdateDataDO dataDO,String opType) throws Exception {

        logger.info("positionUpdateHandler {} {}",JSON.toJSONString(dataDO),opType);

        //如果companyId为空，直接返回
        if(!dataDO.isSetCompany_id()  ){
            return ;
        }


        int all_selected = dataDO.isSetAll_selected()?dataDO.getAll_selected():0;

        List<Integer> pids = dataDO.getPosition_ids();
        //先判断all_selected

        //非全加或者全取消
        if(all_selected == 0) {
            if(opType.equals("add") && !CollectionUtils.isEmpty(pids)) {
                processUpdateData(pids,"add");

            } else if(opType.equals("del") && !CollectionUtils.isEmpty(pids)) {
                processUpdateData(pids,"del");
            }
            return;
        }
        //全选
        if(all_selected == 1 ) {
            //全量新增 要根据筛选条件查询所有positionIds
            List<Integer> esPositionIds = getPositionIdFromEs(dataDO);
            if(opType.equals("add") && !CollectionUtils.isEmpty(esPositionIds) ) {
                processUpdateData(esPositionIds,"add");
                return;
            }
        }

        //全取消
        if(all_selected == 2) {
            List<Integer> esPositionIds = getPositionIdFromEs(dataDO);
            if(opType.equals("del") && !CollectionUtils.isEmpty(esPositionIds)){
                processUpdateData(esPositionIds,"del");
            }
            //处理全取消时又加上某几个职位
            if(opType.equals("del")&& !CollectionUtils.isEmpty(pids)) {
                processUpdateData(pids,"add");

            }
            return;
        }



    }

    @Transactional
    public List<Integer> getPositionIdFromEs(ReferralPositionUpdateDataDO dataDO) throws Exception{

        logger.info("getPositionIdFromEs {} ",JSON.toJSONString(dataDO) );
        
        Map<String,String> query = new HashMap<String,String>() ;

        if(dataDO.isSetCity()) {
            query.put("city",dataDO.getCity());
        }
        if(dataDO.isSetKeyWord()) {
            query.put("keyWord",dataDO.getKeyWord());
        }
        if(dataDO.isSetCompany_id()) {
            query.put("company_id",String.valueOf(dataDO.getCompany_id()));
        }
        if(dataDO.isSetIs_referral()) {
            query.put("is_referral",String.valueOf(dataDO.getIs_referral()));
        }
        if(dataDO.isSetCandidate_source()) {
            if(dataDO.getCandidate_source() >= 0) {
                query.put("candidate_source",String.valueOf(dataDO.getCandidate_source()));
            }
        }
        if(dataDO.isSetEmployment_type()) {
            if(dataDO.getEmployment_type() >= 0) {
                query.put("employment_type",String.valueOf(dataDO.getEmployment_type()));
            }
        }
        query.put("page_size","10000");//查询全部所有记录 理论上不会超过10000条的
        query.put("page_from","1");

        //对HR子账号的特殊处理
        int account_type = dataDO.getAccount_type();
        String account_id = String.valueOf(dataDO.getAccount_id())+"";
        if(dataDO.isSetAccount_type() && account_type == HRAccountType.SubAccount.getType()) {
            query.put("company_id","");//将comapny_id传空，按publisher=account_id查询
            query.put("publisher",account_id);
        }

        logger.info("getPositionIdFromEs query{}",JSON.toJSONString(query));

        Response res =  searchengineServices.searchPositionSuggest(query);

        if (res.getStatus() == 0 && !StringUtils.isNullOrEmpty(res.getData())) {
            JSONObject jobj = JSON.parseObject(res.getData());
            //long totalNum = jobj.getLong("totalNum");
            JSONArray jsonArray  =  jobj.getJSONArray("suggest");

            List<Integer> jdIdList = new ArrayList<>();
            if(jsonArray!=null&&jsonArray.size()>0){
                for(int j=0;j<jsonArray.size();j++) {
                    JSONObject object = jsonArray.getJSONObject(j);
                    Integer pid = object.getIntValue("id");
                    jdIdList.add(pid);
                }
            }
            logger.info("getPositionIdFromEs jdIdList {}",JSON.toJSONString(jdIdList.size()));

            return jdIdList;
        } else {
            return new ArrayList<>();
        }

    }

    @Transactional
    public void processUpdateData(List<Integer> pids ,String optType){

        if(CollectionUtils.isEmpty(pids)) {
            return;
        }

        List<List<Integer>> splitLists = splitList(pids,RECORDNUM);
        Integer taskNum = splitLists.size();
        Integer countTaskNum = 0;
        List<Future> taskFeatures = new ArrayList<>();
        for(List<Integer> list: splitLists) {
            if(optType.equals("add") && !CollectionUtils.isEmpty(list)) {
                Future<Integer> future = tp.startTast(() -> { positionEntity.putReferralPositions(list);return 1; });
                try {
                    //future.get(10, TimeUnit.SECONDS);
                    taskFeatures.add(future);
                }catch (Exception e) {
                    logger.info(e.getClass().getName(),e);
                }

            }else if(optType.equals("del") && !CollectionUtils.isEmpty(list)) {
                Future<Integer> future = tp.startTast(() -> { positionEntity.delReferralPositions(list);return 1; });
                try {
                    //future.get(10, TimeUnit.SECONDS);
                    taskFeatures.add(future);
                }catch (Exception e) {
                    logger.info(e.getClass().getName(),e);
                }
            }
        }

        try{
            for(Future future:taskFeatures) {
                future.get(5, TimeUnit.SECONDS);
                countTaskNum++;
            }
        }catch (Exception e){
            logger.info(e.getClass().getName(),e);
        }

        logger.info("processUpdateData taskNum - countTaskNum {} {} ",taskNum, countTaskNum);



    }

    private  <T> List<List<T>> splitList(List<T> resList,int count){

        if(resList==null ||count<1)
            return  null ;
        List<List<T>> ret=new ArrayList<List<T>>();
        int size=resList.size();
        if(size<=count){ //数据量不足count指定的大小
            ret.add(resList);
        }else{
            int pre=size/count;
            int last=size%count;
            //前面pre个集合，每个大小都是count个元素
            for(int i=0;i<pre;i++){
                List<T> itemList=new ArrayList<T>();
                for(int j=0;j<count;j++){
                    itemList.add(resList.get(i*count+j));
                }
                ret.add(itemList);
            }
            //last的进行处理
            if(last>0){
                List<T> itemList=new ArrayList<T>();
                for(int i=0;i<last;i++){
                    itemList.add(resList.get(pre*count+i));
                }
                ret.add(itemList);
            }
        }
        return ret;

    }

    @CounterIface
    @Transactional
    public void putReferralPositionBonus(ReferralPositionBonusVO referralPositionBonusVO) throws Exception{
        logger.info("putReferralPositionBonus {}",JSON.toJSONString(referralPositionBonusVO) );
        ReferralPositionBonusDO referralPositionBonusDO = referralPositionBonusVO.getPosition_bonus();
        List<ReferralPositionBonusStageDetailDO> detailDOS = referralPositionBonusVO.getData();

        Integer pid = referralPositionBonusDO.getPosition_id();

        com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralPositionBonus referralPositionBonus =  referralPositionBonusDao.fetchOne(ReferralPositionBonus.REFERRAL_POSITION_BONUS.POSITION_ID,pid);

        LocalDateTime now = LocalDateTime.now();
        if(referralPositionBonus == null) {
           int referralPositionBounsId = referralPositionBonusDao.createReferralPositionBonus(referralPositionBonusDO.getPosition_id(),referralPositionBonusDO.getTotal_bonus());

           for(ReferralPositionBonusStageDetailDO detailDO:detailDOS) {
               ReferralPositionBonusStageDetail referralPositionBonusStageDetailRecord = new ReferralPositionBonusStageDetail();
               referralPositionBonusStageDetailRecord.setReferralPositionBonusId(referralPositionBounsId);
               referralPositionBonusStageDetailRecord.setStageBonus(detailDO.getStage_bonus());
               referralPositionBonusStageDetailRecord.setStageProportion(detailDO.getStage_proportion());
               referralPositionBonusStageDetailRecord.setPositionId(pid);
               referralPositionBonusStageDetailRecord.setCreateTime(Timestamp.valueOf(now));
               referralPositionBonusStageDetailRecord.setUpdateTime(Timestamp.valueOf(now));

               referralPositionBonusStageDetailDao.insert(referralPositionBonusStageDetailRecord);
           }

        } else {

            referralPositionBonus.setTotalBonus(referralPositionBonusDO.getTotal_bonus());
            referralPositionBonus.setUpdateTime(Timestamp.valueOf(now));
            referralPositionBonusDao.update(referralPositionBonus);

            for(ReferralPositionBonusStageDetailDO detailDO:detailDOS ) {
                ReferralPositionBonusStageDetail referralPositionBonusStageDetail =  referralPositionBonusStageDetailDao.fetchByPositionIdStageType(detailDO.getPosition_id(),detailDO.getStage_type());
                if(referralPositionBonusStageDetail == null) {
                    ReferralPositionBonusStageDetail referralPositionBonusStageDetailRecord = new ReferralPositionBonusStageDetail();
                    referralPositionBonusStageDetailRecord.setReferralPositionBonusId(referralPositionBonus.getId());
                    referralPositionBonusStageDetailRecord.setStageBonus(detailDO.getStage_bonus());
                    referralPositionBonusStageDetailRecord.setStageProportion(detailDO.getStage_proportion());
                    referralPositionBonusStageDetailRecord.setPositionId(pid);
                    referralPositionBonusStageDetailRecord.setCreateTime(Timestamp.valueOf(now));
                    referralPositionBonusStageDetailRecord.setUpdateTime(Timestamp.valueOf(now));
                } else {
                    referralPositionBonusStageDetail.setStageBonus(detailDO.getStage_bonus());
                    referralPositionBonusStageDetail.setStageProportion(detailDO.getStage_proportion());
                    referralPositionBonusStageDetailDao.update(referralPositionBonusStageDetail);
                }
            }

        }

    }

}
