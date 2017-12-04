package com.moseeker.company.service.impl;

import com.moseeker.baseorm.dao.talentpooldb.TalentpoolHrTalentDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolTalentDao;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolHrTalentRecord;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String,Object> result=new HashMap<>();
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
        result.put("nopower",unUseList);
        result.put("repeat",unApplierIdList);
        result.put("userIds",idList);


        return ResponseUtils.success(result);
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
        Map<String,Object> result=new HashMap<>();
        result.put("nopower",unUseList);
        result.put("norecord",unApplierIdList);
        result.put("userIds",idList);
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
    public Response addBatchTalentTag(){

        return null;
    }
    /*
     删除标签
     */
    public Response cancaleTalentTag(){

        return null;
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
            return ResponseUtils.fail(1,"该人未投递过此hr，无法操作");
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
