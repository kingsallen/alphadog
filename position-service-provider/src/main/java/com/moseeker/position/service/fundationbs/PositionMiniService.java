package com.moseeker.position.service.fundationbs;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrRecruitStatisticsDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyAccount;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyAccountRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrRecruitStatisticsRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserHrAccount;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.TalentPoolEntity;
import com.moseeker.entity.UserAccountEntity;
import com.moseeker.position.pojo.CompanyAccount;
import com.moseeker.position.pojo.PositionMiniBean;
import com.moseeker.position.pojo.PositionMiniInfo;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zztaiwll on 18/1/24.
 */
@Service
public class PositionMiniService {
    @Autowired
    public UserHrAccountDao userHrAccountDao;
    @Autowired
    public HrCompanyAccountDao hrCompanyAccountDao;
    @Autowired
    public HrCompanyDao hrCompanyDao;
    @Autowired
    public JobPositionDao jobPositionDao;
    @Autowired
    public JobApplicationDao jobApplicationDao;
    @Autowired
    public TalentPoolEntity talentPoolEntity;
    @Autowired
    public HrRecruitStatisticsDao hrRecruitStatisticsDao;
    SearchengineServices.Iface searchengineServices = ServiceManager.SERVICEMANAGER.getService(SearchengineServices.Iface.class);
    @CounterIface
    public PositionMiniBean getPositionMiniList(int accountId,String keyWord,int page,int pageSize) throws TException {
        CompanyAccount account=this.getAccountInfo(accountId);
        PositionMiniBean result=this.getAccountStatus(keyWord,page,pageSize,account);
        List<PositionMiniInfo> list=getSearchdata(keyWord,page,pageSize,account);
        this.handlerPositionMiniInfoList(list);
        result.setPositionList(list);
        this.handlerData(result,account);
        return result;
    }

    @CounterIface
    public  Map<String,Object> getPositionMiniSug(int accountId,String keyword,int page,int pageSize) throws TException {
        Map<String,String> params=this.handlerParams(accountId,keyword,page,pageSize);
        Response res=searchengineServices.searchPositionSuggest(params);
        if(res.getStatus()==0&&StringUtils.isNotNullOrEmpty(res.getData())){
            if("\"\"".equals(res.getData().trim())){
                return null;
            }
            Map<String,Object> result=JSON.parseObject(res.getData(),Map.class);
            return result;
        }
        return null;
    }
    /*
     获取es的数据并处理
     */


    /*
      获取请求es的参数
     */
    private Map<String,String> handlerParams(int accountId,String keyword,int pageNum,int pageSize){
        Map<String,String> map=new HashMap<>();
        UserHrAccount account=userHrAccountDao.getHrAccount(accountId);
        if(account.getAccountType()==0){
            map.put("company_id",String.valueOf(account.getCompanyId()));
        }else{
            map.put("publisher",String.valueOf(accountId));
        }
        map.put("keyWord",keyword);
        map.put("page_from",String.valueOf(pageNum));
        map.put("page_size",String.valueOf(pageSize));
        map.put("return_params","title");
        map.put("flag","1");
        return map;
    }




