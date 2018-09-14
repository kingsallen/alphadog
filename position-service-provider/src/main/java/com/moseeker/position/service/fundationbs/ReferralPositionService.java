package com.moseeker.position.service.fundationbs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.config.HRAccountType;
import com.moseeker.baseorm.dao.referraldb.ReferralCompanyConfJooqDao;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.PositionEntity;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.struct.ReferralPositionUpdateDataDO;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    SearchengineServices.Iface searchengineServices = ServiceManager.SERVICEMANAGER.getService(SearchengineServices.Iface.class);


    Logger logger = LoggerFactory.getLogger(this.getClass());

    private ThreadPool tp = ThreadPool.Instance;


    @CounterIface
    @Transactional
    public void putReferralPositions(ReferralPositionUpdateDataDO dataDO) throws Exception{

        this.positionIdsHandler(dataDO,"add");

    }
    @CounterIface
    @Transactional
    public void delReferralPositions(ReferralPositionUpdateDataDO dataDO) throws Exception {
        this.positionIdsHandler(dataDO,"del");

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


    public void positionIdsHandler(ReferralPositionUpdateDataDO dataDO,String opType) throws Exception {

        logger.info("positionIdsHandler {}",JSON.toJSONString(dataDO));
        int all_selected = dataDO.isSetAll_selected()?dataDO.getAll_selected():0;

        List<Integer> pids = dataDO.getPosition_ids();
        //先判断all_selected

        //非全加或者全取消
        if(all_selected == 0) {
            if(opType.equals("add") && !CollectionUtils.isEmpty(pids)) {
                positionEntity.putReferralPositions(pids);
            } else if(opType.equals("del") && !CollectionUtils.isEmpty(pids)) {
                positionEntity.delReferralPositions(pids);
            }
            return;
        }
        //全选
        if(all_selected == 1 ) {
            //全量新增 要根据筛选条件查询所有positionIds
            List<Integer> esPositionIds = getPositionIdFromEs(dataDO);
            if(opType.equals("add") && !CollectionUtils.isEmpty(esPositionIds) ) {
                positionEntity.putReferralPositions(esPositionIds);
                return;
            }
        }

        //全取消
        if(all_selected == 2) {
            List<Integer> esPositionIds = getPositionIdFromEs(dataDO);
            if(opType.equals("del") && !CollectionUtils.isEmpty(esPositionIds)){
                positionEntity.delReferralPositions(esPositionIds);
            }
            //处理全取消时又加上某几个职位
            if(opType.equals("del")&& !CollectionUtils.isEmpty(pids)) {
                positionEntity.putReferralPositions(pids);
            }
            return;
        }



    }

    public List<Integer> getPositionIdFromEs(ReferralPositionUpdateDataDO dataDO) throws Exception{

        Map<String,String> query = new HashMap<String,String>() ;

        query.put("city",dataDO.getCity());
        query.put("keyWord",dataDO.getKeyWord());
        query.put("company_id",String.valueOf(dataDO.getCompany_id()));
        query.put("candidate_source",String.valueOf(dataDO.getCandidate_source()));
        query.put("employment_type",String.valueOf(dataDO.getEmployment_type()));
        query.put("page_size","10000");//查询全部所有记录 理论上不会超过10000条的
        query.put("page_from","1");

        //对HR子账号的特殊处理
        int account_type = dataDO.getAccount_type();
        String account_id = String.valueOf(dataDO.getAccount_id());
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
            logger.info("getPositionIdFromEs jdIdList {}",JSON.toJSONString(jdIdList));

            return jdIdList;
        } else {
            return new ArrayList<>();
        }


    }


}
