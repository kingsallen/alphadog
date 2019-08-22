package com.moseeker.company.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.baseorm.dao.hrdb.HrCompanyConfDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.talentpooldb.*;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyEmailInfo;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyConfRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.*;
import com.moseeker.baseorm.db.talentpooldb.tables.records.*;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserHrAccount;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.thread.ScheduledThread;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.company.bean.*;
import com.moseeker.company.constant.TalentPoolTalentStatus;
import com.moseeker.company.constant.TalentPublicStatus;
import com.moseeker.company.constant.TalentStateEnum;
import com.moseeker.company.constant.TalentpoolTagStatus;
import com.moseeker.company.utils.ValidateTalent;
import com.moseeker.company.utils.ValidateTalentTag;
import com.moseeker.company.utils.ValidateUtils;
import com.moseeker.entity.TalentPoolEmailEntity;
import com.moseeker.entity.TalentPoolEntity;
import com.moseeker.entity.pojo.talentpool.PageInfo;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.struct.ActionForm;
import com.moseeker.thrift.gen.company.struct.TalentpoolCompanyTagDO;
import com.moseeker.thrift.gen.company.struct.TalentpoolHrAutomaticTagDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by zztaiwll on 17/12/4.
 */
@Service
public class TalentPoolService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private SerializeConfig serializeConfig = new SerializeConfig(); // 生产环境中，parserConfig要做singleton处理，要不然会存在性能问题

    public TalentPoolService(){
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }
    private ThreadPool tp = ThreadPool.Instance;

    ScheduledThread thread=ScheduledThread.Instance;
    @Autowired
    private CompanyTagService tagService;
    @Autowired
    private TalentpoolHrTalentDao talentpoolHrTalentDao;
    @Autowired
    private TalentPoolEntity talentPoolEntity;
    @Autowired
    private TalentpoolTagDao talentpoolTagDao;
    @Autowired
    private TalentpoolUserTagDao talentpoolUserTagDao;
    @Autowired
    private TalentpoolCompanyTagUserDao talentpoolCompanyTagUserDao;
    @Autowired
    private TalentpoolCommentDao talentpoolCommentDao;
    @Autowired
    private TalentpoolTalentDao talentpoolTalentDao;
    @Autowired
    private JobApplicationDao jobApplicationDao;
    @Autowired
    private JobPositionDao jobPositionDao;
    @Autowired
    private UserHrAccountDao userHrAccountDao;
    @Autowired
    private TalentpoolApplicationDao talentpoolApplicationDao;
    @Autowired
    private HrCompanyConfDao hrCompanyConfDao;
    @Autowired
    private ValidateTalent validateTalent;
    @Autowired
    private ValidateTalentTag validateTalentTag;
    @Autowired
    private ValidateUtils validateUtils;
    @Autowired
    private TalentpoolPastDao talentpoolPastDao;
    @Autowired
    private TalentpoolCompanyTagDao talentpoolCompanyTagDao;
    @Autowired
    private TalentpoolHrAutomaticTagDao talentpoolHrAutomaticTagDao;
    @Autowired
    private TalentpoolHrAutomaticTagUserDao talentpoolHrAutomaticTagUserDao;
    @Autowired
    private HrCompanyDao hrCompanyDao;
    @Resource(name = "cacheClient")
    private RedisClient redisClient;
    @Autowired
    private TalentPoolEmailEntity talentPoolEmailEntity;

    SearchengineServices.Iface service = ServiceManager.SERVICE_MANAGER.getService(SearchengineServices.Iface.class);


    /*
      修改开启人才库的申请记录
     */
    @CounterIface
    @Transactional
    public Response upsertTalentPoolApplication(int hrId,int companyId,int type){
        int count=this.validateHrAndCompany(hrId,companyId);
        if(count==0){
            return ResponseUtils.fail(1,"此账号不是主账号");
        }
        HrCompanyConfRecord record=this.getHrCompanyConfRecordByCompanyId(companyId);
        if(record==null){
            return ResponseUtils.fail(1,"此公司无配置");
        }
        int result=talentpoolApplicationDao.inserOrUpdateTalentPoolApplication(hrId,companyId,type);
        if(result==0){
            return ResponseUtils.fail(1,"操作失败");
        }
        return ResponseUtils.success("");
    }


    /*
     批量添加人才
     @auth:zzt
     @params: hrId hr编号
              userIdList 用户的编号的集合
              companyId 公司的编号
     @return response(status:0,message:"success,data:[])
             response(status:1,message:"xxxxxx")
    */
    @Transactional
    public Response batchAddTalent(int hrId, Set<Integer> userIdList, int companyId,int isGdpr)throws TException{
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        ValidateTalentBean bean=validateTalent.handlerApplierId(hrId,userIdList,companyId,isGdpr);
        Set<Integer> applierIdList=bean.getUserIdSet();
        Set<Integer> unUseList=bean.getUnUseUserIdSet();
        if(StringUtils.isEmptySet(applierIdList)){
            if(bean.getFlag()==1){
                return ResponseUtils.fail(TalentPoolTalentStatus.TALENT_POOL_NO_PASS_GDPR.getCode(),TalentPoolTalentStatus.TALENT_POOL_NO_PASS_GDPR.getMessage());
            }
            return ResponseUtils.fail(1,"hr无权操作人才");
        }
        List<Map<String,Object>> talentList=talentPoolEntity.getTalentpoolHrTalentByIdList(hrId,applierIdList);
        Set<Integer> unApplierIdList=talentPoolEntity.getIdListByTalentpoolHrTalentList(talentList);
        Set<Integer> idList=talentPoolEntity.filterIdList(applierIdList,unApplierIdList);
        talentPoolEntity.addTalents(idList,hrId,companyId,0);
        Map<String,Object> result=this.handlerBatchTalentResult(unUseList,unApplierIdList,idList,companyId);
        if(result==null||result.isEmpty()){
            return  ResponseUtils.success("");
        }

        thread.startTast(new Runnable(){
            @Override
            public void run() {
                try {
                    tagService.handlerCompanyTagTalent(idList, companyId);
                    tagService.handlerUserIdAndHrTag(idList,hrId,companyId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },80000);

//        tp.startTast(() -> {
//            tagService.handlerCompanyTagTalent(idList, companyId);
//            return 0;
//        });
        return ResponseUtils.success(result);
    }
    /*
     处理所有的加入人才库
     */
    @CounterIface
    public void addAllTalent(int hrId,Map<String,String> params,int companyId,int isGdpr){
        try{
            tp.startTast(() -> {
                int total=service.talentSearchNum(params);
                if(total>0) {
                    int totalPageNum = (int) Math.ceil((double) total / 100);
                    for(int i=1;i<=totalPageNum;i++){
                        if(isGdpr==1){
                            params.put("is_gdpr", 1 + "");
                        }
                        params.put("page_number", i + "");
                        params.put("page_size", 100 + "");
                        try {
                            List<Integer> userIdList = service.getTalentUserIdList(params);
                            if (!StringUtils.isEmptyList(userIdList)) {
                                Set<Integer> userIdSet = this.talentPoolEntity.converListToSet(userIdList);
                                this.batchAddTalent(hrId, userIdSet, companyId,isGdpr);
                            }
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
                return 0;
            });
        }catch(Exception e){

        }


    }


    /*
      批量取消人才
      @auth:zzt
      @params: hrId hr编号
               userIdList 用户的编号的集合
               companyId 公司的编号
      @return response(status:0,message:"success,data:[])
              response(status:1,message:"xxxxxx")
     */
    @Transactional
    public Response batchCancelTalent(int hrId, Set<Integer> userIdList, int companyId,int isGDPR)throws TException{
        //验证hr
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        //验证所有人才是否可以操作
        ValidateTalentBean bean=validateTalent.handlerApplierId(hrId,userIdList,companyId,isGDPR);
        //被操作的人中申请该hr下职位的人
        Set<Integer> applierIdList=bean.getUserIdSet();
        //被操作的人中没有申请该hr下职位的人
        Set<Integer> unUseList=bean.getUnUseUserIdSet();
        //所有人都没有收藏，所以都不能操作
        if(StringUtils.isEmptySet(applierIdList)){
            if(bean.getFlag()==1){
                return ResponseUtils.fail(TalentPoolTalentStatus.TALENT_POOL_NO_PASS_GDPR.getCode(),TalentPoolTalentStatus.TALENT_POOL_NO_PASS_GDPR.getMessage());
            }
            return ResponseUtils.fail(1,"hr无权操作人才");
        }
        //获取hr下的已被收藏的人才
        Set<Integer> idList=validateUtils.getTalentuserIdSet(hrId,applierIdList);
        //在申请了这个hr下职位的人中，没有被收藏的人
        Set<Integer> unApplierIdList=talentPoolEntity.filterIdList(applierIdList,idList);

        if(!StringUtils.isEmptySet(idList)){
            talentPoolEntity.cancleTalents(idList,hrId,companyId,0);
        }
        Map<String,Object> result=this.handlerBatchTalentResult(unUseList,unApplierIdList,idList,companyId);
        if(result==null||result.isEmpty()){
            return  ResponseUtils.success("");
        }
        return ResponseUtils.success(result);
    }
    /*
     全部取消收藏
     */
    public void cancleAllTalent(int hrId, Map<String,String> params, int companyId,int isGdpr){
        try{
            tp.startTast(() -> {
                int total=service.talentSearchNum(params);
                if(total>0) {
                    int totalPageNum = (int) Math.ceil((double) total / 100);
                    for(int i=1;i<=totalPageNum;i++){
                        if(isGdpr==1){
                            params.put("is_gdpr", 1 + "");
                        }
                        params.put("page_number", i + "");
                        params.put("page_size", 100 + "");
                            try {
                                List<Integer> userIdList = service.getTalentUserIdList(params);
                                if (!StringUtils.isEmptyList(userIdList)) {
                                    Set<Integer> userIdSet = this.talentPoolEntity.converListToSet(userIdList);
                                    this.batchCancelTalent(hrId, userIdSet, companyId,isGdpr);
                                }
                            } catch (Exception e) {
                                logger.error(e.getMessage(), e);
                            }

                    }
                }
                return 0;
             });
        }catch(Exception e){

        }
    }

    /*
     批量添加标签
     @auth:zzt
     @params: hrId hr编号
              userIdList 用户的编号的集合
              tagIdList  标签的编号集合
              companyId 公司的编号
     @return response(status:0,message:"success,data:[])
             response(status:1,message:"xxxxxx")
     */
    @Transactional
    public Response addBatchTalentTag(int hrId,Set<Integer> userIdList,Set<Integer> tagIdList,int companyId)throws TException{
        ValidateTagBean validateResult=validateTalentTag.validateAddTag(hrId, userIdList, tagIdList, companyId,0);
        if(validateResult.getStatus()==1){
            return ResponseUtils.fail(validateResult.getStatus(),validateResult.getErrorMessage());
        }
        Set<Integer> idList=validateResult.getIdList();
        Set<Integer> nouseList=validateResult.getNouseList();
        List<TalentpoolUserTagRecord> recordList=new ArrayList<>();
        for(Integer id:idList){
            for(Integer tagId:tagIdList){
                TalentpoolUserTagRecord record=new TalentpoolUserTagRecord();
                record.setUserId(id);
                record.setTagId(tagId);
                recordList.add(record);
            }
        }
        talentpoolUserTagDao.addAllRecord(recordList);
        for(Integer tagId:tagIdList){
            talentpoolTagDao.updateTagNum(tagId,idList.size());
        }
        talentPoolEntity.realTimeUpdate(talentPoolEntity.converSetToList(idList));
        List<Map<String,Object>> hrTagList=validateResult.getHrTagList();
        Set<Integer> userTagIdList=validateResult.getUserTagIdList();
        Map<Integer,Object> usertagMap=handlerUserTagResult(hrTagList,userTagIdList,idList,tagIdList,companyId,1);
        if(usertagMap==null||userIdList.isEmpty()){
            ResponseUtils.fail(1,"不满足添加标签的条件");
        }
        Map<String,Object> result=new HashMap<>();
        result.put("nopower",nouseList);
        result.put("use",usertagMap);
        return ResponseUtils.success(result);
    }
    /*
     将标签打到人才上
     */
    @CounterIface
    public void addAllTalentTag(Map<String,String> params,List<Integer> tagIdList,int companyId,int hrId){
        try{
            tp.startTast(() -> {
                int total=service.talentSearchNum(params);
                if(total>0){
                    int totalPageNum=(int)Math.ceil((double)total/100);
                    Set<Integer> tagIdSet=this.talentPoolEntity.converListToSet(tagIdList);
                    for(int i=1;i<=totalPageNum;i++){

                        params.put("page_number", i + "");
                        params.put("page_size", 100 + "");
                            try {
                                List<Integer> userIdList = service.getTalentUserIdList(params);
                                if (!StringUtils.isEmptyList(userIdList)) {
                                    Set<Integer> userIdSet = this.talentPoolEntity.converListToSet(userIdList);
                                    this.addNewBatchTalentTag(hrId, userIdSet, tagIdSet, companyId);
                                }
                            } catch (Exception e) {
                                logger.error(e.getMessage(), e);
                            }

                    }

                }
                return 0;
             });
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }

    }


    /*

     */

    /*
     批量添加标签先删除所有先前的标签，然后打上新的标签
     @auth:zzt
     @params: hrId hr编号
              userIdList 用户的编号的集合
              tagIdList  标签的编号集合
              companyId 公司的编号
     @return response(status:0,message:"success,data:[])
             response(status:1,message:"xxxxxx")
    */
    @CounterIface
    public Response addNewBatchTalentTag(int hrId,Set<Integer> userIdList,Set<Integer> tagIdList,int companyId)throws TException{
        ValidateTagBean validateResult=validateTalentTag.validateAddTag(hrId, userIdList, tagIdList, companyId,1);
        if(validateResult.getStatus()==1){
            return ResponseUtils.fail(validateResult.getStatus(),validateResult.getErrorMessage());
        }
        Set<Integer> idList=validateResult.getIdList();
        Set<Integer> nouseList=validateResult.getNouseList();
        Set<Integer> userTagIdList=validateResult.getUserTagIdList();
        //先删除所有的标签
        if(!StringUtils.isEmptySet(userTagIdList)){
            List<TalentpoolUserTagRecord> recordList=new ArrayList<>();
            for(Integer id:idList){
                for(Integer tagId:userTagIdList){
                    TalentpoolUserTagRecord record=new TalentpoolUserTagRecord();
                    record.setUserId(id);
                    record.setTagId(tagId);
                    recordList.add(record);
                }
            }
            talentpoolUserTagDao.deleteRecords(recordList);
            talentpoolTagDao.updateTagListNum(userTagIdList,0-idList.size());
        }
        //添加新的标签
        if(!StringUtils.isEmptySet(tagIdList)){
            List<TalentpoolUserTagRecord> recordList=new ArrayList<>();
            for(Integer id:idList){
                for(Integer tagId:tagIdList){
                    TalentpoolUserTagRecord record=new TalentpoolUserTagRecord();
                    record.setUserId(id);
                    record.setTagId(tagId);
                    recordList.add(record);
                }
            }
            talentpoolUserTagDao.addAllRecord(recordList);
            talentpoolTagDao.updateTagListNum(tagIdList,idList.size());
        }
        talentPoolEntity.realTimeUpdate(talentPoolEntity.converSetToList(idList));
        List<Map<String,Object>> hrTagList=validateResult.getHrTagList();
        userTagIdList=tagIdList;
        Map<Integer,Object> usertagMap=handlerUserTagResult(hrTagList,userTagIdList,idList,tagIdList,companyId,1);
        Map<String,Object> result=new HashMap<>();
        result.put("nopower",nouseList);
        result.put("use",usertagMap);
        return ResponseUtils.success(result);
    }
    /*
     批量删除标签
     @auth:zzt
     @params: hrId hr编号
              userIdList 用户的编号的集合
              tagIdList  标签的编号集合
              companyId 公司的编号
     @return response(status:0,message:"success,data:[])
             response(status:1,message:"xxxxxx")
     */
    @CounterIface
    @Transactional
    public Response batchCancelTalentTag(int hrId,Set<Integer> userIdList,Set<Integer> tagIdList,int companyId)throws TException{

        ValidateTagBean validateResult=this.validateCancleTag(hrId, userIdList, tagIdList, companyId);
        if(validateResult.getStatus()==1){
            return ResponseUtils.fail(validateResult.getStatus(),validateResult.getErrorMessage());
        }
        Set<Integer> idList= validateResult.getIdList();
        Set<Integer> nouseList=validateResult.getNouseList();
        List<TalentpoolUserTagRecord> recordList=new ArrayList<>();
        for(Integer id:idList){
            for(Integer tagId:tagIdList){
                TalentpoolUserTagRecord record=new TalentpoolUserTagRecord();
                record.setUserId(id);
                record.setTagId(tagId);
                recordList.add(record);
            }
        }
        talentpoolUserTagDao.deleteRecords(recordList);
        for(Integer tagId:tagIdList){
            talentpoolTagDao.updateTagNum(tagId,0-idList.size());
        }
        talentPoolEntity.realTimeUpdate(talentPoolEntity.converSetToList(idList));
        List<Map<String,Object>> hrTagList=validateResult.getHrTagList();
        Set<Integer> userTagIdList=validateResult.getUserTagIdList();
        Map<Integer,Object> usertagMap=handlerUserTagResult(hrTagList,userTagIdList,idList,tagIdList,companyId,0);
        if(usertagMap==null||userIdList.isEmpty()){
            ResponseUtils.fail(1,"不满足删除标签的条件");
        }
        Map<String,Object> result=new HashMap<>();
        result.put("nopower",nouseList);
        result.put("use",usertagMap);
        return ResponseUtils.successWithoutStringify(result.toString());
    }

    /*
     hr添加标签
     @auth:zzt
     @params: hrId hr编号
              companyId 公司的编号
              name 标签名称
     @return response(status:0,message:"success,data:[])
             response(status:1,message:"xxxxxx")
     */
    @CounterIface
    @Transactional
    public Response addHrTag(int hrId,int companyId,String name)throws TException{
        if(StringUtils.isNullOrEmpty(name)){
            return ResponseUtils.fail(1,"标签名称不能为空");
        }
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        int count=this.validateName(hrId,name);
        if(count>0){
            return ResponseUtils.fail(1,"该标签重名");
        }
        TalentpoolTagRecord record=new TalentpoolTagRecord();
        record.setHrId(hrId);
        record.setName(name);
        record=talentpoolTagDao.addRecord(record);
        return ResponseUtils.success(this.getTalentpoolTagById(record.getId()));
    }



    private Map<String,Object> getTalentpoolTagById(int tagId){
        Query query=new Query.QueryBuilder().where("id",tagId).buildQuery();
        Map<String,Object> result=talentpoolTagDao.getMap(query);
        return result;
    }
    /*
      删除标签
      @auth:zzt
      @params: hrId hr编号
               companyId 公司的编号
               tag_id 标签编号
      @return response(status:0,message:"success,data:[])
             response(status:1,message:"xxxxxx")
     */
    @CounterIface
    @Transactional
    public Response deleteHrTag(int hrId,int companyId,int tagId)throws TException{
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        TalentpoolTagRecord validateRecord=talentPoolEntity.validateTag(hrId,tagId);
        if(validateRecord==null){
            return ResponseUtils.fail(1,"这个标签不属于这个hr");
        }
        TalentpoolTagRecord record=new TalentpoolTagRecord();
        record.setId(tagId);
        talentpoolTagDao.deleteRecord(record);
        //删除所有TalentpoolUserTagRecord记录
        List<TalentpoolUserTagRecord> list=getTalentpoolUserByTagId( tagId);
        if(!StringUtils.isEmptyList(list)){
            talentpoolUserTagDao.deleteRecords(list);
            List<Integer> userIdList=new ArrayList<>();
            //实时更新tag
            for(TalentpoolUserTagRecord record1:list){
                userIdList.add(record1.getUserId());
            }
            talentPoolEntity.realTimeUpdate(userIdList);
        }
        return ResponseUtils.success("");
    }
    /*
      修改标签，不可重复
      @auth:zzt
      @params: hrId hr编号
               companyId 公司的编号
               tag_id 标签编号
               name  标签名称
      @return response(status:0,message:"success,data:[])
             response(status:1,message:"xxxxxx")
     */
    @CounterIface
    @Transactional
    public Response updateHrTag(int hrId,int companyId,int tagId,String name)throws TException{
        if(StringUtils.isNullOrEmpty(name)){
            return ResponseUtils.fail(1,"标签名称不能为空");
        }
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        TalentpoolTagRecord validateRecord=talentPoolEntity.validateTag(hrId,tagId);
        if(validateRecord==null){
            return ResponseUtils.fail(1,"这个标签不属于这个hr");
        }
        int count=this.validateUpdateName(hrId,name,tagId);
        if(count>0){
            return ResponseUtils.fail(1,"该标签重名");
        }
        TalentpoolTagRecord record=new TalentpoolTagRecord();
        record.setName(name);
        record.setId(tagId);
        talentpoolTagDao.updateRecord(record);
        List<TalentpoolUserTagRecord> list=getTalentpoolUserByTagId( tagId);
        if(!StringUtils.isEmptyList(list)){
            //实时更新tag
            List<Integer> userIdList=new ArrayList<>();
            for(TalentpoolUserTagRecord record1:list){
                userIdList.add(record1.getUserId());
            }
            talentPoolEntity.realTimeUpdate(userIdList);
        }
        return ResponseUtils.success(this.getTalentpoolTagById(tagId));
    }
    /*
      查询标签
      @auth:zzt
      @params: hrId      hr编号
               companyId 公司的编号
               pageNum   页号
               pageSize  每页条数
      @return response(status:0,message:"success,data:[])
             response(status:1,message:"xxxxxx")
     */
    @CounterIface
    public Map<String,Object> getAllHrTag(int hrId,int companyId,int pageNum,int pageSize)throws TException{
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            Map<String,Object> result=new HashMap<>();
            result.put("flag",1);
            return result;
        }
        PageInfo info=this.getLimitStart(pageNum,pageSize);
        Map<String,Object> result=this.handleTagData(hrId,pageNum,info.getLimit(),info.getPageSize());
        logger.info("======================");
        logger.info(JSON.toJSONString(result));
        return result;
    }

    @CounterIface
    /*
      添加备注
      @auth:zzt
      @params: hrId      hr编号
               companyId 公司的编号
               userId    用户标号
               content   备注内容
      @return response(status:0,message:"success,data:[])
             response(status:1,message:"xxxxxx")
     */
    public Response addTalentComment(int hrId,int companyId,int userId,String content)throws TException{

        if(StringUtils.isNullOrEmpty(content)){
            return ResponseUtils.fail(1,"该hr的备注内容不能为空");
        }
        if(content.length()>600){
            return ResponseUtils.fail(1,"备注内容需在300字以内");
        }
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        int validate=talentPoolEntity.validateComment(hrId,companyId,userId);
        if(validate==0){
            return ResponseUtils.fail(1,"该hr无权操作此简历");
        }
//        if(!this.validateGDPR(companyId,userId)){
//            return ResponseUtils.fail(1,"该简历无法操作");
//        }
        TalentpoolCommentRecord record=new TalentpoolCommentRecord();
        record.setCompanyId(companyId);
        record.setHrId(hrId);
        record.setUserId(userId);
        record.setContent(content);
        talentpoolCommentDao.addRecord(record);
        Map<String,Object> map=this.getAllComment(companyId,userId);
        if(map==null||map.isEmpty()){
            return ResponseUtils.success("");
        }
        List<Map<String,Object>> list=new ArrayList<>();
        list.add(map);
        list=this.handlerHrCommentData(list);
        talentPoolEntity.realTimeUpdateComment(userId);
        return ResponseUtils.success(list);
    }
    /*
     校验jdpr
     */
    private boolean validateGDPR(int companyId,int userId){
        Set<Integer> userSet=new HashSet<>();
        userSet.add(userId);
        Set<Integer> result=talentPoolEntity.filterGRPD(companyId,userSet);
        if(!StringUtils.isEmptySet(result)){
            return false;
        }
        return true;
    }

    @CounterIface
    public Response addProfileComment(int userId,int accountId, String content)throws TException{

        if(StringUtils.isNullOrEmpty(content)){
            return ResponseUtils.fail(1,"该hr的备注内容不能为空");
        }
        if(content.length()>600){
            return ResponseUtils.fail(1,"备注内容需在300字以内");
        }
        UserHrAccount account = userHrAccountDao.getHrAccount(accountId);
        int validate=talentPoolEntity.validateComment(accountId,account.getCompanyId(),userId);
        if(validate==0){
            return ResponseUtils.fail(1,"该hr无权操作此简历");
        }
        TalentpoolCommentRecord record=new TalentpoolCommentRecord();
        record.setCompanyId(account.getCompanyId());
        record.setHrId(accountId);
        record.setUserId(userId);
        record.setContent(content);
        talentpoolCommentDao.addRecord(record);
        return ResponseUtils.success("");
    }

    /*
      获取人才库首页的统计

      @auth:zzt
      @params: hrId      hr编号
               companyId 公司的编号
               type      查询的类型 0，所有的统计 1，hr公布的人才的数量 2，收藏的人才数量 3，公司下所有被公开的人才数量 4，标签下的人才的数量
      @return response(status:0,message:"success,data:[])
             response(status:1,message:"xxxxxx")
     */
    @CounterIface
    public Response getTalentState(int hrId,int companyId,int type){
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        Map<String,Object> result=new HashMap<>();

        if(type== TalentStateEnum.TALENTPOOL_SEARCH_ALL.getValue()){
            int talentNum=this.getAllHrTalent(hrId);
            int companyPublicNum=talentPoolEntity.getPublicTalentCount(companyId);
            int hrPublicNum=this.getHrPublicTalentCount(hrId);
            int allTalentNum=this.getCompanyTalentCount(companyId);
            List<Map<String,Object>> list=this.getTagByHrNoOrder(hrId,0,Integer.MAX_VALUE);
            result.put("allpublic",companyPublicNum);
            result.put("hrpublic",hrPublicNum);
            result.put("talent",talentNum);
            result.put("tag",list);
            result.put("alltalent",allTalentNum);
            result.put("company_tag",getCompanyTagList(companyId));
            result.put("hr_auto_tag",this.getHrAutoTagList(hrId));
        }else if(type==TalentStateEnum.TALENTPOOL_SEARCH_PUBLIC.getValue()){
            int hrPublicNum=this.getHrPublicTalentCount(hrId);
            result.put("hrpublic",hrPublicNum);
        }else if(type==TalentStateEnum.TALENTPOOL_SEARCH_ALL_TALENT.getValue()){
            int talentNum=this.getAllHrTalent(hrId);
            result.put("talent",talentNum);
        }else if(type==TalentStateEnum.TALENTPOOL_SEARCH_ALL_PUBLIC.getValue()) {
            int companyPublicNum = talentPoolEntity.getPublicTalentCount(companyId);
            result.put("allpublic", companyPublicNum);
        }else if(type==TalentStateEnum.TALENTPOOL_SEARCH_HR_TAG.getValue()){
            List<Map<String,Object>> list=talentPoolEntity.getTagByHr(hrId,0,Integer.MAX_VALUE);
            result.put("tag",list);
        }else if(type==TalentStateEnum.TALENTPOOL_SEARCH_HR_AUTO_TAG.getValue()){
            result.put("hr_auto_tag",getHrAutoTagList(hrId));
        }
        return ResponseUtils.success(result);
    }

    private List<Map<String,Object>> getHrAutoTagList(int hrId){
        List<Map<String,Object>> list=talentpoolHrAutomaticTagDao.getHrAutomaticTagMapListByHrId(hrId);
        return  list;
    }

    //分页获取标签
    @CounterIface
    public TalentTagPOJO getTalentTagByPage(int hrId,int companyId,int page,int pageSize){
        TalentTagPOJO pojo=new TalentTagPOJO();
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            pojo.setFlag(1);
            return pojo;
        }
        PageInfo pageInfo=getLimitStart(page,pageSize);
        pojo= this.getTagData(hrId,page,pageInfo.getLimit(),pageSize);
        return pojo;
    }

    /*
      人才的来源来源
      @auth:zzt
      @params: hrId      hr编号
               companyId 公司的编号
               userId    用户标号
      @return response(status:0,message:"success,data:[])
             response(status:1,message:"xxxxxx")
     */
    @CounterIface
    public Response getUserOrigin(int hrId,int companyId,int userId){
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        Map<String,Object> result=new HashMap<>();
        Map<String,Object> hrMap=handleUpLoadHr(companyId,userId);
        if(hrMap!=null){
            result=hrMap;
        }else{
            result.put("isupload",0);
            int origin=this.getApplicationOrigin(companyId,userId);
            result.put("origin",origin);
        }
        return  ResponseUtils.success(result);
    }

    /*
      删除备注
      @auth:zzt
      @params: hrId      hr编号
               companyId 公司的编号
               userId    用户标号
               comId     备注编号
      @return response(status:0,message:"success,data:[])
             response(status:1,message:"xxxxxx")
     */
    @CounterIface
    public Response delTalentComment(int hrId,int companyId,int comId)throws TException{
        int count=talentPoolEntity.getUserHrCommentCount(comId,hrId);
        if(count==0){
            return ResponseUtils.fail(1,"该备注不属于这个hr下的这个人才");
        }
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        Query query=new Query.QueryBuilder().where("id",comId).buildQuery();
        TalentpoolCommentRecord record1= talentpoolCommentDao.getRecord(query);

        TalentpoolCommentRecord record=new TalentpoolCommentRecord();
        record.setId(comId);
        talentpoolCommentDao.deleteRecord(record);
        if(record1!=null){
            int userId=record1.getUserId();
            talentPoolEntity.realTimeUpdateComment(userId);
        }
        return ResponseUtils.success("");
    }


    /*
      分页获取所有的备注
      @auth:zzt
      @params: hrId      hr编号
               companyId 公司的编号
               userId    用户标号
               pageNum   页码
               pageSize  每页数量
      @return response(status:0,message:"success,data:[])
             response(status:1,message:"xxxxxx")
     */
    @CounterIface
    public Response getAllTalentComment(int hrId,int companyId,int userId,int pageNum,int pageSize){
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        int validate=talentPoolEntity.validateComment(hrId,companyId,userId);
        if(validate==0){
            return ResponseUtils.fail(1,"该hr无权操作此简历");
        }
        Map<String,Object> result=this.handleCommentData(companyId,userId,pageNum,pageSize);
        if(result==null||result.isEmpty()){
            return  ResponseUtils.success("");
        }
        return ResponseUtils.success(result);
    }
    /*
     所有选中的人才公开处理
     */
    @CounterIface
    public void addAllTalentPublic(Map<String,String> params,int companyId,int hrId,int isGdpr){
        try{
            tp.startTast(() -> {
                int validateFlag=validateCompany(companyId);
                if(validateFlag==0){
                    int total=service.talentSearchNum(params);
                    if(total>0){
                        int totalPageNum=(int)Math.ceil((double)total/100);
                        for(int i=1;i<=totalPageNum;i++){
                            if(isGdpr==1){
                                params.put("is_gdpr", 1 + "");
                            }
                            params.put("page_number", i + "");
                            params.put("page_size", 100 + "");
                                try {
                                    List<Integer> userIdList = service.getTalentUserIdList(params);
                                    if (!StringUtils.isEmptyList(userIdList)) {
                                        for(Integer userId:userIdList){
                                            Set<Integer> userIdSet = new HashSet<>();
                                            userIdSet.add(userId);
                                            try {
                                                logger.info("========执行为{}公开的操作=====",JSON.toJSON(userIdSet));
                                                Response res=this.AddbatchPublicTalent(hrId, companyId, userIdSet,isGdpr);
                                                logger.info("========执行为{}公开的操作的结果为{}=====",JSON.toJSON(userIdSet),JSON.toJSONString(res));
                                            }catch(Exception e){
                                                logger.info(e.getMessage(),e);
                                            }
                                        }

                                    }
                                } catch (Exception e) {
                                    logger.error(e.getMessage(), e);
                                }
                        }

                    }
                }
                return 0;
            });
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
    }
    /*
     批量公开
     @auth:zzt
     @params:  hrId      hr编号
               companyId 公司的编号
               userIdList用户标号集合
     @return response(status:0,message:"success,data:[])
             response(status:1,message:"xxxxxx")
     */
//    @UpdateEs(tableName = "talentpool_hr_talent", argsIndex = 2, argsName = "user_id")
    @CounterIface
    public Response AddbatchPublicTalent(int hrId,int companyId,Set<Integer> userIdList,int isGdpr)throws TException{
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        int validateFlag=validateCompany(companyId);
        if(validateFlag==-1){
            return ResponseUtils.fail(1,"该公司不存在");
        }
        if(validateFlag==-2){
            return ResponseUtils.fail(1,"该公司不是付费公司无法使用该功能");
        }
        if(validateFlag==1){
            return ResponseUtils.fail(1,"该公司是付费普通账号，无法使用公开功能");
        }
        int validate=this.validatePublic(hrId,userIdList,isGdpr,companyId);
        if(validate==TalentPublicStatus.TALENT_PUBLIC_IS_ERROR.getCode()){
            return ResponseUtils.fail(TalentPublicStatus.TALENT_PUBLIC_IS_ERROR.getCode(),TalentPublicStatus.TALENT_PUBLIC_IS_ERROR.getMessage());
        }
        if(validate==TalentPublicStatus.TALENT_PUBLIC_HAS_DO.getCode()){
            return ResponseUtils.fail(TalentPublicStatus.TALENT_PUBLIC_HAS_DO.getCode(),TalentPublicStatus.TALENT_PUBLIC_HAS_DO.getMessage());
        }
        if(validate==TalentPublicStatus.TALENT_PUBLIC_NO_PASS_GDPR.getCode()){
            return ResponseUtils.fail(TalentPublicStatus.TALENT_PUBLIC_NO_PASS_GDPR.getCode(),TalentPublicStatus.TALENT_PUBLIC_NO_PASS_GDPR.getMessage());
        }
        List<TalentpoolHrTalentRecord> list=new ArrayList<>();
        for(Integer userId:userIdList){
            TalentpoolHrTalentRecord record=new TalentpoolHrTalentRecord();
            record.setHrId(hrId);
            record.setUserId(userId);
            record.setPublic((byte)1);
            list.add(record);
        }
        talentpoolHrTalentDao.updateRecords(list);
        talentpoolTalentDao.batchUpdateNum(new ArrayList<>(userIdList),companyId,1,0);
        Map<Integer,Object> result=this.handlePublicTalentData(userIdList,companyId);
//        tagService.handlerHrAutoTagCancelPublic(userIdList,companyId);

        talentPoolEntity.realTimePublicUpdate(talentPoolEntity.converSetToList(userIdList));
        thread.startTast(new Runnable(){
            @Override
            public void run() {
                try {
        tagService.handlerHrAutoTagAddPublic(userIdList,companyId,hrId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },80000);
        //处理hr自动标签

        if(result==null||result.isEmpty()){
            return  ResponseUtils.success("");
        }
        return ResponseUtils.success(result);
    }
    /*
     验证公司
     */
    private int validateCompany(int companyId){
        Query query=new Query.QueryBuilder().where("id",companyId).buildQuery();
        HrCompanyRecord record=hrCompanyDao.getRecord(query);
        if(record==null){
            return -1;
        }
        if(record.getType()!=0){
            return -2;
        }
        Query query1=new Query.QueryBuilder().where("company_id",companyId).and("disable",1).and("activation",1).and("account_type",2).buildQuery();
        int count=userHrAccountDao.getCount(query1);
        if(count==0){
            return 0;
        }
        return 1;
    }
    /*
     批量取消公开
     @auth:zzt
     @params:  hrId      hr编号
               companyId 公司的编号
               userIdList用户标号集合
     @return response(status:0,message:"success,data:[])
             response(status:1,message:"xxxxxx")
     */
//    @UpdateEs(tableName = "talentpool_hr_talent", argsIndex = 2, argsName = "user_id")
    @CounterIface
    public Response cancelBatchPublicTalent(int hrId,int companyId,Set<Integer> userIdList)throws TException{
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        int validateFlag=validateCompany(companyId);
        if(validateFlag==-1){
            return ResponseUtils.fail(1,"该公司不存在");
        }
        if(validateFlag==-2){
            return ResponseUtils.fail(1,"该公司不是付费公司无法使用该功能");
        }
        if(validateFlag==1){
            return ResponseUtils.fail(1,"该公司是付费普通账号，无法使用公开功能");
        }
        boolean validate=this.validateCanclePublic(hrId,userIdList);
        if(!validate){
            return ResponseUtils.fail(1,"无法满足操作条件");
        }
        List<TalentpoolHrTalentRecord> list=new ArrayList<>();
        for(Integer userId:userIdList){
            TalentpoolHrTalentRecord record=new TalentpoolHrTalentRecord();
            record.setHrId(hrId);
            record.setUserId(userId);
            record.setPublic((byte)0);
            list.add(record);
        }
        talentpoolHrTalentDao.updateRecords(list);
        talentpoolTalentDao.batchUpdateNum(new ArrayList<>(userIdList),companyId,-1,0);
        talentPoolEntity.handlerPublicTag(userIdList,companyId);
        Map<Integer,Object> result=this.handlePublicTalentData(userIdList,companyId);
        talentPoolEntity.realTimePublicUpdate(talentPoolEntity.converSetToList(userIdList));
        thread.startTast(new Runnable(){
            @Override
            public void run() {
                try {
                    tagService.handlerHrAutoTagCancelPublic(userIdList,companyId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },80000);
        if(result==null||result.isEmpty()){
            return  ResponseUtils.success("");
        }
        return ResponseUtils.success(result);
    }

    /*
     删除所有选中的人才公开处理
     */
    @CounterIface
    public void addAllTalentPrivate(Map<String,String> params,int companyId,int hrId){
        try{
            tp.startTast(() -> {
                int validateFlag=validateCompany(companyId);
                if(validateFlag==0){
                    int total=service.talentSearchNum(params);
                    if(total>0){
                        int totalPageNum=(int)Math.ceil((double)total/100);
                        for(int i=1;i<=totalPageNum;i++){
                            params.put("page_number", i + "");
                            params.put("page_size", 100 + "");
                            try {
                                List<Integer> userIdList = service.getTalentUserIdList(params);
                                if (!StringUtils.isEmptyList(userIdList)) {
                                    for(Integer userId:userIdList){
                                        Set<Integer> userIdSet=new HashSet<>();
                                        userIdSet.add(userId);
                                        logger.info("========执行为{}私有的操作=====",JSON.toJSON(userIdSet));
                                        Response res=this.cancelBatchPublicTalent(hrId,companyId,userIdSet);
                                        logger.info("========执行为{}私有的操作的结果为{}=====",JSON.toJSON(userIdSet),JSON.toJSONString(res));
                                    }

                                }
                            } catch (Exception e) {
                                logger.error(e.getMessage(), e);
                            }
                        }
                    }
                }
                return 0;
            });
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
    }


    /*
     分页获取公司下所有公开
     @auth:zzt
     @params:  hrId      hr编号
               companyId 公司的编号
               pageNum   页码
               pageSize  每页数量
     @return response(status:0,message:"success,data:[])
             response(status:1,message:"xxxxxx")
     */
    @CounterIface
    public Response getCompanyPublic(int hrId,int companyId,int pageNum,int pageSize){
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        Map<String,Object> result=this.handlePublicTalentData(companyId,pageNum,pageSize);
        if(result==null||result.isEmpty()){
            return  ResponseUtils.success("");
        }
        return ResponseUtils.success(result);
    }
    /*
     获取该用户在这个公司下被公开的记录
     @auth:zzt
     @params:  hrId      hr编号
               companyId 公司的编号
               userId    user编号
     @return response(status:0,message:"success,data:[])
             response(status:1,message:"xxxxxx")
     */
    @CounterIface
    public Response getCompanyUserPublic(int hrId,int companyId,int userId){
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        Set<Integer> set=new HashSet<>();
        set.add(userId);
        Map<Integer,Object> map=this.handlePublicTalentData(set,companyId);
        if(map==null||map.isEmpty()){
            return  ResponseUtils.success("");
        }
        List<Object> result= (List<Object>) map.get(userId);
        if(StringUtils.isEmptyList(result)){
            return  ResponseUtils.success("");
        }
        return ResponseUtils.success(result);

    }

    /*
     根据user_id获取这个人在这个公司下的被收藏的记录
     @auth:zzt
     @params:  hrId      hr编号
               companyId 公司的编号
               userId    user编号
     @return response(status:0,message:"success,data:[])
             response(status:1,message:"xxxxxx")
     */
    @CounterIface
    public Response getCompanyTalent(int hrId,int companyId,int userId){
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        Set<Integer> idList=new HashSet<>();
        idList.add(userId);
        List<Map<String,Object>> userHrList=talentPoolEntity.getCompanyHrList(companyId);
        Map<Integer,Set<Map<String,Object>>> hrSet=talentPoolEntity.getBatchAboutTalent(idList,userHrList);
        if(hrSet==null||hrSet.isEmpty()){
            return  ResponseUtils.success("");
        }
        Set<Map<String,Object>> result=hrSet.get(userId);
        if(StringUtils.isEmptySet(result)){
            return  ResponseUtils.success("");
        }
        return ResponseUtils.success(result);
    }

    /*
     获取这个人在这个hr下的所有标签

     */
    @CounterIface
    public  Response getHrUserTag(int hrId,int companyId,int userId){
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        //获取hr下所有的tag
        List<Map<String,Object>> tagList = new ArrayList<>();
        List<Map<String,Object>> hrTagList=talentPoolEntity.getTagByHr(hrId,0,Integer.MAX_VALUE);
        if(hrTagList != null && hrTagList.size()>0){
            //获取hr下所有的tagId
            Set<Integer> hrTagIdList=validateTalentTag.getIdByTagList(hrTagList);
            Set<Integer> tagIdList=validateTalentTag.getUserTagIdList(userId,hrTagIdList);
            List<Map<String,Object>> allTagList=this.getUserTagByUserIdAndTagIdMap(userId,hrTagIdList);
            if(!StringUtils.isEmptyList(allTagList)){
                for(Map<String,Object> map:allTagList){
                    int tagId= (int) map.get("tag_id");
                    for(Map<String,Object> map1:hrTagList){
                        int id= (int) map1.get("id");
                        String name=(String)map1.get("name");
                        if(id==tagId){
                            map.put("name",name);
                            map.put("type","hr_tag");
                        }
                    }
                    tagList.add(map);
                }
            }
        }
        //获取人才的公司标签
        List<Map<String,Object>> companyTagList=talentPoolEntity.getCompanyTagByCompanyId(companyId,0,Integer.MAX_VALUE);
        if(companyTagList!= null && companyTagList.size()>0){
            Set<Integer> companyTagIdList = validateTalentTag.getIdByTagList(companyTagList);
            List<Map<String,Object>> allCompanyTagList=this.getUserCompanyTagByUserIdAndTagIdMap(userId,companyTagIdList);
            if(!StringUtils.isEmptyList(allCompanyTagList)){
                for(Map<String,Object> map:allCompanyTagList){
                    int tagId= (int) map.get("tag_id");
                    for(Map<String,Object> map1:companyTagList){
                        int id= (int) map1.get("id");
                        String name=(String)map1.get("name");
                        if(id==tagId){
                            map.put("name",name);
                            map.put("color",map1.get("color"));
                            map.put("type","company_tag");
                        }
                    }
                    tagList.add(map);
                }
            }
        }
        //添加hr自动标签
        List<TalentpoolHrAutomaticTag> hrAutoTagList=talentpoolHrAutomaticTagDao.getHrAutomaticTagListByHrId(hrId);
        if(!StringUtils.isEmptyList(hrAutoTagList)){
            List<Integer> tagIdList=this.getHrAutoTagIdList(hrAutoTagList);
            if(!StringUtils.isEmptyList(tagIdList)){
                List<TalentpoolHrAutomaticTagUser> dataList=talentpoolHrAutomaticTagUserDao.getDataByTagIdAndUserId(tagIdList,userId);
                if(!StringUtils.isEmptyList(dataList)){
                    for(TalentpoolHrAutomaticTagUser data:dataList){
                        int tagId=data.getTagId();
                        Map<String,Object> tagData=new HashMap<>();
                        for(TalentpoolHrAutomaticTag tag:hrAutoTagList){
                            if(tagId==tag.getId()){
                                String name=tag.getName();
                                tagData.put("name",name);
                                tagData.put("type","hr_auto_tag");
                                tagData.put("hr_id",hrId);
                                tagData.put("tag_id",tagId);
                                tagData.put("user_id",data.getUserId());
                                break;
                            }
                        }
                        tagList.add(tagData);
                    }
                }
            }
        }
        return  ResponseUtils.success(tagList);
    }
    /*
     获取hr自动标签的id
     */
    private List<Integer> getHrAutoTagIdList( List<TalentpoolHrAutomaticTag> hrAutoTagList){
        if(StringUtils.isEmptyList(hrAutoTagList)){
            return null;
        }
        List<Integer> result=new ArrayList<>();
        for(TalentpoolHrAutomaticTag data:hrAutoTagList){
            result.add(data.getId());
        }
        return result;
    }
    /*
     获取user集合下所有的收藏的和公开的hr
     */
    @CounterIface
    public Response getPublicAndHrTalentByUserIdList(int hrId,int companyId,Set<Integer> userIdSet){
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        Map<Integer,Object> result=talentPoolEntity.handlerPublicAndTalent(companyId,userIdSet);
        if(result==null||result.isEmpty()){
            ResponseUtils.success("");
        }
        return ResponseUtils.success(result);
    }
    /*
    @Params:
         company_id 公司id
         type :0职位 1公司
         flag 0标签 1筛选规则
    @user:zzt
     */
    @CounterIface
    public List<TalentpoolPast> getPastPositionOrCompany(int companyId, int type, int flag){
        List<TalentpoolPast> list=talentpoolPastDao.getPastList(companyId,type,flag);
        if(StringUtils.isEmptyList(list)){
            list=new ArrayList<>();
        }
        return list;
    }
    /*
     添加曾任职务和曾任公司
     */
    public int addPastPositionOrCompany(int companyId, int type, int flag,String name){
        if(StringUtils.isNullOrEmpty(name)){
            return -1;
        }
        int result=talentpoolPastDao.upsertPast(companyId,type,flag,name);
        return result;
    }

    public Response getHrAutoTagList(int hrId,int companyId, int pageNumber, int pageSize) throws TException {
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(hrId,companyId);
        if(flag == -1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
        }else if(flag == -2){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
        }
        Map<String, Object> tagListInfo = new HashMap<>();
        PageInfo info = new PageInfo();
        if(flag == 2 || flag == 0) {
            info = this.getLimitStart( pageNumber, pageSize);
        }else{
            if(pageNumber == 0){
                pageNumber = 1;
            }
            if(pageSize == 0){
                pageSize = 8;
            }
            info.setLimit((pageNumber-1)*pageSize);
            info.setPageSize(pageSize);
        }
        List<Map<String,Object>> dataList=talentpoolHrAutomaticTagDao.getHrAutomaticTagMapListByHrIdPage(hrId,info.getLimit(), info.getPageSize());
        int total=talentpoolHrAutomaticTagDao.getHrAutomaticTagCountByHrId(hrId);
        if(!StringUtils.isEmptyList(dataList)){
            List<Map<String, Object>> result=new ArrayList<>();
            for(Map<String,Object> data:dataList){
                Map<String,Object> dataResult=new HashMap<>();
                int tagId=(int)data.get("id");
                int totalNum=tagService.getHrTagtalentNum(hrId,companyId,tagId);
                dataResult.put("person_num",totalNum);
                //从redis获取正在执行
                boolean  isEXecute=tagService.getHtAutoTagIsExcute(tagId);
                dataResult.put("is_execute",isEXecute);
                //此处预估时间统一2h
                dataResult.put("expire_time",2);
                dataResult.put("tag_data",data);
                result.add(dataResult);
            }
            tagListInfo.put("data",result);
        }
        tagListInfo.put("total",total);
        tagListInfo.put("page_number", pageNumber);
        tagListInfo.put("page_size", info.getPageSize());
        return ResponseUtils.success(tagListInfo);
    }
    @CounterIface
    public Response getHrAutoTagListById(int hrId,int companyId, int id) throws TException {
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(hrId,companyId);
        if(flag == -1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
        }else if(flag == -2){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
        }
        Map<String,Object> result=talentpoolHrAutomaticTagDao.getSingleDataById(id);
        Map<String, Object> params = new HashMap<>();
        if(result.get("keywords")!= null && !"".equals((String)result.get("keywords"))){
            List<String> keywords = StringUtils.stringToList((String)result.get("keywords"), ";");
            result.put("keyword_list", keywords);
        }
        boolean  isEXecute=tagService.getHtAutoTagIsExcute(id);
        result.put("is_execute",isEXecute);
        result.put("expire_time",2);
        logger.info(JSON.toJSONString("======================================"));
        logger.info(JSON.toJSONString(result));
        logger.info(JSON.toJSONString("======================================"));
        return ResponseUtils.success(result);
    }
    @CounterIface
    public Response getCompanyTagList(int hrId,int companyId, int page_number, int page_size) throws TException {
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(hrId,companyId);
        if(flag == -1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
        }else if(flag == -2){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
        }else if(flag == -3){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
        }
        PageInfo info = new PageInfo();
        if(flag == 2 || flag == 0) {
            info = this.getLimitStart( page_number, page_size);
        }else{
            if(page_number == 0){
                page_number = 1;
            }
            if(page_size == 0){
                page_size = 8;
            }
            info.setLimit((page_number-1)*page_size);
            info.setPageSize(page_size);
        }
        Map<String, Object> tagListInfo = new HashMap<>();
        List<Map<String, Object>> tagList = talentPoolEntity.handlerCompanyTagBycompanyId(companyId, info.getLimit(), info.getPageSize());
        int count = talentPoolEntity.handlerCompanyTagCountBycompanyId(companyId);
        if(tagList != null && tagList.size()>0){
            List<Map<String, Object>> tagProfileList = talentPoolEntity.handlerTagCountByTagIdList(tagList);

            if(!StringUtils.isEmptyList(tagProfileList)){
                for(Map<String, Object> map:tagProfileList){
                    Map<String, Object> companyTag= (Map<String, Object>) map.get("company_tag");
                    int id=(Integer)companyTag.get("id");
                    //获取企业标签下人数
                    int totalNum=tagService.getTagtalentNum(hrId,companyId,id);
                    map.put("person_num",totalNum);
                    //从redis获取正在执行
                    boolean  isEXecute=tagService.getCompanyTagIsExecute(id);
                    map.put("is_execute",isEXecute);
                    //此处预估时间统一2h
                    map.put("expire_time",2);


                }
            }
            tagListInfo.put("tags", tagProfileList);
        }
        tagListInfo.put("total", count);
        tagListInfo.put("page_number", page_number);
        tagListInfo.put("page_size", info.getPageSize());
        String result=JSON.toJSONString(tagListInfo,serializeConfig);
        return ResponseUtils.successWithoutStringify(result);


    }

    /**
     * 获取分页数据
     *
     * @param page_number 当前页数
     * @param page_size   每页数据量
     * @return  list.get(0) 查询从第几条开始查询 list.get(1) 每页的数据量
     */
    private PageInfo getLimitStart( int page_number, int page_size){
        int limit = 0;
        if(page_number == 0 || page_number == 1){
            limit = 0;
            if(page_size == 0){
                page_size = 7;
            }
        }else{
            if(page_size == 0){
                page_size = 8;
            }
            limit = (page_number-1)*page_size - 1;
        }
        PageInfo info = new PageInfo();
        info.setLimit(limit);
        info.setPageSize(page_size);
        return  info;
    }

    /**
     * 删除企业标签以及和人才之间的关系表，更新ES
     * @param hrId
     * @param companyId
     * @param company_tag_ids
     * @return
     */
    @Transactional
    public Response deleteCompanyTags(int hrId, int companyId, List<Integer> company_tag_ids){
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(hrId,companyId);
        if(flag == -1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
        }else if(flag == -2){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
        }else if(flag == -3){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
        }else if(flag == 1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.TALENT_POOL_ACCOUNT_STATUS);
        }
        int result = talentPoolEntity.deleteCompanyTags(companyId, company_tag_ids);
        try {
            tp.startTast(() -> {
                tagService.handlerCompanyTag(company_tag_ids, 2, null);
                return 0;
            });
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }

        return ResponseUtils.success("");
    }

    private void delRediskey(List<Integer> tagIdList){
        List<Map<String,Object>> list=talentpoolHrAutomaticTagDao.getDataByIdList(tagIdList);
        if(!StringUtils.isEmptyList(list)){
            for(Map<String,Object> data:list){
                int id=(int)data.get("id")  ;
                String name=(String)data.get("name");
                redisClient.del(Constant.APPID_ALPHADOG, KeyIdentifier.TALENTPOOL_HR_AUTOMATIC_TAG_ADD.toString(), id + "", name);
            }
        }
    }

    @Transactional
    public Response deleteHrAutoTags(int hrId, int companyId, List<Integer> tag_ids){
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(hrId,companyId);
        if(flag == -1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
        }else if(flag == -2){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
        }else if(flag == -3){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
        }
        talentpoolHrAutomaticTagDao.deleteByIdList(tag_ids);
        try {
            tp.startTast(() -> {
                tagService.handlerHrAutomaticTag(tag_ids, TalentpoolTagStatus.TALENT_POOL_DEL_TAG.getValue(), null);
                return 0;
            });
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
        this.delRediskey(tag_ids);
        return ResponseUtils.success("");
    }
    /**
     * 获取企业标签信息
     * @param hrId          hr编号
     * @param companyId     公司编号
     * @param company_tag_id 标签编号
     * @return
     */
    public Map<String, Object> getCompanyTagInfo(int hrId, int companyId, int company_tag_id){
        Map<String, Object> params = new HashMap<>();
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(hrId,companyId);
        if(flag == -1){
            params.put("responseStatus", -1);
            return  params;
        }else if(flag == -2){
            params.put("responseStatus", -2);
            return  params;
        }else if(flag == -3){
            params.put("responseStatus", -3);
            return  params;
        }
        Map<String, Object> companyTag = talentPoolEntity.getCompanyTagInfo(companyId, company_tag_id);
        if(companyTag!=null&&!companyTag.isEmpty()){
            boolean  isEXecute=tagService.getCompanyTagIsExecute(company_tag_id);
            companyTag.put("is_execute",isEXecute);
            companyTag.put("expire_time",2);
            //此处预估时间统一2h
            companyTag.put("expire_time",2);
        }
        params.put("responseStatus", 0);
        params.put("data", companyTag);
        return params;
    }

    @Transactional
    public Response addCompanyTag(TalentpoolCompanyTagDO companyTagDO, int hr_id){
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(hr_id,companyTagDO.getCompany_id());
        if(flag == -1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
        }else if(flag == -2){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
        }else if(flag == -3){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
        }else if(flag == 1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.TALENT_POOL_ACCOUNT_STATUS);
        }
        try {
            String info = redisClient.get(Constant.APPID_ALPHADOG, KeyIdentifier.TALENTPOOL_COMPANY_TAG_ADD.toString(), companyTagDO.getCompany_id() + "", companyTagDO.getName());
            if (StringUtils.isNullOrEmpty(info)) {
                redisClient.set(Constant.APPID_ALPHADOG, KeyIdentifier.TALENTPOOL_COMPANY_TAG_ADD.toString(), companyTagDO.getCompany_id() + "", companyTagDO.getName(), "true");
                String result = talentPoolEntity.validateCompanyTalentPoolV3ByTagName(companyTagDO.getName(), companyTagDO.getCompany_id(), companyTagDO.getId());
                if ("OK".equals(result)) {
                    String filterString = talentPoolEntity.validateCompanyTalentPoolV3ByFilter(companyTagDO);
                    if (StringUtils.isNullOrEmpty(filterString)) {
                        int id = talentPoolEntity.addCompanyTag(companyTagDO);
                        List<Integer> idList = new ArrayList<>();
                        idList.add(id);
                        //ES更新
                        try {
                            tp.startTast(() -> {
                                Map<String, Object> map = JSON.parseObject(JSON.toJSONString(companyTagDO));
                                if(companyTagDO.isSetKeyword_list()){
                                    String keyword = StringUtils.listToString(companyTagDO.getKeyword_list(), ";");
                                    map.put("keywords", keyword);
                                }
                                tagService.handlerCompanyTag(idList, 0, map);
                                return 0;
                            });
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                        return ResponseUtils.success("");
                    } else {
                        return ResponseUtils.fail(1, filterString);
                    }
                }
                return ResponseUtils.fail(1, result);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            redisClient.del(Constant.APPID_ALPHADOG, KeyIdentifier.TALENTPOOL_COMPANY_TAG_ADD.toString(), companyTagDO.getCompany_id() + "", companyTagDO.getName());
        }
        return ResponseUtils.fail(1, "请稍后重试");
    }
    /*
     添加hr的自动标签
     */
    @CounterIface
    public Response addHrAutomaticTag(TalentpoolHrAutomaticTagDO data,int companyId ){
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(data.getHr_id(),companyId);
        if(flag == -1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
        }else if(flag == -2){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
        }else if(flag == -3){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
        }
        data.setColor("#FFD060");
        String info = redisClient.get(Constant.APPID_ALPHADOG, KeyIdentifier.TALENTPOOL_HR_AUTOMATIC_TAG_ADD.toString(), data.getHr_id() + "", data.getName());
        if (StringUtils.isNullOrEmpty(info)) {
            try {
            redisClient.setNoTime(Constant.APPID_ALPHADOG, KeyIdentifier.TALENTPOOL_HR_AUTOMATIC_TAG_ADD.toString(), data.getHr_id() + "", data.getName(), "true");
            //todo 这里应该考虑如何解耦,这个方法非常不好，需要和TalentpoolCompanyTagDO解耦，但是先这么做着
                String filterString = talentPoolEntity.validateCompanyTalentPoolV3ByFilter(JSON.parseObject(JSON.toJSONString(data), TalentpoolCompanyTagDO.class));
            if (StringUtils.isNullOrEmpty(filterString)) {
                    int id = this.addHrAutomaticData(data);
                List<Integer> idList = new ArrayList<>();
                idList.add(id);
                //ES更新
                try {
                    tp.startTast(() -> {
                        Map<String, Object> map = JSON.parseObject(JSON.toJSONString(data));
                            if (data.isSetKeyword_list()) {
                            String keyword = StringUtils.listToString(data.getKeyword_list(), ";");
                            map.put("keywords", keyword);
                            map.put("company_id",companyId);
                        }
                        tagService.handlerHrAutomaticTag(idList, TalentpoolTagStatus.TALENT_POOL_ADD_TAG.getValue(), map);
                        return 0;
                    });
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                return ResponseUtils.success("");
                } else {
                    return ResponseUtils.fail(1, filterString);
            }
            }catch(Exception e){
                logger.error(e.getMessage(),e);
                redisClient.del(Constant.APPID_ALPHADOG, KeyIdentifier.TALENTPOOL_HR_AUTOMATIC_TAG_ADD.toString(), data.getHr_id() + "", data.getName());
                return ResponseUtils.fail(1, e.getMessage());
            }
        }
        return ResponseUtils.fail(1,"请不要重复执行");
    }

    private int addHrAutomaticData(TalentpoolHrAutomaticTagDO data){
        TalentpoolHrAutomaticTagRecord record=com.moseeker.baseorm.util.BeanUtils.structToDBAll(data,TalentpoolHrAutomaticTagRecord.class);
        String keyword = StringUtils.listToString(data.getKeyword_list(), ";");
        record.setKeywords(keyword);
        record=talentpoolHrAutomaticTagDao.addRecord(record);
        return record.getId();
    }
    private void updateHrAutomaticData(TalentpoolHrAutomaticTagDO data){
        TalentpoolHrAutomaticTagRecord record=com.moseeker.baseorm.util.BeanUtils.structToDBAll(data,TalentpoolHrAutomaticTagRecord.class);
        String keyword = StringUtils.listToString(data.getKeyword_list(), ";");
        record.setKeywords(keyword);
        talentpoolHrAutomaticTagDao.updateRecord(record);
    }
    /*
     更新hr自动标签
     */
    @CounterIface
    public Response updateHrAutoTag(TalentpoolHrAutomaticTagDO data,int companyId ){
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(data.getHr_id(),companyId);
        if(flag == -1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
        }else if(flag == -2){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
        }else if(flag == -3){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
        }
        if(data.getId()==0){
            ResponseUtils.fail(1,"标签id不能为空");
        }
        data.setColor("#FFD060");
        String result=talentPoolEntity.validateHrTalentPoolV3ByTagName(data.getName(),data.getHr_id(),data.getId());
        if("OK".equals(result)){
            String filterString=talentPoolEntity.validateCompanyTalentPoolV3ByFilter(JSON.parseObject(JSON.toJSONString(data),TalentpoolCompanyTagDO.class));
            String statusString=talentpoolHrAutomaticTagDao.validateTagStatusById(data.getId());
            String resultString=filterString+"   "+statusString;
            if(StringUtils.isNullOrEmpty(resultString)){
                this.updateHrAutomaticData(data);
                List<Integer> idList = new ArrayList<>();
                idList.add(data.getId());
                //ES更新
                try {
                    tp.startTast(() -> {
                        Map<String, Object> tag = this.handlerHrAutoData(data.getId(), data);
                        tag.put("company_id",companyId);
                        tagService.handlerHrAutomaticTag(idList, TalentpoolTagStatus.TALENT_POOL_UPDATE_TAG.getValue(), tag);
                        return 0;
                    });
                }catch(Exception e){
                    logger.error(e.getMessage(),e);
                }
                return  ResponseUtils.success("");
            }else{
                return ResponseUtils.fail(1, resultString);
            }
        }
        return ResponseUtils.fail(1, result);
    }
    @Transactional
    public Response updateCompanyTag(TalentpoolCompanyTagDO companyTagDO, int hr_id){
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(hr_id,companyTagDO.getCompany_id());
        if(flag == -1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
        }else if(flag == -2){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
        }else if(flag == -3){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
        }else if(flag == 1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.TALENT_POOL_ACCOUNT_STATUS);
        }

        String result = talentPoolEntity.validateCompanyTalentPoolV3ByTagName(companyTagDO.getName(), companyTagDO.getCompany_id(), companyTagDO.getId());
        if("OK".equals(result)){
            String filterString = talentPoolEntity.validateCompanyTalentPoolV3ByFilter(companyTagDO);
            String statusString = talentPoolEntity.validateCompanyTalentPoolV3ByStatus(companyTagDO);
            statusString = statusString + filterString;
            if(StringUtils.isNullOrEmpty(statusString)){

                int id = talentPoolEntity.updateCompanyTag(companyTagDO);
                List<Integer> idList = new ArrayList<>();
                idList.add(id);
                //ES更新
                try {
                    tp.startTast(() -> {
                        Map<String, Object> tag = handlerCompanyData(id, companyTagDO);
                        logger.info("==============tag:{}",JSON.toJSONString(tag));
                        tagService.handlerCompanyTag(idList, 1, tag);
                        return 0;
                    });
                }catch(Exception e){
                    logger.error(e.getMessage(),e);
                }
                return  ResponseUtils.success("");
            }else{
                return ResponseUtils.fail(1, statusString);
            }
        }
        return ResponseUtils.fail(1, result);
    }
    private Map<String,Object> handlerCompanyData(int id,TalentpoolCompanyTagDO companyTagDO){
        Map<String,Object> map=getCompanyTagById(id);
        if(map!=null&&!map.isEmpty()){
            combineData(map,companyTagDO);
        }else{
            map=new HashMap<>();
        }
        return map;
    }

    private Map<String,Object> handlerHrAutoData(int id,TalentpoolHrAutomaticTagDO tagData){
        Map<String,Object> map=talentpoolHrAutomaticTagDao.getSingleDataById(id);
        if(!StringUtils.isEmptyMap(map)){
            combineHrAutoData(map,tagData);
        }
        return map;
    }
    /*
      根据标签id获取企业标签的信息
     */
    private Map<String,Object> getCompanyTagById(int id){
        Query query=new Query.QueryBuilder().where("id",id).buildQuery();
        Map<String,Object> map=talentpoolCompanyTagDao.getMap(query);
        return map;
    }
    /*
     处理数据，将新旧数据合并
     */
    private void combineData(Map<String,Object> map,TalentpoolCompanyTagDO companyTagDO){

        if(companyTagDO.isSetOrigins()){
            map.put("origins",companyTagDO.getOrigins());
        }
        if(companyTagDO.isSetWork_years()){
            map.put("work_years",companyTagDO.getWork_years());
        }
        if(companyTagDO.isSetCity_name()){
            map.put("city_name",companyTagDO.getCity_name());
        }
        if(companyTagDO.isSetDegree()){
            map.put("degree",companyTagDO.getDegree());
        }
        if(companyTagDO.isSetPast_position()){
            map.put("past_position",companyTagDO.getPast_position());
        }
        if(companyTagDO.isSetIn_last_job_search_position()){
            map.put("in_last_job_search_position",companyTagDO.getIn_last_job_search_position());
        }
        if(companyTagDO.isSetMin_age()){
            map.put("min_age",companyTagDO.getMin_age());
        }
        if(companyTagDO.isSetMax_age()){
            map.put("max_age",companyTagDO.getMax_age());
        }
        if(companyTagDO.isSetIntention_city_name()){
            map.put("intention_city_name",companyTagDO.getIntention_city_name());
        }
        if(companyTagDO.isSetIntention_salary_code()){
            map.put("intention_salary_code",companyTagDO.getIntention_salary_code());
        }
        if(companyTagDO.isSetSex()){
            map.put("sex",companyTagDO.getSex());
        }
        if(companyTagDO.isSetIs_recommend()){
            map.put("is_recommend",companyTagDO.getIs_recommend());
        }
        if(companyTagDO.isSetCompany_name()){
            map.put("company_name",companyTagDO.getCompany_name());
        }
        if(companyTagDO.isSetIn_last_job_search_company()){
            map.put("in_last_job_search_company",companyTagDO.getIn_last_job_search_company());
        }
    }
    /*
     合并hr自动标签数据
     */
    private void combineHrAutoData(Map<String,Object> map,TalentpoolHrAutomaticTagDO companyTagDO){

        if(companyTagDO.isSetOrigins()){
            map.put("origins",companyTagDO.getOrigins());
        }
        if(companyTagDO.isSetWork_years()){
            map.put("work_years",companyTagDO.getWork_years());
        }
        if(companyTagDO.isSetCity_name()){
            map.put("city_name",companyTagDO.getCity_name());
        }
        if(companyTagDO.isSetDegree()){
            map.put("degree",companyTagDO.getDegree());
        }
        if(companyTagDO.isSetPast_position()){
            map.put("past_position",companyTagDO.getPast_position());
        }
        if(companyTagDO.isSetIn_last_job_search_position()){
            map.put("in_last_job_search_position",companyTagDO.getIn_last_job_search_position());
        }
        if(companyTagDO.isSetMin_age()){
            map.put("min_age",companyTagDO.getMin_age());
        }
        if(companyTagDO.isSetMax_age()){
            map.put("max_age",companyTagDO.getMax_age());
        }
        if(companyTagDO.isSetIntention_city_name()){
            map.put("intention_city_name",companyTagDO.getIntention_city_name());
        }
        if(companyTagDO.isSetIntention_salary_code()){
            map.put("intention_salary_code",companyTagDO.getIntention_salary_code());
        }
        if(companyTagDO.isSetSex()){
            map.put("sex",companyTagDO.getSex());
        }
        if(companyTagDO.isSetIs_recommend()){
            map.put("is_recommend",companyTagDO.getIs_recommend());
        }
        if(companyTagDO.isSetCompany_name()){
            map.put("company_name",companyTagDO.getCompany_name());
        }
        if(companyTagDO.isSetIn_last_job_search_company()){
            map.put("in_last_job_search_company",companyTagDO.getIn_last_job_search_company());
        }
    }
    /*
     处理建简历和企业标签的关系，用于其他服务远程调用的
     */
    public void handlerProfileCompanyTag(Set<Integer> userIdSet,int companyId) throws Exception {
        tagService.handlerCompanyTagTalent(userIdSet,companyId);
    }


    /**
     * 获取简历筛选规则的列表
     * @param hrId          hr编号
     * @param companyId     公司编号
     * @param page_number   当前页数
     * @param page_size     每页数据量
     * @return              筛选列表
     * @throws TException
     */
    @CounterIface
    public Response getProfileFilterList(int hrId,int companyId, int page_number, int page_size) throws TException {
        HrCompanyDO companyDO = talentPoolEntity.getCompanyDOByCompanyIdAndParentId(companyId);
        if(companyDO == null){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_NOT_MU);
        }
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(hrId,companyId);
        if(flag == -1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
        }else if(flag == -2){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
        }else if(flag == -3){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
        }
        PageInfo info = this.getLimitStart( page_number, page_size);
        Map<String, Object> filterListInfo = new HashMap<>();
        List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilter> filterList = talentPoolEntity
                .handlerProfileFiltercompanyId(companyId, info.getLimit(), info.getPageSize());
        int count = talentPoolEntity.handlerProfileFilterCountBycompanyId(companyId);
        List<Map<String, Object>> profileFilterList = new ArrayList<>();
        if(filterList != null && filterList.size()>0){
            profileFilterList = talentPoolEntity.handlerFilterPositionCountByFilterIdList(filterList, companyId);
        }
        filterListInfo.put("filter_data", profileFilterList);
        filterListInfo.put("total", count);
        filterListInfo.put("page_number", page_number);
        filterListInfo.put("page_size", info.getPageSize());
        String result=JSON.toJSONString(filterListInfo,serializeConfig);
        return ResponseUtils.successWithoutStringify(result);
    }

    /**
     * 更新简历筛选规则的标签状态
     * @param hrId          hr编号
     * @param companyId     公司编号
     * @param disable       规则状态
     * @param filter_ids    规则标签
     * @return
     */
    @CounterIface
    public Response handerProfileFilters(int hrId, int companyId, int disable, List<Integer> filter_ids){
        HrCompanyDO companyDO = talentPoolEntity.getCompanyDOByCompanyIdAndParentId(companyId);
        if(companyDO == null){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_NOT_MU);
        }
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(hrId,companyId);
        if(flag == -1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
        }else if(flag == -2){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
        }else if(flag == -3){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
        }else if(flag == 1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.TALENT_POOL_ACCOUNT_STATUS);
        }
        int result = talentPoolEntity.updateProfileFilterStatusByFilterIds(companyId, disable, filter_ids);
        return ResponseUtils.success("");
    }

    /**
     * 获取企业筛选规则信息
     * @param hrId          hr编号
     * @param companyId     公司编号
     * @param filter_id     筛选规则编号
     * @return
     */
    @CounterIface
    public Response getProfileFilterInfo(int hrId, int companyId, int filter_id){
        HrCompanyDO companyDO = talentPoolEntity.getCompanyDOByCompanyIdAndParentId(companyId);
        if(companyDO == null){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_NOT_MU);
        }
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(hrId,companyId);
        if(flag == -1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
        }else if(flag == -2){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
        }else if(flag == -3){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
        }else if(flag == 1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.TALENT_POOL_ACCOUNT_STATUS);
        }
        Map<String, Object> profileFilterInfo = talentPoolEntity.getProfileFilterInfo(companyId, filter_id);

        return ResponseUtils.successWithoutStringify(JSON.toJSONString(profileFilterInfo, serializeConfig));
    }


    @CounterIface
    @Transactional
    public Response addProfileFilter(TalentpoolCompanyTagDO filterDO, List<ActionForm> ActionFormList, List<Integer> positionIdList, int hr_id, int position_total){
        HrCompanyDO companyDO = talentPoolEntity.getCompanyDOByCompanyIdAndParentId(filterDO.getCompany_id());
        if(companyDO == null){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_NOT_MU);
        }
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(hr_id,filterDO.getCompany_id());
        if(flag == -1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
        }else if(flag == -2){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
        }else if(flag == -3){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
        }else if(flag == 1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.TALENT_POOL_ACCOUNT_STATUS);
        }
        try {
            logger.info("addProfileFilter filter:{}", filterDO);
            String info = redisClient.get(Constant.APPID_ALPHADOG, KeyIdentifier.TALENTPOOL_PROFILE_FILTER_ADD.toString(), companyDO.getId() + "", filterDO.getName());
            logger.info("addProfileFilter info:{}", info);
            if (StringUtils.isNullOrEmpty(info)) {
                redisClient.set(Constant.APPID_ALPHADOG, KeyIdentifier.TALENTPOOL_PROFILE_FILTER_ADD.toString(), companyDO.getId() + "", filterDO.getName(), "true");
                logger.info("addProfileFilter redis info:{}", info);
                String result = talentPoolEntity.validateCompanyTalentPoolV3ByFilterName(filterDO.getName(), filterDO.getCompany_id(), filterDO.getId());
                logger.info("addProfileFilter result:{}", result);
                if ("OK".equals(result)) {
                    String filterString = talentPoolEntity.validateCompanyTalentPoolV3ByFilter(filterDO);
                    logger.info("addProfileFilter filterString:{}", filterString);
                    if (StringUtils.isNullOrEmpty(filterString)) {
                        int id = talentPoolEntity.addCompanyProfileFilter(filterDO, ActionFormList, positionIdList, position_total);
                        Map<String, Object> params = new HashMap<>();
                        params.put("id", id);
                        params.put("name",filterDO.getName());
                        return ResponseUtils.success(params);
                    } else {
                        return ResponseUtils.fail(1, filterString);
                    }
                }
                return ResponseUtils.fail(1, result);
            }
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }finally {
            redisClient.del(Constant.APPID_ALPHADOG, KeyIdentifier.TALENTPOOL_PROFILE_FILTER_ADD.toString(), companyDO.getId() + "", filterDO.getName());
        }
        return ResponseUtils.fail(1, "请稍后重试");
    }
    @Transactional
    public Response updateProfileFilter(TalentpoolCompanyTagDO filterDO, List<ActionForm> ActionFormList, List<Integer> positionIdList, int hr_id, int position_total){
        HrCompanyDO companyDO = talentPoolEntity.getCompanyDOByCompanyIdAndParentId(filterDO.getCompany_id());
        if(companyDO == null){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_NOT_MU);
        }
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(hr_id,filterDO.getCompany_id());
        if(flag == -1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
        }else if(flag == -2){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
        }else if(flag == -3){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
        }else if(flag == 1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.TALENT_POOL_ACCOUNT_STATUS);
        }
        String result = talentPoolEntity.validateCompanyTalentPoolV3ByFilterName(filterDO.getName(), filterDO.getCompany_id(), filterDO.getId());
        if("OK".equals(result)){
            String filterString = talentPoolEntity.validateCompanyTalentPoolV3ByFilter(filterDO);
            if(StringUtils.isNullOrEmpty(filterString)){
                int id = talentPoolEntity.updateCompanyProfileFilter(filterDO, ActionFormList, positionIdList, position_total);
                return  ResponseUtils.success("");
            }else{
                return ResponseUtils.fail(1, filterString);
            }
        }
        return ResponseUtils.fail(1, result);
    }

    @CounterIface
    public Response getTalentCountByPositionFilter(int hr_id, int company_id, int position_id) throws TException {
        HrCompanyDO companyDO = talentPoolEntity.getCompanyDOByCompanyIdAndParentId(company_id);
        if(companyDO == null){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_NOT_MU);
        }
        int flag=talentPoolEntity.validateCompanyTalentPoolV3(hr_id, company_id);
        if(flag == -1){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_STATUS_NOT_AUTHORITY);
        }else if(flag == -2){
            return ResponseUtils.fail(ConstantErrorCodeMessage.HR_NOT_IN_COMPANY);
        }else if(flag == -3){
            return ResponseUtils.fail(ConstantErrorCodeMessage.COMPANY_CONF_TALENTPOOL_NOT);
        }
        List<Map<String, Object>> filterMapList = talentPoolEntity.getProfileFilterByPosition(position_id, company_id);
        Map<String, Object> map = new HashMap<>();
        map.put("is_filter", false);
        if(filterMapList != null && filterMapList.size()>0){
            map.put("is_filter", true);
            List<Map<String, String>> filterList = new ArrayList<>();
            for(Map<String, Object> filterMap : filterMapList){
                Map<String, String> params = new HashMap<>();
                if (filterMap != null && !filterMap.isEmpty()) {
                    for (String key : filterMap.keySet()) {
                        params.put(key, String.valueOf(filterMap.get(key)));
                    }
                }
                params.put("hr_id", String.valueOf(hr_id));
                params.put("account_type", String.valueOf(flag));
                filterList.add(params);
            }
            Response res = service.queryProfileFilterUserIdList(filterList, 0, 0);
            if(res.getStatus()==0&&StringUtils.isNotNullOrEmpty(res.getData())&&!"null".equals(res.getData())){
                Map<String,Object> data=JSON.parseObject(res.getData());
                int totalNum=(Integer)data.get("totalNum");
                map.put("num",totalNum);
                HrCompanyEmailInfo info = talentPoolEmailEntity.getHrCompanyEmailInfoByCompanyId(company_id);
                map.put("enable",false);
                if(totalNum <= info.getBalance()){
                    map.put("enable", true);
                }
                String time=redisClient.get(Constant.APPID_ALPHADOG, KeyIdentifier.LAST_SEND_POSITION_INVITE.toString(),
                        String.valueOf(hr_id),String.valueOf(position_id));
                map.put("last_send_time",time);
                return ResponseUtils.success(map);
            }


        }
        return ResponseUtils.success("");
    }
    @CounterIface
    public Map<Integer,String> getUserComment(int companyId,List<Integer> userIdList){
        Map<Integer,String> result=new HashMap<>();
        List<TalentpoolCommentRecord> commentList=this.getTalentpoolCommentList(companyId,userIdList);
        if(!StringUtils.isEmptyList(commentList)){
            result=this.handlerCommentData(commentList);
        }
        return result;
    }
    /*
     根据公司id和user_id列表获取人才备注
     */
    private List<TalentpoolCommentRecord> getTalentpoolCommentList(int companyId,List<Integer> userIdList){
        Query query=new Query.QueryBuilder().where(new Condition("user_id",userIdList.toArray(),ValueOp.IN)).and("company_id",companyId).buildQuery();
        List<TalentpoolCommentRecord> list=talentpoolCommentDao.getRecords(query);
        return list;
    }
    /*
    处理人才备注信息
     */
    private Map<Integer,String> handlerCommentData(List<TalentpoolCommentRecord> dataList){
        Map<Integer,String> result=new HashMap<>();
        if(!StringUtils.isEmptyList(dataList)){
            for(TalentpoolCommentRecord data:dataList){
                int userId=data.getUserId();
                String content=data.getContent();
                if(StringUtils.isEmptyMap(result)){
                    result.put(userId,content);
                }else{
                    if(StringUtils.isNotNullOrEmpty(result.get(userId))){
                        String comments=result.get(userId)+";"+content;
                        result.put(userId,comments);
                    }else{
                        result.put(userId,content);
                    }
                }
            }
        }

        return result;
    }
    //处理批量操作的结果
    private Map<String,Object> handlerBatchTalentResult( Set<Integer> unUseList,Set<Integer>unApplierIdList,Set<Integer> idList ,int companyd){
        List<Map<String,Object>> userHrList=talentPoolEntity.getCompanyHrList(companyd);
        Map<Integer,Set<Map<String,Object>>> unhrSet=talentPoolEntity.getBatchAboutTalent(unUseList,userHrList);
        Map<Integer,Set<Map<String,Object>>> unApplierHrSet=talentPoolEntity.getBatchAboutTalent(unApplierIdList,userHrList);
        Map<Integer,Set<Map<String,Object>>> hrSet=talentPoolEntity.getBatchAboutTalent(idList,userHrList);
        Map<String,Object> result=new HashMap<>();
        Map<String,Object> unhrMap=new HashMap<>();
        Map<String,Object> unApplierHrMap=new HashMap<>();
        Map<String,Object> hrMap=new HashMap<>();
        unhrMap.put("userIds",unUseList);
        unhrMap.put("hrs",unhrSet);
        result.put("nopower",unhrMap);
        unApplierHrMap.put("userIds",unApplierIdList);
        unApplierHrMap.put("hrs",unApplierHrSet);
        result.put("nooperator",unApplierHrMap);
        hrMap.put("userIds",idList);
        hrMap.put("hrs",hrSet);
        result.put("use",hrMap);
        return result;

    }
    /*
     获得标签下的人才数量
     */
    private Map<String,Object> handlerTagTalentNum(int hrId){
        List<Map<String,Object>> list=talentPoolEntity.getTagByHr(hrId,0,Integer.MAX_VALUE);
        Map<String,Object> tagResult=new HashMap<>();
        return tagResult;
    }
    /*


    /*
     处理分页数据
     */
    private Map<String,Object> handleCommentData(int companyId,int userId,int pageNum,int pageSize){
        int count=this.getUserCommentCount(companyId,userId);
        double page=((double)count)/pageSize;
        int total= (int) Math.ceil(page);
        List<Map<String,Object>> list=this.getAllCommentByPage(companyId,userId,pageNum,pageSize);
        list=this.handlerHrCommentData(list);
        Map<String,Object> result=new HashMap<>();
        result.put("page_number",pageNum);
        result.put("page_size",pageSize);
        result.put("total_page",total);
        result.put("total_row",count);
        result.put("data",list);
        return result;
    }

    private List<Map<String,Object>> handlerHrCommentData(List<Map<String,Object>> list){
        Set<Integer> hrIdSet=new HashSet<>();
        if(!StringUtils.isEmptyList(list)){
            for(Map<String,Object> map:list){
                int hrId= (int) map.get("hr_id");
                hrIdSet.add(hrId);
            }
        }
        if(!StringUtils.isEmptySet(hrIdSet)){
            List<Map<String,Object>> userHrList=this.getUserHrAccountList(hrIdSet);
            if(!StringUtils.isEmptyList(userHrList)){
                for(Map<String,Object> map:userHrList){
                    int id= (int) map.get("id");
                    for(Map<String,Object> map1:list){
                        int hrId= (int) map1.get("hr_id");
                        if(id==hrId){
                            map1.put("hr",JSON.toJSONString(map));
                        }
                    }

                }
            }

        }
        return list;
    }

    private List<Map<String,Object>> getUserHrAccountList(Set<Integer> hrIdList){
        Query query=new Query.QueryBuilder().where(new Condition("id",hrIdList.toArray(),ValueOp.IN))
                .and("activation",1).and("disable",1).buildQuery();
        List<Map<String,Object>> list=userHrAccountDao.getMaps(query);
        return list;
    }
    /*
     获取公司下这个人的备注数量
     */
    private int getUserCommentCount(int companyId,int userId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).and("user_id",userId).buildQuery();
        int count=talentpoolCommentDao.getCount(query);
        return count;
    }
    /*
     根据useridlist 和公司获取公司下所有公布useridlist的情况
     */
    private Map<Integer,Object> handlePublicTalentData(Set<Integer> userIds,int companyId){
        List<Map<String,Object>> hrList=talentPoolEntity.getCompanyHrList(companyId);
        Set<Integer> hrIdList=talentPoolEntity.getIdListByUserHrAccountList(hrList);
        if(StringUtils.isEmptySet(hrIdList)){
            return null;
        }
        List<Map<String,Object>> resultList=this.getTalentpoolPublicByHrListAndUserIdList(hrIdList,userIds);
        if(StringUtils.isEmptyList(resultList)){
            return null;
        }
        Map<Integer,Set<Integer>> userHrPublicMap=new HashMap<>();
        for(Integer userId:userIds){
            Set<Integer> set=new HashSet<>();
            for(Map<String,Object> record:resultList){
                int applierId=(int)record.get("user_id");
                int hrId=(int)record.get("hr_id");

                if(userId==applierId){
                    if(userHrPublicMap.get(userId)==null){
                        set.add(hrId);
                        userHrPublicMap.put(userId,set);
                    }else{
                        set=userHrPublicMap.get(userId);
                        set.add(hrId);

                    }
                }
            }
            userHrPublicMap.put(userId,set);
        }
        if(userHrPublicMap==null||userHrPublicMap.isEmpty()){
            return null;
        }
        Map<Integer,Object> result=new HashMap<>();
        for(Integer key:userHrPublicMap.keySet()){
            Set<Integer> hrIdSet=userHrPublicMap.get(key);
            if(StringUtils.isEmptySet(hrIdSet)){
                result.put(key,"");
            }else{
                List<Object> list=new ArrayList<>();
                for(Integer hrId:hrIdSet){
                    for(Map<String,Object> map:hrList){
                        int id=(int)map.get("id");
                        if(id==hrId){
                            list.add(map);
                        }
                    }
                }
                result.put(key,list);

            }

        }
        return result;
    }

    /*
     分页获取数据
     */
    private Map<String,Object> handlePublicTalentData(int companyId,int pageNum,int pageSize){
        int count=talentPoolEntity.getPublicTalentCount(companyId);
        double page=((double)count)/pageSize;
        int total= (int) Math.ceil(page);
        List<Map<String,Object>> list=getPublicTalentByCompanyId(companyId,pageNum,pageSize);
        Map<String,Object> result=new HashMap<>();
        result.put("page_number",pageNum);
        result.put("page_size",pageSize);
        result.put("total_page",total);
        result.put("total_row",count);
        result.put("data",list);
        return result;
    }
    /*
     获取hr下公开的人才
     */
    private List<Map<String,Object>> getTalentpoolHrTalentPublic(int hrId,Set<Integer> userIdList){
        Query query=new Query.QueryBuilder().where("hr_id",hrId).and(new Condition("user_id",userIdList.toArray(),ValueOp.IN))
                .and("public",1).buildQuery();
        List<Map<String,Object>> list=talentpoolHrTalentDao.getMaps(query);
        return list;
    }
    /*
      获取公开人才的信息
     */
    private List<Map<String,Object>> getTalentpoolPublicByHrListAndUserIdList(Set<Integer> hrIdList,Set<Integer> userIdList){
        if(StringUtils.isEmptySet(hrIdList)||StringUtils.isEmptySet(userIdList)){
            return null;
        }
        Query query=new Query.QueryBuilder().where(new Condition("hr_id",hrIdList.toArray(),ValueOp.IN)).and(new Condition("user_id",userIdList.toArray(),ValueOp.IN))
                .and("public",1).buildQuery();
        List<Map<String,Object>> list=talentpoolHrTalentDao.getMaps(query);
        return list;
    }


    /*
     获取公司下所有的hr的公开的人才
     */
    private List<Map<String,Object>> getTalentpoolPublicByHrList(Set<Integer> hrIdList){
        Query query=new Query.QueryBuilder().where(new Condition("hr_id",hrIdList.toArray(),ValueOp.IN))
                .and("public",1).buildQuery();
        List<Map<String,Object>> list=talentpoolHrTalentDao.getMaps(query);
        return list;
    }
    /*
      验证是否可以公开
     */
    private  int validatePublic(int hrId,Set<Integer> userIdList,int isGdpr,int companyId){
        List<Map<String,Object>> list=talentPoolEntity.getTalentpoolHrTalentByIdList(hrId,userIdList);
        if(!StringUtils.isEmptyList(list)&&list.size()==userIdList.size()){
            for(Map<String,Object> map:list){
                byte ispublic= (byte) map.get("public");
                if(ispublic==1){
                    return TalentPublicStatus.TALENT_PUBLIC_HAS_DO.getCode();
                }
            }
            if(isGdpr==1){
                Set<Integer> idList=talentPoolEntity.filterGRPD(companyId,userIdList);
                if(StringUtils.isEmptySet(idList)||idList.size()!=userIdList.size()){
                    return TalentPublicStatus.TALENT_PUBLIC_NO_PASS_GDPR.getCode();
                }
            }
            return 1;
        }
        return TalentPublicStatus.TALENT_PUBLIC_IS_ERROR.getCode();
    }

    /*
     处理hr下分页获取人才数据
     */
    private Map<String,Object> handleTagData(int hrId,int pageNum,int pageFrom,int pageSize){
        int count=this.getTagByHrCount(hrId);
        double page=((double)count)/pageSize;
        int total= (int) Math.ceil(page);
//        List<Map<String,Object>> hrTagList=talentPoolEntity.getTagByHr(hrId,pageNum,pageSize);
        List<TalentpoolTag> hrTagList=talentpoolTagDao.getTagByPage(hrId,pageFrom,pageSize);
        Map<String,Object> result=new HashMap<>();
        result.put("page_number",pageNum);
        result.put("page_size",pageSize);
        result.put("total_page",total);
        result.put("total_row",count);
        result.put("data",hrTagList);
        return result;
    }

    private TalentTagPOJO getTagData(int hrId,int pageNum, int pageForm, int pageSize){
        int count=this.getTagByHrCount(hrId);
        List<TalentpoolTag> hrTagList=talentpoolTagDao.getTagByPage(hrId,pageForm,pageSize);
        TalentTagPOJO talentTagPOJO=new TalentTagPOJO();
        talentTagPOJO.setPage_number(pageNum);
        talentTagPOJO.setPage_size(pageSize);
        talentTagPOJO.setTags(hrTagList);
        talentTagPOJO.setTotal(count);
        return talentTagPOJO;
    }

    /*
     验证是否可以取消公开
     */
    private boolean validateCanclePublic(int hrId,Set<Integer> userIdList){
        List<Map<String,Object>> list=this.getTalentpoolHrTalentPublic(hrId,userIdList);
        if(!StringUtils.isEmptyList(list)&&list.size()==userIdList.size()){
            return true;
        }
        return false;
    }
    /*
     查询该hr下标签名字的数量
     */
    private int validateName(int hrId,String name){
        Query query=new Query.QueryBuilder().where("hr_id",hrId).and("name",name).buildQuery();
        int count=talentpoolTagDao.getCount(query);
        return count;
    }

    /*
     修改是查询重名
     */
    private int validateUpdateName(int hrId,String name,int tagId){

        Query query=new Query.QueryBuilder().where("hr_id",hrId).and("name",name).and(new Condition("id",tagId,ValueOp.NEQ)).buildQuery();
        int count=talentpoolTagDao.getCount(query);
        return count;

    }

    /*
     根据tagid获取所有TalentpoolUserTagRecord记录
     */
    private List<TalentpoolUserTagRecord> getTalentpoolUserByTagId(int tagId ){
        Query query=new Query.QueryBuilder().where("tag_id",tagId).buildQuery();
        List<TalentpoolUserTagRecord> list=talentpoolUserTagDao.getRecords(query);
        return list;
    }


    /*
       处理标签的添加和删除后返回的数据
       type=1是添加的情况
       type=2是删除的情况
       本处采用一个接口，所有都视为添加

     */
    private Map<Integer,Object> handlerUserTagResult( List<Map<String,Object>> hrTagList,Set<Integer> userTagIdList,Set<Integer> idList,Set<Integer> tagIdList,int companyId,int type){
        Map<Integer,Object> result=new HashMap<>();
        if(!StringUtils.isEmptyList(hrTagList)){
            List<Map<String,Object>> handlerTag=this.getTagData(tagIdList);
            List<Map<String,Object>> resultList=new ArrayList<>();
            if(type==1){
                if(StringUtils.isEmptySet(userTagIdList)){
                    resultList=handlerTag;
                }else {
                    for (Integer tagId : userTagIdList) {
                        for (Map<String,Object> record : hrTagList) {
                            int id=(int)record.get("id");
                            if (tagId ==id ) {
                                resultList.add(record);
                                break;
                            }
                        }
                    }
                }

            }else{
                if(!StringUtils.isEmptySet(userTagIdList)){
                    userTagIdList.removeAll(tagIdList);
                    if(!StringUtils.isEmptySet(userTagIdList)){
                        for (Integer tagId : userTagIdList) {
                            for (Map<String,Object> record : hrTagList) {
                                int id=(int)record.get("id");
                                if (tagId ==id ) {
                                    resultList.add(record);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            for(Integer userId:idList){
                result.put(userId,resultList);
            }
        }
        this.handlerUserCompanyTagData(result,companyId,idList);
        return result;
    }
    /*
        获取被打标签的人在该公司该hr下的企业标签，母账号看到所有，子账号只能看到自己人才下的人才的企业标签
        */
    private void  handlerUserCompanyTagData(Map<Integer,Object> usertagMap,int companyId,Set<Integer> idList){
        //获取人才和企业标签的关系，即这个人彩霞所哟肚饿企业标签（包含其他公司的，我们所做的就是把所有标签中的属于该公司的标签筛选出来）
        List<TalentpoolCompanyTagUser> userTagList=this.getUserCompanyTagList(idList);
        List<UserCompanyTagBean> list=this.getUserCompanyTagList(userTagList,idList);
        Set<Integer> tagIdSet=this.getCompanyTagIdSet(userTagList);
        if(!StringUtils.isEmptyList(list)&&!StringUtils.isEmptySet(tagIdSet)){
            List<Map<String,Object>> companyTagList=this.getCompanyTagList(companyId,tagIdSet);
            if(!StringUtils.isEmptyList(companyTagList)){
                for(UserCompanyTagBean tagBean:list){
                    int userId=tagBean.getUserId();
                    //根据userid和companyTag.id的关系，取出所有在这公司下这个人才下的企业标签
                    List<Map<String,Object>> tagList=new ArrayList<>();
                    Set<Integer> userTagIdSet=tagBean.getCompanytagidList();
                    if(!StringUtils.isEmptySet(userTagIdSet)){
                        for(Map<String,Object> map:companyTagList){
                            int id=(int)map.get("id");
                            for(Integer tagId:userTagIdSet){
                                if(id==tagId){
                                    tagList.add(map);
                                    break;
                                }
                            }
                        }
                        //将这个人才下的企业标签塞到他的人才库标签列表下
                        if(!StringUtils.isEmptyList(tagList)){
                            for(Integer key: usertagMap.keySet()){
                                if(userId==key){
                                    List<Map<String,Object>> tagMapList= (List<Map<String, Object>>) usertagMap.get(key);
                                    if(tagMapList==null){
                                        tagMapList=new ArrayList<>();
                                    }
                                    tagMapList.addAll(tagList);
                                    break;
                                }

                            }
                        }

                    }
                }
            }
        }
    }
    /*
     根绝tagId获取所有有效的companytag
     */
    private List<Map<String,Object>> getCompanyTagList(int companyId,Set<Integer> tagIdSet ){
        Query query=new Query.QueryBuilder().where("company_id",companyId)
                .and(new Condition("id",tagIdSet.toArray(),ValueOp.IN))
                .and("disable",1).orderBy("update_time",Order.DESC).buildQuery();
        List<Map<String,Object>> list=talentpoolCompanyTagDao.getMaps(query);
        return list;
    }
    /*
     获取人才和标签的关系表
     */
    private List<TalentpoolCompanyTagUser> getUserCompanyTagList(Set<Integer> idList){
        if(StringUtils.isEmptySet(idList)){
            return null;
        }
        List<TalentpoolCompanyTagUser> tagList=talentpoolCompanyTagUserDao.getTagByUserIdSet(idList);
        if(StringUtils.isEmptyList(tagList)){
            return null;
        }
        return tagList;
    }
    /*
     获取tagIdSet
     */
    private Set<Integer> getCompanyTagIdSet(List<TalentpoolCompanyTagUser> tagList){
        if(StringUtils.isEmptyList(tagList)){
            return null;
        }
        Set<Integer> tagIdset=new HashSet<>();
        for(TalentpoolCompanyTagUser tagUser: tagList){
            tagIdset.add(tagUser.getTagId());
        }
        return tagIdset;
    }
    /*
     获取UserCompanyTagBean
     */
    private List<UserCompanyTagBean>  getUserCompanyTagList(List<TalentpoolCompanyTagUser> tagList,Set<Integer> idList){
        if(StringUtils.isEmptyList(tagList)||StringUtils.isEmptySet(idList)){
            return null;
        }
        List<UserCompanyTagBean> result=new ArrayList<>();
        for(Integer userId:idList){
            UserCompanyTagBean bean=new UserCompanyTagBean();
            bean.setUserId(userId);
            Set<Integer> tagIdset=new HashSet<>();
            for(TalentpoolCompanyTagUser tagUser:tagList){
                int id=tagUser.getUserId();
                if(id==userId){
                    tagIdset.add(tagUser.getTagId());
                }
            }
            bean.setCompanytagidList(tagIdset);
            result.add(bean);
        }
        return result;
    }
    /*
     根据tagid的集合获取tag信息
     */
    private List<Map<String,Object>> getTagData(Set<Integer> tagIdList){
        Query query=new Query.QueryBuilder().where(new Condition("id",tagIdList.toArray(),ValueOp.IN)).orderBy("update_time",Order.DESC).buildQuery();
        List<Map<String,Object>> list=talentpoolTagDao.getMaps(query);
        return list;
    }
    /*
       校验批量删除标签
      */
    private ValidateTagBean validateCancleTag(int hrId,Set<Integer> userIdList,Set<Integer> tagIdList,int companyId){
        ValidateTagBean result=new ValidateTagBean();
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            result.setStatus(1);
            result.setErrorMessage("该hr不属于该company_id");
            return result;
        }
        ValidateCommonBean validateResult=validateTalentTag.validateUserIdTag(hrId,userIdList,tagIdList,companyId);
        Set<Integer> idList=validateResult.getUseId();
        if(StringUtils.isEmptySet(idList)){
            result.setStatus(1);
            result.setErrorMessage("该无权操作这些人才");
            return result;
        }
        ValidateTagBean validateTag=validateTalentTag.validateTag(idList,hrId);
        int flagTag= validateTag.getStatus();
        if(flagTag==1){
            result.setStatus(1);
            result.setErrorMessage("不满足批量操作条件");
            return result;
        }
        List<Map<String,Object>> hrTagList=validateTag.getHrTagList();
        Set<Integer> userTagIdList=validateTag.getUserTagIdList();
        boolean validateOperTag=this.validateCancleOperatorTag(validateTalentTag.getIdByTagList(hrTagList),tagIdList,userTagIdList);
        if(!validateOperTag){
            result.setStatus(1);
            result.setErrorMessage("操作的标签不是hr定义的标签");
            return result;
        }
        result.setHrTagList(hrTagList);
        result.setIdList(idList);
        result.setUserTagIdList(userTagIdList);
        result.setNouseList(validateResult.getUnuseId());
        return result;
    }




    /*
        验证添加标签是否符合操作条件
        */
    private boolean validateCancleOperatorTag(Set<Integer> hrTagIdList,Set<Integer> tagIdList, Set<Integer> userTagIdList){
        if(StringUtils.isEmptySet(hrTagIdList)||StringUtils.isEmptySet(userTagIdList)){
            return false;
        }
        for(Integer id:tagIdList){
            if(!hrTagIdList.contains(id)) {
                return false;
            }
        }
        if(!StringUtils.isEmptySet(userTagIdList)){
            for(Integer id:tagIdList){
                if(!userTagIdList.contains(id)){
                    return false;
                }
            }
        }else{
            return false;
        }
        return true;
    }


    private List<Map<String,Object>> getTagByHrNoOrder(int hrId,int pageNum,int pageSize){
        Query query=new Query.QueryBuilder().where("hr_id",hrId)
                .setPageNum(pageNum).setPageSize(pageSize)
                .orderBy("update_time",Order.DESC)
                .buildQuery();
        List<Map<String,Object>> list= talentpoolTagDao.getMaps(query);
        return list;
    }

    /*
      查询hr下所有的标签
     */
    private int getTagByHrCount(int hrId){
        Query query=new Query.QueryBuilder().where("hr_id",hrId).buildQuery();
        int count= talentpoolTagDao.getCount(query);
        return count;
    }



    /*
       获取一个人才在这个hr下拥有的标签map
    */
    private List<Map<String,Object>> getUserTagByUserIdAndTagIdMap(int userId,Set<Integer> tagIdList){
        if(StringUtils.isEmptySet(tagIdList)){
            return null;
        }
        Query query=new Query.QueryBuilder().where("user_id",userId).and(new Condition("tag_id",tagIdList.toArray(),ValueOp.IN)).orderBy("create_time",Order.DESC).buildQuery();
        List<Map<String,Object>> list=talentpoolUserTagDao.getMaps(query);
        return list;
    }

    /*
       获取一个人才在这个公司下拥有的标签map
    */
    private List<Map<String,Object>> getUserCompanyTagByUserIdAndTagIdMap(int userId,Set<Integer> tagIdList){
        if(StringUtils.isEmptySet(tagIdList)){
            return null;
        }
        Query query=new Query.QueryBuilder().where("user_id",userId)
                .and(new Condition("tag_id",tagIdList.toArray(),ValueOp.IN))
                .buildQuery();
        List<Map<String,Object>> list=talentpoolCompanyTagUserDao.getMaps(query);
        return list;
    }




    /*
     查看是否有记录
     */
    private int getTalentpoolHrTalentCount(int userId,int hrId){
        Query query=new Query.QueryBuilder().where("user_id",userId).and("hr_id",hrId).buildQuery();
        int count=talentpoolHrTalentDao.getCount(query);
        return count;
    }
    /*
      验证是否可操作
     */
    private Response validateHrAndUser(int hrId, int userId, int companyId){
        int valdateNum=talentPoolEntity.validateHrTalent(hrId,userId,companyId);
        if(valdateNum==2){
            return ResponseUtils.fail(1,"hr不属于本公司无法操作");
        }else if(valdateNum==3){
            return ResponseUtils.fail(1,"所有人未投递过此hr，无法操作");
        }
        return null;
    }


    /*
      获取公司下所有的人才
     */
    private List<Map<String,Object>> getPublicTalentByCompanyId(int companyId,int pageNum,int pageSize){
        Query query=new Query.QueryBuilder().where("company_id",companyId).and(new Condition("public_num",0,ValueOp.GT))
                .setPageNum(pageNum).setPageSize(pageSize).buildQuery();
        List<Map<String,Object>> list=talentpoolTalentDao.getMaps(query);
        return list;
    }

    /*
    获取公司下所有人才
     */
    private int getCompanyTalentCount(int companyId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).buildQuery();
        int count=talentpoolTalentDao.getCount(query);
        return count;
    }
    /*
     获取hr下所有的收藏数量
     */
    private int getAllHrTalent(int hrId){
        Query query=new Query.QueryBuilder().where("hr_id",hrId).buildQuery();
        int count=talentpoolHrTalentDao.getCount(query);
        return count;
    }
    /*
     获取hr下公开的人才数量
     */
    private int getHrPublicTalentCount(int hrId){
        Query query=new Query.QueryBuilder().where("hr_id",hrId).and("public",1).buildQuery();
        int count=talentpoolHrTalentDao.getCount(query);
        return count;
    }




    /*
     是否是上传
     */
    private int isUploadTalent(int companyId,int userId){
        Query query=new Query.QueryBuilder().where("upload",1).and("company_id",companyId).and("user_id",userId).buildQuery();
        int count=talentpoolTalentDao.getCount(query);
        return count;
    }
    /*
     获取用户来源
     */
    private int getApplicationOrigin(int companyId,int userId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).and("applier_id",userId).buildQuery();
        JobApplicationRecord  record=jobApplicationDao.getRecord(query);
        if(record==null){
            return -1;
        }
        return record.getOrigin();
    }
    /*
      处理上传人的接口
     */
    private Map<String,Object> handleUpLoadHr(int companyId,int userId){
        TalentpoolTalentRecord record=this.getTalentUpload(companyId,userId);
        if(record==null){
            return null;
        }
        Map<String,Object> result=new HashMap<>();
        result.put("isupload",1);
        TalentpoolHrTalentRecord hrTalent=this.getUploadHr(userId);
        if(hrTalent==null){
            return null;
        }
        result.put("hr",hrTalent.getHrId());
        return result;
    }
    /*
    获取上传talent的人的信息
     */
    private TalentpoolTalentRecord getTalentUpload(int companyId,int userId){
        Query query=new Query.QueryBuilder().where("upload",1).and("company_id",companyId).and("user_id",userId).buildQuery();
        TalentpoolTalentRecord record=talentpoolTalentDao.getRecord(query);
        return record;
    }
    /*
     根据上传的user_id获取上传的hr
     */
    private TalentpoolHrTalentRecord getUploadHr(int userId){
        Query query=new Query.QueryBuilder().where("user_id",userId).buildQuery();
        TalentpoolHrTalentRecord record=talentpoolHrTalentDao.getRecord(query);
        return record;
    }
    /*
     获取这个人在这家公司下所有的备注
      */
    private Map<String,Object> getAllComment(int companyId,int userId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).and("user_id",userId).orderBy("create_time", Order.DESC).buildQuery();
        Map<String,Object> list=talentpoolCommentDao.getMap(query);
        return list;
    }
    /*
     分页获取这个人在这家公司下所有的备注
     */
    private List<Map<String,Object>> getAllCommentByPage(int companyId,int userId,int pageNum,int pageSize){
        Query query=new Query.QueryBuilder().where("company_id",companyId).and("user_id",userId)
                .setPageNum(pageNum).setPageSize(pageSize).orderBy("create_time",Order.DESC).buildQuery();
        List<Map<String,Object>> list=talentpoolCommentDao.getMaps(query);
        return list;
    }



    /*
    获取此账号是不是此公司的主账号
    */
    private int validateHrAndCompany(int hrId,int companyId){
        Query query=new Query.QueryBuilder().where("id",hrId).and("company_id",companyId).and("activation",1).and("disable",1).andInnerCondition("account_type",0).or("account_type",2)
                .buildQuery();
        int count =userHrAccountDao.getCount(query);
        return count;
    }
    /*
      根据公司id获取公司配置
     */
    private HrCompanyConfRecord getHrCompanyConfRecordByCompanyId(int companyId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).buildQuery();
        HrCompanyConfRecord hrCompanyConfRecord=hrCompanyConfDao.getRecord(query);
        return hrCompanyConfRecord;
    }
    private List<Map<String,Object>> getCompanyTagList(int companyId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).and("disable",1)
                .orderBy("update_time", Order.DESC).buildQuery();
        List<Map<String,Object>> list=talentpoolCompanyTagDao.getMaps(query);
        return list;
    }

}