    /*
    将userHrAccount的数据存放到PositionMiniInfo中
     */
    public void handlerData(PositionMiniBean result,CompanyAccount account){
        UserHrAccount userHrAccount=account.getUserHrAccount();
        if(userHrAccount.getAccountType()==0){
            int companyId= userHrAccount.getCompanyId();
            List<Map<String,Object>> list=getHrByCompanyId(companyId);
            if(!StringUtils.isEmptyList(list)){
                List<PositionMiniInfo> positionList=result.getPositionList();
                if(!StringUtils.isEmptyList(positionList)){
                    for(PositionMiniInfo info:positionList){
                        int publisher=info.getPublisher();
                        for(Map<String,Object> map:list){
                            int id=(int)map.get("id");
                            if(id==publisher){
                                Map<String,Object> newMap=StringUtils.convertUnderKeyToCamel(map);
                                UserHrAccount hrAccount=JSON.parseObject(JSON.toJSONString(newMap),UserHrAccount.class);
                                info.setAccount(hrAccount);
                                break;
                            }
                        }
                    }
                }
            }

        }
    }
    /*
     处理PositionMiniInfo数据，获取resumeNum，pageView
     */
    public void handlerPositionMiniInfoList(List<PositionMiniInfo> list){
        if(!StringUtils.isEmptyList(list)){
            List<Integer> positionIdList=this.getPositionIdByPositionMiniInfoList(list);
            if(!StringUtils.isEmptyList(positionIdList)){
                List<HrRecruitStatisticsRecord> applist=this.getAppByPositionId(positionIdList);
                if(!StringUtils.isEmptyList(applist)){
                    for(PositionMiniInfo info:list){
                        int id=info.getId();
                        for(HrRecruitStatisticsRecord recruitStatisticsRecord:applist){
                            int positionId=recruitStatisticsRecord.getPositionId();
                            if(positionId==id){
                                int resumeNum=recruitStatisticsRecord.getApplyNum();
                                info.setResumeNum(resumeNum);
                                continue;
                            }
                        }
                    }
                }
            }
        }

    }
    /*
     根据positionIdList获取收到的简历数
     */
    public List<HrRecruitStatisticsRecord> getAppByPositionId(List<Integer> positionIdList){
        Query query=new Query.QueryBuilder().where(new Condition("position_id",positionIdList.toArray(),ValueOp.IN)).buildQuery();
        List<HrRecruitStatisticsRecord> list=hrRecruitStatisticsDao.getRecords(query);
        return list;
    }
    /*
     根据PositionMiniInfo 获取id列表
     */
    public List<Integer> getPositionIdByPositionMiniInfoList(List<PositionMiniInfo> list){
        if(StringUtils.isEmptyList(list)){
            return null;
        }
        List<Integer> result=new ArrayList<>();
        for(PositionMiniInfo info:list){
            result.add(info.getId());
        }
        return result;
    }
    /*
     根据条件，获取es数据
     */
    public List<PositionMiniInfo> getSearchdata(String keyWord,int page,int pageSize,CompanyAccount account) throws TException {
        Map<String,String> params=new HashMap<>();
        params.put("keyword",keyWord);
        params.put("page",String.valueOf(page));
        params.put("pageSize",String.valueOf(pageSize));
        UserHrAccount userHrAccount=account.getUserHrAccount();
        if(userHrAccount.getAccountType()==0){
            params.put("motherCompanyId",String.valueOf(userHrAccount.getCompanyId()));
        }else{
            params.put("publisher",String.valueOf(userHrAccount.getId()));
        }
        Response res=searchengineServices.queryPositionMini(params);
        if(res.getStatus()==0&& StringUtils.isNotNullOrEmpty(res.getData())){
            if("\"\"".equals(res.getData().trim())){
                return null;
            }
            Map<String,Object> data= JSON.parseObject(res.getData(),Map.class);
            List<PositionMiniInfo> result=this.handlerDatoToresult(data);
            return result;
        }
        return null;
    }

    /*
     组装查询结果
     */
    public List<PositionMiniInfo> handlerDatoToresult(Map<String,Object> data){
        List<PositionMiniInfo> result=new ArrayList<>();
        if(data!=null&&!data.isEmpty()){
            List<Map<String,Object>> list= (List<Map<String, Object>>) data.get("positionList");
            SimpleDateFormat ff=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(!StringUtils.isEmptyList(list)){
                for(Map<String,Object> map:list){
                    PositionMiniInfo info=new PositionMiniInfo();
                    info.setDepartment((String)map.get("department"));
                    info.setId((int)map.get("id"));
                    info.setPageView((int)map.get("visitnum"));
                    info.setPriority((int)map.get("priority"));
                    info.setStatus((int)map.get("status"));
                    info.setTitle((String)map.get("title"));
                    Date date=new Date((long)map.get("update_time"));
                    info.setUpdateTime(ff.format(date));
                    info.setCities((String)map.get("city"));
                    info.setPublisher((int)map.get("publisher"));
                    result.add(info);
                }
            }
        }
        return result;
    }

