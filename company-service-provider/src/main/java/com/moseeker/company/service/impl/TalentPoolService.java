package com.moseeker.company.service.impl;

import com.moseeker.baseorm.dao.talentpooldb.*;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolCommentRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolHrTalentRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolTagRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolUserTagRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.TalentPoolEntity;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Intention;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by zztaiwll on 17/12/4.
 */
@Service
@Transactional
public class TalentPoolService {
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
    /*
     添加人才到人才库
     */
    public Response addTalent(int hrId, int userId, int companyId) throws Exception {
        Response res=validateHrAndUser(hrId,userId,companyId);
        if(res!=null){
            return res;
        }
        int flag=talentpoolHrTalentDao.upserTalpoolHrTalent(userId,companyId);
        if(flag==0){
            throw new Exception();
        }
        int count=this.getTalentpoolHrTalentCount(userId,hrId);
        if(count>0){
            return ResponseUtils.fail(1,"已收藏该人才");
        }
        talentPoolEntity.handlerTalentpoolTalent(userId,companyId,0,0,1);
        List<UserHrAccountRecord> list=talentPoolEntity.getHrAboutTalent(userId,companyId);
        Map<String,Object> result=new HashMap<>();
        result.put("user_id",userId);
        result.put("hrs",list);
        return ResponseUtils.success(result);
    }

    /*
     批量添加人才
    */
    @CounterIface
    public Response batchAddTalent(int hrId, Set<Integer> userIdList, int companyId){
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        Map<String,Object> validateMap=talentPoolEntity.handlerApplierId(hrId,userIdList,companyId);
        Set<Integer> unUseList= (Set<Integer>) validateMap.get("unuse");
        Set<Integer> applierIdList= (Set<Integer>) validateMap.get("use");
        if(StringUtils.isEmptySet(applierIdList)){
            return ResponseUtils.fail(1,"hr无权操作人才");
        }
        List<TalentpoolHrTalentRecord> talentList=this.getTalentpoolHrTalentByIdList(hrId,applierIdList);
        Set<Integer> unApplierIdList=talentPoolEntity.getIdListByTalentpoolHrTalentList(talentList);
        Set<Integer> idList=talentPoolEntity.filterIdList(applierIdList,unApplierIdList);
        if(!StringUtils.isEmptySet(idList)){
            List<TalentpoolHrTalentRecord> recordList=new ArrayList<>();
            for(Integer id:idList){
                TalentpoolHrTalentRecord record=new TalentpoolHrTalentRecord();
                record.setHrId(hrId);
                record.setUserId(id);
                recordList.add(record);
            }
            talentpoolHrTalentDao.addAllRecord(recordList);
            for(Integer id:idList){
                talentPoolEntity.handlerTalentpoolTalent(id,companyId,0,0,1);
            }
        }
        Map<String,Object> result=this.handlerBatchTalentResult(unUseList,unApplierIdList,idList,companyId);
        return ResponseUtils.successWithoutStringify(result.toString());
    }

