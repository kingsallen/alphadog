package com.moseeker.entity;

import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolHrTalentDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolTalentDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyAccountRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolHrTalentRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolTalentRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zztaiwll on 17/12/1.
 */
@Service
public class TalentPoolEntity {
    @Autowired
    private JobApplicationDao jobApplicationDao;
    @Autowired
    private UserHrAccountDao userHrAccountDao;
    @Autowired
    private HrCompanyDao hrCompanyDao;
    @Autowired
    private TalentpoolTalentDao talentpoolTalentDao;
    @Autowired
    private TalentpoolHrTalentDao talentpoolHrTalentDao;
    @Autowired
    private HrCompanyAccountDao hrCompanyAccountDao;

    /*
        验证hr操作user_id是否合法
        1:有操作权限
        2：hr不属于本公司无法操作
        3，该人未投递过此hr，无法操作
     */
    public int validateHrTalent(int hrId,int userId,int companyId){
        int result=this.validateHr(hrId,companyId);
        if(result==0){
            return 2;
        }
        int result1=this.validateApplication(hrId,userId,companyId);
        if(result1==0){
            return 3;
        }
        return 1;
    }

    /*
     验证这个hr是否是这家公司的
     */
    private int validateHr(int hrId,int companyId){
        List<UserHrAccountRecord> hrList=getCompanyHrList(companyId);
        List<Integer> hrIdList=this.getIdListByUserHrAccountList(hrList);
        if(StringUtils.isEmptyList(hrList)){
            return 0;
        }
        if(!hrIdList.contains(hrId)){
            return 0;
        }
        return 1;
    }
    /*
     验证user_id是否投递过这个hr
     */
    private int validateApplication(int hrId,int userId,int companyId){
        int result1=this.valiadteMainAccount(hrId,companyId);
        if(result1>0){
            return  this.valicateCompanyApplication(userId,companyId);
        }else{
            int result2=this.validatePublisheruserApp(hrId,userId);
            return result2;
        }
    }
    private int validatePublisheruserApp(int hrId,int userId){

        Query query=new Query.QueryBuilder().where("publisher",hrId).and("applier_id",userId).buildQuery();
        int result=jobApplicationDao.getCount(query);
        return result;
    }

    private int valiadteMainAccount(int hrId,int companyId){
        Query query=new Query.QueryBuilder().where("id",hrId).and("company_id",companyId).and("account_type",0).buildQuery();
        int result=userHrAccountDao.getCount(query);
        return result;
    }