    public PositionMiniBean getAccountStatus(String keyWord,int page,int pageSize,CompanyAccount account) throws TException {
        PositionMiniBean bean=new PositionMiniBean();
        UserHrAccount account1=account.getUserHrAccount();
        bean.setAccount(account1);
        int accountType=account1.getAccountType();
        if(accountType==0){
            int companyId=account1.getCompanyId();
            Map<String,String> params=new HashMap<>();
            params.put("keyword",keyWord);
            params.put("page",String.valueOf(page));
            params.put("pageSize",String.valueOf(pageSize));
            params.put("motherCompanyId",String.valueOf(companyId));
            params.put("status","0");
            Response res=searchengineServices.queryPositionMini(params);
            if(res.getStatus()==0&& StringUtils.isNotNullOrEmpty(res.getData())){
                Map<String,Object> data= JSON.parseObject(res.getData(),Map.class);
                int total=(int)data.get("totalNum");
                bean.setTrickTotal((int)total);
            }
            params.put("status","1");
            Response res1=searchengineServices.queryPositionMini(params);
            if(res1.getStatus()==0&& StringUtils.isNotNullOrEmpty(res.getData())){
                Map<String,Object> data= JSON.parseObject(res1.getData(),Map.class);
                int total=(int)data.get("totalNum");
                bean.setUnderTatal((int)total);
            }

        }else{
            int accountId=account1.getId();
            Map<String,String> params=new HashMap<>();
            params.put("keyword",keyWord);
            params.put("page",String.valueOf(page));
            params.put("pageSize",String.valueOf(pageSize));
            params.put("publisher",String.valueOf(accountId));
            params.put("status","0");
            Response res=searchengineServices.queryPositionMini(params);
            if(res.getStatus()==0&& StringUtils.isNotNullOrEmpty(res.getData())){
                Map<String,Object> data= JSON.parseObject(res.getData(),Map.class);
                int total=(int)data.get("totalNum");
                bean.setTrickTotal((int)total);
            }
            params.put("status","1");
            Response res1=searchengineServices.queryPositionMini(params);
            if(res1.getStatus()==0&& StringUtils.isNotNullOrEmpty(res.getData())){
                Map<String,Object> data= JSON.parseObject(res1.getData(),Map.class);
                int total=(int)data.get("totalNum");
                bean.setUnderTatal((int)total);
            }
        }
        return bean;
    }
    public List<Map<String,Object>> getHrByCompanyId(int companyId){
        List<Map<String,Object>> list=talentPoolEntity.getCompanyHrList(companyId);
        return list;
    }
    /*
    根据companyId获取hrid的列表
     */
    public Set<Integer> gethrIdByCompanyId(int companyId){
        List<Map<String,Object>> list=getHrByCompanyId(companyId);
        Set<Integer> result=talentPoolEntity.getIdListByUserHrAccountList(list);
        return result;
    }
    //    /*
//     获取主账号下所有的有效值位
//     */
//    public int getPositionUsedByHrIdSet(Set<Integer> hrSet){
//        if(StringUtils.isEmptySet(hrSet)){
//            return 0;
//        }
//        Query query=new Query.QueryBuilder().where(new Condition("publisher",hrSet.toArray(), ValueOp.IN)).and("status",0).buildQuery();
//        int count=jobPositionDao.getCount(query);
//        return count;
//    }
//    /*
//         获取主账号下所有的无效值位
//         */
//    public int getPositionUnUsedByHrIdSet(Set<Integer> hrSet){
//        if(StringUtils.isEmptySet(hrSet)){
//            return 0;
//        }
//        Query query=new Query.QueryBuilder().where(new Condition("publisher",hrSet.toArray(), ValueOp.IN)).and("status",1).buildQuery();
//        int count=jobPositionDao.getCount(query);
//        return count;
//    }
//    /*
//     获取主账号下所有的有效值位
//     */
//    public int getPositionUsedByHrId(int hrId){
//        Query query=new Query.QueryBuilder().where("publisher",hrId).and("status",0).buildQuery();
//        int count=jobPositionDao.getCount(query);
//        return count;
//    }
//    /*
//         获取主账号下所有的无效值位
//         */
//    public int getPositionUnUsedByHrId(int hrId){
//
//        Query query=new Query.QueryBuilder().where("publisher",hrId).and("status",1).buildQuery();
//        int count=jobPositionDao.getCount(query);
//        return count;
//    }
    /*
     校验账号是是否可用,并且获取账号信息和公司信息
     */
    public CompanyAccount getAccountInfo(int accountId){
        CompanyAccount companyAccountBean=new CompanyAccount();
        UserHrAccount account=userHrAccountDao.getHrAccount(accountId);
        if(account!=null){
            HrCompanyAccountRecord companyAccount=getHrCompanyAccount(accountId);
            if(companyAccount!=null){
                int companyId=companyAccount.getCompanyId();
                HrCompany hrCompany=hrCompanyDao.getHrCompanyById(companyId);
                if(hrCompany!=null){
                    companyAccountBean.setHrCompany(hrCompany);
                    companyAccountBean.setUserHrAccount(account);
                }
            }
        }
        return companyAccountBean;
    }
    /*

     */
    /*
     获取所有的再招和下架
     */
    public PositionMiniBean getResultBean(){

        return null;
    }
    /*
     获取所有的hr信息，并转成UserHrAccount
     */
    public List<UserHrAccount> getCompanyHr(int companyId){
        List<Map<String,Object>> hrList=talentPoolEntity.getCompanyHrList(companyId);
        if(StringUtils.isEmptyList(hrList)){
            return null;
        }
        List<UserHrAccount> result=new ArrayList<>();
        for(Map<String,Object> map:hrList){
            Map<String,Object> hrMap=StringUtils.convertUnderKeyToCamel(map);
            UserHrAccount account=JSON.parseObject(JSON.toJSONString(hrMap),UserHrAccount.class);
            result.add(account);
        }
        return result;
    }

    /*
     获取HrCompanyAccount
     */
    public HrCompanyAccountRecord getHrCompanyAccount(int accountId){
        Query query=new Query.QueryBuilder().where("account_id",accountId).buildQuery();
        HrCompanyAccountRecord companyAccount=hrCompanyAccountDao.getRecord(query);
        return companyAccount;
    }
}
