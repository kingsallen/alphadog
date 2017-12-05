package com.moseeker.company.service.impl;

import com.moseeker.baseorm.dao.talentpooldb.TalentpoolHrTalentDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolTagDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolTalentDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolUserTagDao;
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
    public Response batchAddTalent(int hrId, List<Integer> userIdList, int companyId){
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        Map<String,Object> validateMap=talentPoolEntity.handlerApplierId(hrId,userIdList,companyId);
        List<Integer> unUseList= (List<Integer>) validateMap.get("unuse");
        List<Integer> applierIdList= (List<Integer>) validateMap.get("use");
        if(StringUtils.isEmptyList(applierIdList)){
            return ResponseUtils.fail(1,"hr无权操作人才");
        }
        List<TalentpoolHrTalentRecord> talentList=this.getTalentpoolHrTalentByIdList(hrId,applierIdList);
        List<Integer> unApplierIdList=talentPoolEntity.getIdListByTalentpoolHrTalentList(talentList);
        List<Integer> idList=talentPoolEntity.filterIdList(applierIdList,unApplierIdList);
        if(!StringUtils.isEmptyList(idList)){
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
        return ResponseUtils.success(result);
    }

    private Map<String,Object> handlerBatchTalentResult( List<Integer> unUseList,List<Integer>unApplierIdList,List<Integer> idList ,int companyd){
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
        return ResponseUtils.success(result);
    }
    /*
      批量取消人才
     */
    @CounterIface
    public Response batchCancleTalent(int hrId, List<Integer> userIdList, int companyId){
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            return ResponseUtils.fail(1,"该hr不属于该company_id");
        }
        Map<String,Object> validateMap=talentPoolEntity.handlerApplierId(hrId,userIdList,companyId);
        List<Integer> unUseList= (List<Integer>) validateMap.get("unuse");
        List<Integer> applierIdList= (List<Integer>) validateMap.get("use");
        if(StringUtils.isEmptyList(applierIdList)){
            return ResponseUtils.fail(1,"hr无权操作人才");
        }
        List<TalentpoolHrTalentRecord> talentList=this.getTalentpoolHrTalentByIdList(hrId,applierIdList);
        List<Integer> idList=talentPoolEntity.getIdListByTalentpoolHrTalentList(talentList);
        List<Integer> unApplierIdList=talentPoolEntity.filterIdList(applierIdList,idList);
        if(!StringUtils.isEmptyList(idList)){
            List<TalentpoolHrTalentRecord> recordList=new ArrayList<>();
            for(Integer userId:idList){
                TalentpoolHrTalentRecord record=new TalentpoolHrTalentRecord();
                record.setUserId(userId);
                record.setHrId(hrId);
                recordList.add(record);
            }
            talentpoolHrTalentDao.deleteRecords(recordList);
            for(Integer id:idList){
                talentPoolEntity.handlerTalentpoolTalent(id,companyId,0,0,-1);
            }
        }
        Map<String,Object> result=this.handlerBatchTalentResult(unUseList,unApplierIdList,idList,companyId);
        return ResponseUtils.success(result);
    }
    /*
      添加标签
     */
    public Response addTalentTag(){

        return null;
    }
    /*
     批量添加标签
     */
    public Response addBatchTalentTag(int hrId,List<Integer> userIdList,List<Integer> tagIdList,int companyId){
        Map<String,Object> validateResult=this.validateAddTag(hrId, userIdList, tagIdList, companyId);
        if(validateResult.get("result")!=null){
            return (Response)validateResult.get("result");
        }
        List<Integer> idList= (List<Integer>) validateResult.get("idList");
        List<Integer> nouseList= (List<Integer>)validateResult.get("nouseList");
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
        return null;
    }
    /*
     删除标签
     */
    public Response cancaleTalentTag(int hrId,List<Integer> userIdList,List<Integer> tagIdList,int companyId){

        Map<String,Object> validateResult=this.validateCancleTag(hrId, userIdList, tagIdList, companyId);
        if(validateResult.get("result")!=null){
            return (Response)validateResult.get("result");
        }
        List<Integer> idList= (List<Integer>) validateResult.get("idList");
        List<Integer> nouseList= (List<Integer>)validateResult.get("nouseList");
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



        return null;
    }

    private Map<String,Object> validateCancleTag(int hrId,List<Integer> userIdList,List<Integer> tagIdList,int companyId){
        Map<String,Object> result=new HashMap<>();
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            result.put("result",ResponseUtils.fail(1,"该hr不属于该company_id"));
            return result;
        }
        Map<String,Object> validateResult=this.validateUserIdTag(hrId,userIdList,tagIdList,companyId);
        List<Integer> idList= (List<Integer>) validateResult.get("use");
        if(StringUtils.isEmptyList(idList)){
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
        List<Integer> userTagList= (List<Integer>) validateResult.get("userTagIdList");
        boolean validateOperTag=this.validateCancleOperatorTag(this.getIdByTagList(hrTagList),tagIdList,userTagList);
        if(!validateOperTag){
            result.put("result",ResponseUtils.fail(1,"操作的标签不是hr定义的标签"));
            return result;
        }
        result.put("hrTagList",hrTagList);
        result.put("idList",idList);
        result.put("nouseList",(List<Integer>)validateResult.get("nouse"));
        return result;
    }
    private Map<String,Object> validateAddTag(int hrId,List<Integer> userIdList,List<Integer> tagIdList,int companyId){
        Map<String,Object> result=new HashMap<>();
        int flag=talentPoolEntity.validateHr(hrId,companyId);
        if(flag==0){
            result.put("result",ResponseUtils.fail(1,"该hr不属于该company_id"));
            return result;
        }
        Map<String,Object> validateResult=this.validateUserIdTag(hrId,userIdList,tagIdList,companyId);
        List<Integer> idList= (List<Integer>) validateResult.get("use");
        if(StringUtils.isEmptyList(idList)){
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
        List<Integer> userTagList= (List<Integer>) validateResult.get("userTagIdList");
        boolean validateOperTag=this.validateAddOperatorTag(this.getIdByTagList(hrTagList),tagIdList,userTagList);
        if(!validateOperTag){
            result.put("result",ResponseUtils.fail(1,"操作的标签不是hr定义的标签"));
            return result;
        }
        result.put("hrTagList",hrTagList);
        result.put("idList",idList);
        result.put("nouseList",(List<Integer>)validateResult.get("nouse"));
        return result;
    }
    private Map<String,Object> validateUserIdTag(int hrId,List<Integer> userIdList,List<Integer> tagIdList,int companyId){
        Map<String,Object> validateMap=talentPoolEntity.handlerApplierId(hrId,userIdList,companyId);
        List<Integer> unUseList= (List<Integer>) validateMap.get("unuse");
        List<Integer> applierIdList= (List<Integer>) validateMap.get("use");
        List<Integer> pubTalentIdList=new ArrayList<>();
        if(!StringUtils.isEmptyList(unUseList)){
            Map<String,Object> validateData=talentPoolEntity.filterNoPowerUserId(unUseList,companyId);
            unUseList=(List<Integer>) validateData.get("unuse");
            pubTalentIdList= (List<Integer>) validateData.get("use");
        }
        List<TalentpoolHrTalentRecord> talentList=this.getTalentpoolHrTalentByIdList(hrId,applierIdList);
        List<Integer> unApplierIdList=talentPoolEntity.getIdListByTalentpoolHrTalentList(talentList);
        List<Integer> idList=talentPoolEntity.filterIdList(applierIdList,unApplierIdList);
        if(!StringUtils.isEmptyList(pubTalentIdList)){
            if(StringUtils.isEmptyList(idList)){
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
    public Map<String,Object> validateTag(List<Integer> userIdList,int hrId){
        Map<String,Object> result=new HashMap<>();
        //获取hr下所有的tag
        List<TalentpoolTagRecord> hrTagList=this.getTagByHr(hrId);
        //获取hr下所有的tagId
        List<Integer> hrTagIdList=this.getIdByTagList(hrTagList);
        if(StringUtils.isEmptyList(hrTagIdList)){
            result.put("result",false);
            return result;
        }
        List<Integer> tagIdList=this.getUserTagIdList(userIdList.get(0),hrTagIdList);
        if(StringUtils.isEmptyList(tagIdList)){
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
    private boolean validateAddOperatorTag(List<Integer> hrTagIdList,List<Integer> tagIdList, List<Integer> userTagIdList){

        for(Integer id:tagIdList){
           if(!hrTagIdList.contains(id)) {
               return false;
           }
        }
        if(!StringUtils.isEmptyList(userTagIdList)){
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
    private boolean validateCancleOperatorTag(List<Integer> hrTagIdList,List<Integer> tagIdList, List<Integer> userTagIdList){
        for(Integer id:tagIdList){
            if(!hrTagIdList.contains(id)) {
                return false;
            }
        }
        if(!StringUtils.isEmptyList(userTagIdList)){
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
    private List<TalentpoolTagRecord> getTagByHr(int hrId){
        Query query=new Query.QueryBuilder().where("hr_id",hrId).buildQuery();
        List<TalentpoolTagRecord> list= talentpoolTagDao.getRecords(query);
        return list;
    }
    /*
     获取TalentpoolTagRecord 集合的id
     */
    private List<Integer> getIdByTagList(List<TalentpoolTagRecord> list){
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        List<Integer> result=new ArrayList<>();
        for(TalentpoolTagRecord record:list){
            result.add(record.getId());
        }
        return result;
    }
    /*
     获取任意一个人才在这家公司下的所有的标签的id
     */
    private List<Integer> getUserTagIdList(int userId,List<Integer> tagIdList){
        List<TalentpoolUserTagRecord> userTagList=getUserTagByUserIdAndTagId(userId,tagIdList);
        List<Integer> idList=this.getTagIdByUserTagList(userTagList);
        return idList;
    }
    /*
     获取一个人才在这个hr下拥有的标签
     */
    private List<TalentpoolUserTagRecord> getUserTagByUserIdAndTagId(int userId,List<Integer> tagIdList){
        if(StringUtils.isEmptyList(tagIdList)){
            return null;
        }
        Query query=new Query.QueryBuilder().where("user_id",userId).and(new Condition("tag_id",tagIdList.toArray(),ValueOp.IN)).buildQuery();
        List<TalentpoolUserTagRecord> list=talentpoolUserTagDao.getRecords(query);
        return list;
    }

    /*
     获取TalentpoolTagRecord 集合的id
     */
    private List<Integer> getTagIdByUserTagList(List<TalentpoolUserTagRecord> list){
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        List<Integer> result=new ArrayList<>();
        for(TalentpoolUserTagRecord record:list){
            result.add(record.getTagId());
        }
        return result;
    }

    /*
      获取userIdList在该hr下的所有的标签
     */
    private List<TalentpoolUserTagRecord> getUserTagByUserIdListAndTagId(List<Integer> userIdList,List<Integer> tagIdList){
        if(StringUtils.isEmptyList(tagIdList)||StringUtils.isEmptyList(userIdList)){
            return null;
        }
        Query query=new Query.QueryBuilder().where(new Condition("user_id",userIdList.toArray(),ValueOp.IN))
                                            .and(new Condition("tag_id",tagIdList.toArray(),ValueOp.IN)).buildQuery();
        List<TalentpoolUserTagRecord> list=talentpoolUserTagDao.getRecords(query);
        return list;
    }

    /*
      批量删除标签
     */
    public Response cancleBatchTalentTag(){

        return null;
    }
    /*
     添加标签
     */
    public Response addHrTag(){

        return null;
    }
    /*
     删除标签
     */
    public Response deleteHrTag(){

        return null;
    }
    /*
    修改标签
     */
    public Response updateHrTag(){

        return null;
    }

    /*
     批量添加标签
     */
    public Response addBatchHrTag(){

        return null;
    }
    /*
     批量删除标签
     */
    public Response deleteBatchHrTag(){

        return null;
    }
    /*
     批量修改标签
     */
    public Response updateBatchHrTag(){

        return null;
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
    private List<TalentpoolHrTalentRecord> getTalentpoolHrTalentByIdList(int hrId, List<Integer> userIdList){
        Query query=new Query.QueryBuilder().where(new Condition("user_id",userIdList.toArray(), ValueOp.IN))
                .and("hr_id",hrId).buildQuery();
        List<TalentpoolHrTalentRecord> list=talentpoolHrTalentDao.getRecords(query);
        return list;
    }

}
