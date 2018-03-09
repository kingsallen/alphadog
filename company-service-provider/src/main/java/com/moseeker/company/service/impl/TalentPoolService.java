package com.moseeker.company.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HrCompanyConfDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.talentpooldb.*;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyConfRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.records.*;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.annotation.notify.UpdateEs;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.*;
import com.moseeker.company.bean.ValidateCommonBean;
import com.moseeker.company.bean.ValidateTagBean;
import com.moseeker.company.bean.ValidateTalentBean;
import com.moseeker.company.utils.ValidateTalent;
import com.moseeker.company.utils.ValidateTalentTag;
import com.moseeker.company.utils.ValidateUtils;
import com.moseeker.entity.TalentPoolEntity;
import com.moseeker.thrift.gen.common.struct.Response;
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
@Transactional
public class TalentPoolService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TalentpoolHrTalentDao talentpoolHrTalentDao;
    @Autowired
    private TalentPoolEntity talentPoolEntity;
    @Autowired
    private TalentpoolTagDao talentpoolTagDao;
    @Autowired
    private TalentpoolUserTagDao talentpoolUserTagDao;
    @Autowired
    private TalentpoolCommentDao talentpoolCommentDao;
    @Autowired
    private TalentpoolTalentDao talentpoolTalentDao;
    @Autowired
    private JobApplicationDao jobApplicationDao;
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

    /*
      修改开启人才库的申请记录
     */
    @CounterIface
    public Response upsertTalentPoolApplication(int hrId,int companyId){
        int count=this.validateHrAndCompany(hrId,companyId);
        if(count==0){
            return ResponseUtils.fail(1,"此账号不是主账号");
        }
        HrCompanyConfRecord record=this.getHrCompanyConfRecordByCompanyId(companyId);
        if(record==null){
            return ResponseUtils.fail(1,"此公司无配置");
        }
        int result=talentpoolApplicationDao.inserOrUpdateTalentPoolApplication(hrId,companyId);
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
    public Response batchAddTalent(int hrId, Set<Integer> userIdList, int companyId)throws TException{
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        ValidateTalentBean bean=validateTalent.handlerApplierId(hrId,userIdList,companyId);
        Set<Integer> applierIdList=bean.getUserIdSet();
        Set<Integer> unUseList=bean.getUnUseUserIdSet();
        if(StringUtils.isEmptySet(applierIdList)){
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
        return ResponseUtils.success(result);
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
    public Response batchCancelTalent(int hrId, Set<Integer> userIdList, int companyId)throws TException{
        //验证hr
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        //验证所有人才是否可以操作
        ValidateTalentBean bean=validateTalent.handlerApplierId(hrId,userIdList,companyId);
        //被操作的人中申请该hr下职位的人
        Set<Integer> applierIdList=bean.getUserIdSet();
        //被操作的人中没有申请该hr下职位的人
        Set<Integer> unUseList=bean.getUnUseUserIdSet();
        //所有人都没有收藏，所以都不能操作
        if(StringUtils.isEmptySet(applierIdList)){
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
     批量添加标签
     @auth:zzt
     @params: hrId hr编号
              userIdList 用户的编号的集合
              tagIdList  标签的编号集合
              companyId 公司的编号
     @return response(status:0,message:"success,data:[])
             response(status:1,message:"xxxxxx")
     */
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
        Map<Integer,Object> usertagMap=handlerUserTagResult(hrTagList,userTagIdList,idList,tagIdList,1);
        if(usertagMap==null||userIdList.isEmpty()){
            ResponseUtils.fail(1,"不满足添加标签的条件");
        }
        Map<String,Object> result=new HashMap<>();
        result.put("nopower",nouseList);
        result.put("use",usertagMap);
        return ResponseUtils.success(result);
    }


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
        Map<Integer,Object> usertagMap=handlerUserTagResult(hrTagList,userTagIdList,idList,tagIdList,1);
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
        Map<Integer,Object> usertagMap=handlerUserTagResult(hrTagList,userTagIdList,idList,tagIdList,0);
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
    public Response getAllHrTag(int hrId,int companyId,int pageNum,int pageSize)throws TException{
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        Map<String,Object> result=this.handleTagData(hrId,pageNum,pageSize);
        logger.info("======================");
        logger.info(JSON.toJSONString(result));
        return ResponseUtils.success(result);
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
        if(content.length()>100){
            return ResponseUtils.fail(1,"备注内容需在50字以内");
        }
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        int validate=talentPoolEntity.validateComment(hrId,companyId,userId);
        if(validate==0){
            return ResponseUtils.fail(1,"该hr无权操作此简历");
        }
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
        return ResponseUtils.success(list);
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

        if(type==0){
            int talentNum=this.getAllHrTalent(hrId);
//            int companyPublicNum=talentPoolEntity.getPublicTalentCount(companyId);
            int companyPublicNum=this.getAllHrPublicTalentNum(companyId);
            int hrPublicNum=this.getHrPublicTalentCount(hrId);
            int allTalentNum=this.getAllTalentNum(companyId);
            List<Map<String,Object>> list=this.getTagByHrNoOrder(hrId,0,Integer.MAX_VALUE);
            result.put("allpublic",companyPublicNum);
            result.put("hrpublic",hrPublicNum);
            result.put("talent",talentNum);
            result.put("tag",list);
            result.put("alltalent",allTalentNum);
        }else if(type==1){
            int hrPublicNum=this.getHrPublicTalentCount(hrId);
            result.put("hrpublic",hrPublicNum);
        }else if(type==2){
            int talentNum=this.getAllHrTalent(hrId);
            result.put("talent",talentNum);
        }else if(type==3){
            int companyPublicNum=talentPoolEntity.getPublicTalentCount(companyId);
            result.put("allpublic",companyPublicNum);
        }else if(type==4){
            List<Map<String,Object>> list=talentPoolEntity.getTagByHr(hrId,0,Integer.MAX_VALUE);
            result.put("tag",list);
        }
        return ResponseUtils.success(result);
    }

    /*
     获取公司下所有人才
     */

    private int getAllTalentNum(int companyId){
        List<Map<String,Object>> hrList=talentPoolEntity.getCompanyHrList(companyId);
        Set<Integer> hrIdList=talentPoolEntity.getIdListByUserHrAccountList(hrList);
        if(!StringUtils.isEmptySet(hrIdList)){
            int count=talentPoolEntity.getAllHrTalentCount(hrIdList);
            return count;
        }
        return 0;

    }
    /*
     获取公司下公开的所有人才
     */
    private int getAllHrPublicTalentNum(int companyId){
        List<Map<String,Object>> hrList=talentPoolEntity.getCompanyHrList(companyId);
        Set<Integer> hrIdList=talentPoolEntity.getIdListByUserHrAccountList(hrList);
        if(!StringUtils.isEmptySet(hrIdList)){
            int count=talentPoolEntity.getAllHrPubTalentCount(hrIdList);
            return count;
        }
        return 0;
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
        TalentpoolCommentRecord record=new TalentpoolCommentRecord();
        record.setId(comId);
        talentpoolCommentDao.deleteRecord(record);
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
     批量公开
     @auth:zzt
     @params:  hrId      hr编号
               companyId 公司的编号
               userIdList用户标号集合
     @return response(status:0,message:"success,data:[])
             response(status:1,message:"xxxxxx")
     */
    @UpdateEs(tableName = "talentpool_hr_talent", argsIndex = 2, argsName = "user_id")
    @CounterIface
    public Response AddbatchPublicTalent(int hrId,int companyId,Set<Integer> userIdList)throws TException{
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        int validate=this.validatePublic(hrId,userIdList);
        if(validate==0){
            return ResponseUtils.fail(1,"无法满足操作条件");
        }
        if(validate==2){
            return ResponseUtils.fail(1,"在公开的人员中存在已公开的人员");
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
        if(result==null||result.isEmpty()){
            return  ResponseUtils.success("");
        }
        return ResponseUtils.success(result);
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
    @UpdateEs(tableName = "talentpool_hr_talent", argsIndex = 2, argsName = "user_id")
    @CounterIface
    public Response cancelBatchPublicTalent(int hrId,int companyId,Set<Integer> userIdList)throws TException{
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
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
        if(result==null||result.isEmpty()){
            return  ResponseUtils.success("");
        }
        return ResponseUtils.success(result);
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
        List<Map<String,Object>> hrTagList=talentPoolEntity.getTagByHr(hrId,0,Integer.MAX_VALUE);
        if(StringUtils.isEmptyList(hrTagList)){
            return ResponseUtils.success("");
        }
        //获取hr下所有的tagId
        Set<Integer> hrTagIdList=validateTalentTag.getIdByTagList(hrTagList);
        Set<Integer> tagIdList=validateTalentTag.getUserTagIdList(userId,hrTagIdList);
        List<Map<String,Object>> allTagList=this.getUserTagByUserIdAndTagIdMap(userId,hrTagIdList);
        if(StringUtils.isEmptyList(allTagList)){
            return ResponseUtils.success("");
        }else{
            for(Map<String,Object> map:allTagList){
                int tagId= (int) map.get("tag_id");
                for(Map<String,Object> map1:hrTagList){
                    int id= (int) map1.get("id");
                    String name=(String)map1.get("name");
                    if(id==tagId){
                        map.put("name",name);
                    }
                }
            }
        }
        return  ResponseUtils.success(allTagList);
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
        List<Map<String,Object>> hrList=talentPoolEntity.getCompanyHrList(companyId);
        Set<Integer> hrIdList=talentPoolEntity.getIdListByUserHrAccountList(hrList);
        if(StringUtils.isEmptySet(hrIdList)){
            return null;
        }
        int count=talentPoolEntity.getPublicTalentCount(hrIdList);
        double page=((double)count)/pageSize;
        int total= (int) Math.ceil(page);
        List<Map<String,Object>> list=getPublicTalentByCompanyId(hrIdList,pageNum,pageSize);
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
    private  int validatePublic(int hrId,Set<Integer> userIdList){
        List<Map<String,Object>> list=talentPoolEntity.getTalentpoolHrTalentByIdList(hrId,userIdList);
        if(!StringUtils.isEmptyList(list)&&list.size()==userIdList.size()){
            for(Map<String,Object> map:list){
                byte ispublic= (byte) map.get("public");
                if(ispublic==1){
                    return 2;
                }
            }
            return 1;
        }
        return 0;
    }

    /*
     处理hr下分页获取人才数据
     */
    private Map<String,Object> handleTagData(int hrId,int pageNum,int pageSize){
        int count=this.getTagByHrCount(hrId);
        double page=((double)count)/pageSize;
        int total= (int) Math.ceil(page);
        List<Map<String,Object>> hrTagList=talentPoolEntity.getTagByHr(hrId,pageNum,pageSize);
        Map<String,Object> result=new HashMap<>();
        result.put("page_number",pageNum);
        result.put("page_size",pageSize);
        result.put("total_page",total);
        result.put("total_row",count);
        result.put("data",hrTagList);
        return result;
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

     */
    private Map<Integer,Object> handlerUserTagResult( List<Map<String,Object>> hrTagList,Set<Integer> userTagIdList,Set<Integer> idList,Set<Integer> tagIdList,int type){
        if(StringUtils.isEmptyList(hrTagList)){
            return null;
        }
        List<Map<String,Object>> handlerTag=this.getTagData(tagIdList);
        List<Map<String,Object>> resultList=new ArrayList<>();
        Map<Integer,Object> result=new HashMap<>();
        if(type==1){
            if(StringUtils.isEmptySet(userTagIdList)){
                resultList=handlerTag;
            }else {
                for (Integer tagId : userTagIdList) {
                    for (Map<String,Object> record : hrTagList) {
                        if (tagId == record.get("id")) {
                            resultList.add(record);
                            break;
                        }
                    }
                }
                resultList.addAll(handlerTag);
            }

        }else{
            if(StringUtils.isEmptySet(userTagIdList)){
                return null;
            }else{
                userTagIdList.removeAll(tagIdList);
            }
            if(!StringUtils.isEmptySet(userTagIdList)){
                for (Integer tagId : userTagIdList) {
                    for (Map<String,Object> record : hrTagList) {
                        if (tagId == record.get("id")) {
                            resultList.add(record);
                            break;
                        }
                    }
                }
            }
        }
        for(Integer userId:idList){
            result.put(userId,resultList);
        }
        return result;
    }

    /*
     根据tagid的集合获取tag信息
     */
    private List<Map<String,Object>> getTagData(Set<Integer> tagIdList){
        Query query=new Query.QueryBuilder().where(new Condition("id",tagIdList.toArray(),ValueOp.IN)).buildQuery();
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
                .orderBy("create_time",Order.DESC)
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
    获取公司下所有的人才
   */
    private List<Map<String,Object>> getPublicTalentByCompanyId(Set<Integer> hrIdSet,int pageNum,int pageSize){
//        Query query=new Query.QueryBuilder().select(new Select("'user_id",SelectOp.DISTINCT)).where(new Condition("hr_id",hrIdSet.toArray(),ValueOp.IN)).and(new Condition("public",1,ValueOp.GT))
//                .setPageNum(pageNum).setPageSize(pageSize).buildQuery();
//        List<Map<String,Object>> list=talentpoolHrTalentDao.getMaps(query);
        return talentpoolHrTalentDao.getAllPublicTalent(hrIdSet,pageNum,pageSize);
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


}