    /*
      取消收藏人才
     */
    @CounterIface
    public Response cancleTalent(int hrId, int userId, int companyId){
        Response res=validateHrAndUser(hrId,userId,companyId);
        if(res!=null){
            return res;
        }
        int count=this.getTalentpoolHrTalentCount(userId,hrId);
        if(count==0){
            return ResponseUtils.fail(1,"已取消该人才");
        }
        TalentpoolHrTalentRecord record=new TalentpoolHrTalentRecord();
        record.setUserId(userId);
        record.setHrId(hrId);
        int flag=talentpoolHrTalentDao.deleteRecord(record);
        talentPoolEntity.handlerTalentpoolTalent(userId,companyId,0,0,-1);
        List<UserHrAccountRecord> list=talentPoolEntity.getHrAboutTalent(userId,companyId);
        Map<String,Object> result=new HashMap<>();
        result.put("user_id",userId);
        result.put("hrs",list);
        Set<Integer> userIdList=new HashSet<>();
        userIdList.add(userId);
        //删除标签
        this.handleCancleTag(hrId,userIdList);
        return ResponseUtils.success(result);
    }
    /*
      批量取消人才
     */
    @CounterIface
    public Response batchCancleTalent(int hrId, Set<Integer> userIdList, int companyId){
        //验证hr
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        //验证所有人才是否可以操作
        Map<String,Object> validateMap=talentPoolEntity.handlerApplierId(hrId,userIdList,companyId);
        //不能操作的
        Set<Integer> unUseList= (Set<Integer>) validateMap.get("unuse");
        //可以操作的
        Set<Integer> applierIdList= (Set<Integer>) validateMap.get("use");
        if(StringUtils.isEmptySet(applierIdList)){
            return ResponseUtils.fail(1,"hr无权操作人才");
        }
        //获取hr下的applierIdList中的人才
        List<TalentpoolHrTalentRecord> talentList=this.getTalentpoolHrTalentByIdList(hrId,applierIdList);
        //获取人才的Id
        Set<Integer> idList=talentPoolEntity.getIdListByTalentpoolHrTalentList(talentList);
        //比较两者，取出不可以操作的
        Set<Integer> unApplierIdList=talentPoolEntity.filterIdList(applierIdList,idList);
        if(!StringUtils.isEmptySet(idList)){
            List<TalentpoolHrTalentRecord> recordList=new ArrayList<>();
            for(Integer userId:idList){
                TalentpoolHrTalentRecord record=new TalentpoolHrTalentRecord();
                record.setUserId(userId);
                record.setHrId(hrId);
                recordList.add(record);
            }
            //取消收藏
            talentpoolHrTalentDao.deleteRecords(recordList);
            for(Integer id:idList){
                talentPoolEntity.handlerTalentpoolTalent(id,companyId,0,0,-1);
            }
            //取消收藏时删除标签，并且计算标签数
            this.handleCancleTag(hrId,idList);
        }
        Map<String,Object> result=this.handlerBatchTalentResult(unUseList,unApplierIdList,idList,companyId);
        return ResponseUtils.successWithoutStringify(result.toString());
    }

