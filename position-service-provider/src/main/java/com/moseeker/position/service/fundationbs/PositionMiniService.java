package com.moseeker.position.service.fundationbs;

import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyAccount;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserHrAccount;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.UserAccountEntity;
import com.moseeker.position.pojo.CompanyAccount;
import com.moseeker.thrift.gen.common.struct.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @CounterIface
    public Response getPositionList(int accountId,String keyWord,int page,int pageSize){

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
