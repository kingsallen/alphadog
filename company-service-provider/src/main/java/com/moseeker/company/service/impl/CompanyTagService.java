package com.moseeker.company.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.talentpooldb.*;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.*;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolCompanyTagUserRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolHrAutomaticTagUserRecord;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserHrAccount;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.util.StringUtils;
import com.moseeker.company.bean.TalentUserHrCompany;
import com.moseeker.company.constant.TalentpoolTagStatus;
import com.moseeker.entity.TalentPoolEntity;
import com.moseeker.entity.biz.CompanyFilterTagValidation;
import com.moseeker.rpccenter.client.ServiceManager;
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
    @Autowired
    private TalentPoolEntity talentPoolEntity;
    @Autowired
    private CompanyFilterTagValidation companyFilterTagValidation;
    @Autowired
    private TalentpoolHrAutomaticTagDao talentpoolHrAutomaticTagDao;
    @Autowired
    private TalentpoolHrAutomaticTagUserDao talentpoolHrAutomaticTagUserDao;
    @Autowired
    private TalentpoolHrTalentDao talentpoolHrTalentDao;
    @Autowired
    private UserHrAccountDao userHrAccountDao;


    SearchengineServices.Iface service = ServiceManager.SERVICEMANAGER.getService(SearchengineServices.Iface.class);


    @CounterIface
    public void handlerCompanyTag(List<Integer> tagIdList, int type,Map<String,Object> map){
        try {
            if (type == TalentpoolTagStatus.TALENT_POOL_DEL_TAG.getValue()) {//删除标签只需要执行删除操作即可
                talentpoolCompanyTagUserDao.deleteByTag(tagIdList);
                this.refrushCompantTag(tagIdList,type,null,KeyIdentifier.COMPANY_TAG_ES_STATUS.toString(),KeyIdentifier.ES_UPDATE_INDEX_COMPANYTAG_ID.toString());
            } else {
                //新增标签，不用调用删除原有表中的标签和人才的对应关系，只需要增加就可以
                //Map<String, Object> map = JSON.parseObject(JSON.toJSONString(DO));//talentpoolCompanyTagDao.getTagById(tagIdList.get(0));
                if (map != null && !map.isEmpty()) {
                    double pageSize=500.0;
                    int totalPage=this.getDataTotalPage(map,pageSize);
                    if(type == TalentpoolTagStatus.TALENT_POOL_UPDATE_TAG.getValue()){
                        talentpoolCompanyTagUserDao.deleteByTag(tagIdList);
                    }
                    if(totalPage == 0){
                        this.refrushCompantTag(tagIdList,type,new ArrayList<>(),KeyIdentifier.COMPANY_TAG_ES_STATUS.toString(),KeyIdentifier.ES_UPDATE_INDEX_COMPANYTAG_ID.toString());
                    }else {
                        for (int i = 1; i <= totalPage; i++) {
                            logger.info("执行第" + i + "页");
                            this.handlerUserIdList(tagIdList, type, map, i, (int)pageSize);
                        }
                    }
                }
            }

        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }

    }
    @CounterIface
    public  void handlerHrAutomaticTag(List<Integer> tagIdList, int type,Map<String,Object> map) throws Exception {
        if (type == TalentpoolTagStatus.TALENT_POOL_DEL_TAG.getValue()) {
            this.deleteHrAutomaticTagUserList(tagIdList);
        } else {
            if (map != null && !map.isEmpty()) {
                map.put("account_type", "1");//查询时因为需要hr账号类型取查询人才库，所以注意
                double pageSize = 500.0;
                int totalPage = this.getDataTotalPage(map, pageSize);
                if (TalentpoolTagStatus.TALENT_POOL_UPDATE_TAG.getValue() == type) {
                    this.deleteHrAutomaticTagUserList(tagIdList);
                }
                if (totalPage == 0) {
                    this.refrushCompantTag(tagIdList, TalentpoolTagStatus.TALENT_POOL_DEL_TAG.getValue(), null, KeyIdentifier.HR_AUTOMATIC_TAG_ES_STATUS.toString(), KeyIdentifier.ES_UPDATE_INDEX_HR_AUTO_ID.toString());
                } else {
                    for (int i = 1; i <= totalPage; i++) {
                        logger.info("执行第" + i + "页");
                        this.handlerHrUserIdList(tagIdList, type, map, i, (int) pageSize);
                    }
                }
            }
        }
    }
    /*
     根据user_user.id列表和公司id处理公司的标签
     */
    public void handlerCompanyTagTalent(Set<Integer> idList,int companyId) throws Exception {

        try {
            logger.info("====================");
            this.handlerHrAutomaticData(idList);
            List<TalentpoolCompanyTagUserRecord> list = new ArrayList<>();
            List<Integer> tagIdList=new ArrayList<>();
            List<TalentpoolCompanyTagUser> deleList=new ArrayList<>();
            List<Map<String, Object>>  tagList = talentpoolCompanyTagDao.getCompanyTagByCompanyId(companyId, 0, Integer.MAX_VALUE);
            if (!StringUtils.isEmptyList(tagList)) {
                for (Integer userId : idList) {
                    logger.info("handlerCompanyTagTalent userId:{}",userId);
                    for (Map<String, Object> tag : tagList) {
                        logger.info("handlerCompanyTagTalent tagId:"+tag.get("id"));
                        TalentpoolCompanyTagUser delRecord=new TalentpoolCompanyTagUser();
                        delRecord.setUserId(userId);
                        delRecord.setTagId((Integer) tag.get("id"));
                        deleList.add(delRecord);
                        if (tag != null && !tag.isEmpty()) {
                            int total=this.getNumByTagAndUser(tag,userId);
                            logger.info("handlerCompanyTagTalent total:"+total);
                            if(total>0){
                                TalentpoolCompanyTagUserRecord record = new TalentpoolCompanyTagUserRecord();
                                record.setUserId(userId);
                                record.setTagId((Integer) tag.get("id"));
                                tagIdList.add((Integer) tag.get("id"));
                                list.add(record);
                            }
                        }
                    }
                }
            }
            if(!StringUtils.isEmptyList(deleList)){
                logger.info("=======需要删除的标签的列表是=========");
                logger.info(JSON.toJSONString(deleList));
                logger.info("===================================");
                talentpoolCompanyTagUserDao.deleteByUserIdAndTagId(deleList);
            }
            /*

             */
            if (!StringUtils.isEmptyList(list)) {
                logger.info("=======需要添加的标签的列表是=========");
                logger.info(list.toString());
                logger.info("===================================");
                talentpoolCompanyTagUserDao.addAllRecord(list);
                for (Integer userId : idList) {
                    this.addRedisRefreshEs(userId,tagIdList, KeyIdentifier.ES_UPDATE_INDEX_COMPANYTAG_ID.toString());
                }
            }
        }catch(Exception e){
            logger.info(e.getMessage(),e);
        }
    }

    /*
     放入redis队列，等待更新
     */
    public void addRedisRefreshEs(int userId,List<Integer> tagIdList,String redisKey){
        Map<String, Object> result = new HashMap<>();
        result.put("user_id", userId);
        result.put("tag_ids",tagIdList);
        client.lpush(Constant.APPID_ALPHADOG,
                redisKey, JSON.toJSONString(result));
        logger.info(" redis 队列中的内容为  result:{}", result);
    }


    //增量添加hr自动标签
    public void handlerUserIdAndHrTag(Set<Integer> userIdList,int hrId,int companyId) throws TException {
        List<TalentpoolHrAutomaticTagUserRecord> list = new ArrayList<>();
        List<Integer> tagIdList=new ArrayList<>();
        List<TalentpoolHrAutomaticTagUserRecord> deleList=new ArrayList<>();
        List<Map<String,Object>> tagList=talentpoolHrAutomaticTagDao.getHrAutomaticTagMapListByHrId( hrId);
        if(!StringUtils.isEmptySet(userIdList)){
            for(Integer userId:userIdList){
                logger.info("handlerUserIdAndHrTag userId:{}",userId);
                if(!StringUtils.isEmptyList(tagList)){
                    for(Map<String,Object> tagMap:tagList){
                        tagMap.put("company_id",companyId);
                        deleList.add(this.getTalentpoolHrAutomaticTagUserRecord((int)tagMap.get("id"),userId));
                        int total=this.getNumByTagAndUser(tagMap,userId);
                        logger.info("===============total====================");
                        logger.info(total+"");
                        logger.info("=====================================");
                        if(total>0){
                          list.add(this.getTalentpoolHrAutomaticTagUserRecord((int)tagMap.get("id"),userId))  ;
                          tagIdList.add((int)tagMap.get("id"));
                        }
                    }
                }

            }
        }
        logger.info("===============tagIdList====================");
        logger.info(JSON.toJSONString(tagIdList));
        logger.info("=====================================");
        logger.info("===============list====================");
        logger.info(list.toString());
        logger.info("=====================================");
        if(!StringUtils.isEmptyList(deleList)){
            talentpoolHrAutomaticTagUserDao.deleteList(deleList);
            logger.info("=========delete success==============");
        }
        if(!StringUtils.isEmptyList(list)){
            int result=talentpoolHrAutomaticTagUserDao.addList(list);
            logger.info("===============TalentpoolHrAutomaticTagUserRecord====================");
            logger.info(result+"");
            logger.info("=====================================");
            for (Integer userId : userIdList) {
                this.addRedisRefreshEs(userId,tagIdList, KeyIdentifier.ES_UPDATE_INDEX_HR_AUTO_ID.toString());
            }
        }
    }
    /*
     组装TalentpoolHrAutomaticTagUserRecord数据
     */
    private TalentpoolHrAutomaticTagUserRecord getTalentpoolHrAutomaticTagUserRecord(int tagId,int userId){
        TalentpoolHrAutomaticTagUserRecord record=new TalentpoolHrAutomaticTagUserRecord();
        record.setTagId(tagId);
        record.setUserId(userId);
        return record;
    }
    //获取企业标签的人才数量
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

    public int getHrTagtalentNum(int hrId,int companyId,int tagId) throws TException {
        Map<String,String> params=new HashMap<>();
        params.put("publisher",hrId+"");
        params.put("tag_ids","talent");
        params.put("hr_id",hrId+"");
        params.put("company_id",companyId+"");
        params.put("hr_account_id",hrId+"");
        params.put("hr_auto_tag",tagId+"");
        int totalNum=service.talentSearchNum(params);
        return totalNum;
    }

    //获取企业标签是否正在执行
    public boolean getCompanyTagIsExecute(int tagId){
        String result=client.get(Constant.APPID_ALPHADOG, KeyIdentifier.COMPANY_TAG_ES_STATUS.toString(),
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
    //获取hr个人自动标签是否在执行
    public boolean  getHtAutoTagIsExcute(int tagId){
        String result=client.get(Constant.APPID_ALPHADOG, KeyIdentifier.HR_AUTOMATIC_TAG_ES_STATUS.toString(),
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
            logger.info("=============开始企业标签====================");
            for (Integer companyId : companyIdSet) {
                this.handlerCompanyTagTalent(userIdset, companyId);
            }
        }catch(Exception e){
            logger.warn(e.getMessage(),e);
        }
    }
    /*
     处理自定义标签
     */
    public void handlerHrAutomaticData(Set<Integer> userIdset){
        try{
            List<TalentUserHrCompany> list=this.getUserTalentCompanyMapData(userIdset);
            //需要获取company_id
            logger.info("============需要处理 的数据是========================");
            logger.info(JSON.toJSONString(list));
            logger.info("=================================================");
            if(!StringUtils.isEmptyList(list)){
                for(TalentUserHrCompany data:list){
                    Set<Integer> userIdList=new HashSet<>();
                    userIdList.add(data.getUserId());
                    logger.info(JSON.toJSONString(userIdList)+"========"+data.getHrId()+"=========="+data.getCompanyId());
                    this.handlerUserIdAndHrTag(userIdList,data.getHrId(),data.getCompanyId());
                }
            }
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
    }
    //获取user和公司之间的关系
    private List<TalentUserHrCompany> getUserTalentCompanyMapData(Set<Integer> userIdset){
        List<TalentpoolHrTalent> list=talentpoolHrTalentDao.getDataByUserId(userIdset);
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        List<Integer> hrIdList=this.getHrIdList(list);
        if(StringUtils.isEmptyList(hrIdList)){
            return null;
        }
        List<UserHrAccount> dataList=userHrAccountDao.getAccountByIdList(hrIdList);
        if(StringUtils.isEmptyList(dataList)){
            return null;
        }
        List<TalentUserHrCompany> result=new ArrayList<>();
        for(TalentpoolHrTalent talent:list){
            int hrId=talent.getHrId();
            int userid=talent.getUserId();
            for(UserHrAccount userHrAccount:dataList){
                if(hrId==userHrAccount.getId()){
                    int companyId=userHrAccount.getCompanyId();
                    TalentUserHrCompany data=new TalentUserHrCompany(userid,hrId,companyId);
                    result.add(data);
                    break;
                }
            }
        }
        return result;
    }

    private List<Integer> getHrIdList( List<TalentpoolHrTalent> list){
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        List<Integer> result=new ArrayList<>();
        for(TalentpoolHrTalent talent:list){
            result.add(talent.getHrId());
        }
        return result;
    }

    /*
     公开时处理非这个hr下的所有的标签
     逻辑：
       1，去掉第二次公开的人才
       2，获取公司下所有的hr信息
       3，查询hr是否从收藏这个人才
       4，根据userid获取需要执行的hr和userid
     */
    public  void  handlerHrAutoTagAddPublic(Set<Integer> userIdSet,int companyId,int hrId) throws TException {
        userIdSet=this.filterAlreadyPub(companyId,userIdSet,1);
        if(!StringUtils.isEmptySet(userIdSet)){
            List<Map<String, Object>> htList=talentPoolEntity.getCompanyHrList(companyId);
            Set<Integer> hrSet = talentPoolEntity.getIdListByUserHrAccountList(htList);
            if(!StringUtils.isEmptySet(hrSet)){
                hrSet.remove(hrId);
                if(!StringUtils.isEmptySet(hrSet)){
                    //获取hr下收藏的人才
                    List<TalentpoolHrTalent> talentList=talentpoolHrTalentDao.getDataByUserIdAndHrId(userIdSet,hrSet);
                    Map<Integer,Set<Integer>> result=new HashMap<>();
                    for(Integer userId:userIdSet){
                        List<Integer> hrIdList=new ArrayList<>();
                        if(!StringUtils.isEmptyList(talentList)){
                            for(TalentpoolHrTalent talent:talentList){
                                if(talent.getUserId()==userId.intValue()){
                                    hrIdList.add(talent.getHrId());
                                }
                            }
                        }
                        for(Integer item:hrSet){
                            if(!hrIdList.contains(item)){
                                if(result.get(item)==null){
                                    Set<Integer> dataSet=new HashSet<>();
                                    dataSet.add(userId);
                                    result.put(item,dataSet);
                                }else{
                                    Set<Integer> dataSet=result.get(item);
                                    dataSet.add(userId);
                                    result.put(item,dataSet);
                                }
                            }
                        }

                    }
                    if(!StringUtils.isEmptyMap(result)){
                        for(Integer key:result.keySet()){
                            Set<Integer> userDataSet=result.get(key);
                            this.handlerUserIdAndHrTag(userDataSet,key,companyId);
                        }
                    }
                }
            }
        }

    }
    /*
     取消公开时处理自动标签
     */
    public void handlerHrAutoTagCancelPublic(Set<Integer> userIdSet,int companyId){
        if(!StringUtils.isEmptySet(userIdSet)){
            List<Map<String, Object>> htList=talentPoolEntity.getCompanyHrList(companyId);
            Set<Integer> hrSet = talentPoolEntity.getIdListByUserHrAccountList(htList);
            if(!StringUtils.isEmptySet(hrSet)){
               talentPoolEntity.handlerHrAutotag(userIdSet,companyId,hrSet);
               for(Integer userId:userIdSet){
                   this.addRedisRefreshEs(userId,new ArrayList<>(),KeyIdentifier.ES_UPDATE_INDEX_HR_AUTO_ID.toString());
               }

            }
        }
    }
    /*
     过滤掉第二次公开的userid
     */
    private Set<Integer> filterAlreadyPub(int companyId,Set<Integer> userIdSet,int num){
        List<Map<String,Object>> list=talentPoolEntity.getPublicTalentGT(companyId,StringUtils.convertSetToList(userIdSet),num);
        if(!StringUtils.isEmptyList(list)){
            Set<Integer> result=new HashSet<>();
            for(Integer userId:userIdSet){
                boolean flag=true;
                for(Map<String,Object> data:list){
                    int id=(int)data.get("user_id");
                    if(id==userId){
                        flag=false;
                    }
                }
                if(flag){
                    result.add(userId);
                }
            }
            return result;
        }
        return userIdSet;
    }

    private List<Integer> getUserIdListPubNum(Set<Integer> userSet,int companyId){

        return null;
    }

    /*
     处理获取的hr自定义的标签的数据
     */
    private void handlerHrUserIdList(List<Integer>tagIdList,int type,Map<String,Object> map,int page,int pageSize) throws TException {
        List<Integer> userIdList=this.getUserIdList(page,pageSize,map);
        this.handlerHrAutoTag(tagIdList,type,userIdList, page);
    }
    /*
     处理标签与数据的对应关系
     */
    private void handlerHrAutoTag(List<Integer>tagIdList,int type,List<Integer> userIdList,int page){
        if(!StringUtils.isEmptyList(userIdList)){
            if(type==TalentpoolTagStatus.TALENT_POOL_ADD_TAG.getValue()){
                List<TalentpoolHrAutomaticTagUserRecord> list = new ArrayList<>();
                for (Integer userId : userIdList) {
                    for(Integer tagId:tagIdList){
                        TalentpoolHrAutomaticTagUserRecord record = new TalentpoolHrAutomaticTagUserRecord();
                        record.setTagId(tagIdList.get(0));
                        record.setUserId(userId);
                        list.add(record);
                    }
                }
                talentpoolHrAutomaticTagUserDao.addList(list);
            }else if(type==TalentpoolTagStatus.TALENT_POOL_UPDATE_TAG.getValue()){

                List<TalentpoolHrAutomaticTagUserRecord> list = new ArrayList<>();
                for (Integer userId : userIdList) {
                    for(Integer tagId:tagIdList){
                        TalentpoolHrAutomaticTagUserRecord record = new TalentpoolHrAutomaticTagUserRecord();
                        record.setTagId(tagIdList.get(0));
                        record.setUserId(userId);
                        list.add(record);
                    }

                }
                talentpoolHrAutomaticTagUserDao.addList(list);
            }
        }
        this.refrushCompantTag(tagIdList,type,userIdList,KeyIdentifier.HR_AUTOMATIC_TAG_ES_STATUS.toString(), KeyIdentifier.ES_UPDATE_INDEX_HR_AUTO_ID.toString(),page);
    }
    /*
     删除标签对应的数据关系
     */
    private  void deleteHrAutomaticTagUserList(List<Integer> tagIdList){
        if(!StringUtils.isEmptyList(tagIdList)){
            talentpoolHrAutomaticTagUserDao.deleteByTagIdList(tagIdList);
        }

    }
    /*
     获取要查询的标签的总页数
     */
    private int getDataTotalPage(Map<String,Object> map,double pageSize) throws TException {
        Map<String,String> params=new HashMap<>();
        for(String key:map.keySet()){
            params.put(key,String.valueOf(map.get(key)));
        }
        params.put("size","0");
        int total=service.queryCompanyTagUserIdListCount(params);
        logger.info("总条数为"+total+"=============================");
        //测试时为100，注意线上为1000
        int totalPage=(int)Math.ceil((double)total/pageSize);
        logger.info("总页数为"+totalPage+"=============================");
        return totalPage;
    }

    private void handlerUserIdList(List<Integer> tagIdList,int type,Map<String,Object> map,int page,int pageSize) throws TException {
        List<Integer> userIdList=this.getUserIdList(page,pageSize,map);
        if (type == TalentpoolTagStatus.TALENT_POOL_ADD_TAG.getValue()) {
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
        } else if (type == TalentpoolTagStatus.TALENT_POOL_UPDATE_TAG.getValue()) {//修改标签需要把表中原有的数据全部删除，
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
        this.refrushCompantTag(tagIdList,type,userIdList,KeyIdentifier.COMPANY_TAG_ES_STATUS.toString(), KeyIdentifier.ES_UPDATE_INDEX_COMPANYTAG_ID.toString());

    }
    private List<Integer> getUserIdList(int page,int pageSize,Map<String,Object> map) throws TException {
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
        return userIdList;
    }
    /*
      通过redis队列，刷新es中标签的索引
     */
    private void refrushCompantTag(List<Integer> tagIdList,int type,List<Integer> userIdList,String statusKey,String redisKey,int page){
        //更新es中tag_id和人才的关系
        if (!StringUtils.isEmptyList(tagIdList)) {
            for (Integer tagId : tagIdList) {
                Map<String, Object> result = new HashMap<>();
                result.put("tag_id", tagId);
                result.put("type", type);
                if(type!=TalentpoolTagStatus.TALENT_POOL_DEL_TAG.getValue()&&!StringUtils.isEmptyList(userIdList)){
                    result.put("user_ids",userIdList );
                }
                if(type==TalentpoolTagStatus.TALENT_POOL_UPDATE_TAG.getValue()){
                    if(page==1){
                        result.put("is_start",true);
                    }else{
                        result.put("is_start",false);
                    }
                }
                client.setNoTime(Constant.APPID_ALPHADOG, statusKey,
                        String.valueOf(tagId), String.valueOf(0));
                client.lpush(Constant.APPID_ALPHADOG,
                        redisKey, JSON.toJSONString(result));
                logger.info(JSON.toJSONString("======================================="));
                logger.info(JSON.toJSONString(result));
                logger.info(JSON.toJSONString("======================================="));
                //将这个的tag置位更新状态0是更新 1是更新完成
            }

        }

    }
    private void refrushCompantTag(List<Integer> tagIdList,int type,List<Integer> userIdList,String statusKey,String redisKey){
        //更新es中tag_id和人才的关系
        if (!StringUtils.isEmptyList(tagIdList)) {
            for (Integer tagId : tagIdList) {
                Map<String, Object> result = new HashMap<>();
                result.put("tag_id", tagId);
                result.put("type", type);
                if(type!=TalentpoolTagStatus.TALENT_POOL_DEL_TAG.getValue()&&!StringUtils.isEmptyList(userIdList)){
                    result.put("user_ids",userIdList );
                }
                client.setNoTime(Constant.APPID_ALPHADOG, statusKey,
                        String.valueOf(tagId), String.valueOf(0));
                client.lpush(Constant.APPID_ALPHADOG,
                        redisKey, JSON.toJSONString(result));
                logger.info(JSON.toJSONString("======================================="));
                logger.info(JSON.toJSONString(result));
                logger.info(JSON.toJSONString("======================================="));
                //将这个的tag置位更新状态0是更新 1是更新完成
            }

        }

    }
    /*
    根据标签内容和user_id查询是否属于这个标签
    */
    private int  getNumByTagAndUser(Map<String,Object> tagMap,int userId) throws TException {
        Map<String, String> params = new HashMap<>();
        for (String key : tagMap.keySet()) {
            params.put(key, String.valueOf(tagMap.get(key)));
        }
        params.put("account_type","1");//查询时因为需要hr账号类型取查询人才库，所以注意
        params.put("size", "0");
        params.put("user_id", String.valueOf(userId));
        int total = service.queryCompanyTagUserIdListCount(params);
        return total;
    }

}
