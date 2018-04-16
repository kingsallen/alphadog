package com.moseeker.company.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolCompanyTagDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolCompanyTagUserDao;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTag;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTagUser;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolCompanyTagUserRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.TalentPoolEntity;
import com.moseeker.entity.biz.CompanyFilterTagValidation;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.struct.TalentpoolCompanyTagDO;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zztaiwll on 18/4/9.
 */
@Service
public class CompanyTagService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TalentpoolCompanyTagUserDao talentpoolCompanyTagUserDao;
    @Autowired
    private TalentpoolCompanyTagDao talentpoolCompanyTagDao;
    @Resource(name = "cacheClient")
    private RedisClient client;
    @Autowired
    private JobApplicationDao jobApplicationDao;
    SearchengineServices.Iface service = ServiceManager.SERVICEMANAGER.getService(SearchengineServices.Iface.class);
    WholeProfileServices.Iface profileService = ServiceManager.SERVICEMANAGER.getService(WholeProfileServices.Iface.class);
    private static String COMPANYTAG_ES_STATUS="COMPANY_TAG_ES_STATUS";
    @Autowired
    private TalentPoolEntity talentPoolEntity;
    @Autowired
    private CompanyFilterTagValidation companyFilterTagValidation;

    /*
      type=0 新增标签
      type=1 修改标签
      type=2 删除标签
     */
    @CounterIface
    public void handlerCompanyTag(List<Integer> tagIdList, int type){
        try {
            if (type == 2) {//删除标签只需要执行删除操作即可
                talentpoolCompanyTagUserDao.deleteByTag(tagIdList);
            } else {
                //新增标签，不用调用删除原有表中的标签和人才的对应关系，只需要增加就可以
                Map<String, Object> map = talentpoolCompanyTagDao.getTagById(tagIdList.get(0));
                Map<String, String> params = new HashMap<>();
                if (map != null && !map.isEmpty()) {
                    for (String key : map.keySet()) {
                        params.put(key, String.valueOf(map.get(key)));
                    }
                    List<Integer> userIdList = service.queryCompanyTagUserIdList(params);
                    logger.info("=========================");
                    logger.info(JSON.toJSONString(userIdList));
                    logger.info("=========================");
                    if (type == 0) {
                        if (!StringUtils.isEmptyList(userIdList)) {
                            List<TalentpoolCompanyTagUserRecord> list = new ArrayList<>();
                            for (Integer userId : userIdList) {
                                TalentpoolCompanyTagUserRecord record = new TalentpoolCompanyTagUserRecord();
                                record.setTagId(tagIdList.get(0));
                                record.setUserId(userId);
                                list.add(record);
                            }
                            talentpoolCompanyTagUserDao.batchAddTagAndUser(list);
                        }

                    } else if (type == 1) {//修改标签需要把表中原有的数据全部删除，
                        talentpoolCompanyTagUserDao.deleteByTag(tagIdList);
                        if (!StringUtils.isEmptyList(userIdList)) {
                            List<TalentpoolCompanyTagUserRecord> list = new ArrayList<>();
                            for (Integer userId : userIdList) {
                                TalentpoolCompanyTagUserRecord record = new TalentpoolCompanyTagUserRecord();
                                record.setTagId(tagIdList.get(0));
                                record.setUserId(userId);
                                list.add(record);
                            }
                            talentpoolCompanyTagUserDao.batchAddTagAndUser(list);
                        }

                    }
                }
            }
            //更新es中tag_id和人才的关系
            if (!StringUtils.isEmptyList(tagIdList)) {
                for (Integer tagId : tagIdList) {
                    Map<String, Object> result = new HashMap<>();
                    result.put("tag_id", tagId);
                    result.put("type", type);
                    client.lpush(Constant.APPID_ALPHADOG,
                            "ES_UPDATE_INDEX_COMPANYTAG_ID", JSON.toJSONString(result));
                    logger.info(JSON.toJSONString("======================================="));
                    logger.info(JSON.toJSONString(result));
                    logger.info(JSON.toJSONString("======================================="));
                    //将这个的tag置位更新状态0是更新 1是更新完成
                    client.set(Constant.APPID_ALPHADOG, COMPANYTAG_ES_STATUS,
                            String.valueOf(tagId), String.valueOf(0));
                }

            }
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
    }
    public void handlerCompanyTagTalent(Set<Integer> idList,int companyId) throws Exception {
        try {
            List<TalentpoolCompanyTagUserRecord> list = new ArrayList<>();
            List<TalentpoolCompanyTag> tagList = talentpoolCompanyTagDao.getCompanyTagByCompanyId(companyId, 0, Integer.MAX_VALUE);
            if (!StringUtils.isEmptyList(tagList)) {
                for (Integer userId : idList) {
                    Response res = profileService.getResource(userId, 0, null);
                    if (res.getStatus() == 0 && StringUtils.isNotNullOrEmpty(res.getData())) {
                        Map<String, Object> profiles = JSON.parseObject(res.getData());
                        for (TalentpoolCompanyTag tag : tagList) {
                            String tagStr = JSON.toJSONString(tag);
                            Map<String, Object> tagMap = JSON.parseObject(tagStr);
                            boolean isflag = companyFilterTagValidation.validateProfileAndComapnyTag(profiles, userId, companyId, tagMap);
                            if (isflag) {
                                TalentpoolCompanyTagUserRecord record = new TalentpoolCompanyTagUserRecord();
                                record.setUserId(userId);
                                record.setTagId(tag.getId());
                                list.add(record);
                            }
                        }
                    }
                }
            }
            if (!StringUtils.isEmptyList(list)) {
                talentpoolCompanyTagUserDao.addAllRecord(list);
                for (Integer userId : idList) {
                    Map<String, Object> result = new HashMap<>();
                    result.put("user_id", userId);
                    client.lpush(Constant.APPID_ALPHADOG,
                            "ES_UPDATE_INDEX_COMPANYTAG_ID", JSON.toJSONString(result));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.info(e.getMessage(),e);
        }
    }



    public int getTagtalentNum(int hrId,int companyId,int tagId) throws TException {
        Map<String,String> params=new HashMap<>();
        int count=talentPoolEntity.valiadteMainAccount(hrId,companyId);
        if(count>0){
            List<Map<String,Object>> hrList=talentPoolEntity.getCompanyHrList(companyId);
            Set<Integer> hrIdList=talentPoolEntity.getIdListByUserHrAccountList(hrList);
            params.put("publisher",talentPoolEntity.convertToString(hrIdList));
            params.put("all_publisher","1");
            params.put("tag_ids","alltalent");
        }else{
            params.put("publisher",hrId+"");
            params.put("tag_ids","talent");
        }
        params.put("hr_id",hrId+"");
        params.put("company_id",companyId+"");
        params.put("hr_account_id",hrId+"");
        params.put("company_tag",tagId+"");
        int totalNum=service.talentSearchNum(params);
        return totalNum;
    }

    //获取企业标签是否正在执行
    public boolean getCompanyTagIsExecute(int tagId){
        String result=client.get(Constant.APPID_ALPHADOG, COMPANYTAG_ES_STATUS,
                String.valueOf(tagId));
        if(StringUtils.isNotNullOrEmpty(result)){
            if(Integer.parseInt(result)>0){
                return false;
            }else{
                return true;
            }

        }
        return false;
    }


    /*
 获取这个人在这家公司下的所有投递
 */
    private List<JobApplicationRecord> getJobApplicationByCompanyIdAndUserId(int companyId,int userId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).and("applier_id",userId).buildQuery();
        List<JobApplicationRecord> list=jobApplicationDao.getRecords(query);
        return list;
    }
    private List<JobApplicationRecord> getJobAppRecommendByCompanyIdAndUserId(int companyId,int userId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).and("applier_id",userId).and(new Condition("recommender_user_id",0, ValueOp.GT)).buildQuery();
        List<JobApplicationRecord> list=jobApplicationDao.getRecords(query);
        return list;
    }

}
