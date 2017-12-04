package com.moseeker.company.service.impl;

import com.moseeker.baseorm.dao.talentpooldb.TalentpoolHrTalentDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolTalentDao;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolHrTalentRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.TalentPoolEntity;
import com.moseeker.thrift.gen.common.struct.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private TalentpoolTalentDao talentpoolTalentDao;
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
      取消收藏人才
     */
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
      批量添加人才
     */
    public Map<String,Object> batchAddTalent(int hrId, List<Integer> userId, int companyId){

        return null;
    }

    /*
      批量取消人才
     */
    public Map<String,Object> batchCancleTalent(int hrId, List<Integer> userId, int companyId){

        return null;
    }
}
