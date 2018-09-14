package com.moseeker.company.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolCompanyTagDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolCompanyTagUserDao;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTagUser;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTag;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolCompanyTagUserRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.TalentPoolEntity;
import com.moseeker.entity.biz.CompanyFilterTagValidation;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import java.util.*;
import javax.annotation.Resource;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    public void handlerCompanyTag(List<Integer> tagIdList, int type,Map<String,Object> map){
        try {
            if (type == 2) {//删除标签只需要执行删除操作即可
                talentpoolCompanyTagUserDao.deleteByTag(tagIdList);
                this.refrushCompantTag(tagIdList,type,null);
            } else {
                //新增标签，不用调用删除原有表中的标签和人才的对应关系，只需要增加就可以
                //Map<String, Object> map = JSON.parseObject(JSON.toJSONString(DO));//talentpoolCompanyTagDao.getTagById(tagIdList.get(0));
                if (map != null && !map.isEmpty()) {
                    Map<String,String> params=new HashMap<>();
                    for(String key:map.keySet()){
                        params.put(key,String.valueOf(map.get(key)));
                    }
                    params.put("size","0");
                    int total=service.queryCompanyTagUserIdListCount(params);
                    logger.info("总条数为"+total+"=============================");
                    //测试时为100，注意线上为1000
                    int totalPage=(int)Math.ceil((double)total/500.0);
                    logger.info("总页数为"+totalPage+"=============================");
                    /*
                     type==1是更新状态，需要把库中的关心先删除，然后把新的标签和人才关系入库
                     */
                    if(type == 1){
                        talentpoolCompanyTagUserDao.deleteByTag(tagIdList);
                    }else {
                        for (int i = 1; i <= totalPage; i++) {
                            logger.info("执行第" + i + "页");
                            this.handlerUserIdList(tagIdList, type, map, i, 500);
                        }
                    }
                }
            }

        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
    }

    private void handlerUserIdList(List<Integer> tagIdList,int type,Map<String,Object> map,int page,int pageSize) throws TException {
        Map<String, String> params = new HashMap<>();
        for (String key : map.keySet()) {
            params.put(key, String.valueOf(map.get(key)));
        }
        params.put("page_number",page+"");
        params.put("page_size",pageSize+"");
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
                talentpoolCompanyTagUserDao.addTagAndUser(list);
            }
        } else if (type == 1) {//修改标签需要把表中原有的数据全部删除，
            if (!StringUtils.isEmptyList(userIdList)) {
                List<TalentpoolCompanyTagUserRecord> list = new ArrayList<>();
                for (Integer userId : userIdList) {
                    TalentpoolCompanyTagUserRecord record = new TalentpoolCompanyTagUserRecord();
                    record.setTagId(tagIdList.get(0));
                    record.setUserId(userId);
                    list.add(record);
                }
                talentpoolCompanyTagUserDao.addTagAndUser(list);
            }

        }
        this.refrushCompantTag(tagIdList,type,userIdList);

    }
    /*
    刷新公司tag
     */
    private void refrushCompantTag(List<Integer> tagIdList,int type,List<Integer> userIdList){
        //更新es中tag_id和人才的关系
        if (!StringUtils.isEmptyList(tagIdList)) {
            for (Integer tagId : tagIdList) {
                Map<String, Object> result = new HashMap<>();
                result.put("tag_id", tagId);
                result.put("type", type);
                if(type!=2){
                    result.put("user_ids",userIdList );
                }
                client.set(Constant.APPID_ALPHADOG, COMPANYTAG_ES_STATUS,
                        String.valueOf(tagId), String.valueOf(0));
                client.lpush(Constant.APPID_ALPHADOG,
                        "ES_UPDATE_INDEX_COMPANYTAG_ID", JSON.toJSONString(result));
                logger.info(JSON.toJSONString("======================================="));
                logger.info(JSON.toJSONString(result));
                logger.info(JSON.toJSONString("======================================="));
                //将这个的tag置位更新状态0是更新 1是更新完成
            }

        }
    }
    /*
     根据user_user.id列表和公司id处理公司的标签
     */
    public void handlerCompanyTagTalent(Set<Integer> idList,int companyId) throws Exception {
        try {
            List<TalentpoolCompanyTagUserRecord> list = new ArrayList<>();
            List<Integer> tagIdList=new ArrayList<>();
            List<TalentpoolCompanyTagUser> deleList=new ArrayList<>();
            List<TalentpoolCompanyTag> tagList = talentpoolCompanyTagDao.getCompanyTagByCompanyId(companyId, 0, Integer.MAX_VALUE);
            if (!StringUtils.isEmptyList(tagList)) {
                for (Integer userId : idList) {
                    Response res = profileService.getResource(userId, 0, null);
                    if (res.getStatus() == 0 && StringUtils.isNotNullOrEmpty(res.getData())) {
                        Map<String, Object> profiles = JSON.parseObject(res.getData());
                        for (TalentpoolCompanyTag tag : tagList) {
                            TalentpoolCompanyTagUser delRecord=new TalentpoolCompanyTagUser();
                            delRecord.setUserId(userId);
                            delRecord.setTagId(tag.getId());
                            deleList.add(delRecord);
                            String tagStr = JSON.toJSONString(tag);
                            Map<String, Object> tagMap = JSON.parseObject(tagStr);
                            boolean isflag = companyFilterTagValidation.validateProfileAndComapnyTag(profiles, userId, companyId, tagMap);
                            if (isflag) {
                                TalentpoolCompanyTagUserRecord record = new TalentpoolCompanyTagUserRecord();
                                record.setUserId(userId);
                                record.setTagId(tag.getId());
                                tagIdList.add(tag.getId());
                                list.add(record);
                            }
                        }
                    }
                }
            }
            talentpoolCompanyTagUserDao.deleteByUserIdAndTagId(deleList);
            /*

             */
            if (!StringUtils.isEmptyList(list)) {
                talentpoolCompanyTagUserDao.addAllRecord(list);
                for (Integer userId : idList) {
                    Map<String, Object> result = new HashMap<>();
                    result.put("user_id", userId);
                    result.put("tag_ids",tagIdList);
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
     处理简历和企业标签的处理
     */
    public void handlerProfileCompanyIds(Set<Integer> userIdset,Set<Integer> companyIdSet){
        try {
            for (Integer companyId : companyIdSet) {
                this.handlerCompanyTagTalent(userIdset, companyId);
            }
        }catch(Exception e){
            logger.warn(e.getMessage(),e);
        }
    }

}
