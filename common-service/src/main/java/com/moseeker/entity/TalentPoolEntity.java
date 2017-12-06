package com.moseeker.entity;

import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolCommentDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolHrTalentDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolTalentDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyAccountRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolHrTalentRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolTalentRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Autowired
    private TalentpoolCommentDao talentpoolCommentDao;

    /*
        验证hr操作user_id是否合法
        1:有操作权限
        2：hr不属于本公司无法操作
        3，该人未投递过此hr，无法操作
     */
    public int validateHrTalent(int hrId,int userId,int companyId){
        int result=validateHr(hrId,companyId);
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
    public int validateHr(int hrId,int companyId){
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
     验证是否能够添加或者删除备注
     */
    public int validateComment(int hrId,int companyId,int userId){
        int count=this.valiadteMainAccount(hrId,companyId);
        if(count>0){
            return this.valicateCompanyApplication(userId,companyId);
        }
        int result2=this.validatePublisherUserApp(hrId,userId);
        if(result2>0){
            return result2;
        }
        List<TalentpoolTalentRecord> list=getTalentpoolTalentByCompanyId(companyId);
        List<Integer> userIdList=getUserIdListByTalentpoolTalent(list);
        if(!StringUtils.isEmptyList(userIdList)){
            if(userIdList.contains(userId)){
                return 1;
            }
        }
        return 0;
    }
    public int validateUserComment(int id,int userId,int hrId){
        Query query=new Query.QueryBuilder().where("hrId",hrId).and("id",id).and("user_id",userId).buildQuery();
        int count=talentpoolCommentDao.getCount(query);
        return count;
    }
    /*
     获取一个人才被这个公司下hr的收藏情况
     */
    public List<UserHrAccountRecord>  getHrAboutTalent(int userId,int companyId){
        List<UserHrAccountRecord> userHrList=this.getCompanyHrList(companyId);
        List<Integer> hrAccountIdList=this.getIdListByUserHrAccountList(userHrList);
        List<TalentpoolHrTalentRecord> talentpoolHrList=this.getTalentpoolHrTalentByuserIdAndhrIdList(userId,hrAccountIdList);
        List<Integer> hrIdList=this.getIdListByTalentpoolHrTalentList(talentpoolHrList);
        List<UserHrAccountRecord> result=this.getHrList(hrIdList,userHrList);
        return result;
    }
    /*
      获取多个人被这家公司下hr的收藏情况
     */
    public Map<Integer,Set<UserHrAccountRecord>> getBatchAboutTalent(List<Integer> userIdList,List<UserHrAccountRecord >userHrList){
        if(StringUtils.isEmptyList(userIdList)||StringUtils.isEmptyList(userHrList)){
            return null;
        }
        List<Integer> hrAccountIdList=this.getIdListByUserHrAccountList(userHrList);
        Map<Integer,Set<UserHrAccountRecord>> result=this.handlerTalentAndHr(userIdList,hrAccountIdList,userHrList);
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
      过滤出来不符合操作权限的applier_id
     */
    public List<Integer> filterIdList(List<Integer> userIdList,List<Integer> list){
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        List<Integer> result=new ArrayList<>();
        for(Integer userId:userIdList){
            for(Integer id:list){
                if(userId==id){
                    break;
                }
                result.add(userId);
            }
        }
        return result;
    }
    /*
      处理批量处理标签的公开人才部分的校验
     */
    public Map<String,Object> filterNoPowerUserId(List<Integer> userIdList,int companyId){
        if(StringUtils.isEmptyList(userIdList)){
            return null;
        }
        Map<String,Object> result=new HashMap<>();
        List<TalentpoolTalentRecord> talentList=this.getTalentpoolTalentByCompanyId(companyId);
        List<Integer> talentIdList=this.getUserIdListByTalentpoolTalent(talentList);
        if(StringUtils.isEmptyList(talentIdList)){
            result.put("nopower",userIdList);
            result.put("use",null);
        }
        List<Integer> noUseIdList=new ArrayList<>();
        for(Integer userId:userIdList){
            if(!talentIdList.contains(userId)){
                noUseIdList.add(userId);
            }
        }
        result.put("nopower",noUseIdList);
        result.put("use",talentIdList);
        return result;
    }
    /*
     获取一个公司下所有的公开的人才
     */
    private List<TalentpoolTalentRecord> getTalentpoolTalentByCompanyId(int companyId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).and(new Condition("public_num",0,ValueOp.GT)).buildQuery();
        List<TalentpoolTalentRecord> list=talentpoolTalentDao.getRecords(query);
        return list;
    }

    /*
      根据TalentpoolTalentRecord获取userId
     */
    public List<Integer> getUserIdListByTalentpoolTalent(List<TalentpoolTalentRecord> list){
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        List<Integer> result=new ArrayList<>();
        for(TalentpoolTalentRecord record:list){
            result.add(record.getUserId());
        }
        return result;
    }


    /*
     验证user_id是否投递过这个hr
     */
    private int validateApplication(int hrId,int userId,int companyId){
        int result1=this.valiadteMainAccount(hrId,companyId);
        if(result1>0){
            return  this.valicateCompanyApplication(userId,companyId);
        }else{
            int result2=this.validatePublisherUserApp(hrId,userId);
            return result2;
        }
    }
    private int validatePublisherUserApp(int hrId,int userId){

        Query query=new Query.QueryBuilder().where("publisher",hrId).and("applier_id",userId).buildQuery();
        int result=jobApplicationDao.getCount(query);
        return result;
    }
    /*
     验证是否是主账号
     */
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


    private  Map<Integer,Set<UserHrAccountRecord>> handlerTalentAndHr(List<Integer> userIdList,List<Integer> hrAccountIdList,List<UserHrAccountRecord> userHrList){
        if(StringUtils.isEmptyList(userIdList)||StringUtils.isEmptyList(hrAccountIdList)||StringUtils.isEmptyList(userHrList)){
            return null;
        }
        List<TalentpoolHrTalentRecord> talentpoolHrList=this.getTalentpoolHrTalentByuserIdListAndhrIdList(userIdList,hrAccountIdList);
        if(StringUtils.isEmptyList(talentpoolHrList)){
            return null;
        }
        Map<Integer,Set<Integer>> userIdHrMap=this.handleTalentAndRecord(userIdList,talentpoolHrList);
        if(userIdHrMap==null||userIdHrMap.isEmpty()){
            return null;
        }
        Map<Integer,Set<UserHrAccountRecord>> result=this.handleDataResult(userIdHrMap,userHrList);
        return result;
    }
    /*
      处理人才和人才库记录的数据，获取map的数据map<人才id，hr的set集合>
     */
    private Map<Integer,Set<Integer>> handleTalentAndRecord(List<Integer> userIdList,List<TalentpoolHrTalentRecord> talentpoolHrList){
        Map<Integer,Set<Integer>> userIdHrMap=new HashMap<>();
        for(Integer userId:userIdList){
            for(TalentpoolHrTalentRecord record:talentpoolHrList){
                int applierId=record.getUserId();
                int hrId=record.getHrId();
                if(userId==applierId){
                    if(userIdHrMap.get(userId)==null){
                        Set<Integer> hrIdSet=new HashSet<>();
                        hrIdSet.add(hrId);
                        userIdHrMap.put(userId,hrIdSet);
                    }else{
                        Set<Integer> hrIdSet=userIdHrMap.get(userId);
                        hrIdSet.add(hrId);
                        userIdHrMap.put(userId,hrIdSet);
                    }
                }
            }
        }
        return userIdHrMap;
    }
    /*
      根据map<人才id，hr的set集合> 处理hr的记录，获取map<人才id,hr记录的集合>
     */
    private Map<Integer,Set<UserHrAccountRecord>>  handleDataResult(Map<Integer,Set<Integer>> userIdHrMap,List<UserHrAccountRecord> userHrList){
        if(userIdHrMap==null||userIdHrMap.isEmpty()||StringUtils.isEmptyList(userHrList)){
            return null;
        }
        Map<Integer,Set<UserHrAccountRecord>> result=new HashMap<>();
        for(Integer key:userIdHrMap.keySet()){
            Set<Integer> hrIdSet=userIdHrMap.get(key);
            if(hrIdSet==null||hrIdSet.size()==0){
                result.put(key,null);
                break;
            }
            Set<UserHrAccountRecord> hrSet=new HashSet<>();
            for(Integer hrId:hrIdSet){
                for(UserHrAccountRecord record:userHrList){
                    int id=record.getId();
                    if(hrId==id){
                        hrSet.add(record);
                        break;
                    }
                }
            }
            result.put(key,hrSet);
        }
        return result;
    }
    /*
      获取公司下所有的hr信息
     */
    public List<UserHrAccountRecord> getCompanyHrList(int companyId){
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
     通过user集合和hr集合获取人才库记录
     */
    private List<TalentpoolHrTalentRecord> getTalentpoolHrTalentByuserIdListAndhrIdList(List<Integer> userIdList,List<Integer> hrIdList){
        if(StringUtils.isEmptyList(hrIdList)||StringUtils.isEmptyList(userIdList)){
            return null;
        }
        Query query=new Query.QueryBuilder().where(new Condition("user_id",userIdList.toArray(),ValueOp.IN)).and(new Condition("hr_id",hrIdList.toArray(),ValueOp.IN)).buildQuery();
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
    private List<Integer> getCompanyIdList(int companyId,List<HrCompanyRecord> list){
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
    private List<Integer> getHrIdByCompanyAccountList(List<HrCompanyAccountRecord> list){
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
    private List<Integer> getIdListByUserHrAccountList(List<UserHrAccountRecord> list){
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
     通过hr_id list获取hrd的信息
     */
    private List<UserHrAccountRecord> getHrList(List<Integer> hrIdList,List<UserHrAccountRecord> hrList){
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

    private List<JobApplicationRecord> getJobApplicationByPublisherAndApplierId(List<Integer> userIdList,int hrId){
        Query query=new Query.QueryBuilder().where("publisher",hrId).and(new Condition("applier_id",userIdList.toArray(),ValueOp.IN))
                .buildQuery();
        List<JobApplicationRecord> list=jobApplicationDao.getRecords(query);
        return list;
    }

    private List<JobApplicationRecord> getJobApplicationByCompanyIdAndApplierId(List<Integer> userIdList,int companyId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).and(new Condition("applier_id",userIdList.toArray(),ValueOp.IN))
                .buildQuery();
        List<JobApplicationRecord> list=jobApplicationDao.getRecords(query);
        return list;
    }
    /*
     处理批量传输的人才，获取其中有效的和无效的
     */
    public Map<String,Object> handlerApplierId(int hrId,List<Integer> userIdList,int companyId){
        int flag= this.valiadteMainAccount(hrId,companyId);
        List<Integer> unUsedApplierIdList=new ArrayList<>();
        List<Integer> applierIdList=new ArrayList<>();
        if(flag>0){
            List<JobApplicationRecord> list=this.getJobApplicationByPublisherAndApplierId(userIdList,hrId);
            applierIdList=this.getIdListByApplicationList(list);
        }else{
            List<JobApplicationRecord> list=this.getJobApplicationByCompanyIdAndApplierId(userIdList,hrId);
            applierIdList=this.getIdListByApplicationList(list);
        }
        unUsedApplierIdList= this.filterIdList(userIdList,applierIdList);
        Map<String,Object> result=new HashMap<>();
        result.put("unuse",unUsedApplierIdList);
        result.put("use",applierIdList);
        return result;
    }
    /*
      获取applierId
     */
    private List<Integer> getIdListByApplicationList(List<JobApplicationRecord> list){
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        List<Integer> result=new ArrayList<>();
        for(JobApplicationRecord record:list){
            result.add(record.getApplierId());
        }
        return result;
    }


}