    private int valicateCompanyApplication(int userId,int companyId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).and("applier_id",userId).buildQuery();
        int result=jobApplicationDao.getCount(query);
        return result;
    }

    public List<UserHrAccountRecord>  getHrAboutTalent(int userId,int companyId){
       List<UserHrAccountRecord> userHrList=this.getCompanyHrList(companyId);
       List<Integer> hrAccountIdList=this.getIdListByUserHrAccountList(userHrList);
       List<TalentpoolHrTalentRecord> talentpoolHrList=this.getTalentpoolHrTalentByuserIdAndhrIdList(userId,hrAccountIdList);
       List<Integer> hrIdList=this.getIdListByTalentpoolHrTalentList(talentpoolHrList);
       List<UserHrAccountRecord> result=this.getHrList(hrIdList,userHrList);
       return result;
    }
    /*
      获取公司下所有的hr信息
     */
    private List<UserHrAccountRecord> getCompanyHrList(int companyId){
        List<HrCompanyRecord> companyList= this.getChildCompany(companyId);
        List<Integer> companyIdList=this.getCompanyIdList(companyId,companyList);
        List<HrCompanyAccountRecord> companyAccountList=this.getHrCompanyAccountById(companyIdList);
        List<Integer> accountIdList=this.getHrIdByCompanyAccountList(companyAccountList);
        List<UserHrAccountRecord> userHrList=this.getHrByhrIdListAndCompanyId(companyId,accountIdList);
        return userHrList;
    }
    /*
      获取在本公司内搜藏这个人才的记录
     */
    private List<TalentpoolHrTalentRecord> getTalentpoolHrTalentByuserIdAndhrIdList(int userId,List<Integer> hrIdList){
        if(StringUtils.isEmptyList(hrIdList)){
            return null;
        }
        Query query=new Query.QueryBuilder().where("user_id",userId).and(new Condition("hr_id",hrIdList.toArray(),ValueOp.IN)).buildQuery();
        List<TalentpoolHrTalentRecord> list=talentpoolHrTalentDao.getRecords(query);
        return list;
    }
    /*
      获取hr列表下所有属于该公司的hr
     */
    private List<UserHrAccountRecord> getHrByhrIdListAndCompanyId(int companyId,List<Integer> hrIdList){
        if(StringUtils.isEmptyList(hrIdList)){
            return null;
        }
        Query query=new Query.QueryBuilder().where("company_id",companyId).and("disable",0)
                        .and(new Condition("id",hrIdList.toArray(), ValueOp.IN))
                        .buildQuery();
        List<UserHrAccountRecord> list=userHrAccountDao.getRecords(query);

        return list;
    }
    /*
      获取公司下所有的有效的子公司
     */
    private List<HrCompanyRecord> getChildCompany(int companyId){
        Query query=new Query.QueryBuilder().where("parent_id",companyId).and("disable",1)
                .buildQuery();
        List<HrCompanyRecord> list=hrCompanyDao.getRecords(query);
        return list;
    }
    /*
     根据公司列表，获取公司id的列表
     */
    public List<Integer> getCompanyIdList(int companyId,List<HrCompanyRecord> list){
        List<Integer> hrIdList=new ArrayList<>();
        hrIdList.add(companyId);
        if(StringUtils.isEmptyList(list)){
            return hrIdList;
        }
        for(HrCompanyRecord record: list){
            hrIdList.add(record.getId());
        }
        return hrIdList;
    }
    /*
     获取所有的公司和hr关连
     */
    private List<HrCompanyAccountRecord> getHrCompanyAccountById(List<Integer> idList){
        if(StringUtils.isEmptyList(idList)){
            return null;
        }
        Query query=new Query.QueryBuilder().where(new Condition("company_id",idList.toArray(),ValueOp.IN)).buildQuery();
        List<HrCompanyAccountRecord> list=hrCompanyAccountDao.getRecords(query);
        return  list;
    }
    /*
     通过HrCompanyAccountRecord获取hrId list
     */
    public List<Integer> getHrIdByCompanyAccountList(List<HrCompanyAccountRecord> list){
        List<Integer> hrIdList=new ArrayList<>();

        if(StringUtils.isEmptyList(list)){
            return null;
        }
        for(HrCompanyAccountRecord record: list){
            hrIdList.add(record.getAccountId());
        }
        return hrIdList;
    }

    /*
      通过user_Hr_Account的list获取hrList
     */
    public List<Integer> getIdListByUserHrAccountList(List<UserHrAccountRecord> list){
        if(StringUtils.isEmptyList(list)){
           return null;
        }
        List<Integer> hrIdList=new ArrayList<>();
        for(UserHrAccountRecord record:list){
            hrIdList.add(record.getId());
        }
        return hrIdList;
    }

    /*
     通过TalentpoolHrTalentRecord 的集合获取hr_id的list
     */
    public List<Integer> getIdListByTalentpoolHrTalentList(List<TalentpoolHrTalentRecord> list){
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        List<Integer> hrIdList=new ArrayList<>();
        for(TalentpoolHrTalentRecord record:list){
            hrIdList.add(record.getHrId());
        }
        return hrIdList;
    }

    /*
     通过hr_id list获取hrd的信息
     */
    public List<UserHrAccountRecord> getHrList(List<Integer> hrIdList,List<UserHrAccountRecord> hrList){
        if(StringUtils.isEmptyList(hrList)||StringUtils.isEmptyList(hrIdList)){
            return null;
        }
        List<UserHrAccountRecord> result=new ArrayList<>();
        for(Integer hrId:hrIdList){
            for(UserHrAccountRecord record:hrList){
                int id=record.getId();
                if(hrId==id){
                    result.add(record);
                    break;
                }
            }
        }
        return result;
    }

    /*
       处理talentpool_talent
     */
    public void handlerTalentpoolTalent(int userId,int companyId,int upload,int publicNum,int collectNum){
        TalentpoolTalentRecord record=this.getTalentpoolTalentRecord(companyId,userId);
        if(record==null){
            if(publicNum>0 || collectNum>0) {
                addTalentpoolTalent(userId, companyId, upload);
            }
        }else{
            int originCollection=record.getCollectNum();
            if(collectNum<0&&Math.abs(collectNum)>originCollection){
                this.deleteTalentpoolTalent(userId,companyId);
            }else{
                this.updateTalentpoolTalentNum(userId,companyId,publicNum,collectNum);
            }
        }
    }
    /*
      获取talentpool_talent
     */
    private TalentpoolTalentRecord getTalentpoolTalentRecord(int companyId,int userId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).and("user_id",userId).buildQuery();
        TalentpoolTalentRecord record=talentpoolTalentDao.getRecord(query);
        return record;
    }
    /*
     添加talentpool_talent记录
     */
    private void addTalentpoolTalent(int userId,int companyId,int upload){
        TalentpoolTalentRecord talentpoolTalentRecord=new TalentpoolTalentRecord();
        talentpoolTalentRecord.setCompanyId(companyId);
        talentpoolTalentRecord.setUserId(userId);
        talentpoolTalentRecord.setCollectNum(1);
        talentpoolTalentRecord.setUpload((byte)upload);
        talentpoolTalentDao.addRecord(talentpoolTalentRecord);

    }
    /*
     修改talentpool_talent记录
     */
    private int updateTalentpoolTalentNum(int userId,int companyId,int publicNum,int collectNum){
        int result=talentpoolTalentDao.updateNum(userId,companyId,publicNum,collectNum);
        return result;
    }
    /*
     移除talentpool_talent
     */

    private int deleteTalentpoolTalent(int userId,int companyId){
        TalentpoolTalentRecord talentpoolTalentRecord=new TalentpoolTalentRecord();
        talentpoolTalentRecord.setCompanyId(companyId);
        talentpoolTalentRecord.setUserId(userId);
        int result=talentpoolTalentDao.deleteRecord(talentpoolTalentRecord);
        return result;
    }



}
