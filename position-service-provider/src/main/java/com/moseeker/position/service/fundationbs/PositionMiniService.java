package com.moseeker.position.service.fundationbs;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyAccount;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserHrAccount;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.TalentPoolEntity;
import com.moseeker.entity.UserAccountEntity;
import com.moseeker.position.pojo.CompanyAccount;
import com.moseeker.position.pojo.PositionMiniBean;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zztaiwll on 18/1/24.
 */
@Service
public class PositionMiniService {
    @Autowired
    private UserAccountEntity userAccountEntity;
    @Autowired
    private HrCompanyAccountDao hrCompanyAccountDao;
    @Autowired
    private HrCompanyDao hrCompanyDao;

    @Autowired
    private TalentPoolEntity talentPoolEntity;

    SearchengineServices.Iface searchengineServices = ServiceManager.SERVICEMANAGER.getService(SearchengineServices.Iface.class);
    @CounterIface
    public Response getPositionList(int accountId,String keyWord,int page,int pageSize) throws TException {
        CompanyAccount account=this.getAccountInfo(accountId);
        String motherCompanyId="";
        String childCompanyId="";
        HrCompany company=account.getHrCompany();
        if(company==null){
            ResponseUtils.fail(1,"account_id已失效");
        }
        int parentId=company.getParentId();
        if(parentId==0){
            motherCompanyId=String.valueOf(company.getId());
        }else{
            childCompanyId=String.valueOf(company.getId());
        }
        Map<String,String> params=new HashMap<>();
        params.put("keyword",keyWord);
        params.put("page",String.valueOf(page));
        params.put("pageSize",String.valueOf(pageSize));
        params.put("childCompanyId",childCompanyId);
        params.put("motherCompanyId",motherCompanyId);
        Response res=searchengineServices.queryPositionMini(params);
        if(res.getStatus()==0&& StringUtils.isNotNullOrEmpty(res.getData())){
            Map<String,Object> data= JSON.parseObject(res.getData(),Map.class);
        }
        return null;
    }

    /*
     校验账号是是否可用,并且获取账号信息和公司信息
     */
    public CompanyAccount getAccountInfo(int accountId){
        CompanyAccount companyAccountBean=new CompanyAccount();
        UserHrAccount account=userAccountEntity.getHrAccount(accountId);
        if(account!=null){
            HrCompanyAccount companyAccount=getHrCompanyAccount(accountId);
            if(companyAccount!=null){
                int companyId=companyAccount.getCompanyId();
                HrCompany hrCompany=this.getHrCompanyById(companyId);
                if(hrCompany!=null){
                    int parentId=hrCompany.getParentId();
                    int disable=hrCompany.getDisable();

                    if(parentId==0){
                        companyAccountBean.setHrCompany(hrCompany);
                        companyAccountBean.setUserHrAccount(account);
                    }else{
                        if(disable==1){
                            companyAccountBean.setHrCompany(hrCompany);
                            companyAccountBean.setUserHrAccount(account);
                        }
                    }
                }
            }
        }
        return companyAccountBean;
    }
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
    private HrCompanyAccount getHrCompanyAccount(int accountId){
        Query query=new Query.QueryBuilder().where("account_id",accountId).buildQuery();
        HrCompanyAccount companyAccount=hrCompanyAccountDao.getData(query,HrCompanyAccount.class);
        return companyAccount;
    }
    /*
     获取公司信息
     */
    private HrCompany getHrCompanyById(int companyId){
        Query query=new Query.QueryBuilder().where("id",companyId).buildQuery();
        HrCompany hrCompany=hrCompanyDao.getData(query,HrCompany.class);
        return hrCompany;
    }
}