    //处理批量操作的结果
    private Map<String,Object> handlerBatchTalentResult( Set<Integer> unUseList,Set<Integer>unApplierIdList,Set<Integer> idList ,int companyd){
        List<UserHrAccountRecord> userHrList=talentPoolEntity.getCompanyHrList(companyd);
        Map<Integer,Set<UserHrAccountRecord>> unhrSet=talentPoolEntity.getBatchAboutTalent(unUseList,userHrList);
        Map<Integer,Set<UserHrAccountRecord>> unApplierHrSet=talentPoolEntity.getBatchAboutTalent(unApplierIdList,userHrList);
        Map<Integer,Set<UserHrAccountRecord>> hrSet=talentPoolEntity.getBatchAboutTalent(idList,userHrList);
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
    //处理取消收藏之后的tag
    private void handleCancleTag(int hrId,Set<Integer> userIdList){
        List<TalentpoolTagRecord> hrTagList=this.getTagByHr(hrId,0,Integer.MAX_VALUE);
        //获取hr下所有的tagId
        Set<Integer> hrTagIdList=this.getIdByTagList(hrTagList);
        if(!StringUtils.isEmptySet(hrTagIdList)){
            List<TalentpoolUserTagRecord> list=this.getUserTagByUserIdListAndTagId(userIdList,hrTagIdList);
            if(!StringUtils.isEmptyList(list)){
                talentpoolUserTagDao.deleteRecords(list);
                for(TalentpoolUserTagRecord record:list){
                    talentpoolTagDao.updateTagNum(record.getTagId(),-1);
                }
            }
        }

    }
    /*
     批量添加标签
     */
    @CounterIface
    public Response addBatchTalentTag(int hrId,Set<Integer> userIdList,Set<Integer> tagIdList,int companyId){
        Map<String,Object> validateResult=this.validateAddTag(hrId, userIdList, tagIdList, companyId);
        if(validateResult.get("result")!=null){
            return (Response)validateResult.get("result");
        }
        Set<Integer> idList= (Set<Integer>) validateResult.get("idList");
        Set<Integer> nouseList= (Set<Integer>)validateResult.get("nouseList");
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
        List<TalentpoolTagRecord> hrTagList=(List<TalentpoolTagRecord>) validateResult.get("hrTagList");
        Set<Integer> userTagIdList= (Set<Integer>)validateResult.get("userTagIdList");
        Map<Integer,Object> usertagMap=handlerUserTagResult(hrTagList,userTagIdList,idList,tagIdList,1);
        if(usertagMap==null||userIdList.isEmpty()){
            ResponseUtils.fail(1,"操作失败");
        }
        Map<String,Object> result=new HashMap<>();
        result.put("nopower",nouseList);
        result.put("use",usertagMap);
        return ResponseUtils.successWithoutStringify(result.toString());
    }
    /*
     删除标签
     */
    @CounterIface
    public Response cancaleBatchTalentTag(int hrId,Set<Integer> userIdList,Set<Integer> tagIdList,int companyId){

        Map<String,Object> validateResult=this.validateCancleTag(hrId, userIdList, tagIdList, companyId);
        if(validateResult.get("result")!=null){
            return (Response)validateResult.get("result");
        }
        Set<Integer> idList= (Set<Integer>) validateResult.get("userIdList");
        Set<Integer> nouseList= (Set<Integer>)validateResult.get("nouseList");
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
        List<TalentpoolTagRecord> hrTagList=(List<TalentpoolTagRecord>) validateResult.get("hrTagList");
        Set<Integer> userTagIdList= (Set<Integer>)validateResult.get("userTagIdList");
        Map<Integer,Object> usertagMap=handlerUserTagResult(hrTagList,userTagIdList,idList,tagIdList,1);
        if(usertagMap==null||userIdList.isEmpty()){
            ResponseUtils.fail(1,"操作失败");
        }
        Map<String,Object> result=new HashMap<>();
        result.put("nopower",nouseList);
        result.put("use",usertagMap);
        return ResponseUtils.successWithoutStringify(result.toString());
    }

    /*
     添加标签
     */
    @CounterIface
    public Response addHrTag(int hrId,int companyId,String name){
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
        talentpoolTagDao.addRecord(record);
        return ResponseUtils.success("");
    }
    /*
     删除标签r
     */
    @CounterIface
    public Response deleteHrTag(int hrId,int companyId,int tagId){
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        TalentpoolTagRecord validateRecord=this.validateTag(hrId,tagId);
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
        }
        return ResponseUtils.success("");
    }
    /*
    修改标签
     */
    @CounterIface
    public Response updateHrTag(int hrId,int companyId,int tagId,String name){
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        TalentpoolTagRecord validateRecord=this.validateTag(hrId,tagId);
        if(validateRecord==null){
            return ResponseUtils.fail(1,"这个标签不属于这个hr");
        }
        int count=this.validateName(hrId,name);
        if(count>0){
            return ResponseUtils.fail(1,"该标签重名");
        }
        TalentpoolTagRecord record=new TalentpoolTagRecord();
        record.setName(name);
        record.setId(tagId);
        talentpoolTagDao.updateRecord(record);
        return ResponseUtils.success("");
    }
    /*
     查询标签
     */
    @CounterIface
    public Response getAllHrTag(int hrId,int companyId,int pageNum,int pageSize){
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        List<TalentpoolTagRecord> hrTagList=this.getTagByHr(hrId,pageNum,pageSize);
        return ResponseUtils.successWithoutStringify(hrTagList.toString());
    }

    @CounterIface
    /*
     添加备注
     */
    public Response addTalentComment(int hrId,int companyId,int userId,String content){

        if(StringUtils.isNullOrEmpty(content)){
            return ResponseUtils.fail(1,"该hr的备注内容不能为空");
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
        List<TalentpoolCommentRecord> list=this.getAllComment(companyId,userId);
        return ResponseUtils.successWithoutStringify(list.toString());
    }

    private List<TalentpoolCommentRecord> getAllComment(int companyId,int userId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).and("user_id",userId).buildQuery();
        List<TalentpoolCommentRecord> list=talentpoolCommentDao.getRecords(query);
        return list;
    }
    /*
     删除备注
     */
    public Response delTalentComment(int hrId,int companyId,int userId,int comId){
        int count=talentPoolEntity.validateUserComment(comId,userId,hrId);
        if(count==0){
            return ResponseUtils.fail(1,"该备注不属于这个hr下的这个人才");
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
        record.setId(comId);
        talentpoolCommentDao.deleteRecord(record);
        List<TalentpoolCommentRecord> list=this.getAllComment(companyId,userId);
        return ResponseUtils.successWithoutStringify(list.toString());
    }


    /*
     获取所有的备注
     */
    public Response getAllTalentComment(int hrId,int companyId,int userId){
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        int validate=talentPoolEntity.validateComment(hrId,companyId,userId);
        if(validate==0){
            return ResponseUtils.fail(1,"该hr无权操作此简历");
        }
        List<TalentpoolCommentRecord> list=this.getAllComment(companyId,userId);
        return ResponseUtils.success(list);
    }

    /*
     批量公开
     */
    public Response AddbatchPublicTalent(int hrId,int companyId,Set<Integer> userIdList){
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        boolean validate=this.validatePublic(hrId,userIdList);
        if(!validate){
            return ResponseUtils.fail(1,"无法满足批量操作的条件");
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
        return ResponseUtils.success("");
    }


    /*
     批量取消公开
     */
    public Response cancleBatchPublicTalent(int hrId,int companyId,Set<Integer> userIdList){
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        boolean validate=this.validateCanclePublic(hrId,userIdList);
        if(!validate){
            return ResponseUtils.fail(1,"无法满足批量操作的条件");
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

        return ResponseUtils.success("");
    }
    /*
     获取hr下公开的人才
     */
    public List<TalentpoolHrTalentRecord> getTalentpoolHrTalentPublic(int hrId,Set<Integer> userIdList){
        Query query=new Query.QueryBuilder().where("hr_id",hrId).and(new Condition("user_id",userIdList.toArray(),ValueOp.IN))
                .and("public",1).buildQuery();
        List<TalentpoolHrTalentRecord> list=talentpoolHrTalentDao.getRecords(query);
        return list;
    }
    /*
      验证是否可以公开
     */
    private  boolean validatePublic(int hrId,Set<Integer> userIdList){
        List<TalentpoolHrTalentRecord> list=getTalentpoolHrTalentByIdList(hrId,userIdList);
        if(!StringUtils.isEmptyList(list)&&list.size()==userIdList.size()){
            return true;
        }
        return false;
    }
    /*
     验证是否可以取消公开
     */
    private boolean validateCanclePublic(int hrId,Set<Integer> userIdList){
        List<TalentpoolHrTalentRecord> list=this.getTalentpoolHrTalentPublic(hrId,userIdList);
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
     根据hr_id和tagId获取记录
     */
    private TalentpoolTagRecord validateTag(int hrId,int tagId){
        Query query=new Query.QueryBuilder().where("hr_id",hrId).and("id",tagId).buildQuery();
        TalentpoolTagRecord record=talentpoolTagDao.getRecord(query);
        return record;
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
    private Map<Integer,Object> handlerUserTagResult( List<TalentpoolTagRecord> hrTagList,Set<Integer> userTagIdList,Set<Integer> idList,Set<Integer> tagIdList,int type){
        if(StringUtils.isEmptyList(hrTagList)){
            return null;
        }
        List<TalentpoolTagRecord> handlerTag=this.getTagData(tagIdList);
        List<TalentpoolTagRecord> resultList=new ArrayList<>();
        Map<Integer,Object> result=new HashMap<>();
        if(type==0){
            if(StringUtils.isEmptySet(userTagIdList)){
                resultList=handlerTag;
            }else {
                for (Integer tagId : userTagIdList) {
                    for (TalentpoolTagRecord record : hrTagList) {
                        if (tagId == record.getId()) {
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
            }
            for (Integer tagId : userTagIdList) {
                for (TalentpoolTagRecord record : hrTagList) {
                    if (tagId == record.getId()) {
                        resultList.add(record);
                        break;
                    }
                }
            }
            resultList.removeAll(handlerTag);
        }
        for(Integer userId:idList){
            result.put(userId,resultList);
        }
        return result;
    }

    /*
     根据tagid的集合获取tag信息
     */
    private List<TalentpoolTagRecord> getTagData(Set<Integer> tagIdList){
        Query query=new Query.QueryBuilder().where(new Condition("id",tagIdList.toArray(),ValueOp.IN)).buildQuery();
        List<TalentpoolTagRecord> list=talentpoolTagDao.getRecords(query);
        return list;
    }
    /*
       校验批量删除标签
      */
    private Map<String,Object> validateCancleTag(int hrId,Set<Integer> userIdList,Set<Integer> tagIdList,int companyId){
        Map<String,Object> result=new HashMap<>();
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            result.put("result",ResponseUtils.fail(1,"该hr不属于该company_id"));
            return result;
        }
        Map<String,Object> validateResult=this.validateUserIdTag(hrId,userIdList,tagIdList,companyId);
        Set<Integer> idList= (Set<Integer>) validateResult.get("use");
        if(StringUtils.isEmptySet(idList)){
            result.put("result",ResponseUtils.fail(1,"该无权操作这些人才"));
            return result;
        }
        Map<String,Object> validateTag=this.validateTag(idList,hrId);
        boolean flagTag= (boolean) validateTag.get("result");
        if(!flagTag){
            result.put("result",ResponseUtils.fail(1,"不满足批量操作条件"));
            return result;
        }
        List<TalentpoolTagRecord> hrTagList= (List<TalentpoolTagRecord>) validateTag.get("hrTagIdList");
        Set<Integer> userTagIdList= (Set<Integer>) validateTag.get("userTagIdList");
        boolean validateOperTag=this.validateCancleOperatorTag(this.getIdByTagList(hrTagList),tagIdList,userTagIdList);
        if(!validateOperTag){
            result.put("result",ResponseUtils.fail(1,"操作的标签不是hr定义的标签"));
            return result;
        }
        result.put("hrTagList",hrTagList);
        result.put("userIdList",idList);
        result.put("userTagIdList",userTagIdList);
        result.put("nouseList",(Set<Integer>)validateResult.get("nouse"));
        return result;
    }
    /*
      校验批量添加标签
     */
    private Map<String,Object> validateAddTag(int hrId,Set<Integer> userIdList,Set<Integer> tagIdList,int companyId){
        Map<String,Object> result=new HashMap<>();
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            result.put("result",ResponseUtils.fail(1,"该hr不属于该company_id"));
            return result;
        }
        Map<String,Object> validateResult=this.validateUserIdTag(hrId,userIdList,tagIdList,companyId);
        Set<Integer> idList= (Set<Integer>) validateResult.get("use");
        if(StringUtils.isEmptySet(idList)){
            result.put("result",ResponseUtils.fail(1,"该无权操作这些人才"));
            return result;
        }
        Map<String,Object> validateTag=this.validateTag(idList,hrId);
        boolean flagTag= (boolean) validateTag.get("result");
        if(!flagTag){
            result.put("result",ResponseUtils.fail(1,"不满足批量操作条件"));
            return result;
        }
        List<TalentpoolTagRecord> hrTagList= (List<TalentpoolTagRecord>) validateResult.get("hrTagIdList");
        Set<Integer> userTagIdList= (Set<Integer>) validateResult.get("userTagIdList");
        boolean validateOperTag=this.validateAddOperatorTag(this.getIdByTagList(hrTagList),tagIdList,userTagIdList);
        if(!validateOperTag){
            result.put("result",ResponseUtils.fail(1,"操作的标签不是hr定义的标签"));
            return result;
        }
        result.put("hrTagList",hrTagList);
        result.put("userIdList",idList);
        result.put("userTagIdList",userTagIdList);
        result.put("nouseList",(Set<Integer>)validateResult.get("nouse"));
        return result;
    }
    private Map<String,Object> validateUserIdTag(int hrId,Set<Integer> userIdList,Set<Integer> tagIdList,int companyId){
        Map<String,Object> validateMap=talentPoolEntity.handlerApplierId(hrId,userIdList,companyId);
        Set<Integer> unUseList= (Set<Integer>) validateMap.get("unuse");
        Set<Integer> applierIdList= (Set<Integer>) validateMap.get("use");
        Set<Integer> pubTalentIdList=new HashSet<>();
        if(!StringUtils.isEmptySet(unUseList)){
            Map<String,Object> validateData=talentPoolEntity.filterNoPowerUserId(unUseList,companyId);
            unUseList=(Set<Integer>) validateData.get("unuse");
            pubTalentIdList= (Set<Integer>) validateData.get("use");
        }
        List<TalentpoolHrTalentRecord> talentList=this.getTalentpoolHrTalentByIdList(hrId,applierIdList);
        Set<Integer> unApplierIdList=talentPoolEntity.getIdListByTalentpoolHrTalentList(talentList);
        Set<Integer> idList=talentPoolEntity.filterIdList(applierIdList,unApplierIdList);
        if(!StringUtils.isEmptySet(pubTalentIdList)){
            if(StringUtils.isEmptySet(idList)){
                idList=pubTalentIdList;
            }else{
                idList.addAll(pubTalentIdList);
            }
        }
        Map<String,Object> result=new HashMap<>();
        result.put("nopower",unUseList);
        result.put("use",idList);
        return result;
    }
    /*
     判断是否可以打标签
     */
    private Map<String,Object> validateTag(Set<Integer> userIdList,int hrId){
        Map<String,Object> result=new HashMap<>();
        //获取hr下所有的tag
        List<TalentpoolTagRecord> hrTagList=this.getTagByHr(hrId,0,Integer.MAX_VALUE);
        //获取hr下所有的tagId
        Set<Integer> hrTagIdList=this.getIdByTagList(hrTagList);
        if(StringUtils.isEmptySet(hrTagIdList)){
            result.put("result",false);
            return result;
        }
        Set<Integer> tagIdList=this.getUserTagIdList(userIdList.iterator().next(),hrTagIdList);
        if(StringUtils.isEmptySet(tagIdList)){
            result.put("result",false);
            return result;
        }
        List<TalentpoolUserTagRecord> tagList=this.getUserTagByUserIdListAndTagId(userIdList,tagIdList);
        if(StringUtils.isEmptyList(tagList)){
            result.put("result",false);
            return result;
        }
        if(userIdList.size()*tagIdList.size()!=tagList.size()){
            result.put("result",false);
            return result;
        }
        for(TalentpoolUserTagRecord record:tagList){
            int tagId=record.getTagId();
            if(!tagIdList.contains(tagId)){
                result.put("result",false);
                return result;
            }
        }
        result.put("result",true);
        result.put("hrTagList",hrTagList);
        result.put("userTagIdList",tagIdList);
        return result;
    }
    /*
     验证添加标签是否符合操作条件
     */
    private boolean validateAddOperatorTag(Set<Integer> hrTagIdList,Set<Integer> tagIdList, Set<Integer> userTagIdList){

        for(Integer id:tagIdList){
           if(!hrTagIdList.contains(id)) {
               return false;
           }
        }
        if(!StringUtils.isEmptySet(userTagIdList)){
            for(Integer id:tagIdList){
               if(userTagIdList.contains(id)){
                   return false;
               }
            }
        }
        return true;
    }
    /*
        验证添加标签是否符合操作条件
        */
    private boolean validateCancleOperatorTag(Set<Integer> hrTagIdList,Set<Integer> tagIdList, Set<Integer> userTagIdList){
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
    /*
      查询hr下所有的标签
     */
    private List<TalentpoolTagRecord> getTagByHr(int hrId,int pageNum,int pageSize){
        Query query=new Query.QueryBuilder().where("hr_id",hrId).setPageNum(pageNum).setPageSize(pageSize).buildQuery();
        List<TalentpoolTagRecord> list= talentpoolTagDao.getRecords(query);
        return list;
    }
    /*
     获取TalentpoolTagRecord 集合的id
     */
    private Set<Integer> getIdByTagList(List<TalentpoolTagRecord> list){
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        Set<Integer> result=new HashSet<>();
        for(TalentpoolTagRecord record:list){
            result.add(record.getId());
        }
        return result;
    }
    /*
     获取任意一个人才在这家公司下的所有的标签的id
     */
    private Set<Integer> getUserTagIdList(int userId,Set<Integer> tagIdList){
        List<TalentpoolUserTagRecord> userTagList=getUserTagByUserIdAndTagId(userId,tagIdList);
        Set<Integer> idList=this.getTagIdByUserTagList(userTagList);
        return idList;
    }
    /*
     获取一个人才在这个hr下拥有的标签
     */
    private List<TalentpoolUserTagRecord> getUserTagByUserIdAndTagId(int userId,Set<Integer> tagIdList){
        if(StringUtils.isEmptySet(tagIdList)){
            return null;
        }
        Query query=new Query.QueryBuilder().where("user_id",userId).and(new Condition("tag_id",tagIdList.toArray(),ValueOp.IN)).buildQuery();
        List<TalentpoolUserTagRecord> list=talentpoolUserTagDao.getRecords(query);
        return list;
    }

    /*
     获取TalentpoolTagRecord 集合的id
     */
    private Set<Integer> getTagIdByUserTagList(List<TalentpoolUserTagRecord> list){
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        Set<Integer> result=new HashSet<>();
        for(TalentpoolUserTagRecord record:list){
            result.add(record.getTagId());
        }
        return result;
    }

    /*
      获取userIdList在该hr下的所有的标签
     */
    private List<TalentpoolUserTagRecord> getUserTagByUserIdListAndTagId(Set<Integer> userIdList,Set<Integer> tagIdList){
        if(StringUtils.isEmptySet(tagIdList)||StringUtils.isEmptySet(userIdList)){
            return null;
        }
        Query query=new Query.QueryBuilder().where(new Condition("user_id",userIdList.toArray(),ValueOp.IN))
                                            .and(new Condition("tag_id",tagIdList.toArray(),ValueOp.IN)).buildQuery();
        List<TalentpoolUserTagRecord> list=talentpoolUserTagDao.getRecords(query);
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
      获取人才
     */
    private List<TalentpoolHrTalentRecord> getTalentpoolHrTalentByIdList(int hrId, Set<Integer> userIdList){
        Query query=new Query.QueryBuilder().where(new Condition("user_id",userIdList.toArray(), ValueOp.IN))
                .and("hr_id",hrId).buildQuery();
        List<TalentpoolHrTalentRecord> list=talentpoolHrTalentDao.getRecords(query);
        return list;
    }

}
