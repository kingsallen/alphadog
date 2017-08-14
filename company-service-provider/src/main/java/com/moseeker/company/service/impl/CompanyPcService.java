package com.moseeker.company.service.impl;

import com.moseeker.baseorm.dao.hrdb.HrCompanyAccountDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyConfDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyConfDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zztaiwll on 17/8/14.
 * 用于处理pc2.0的公司详情信息
 */
@Service
public class CompanyPcService {
    @Autowired
    private HrCompanyDao hrCompanyDao;
    @Autowired
    private HrCompanyConfDao hrCompanyConfDao;
    @Autowired
    private HrCompanyAccountDao hrCompanyAccountDao;

    public Map<String,Object> getCompanyInfo(int companyId){


        return null;
    }
    /*
       获取公司信息
     */
    public HrCompanyDO getHrCompany(int companyId){
        Query query=new Query.QueryBuilder().where("id",companyId).buildQuery();
        HrCompanyDO DO= hrCompanyDao.getData(query);
        return DO;
    }
    /*
       获取公司配置
     */
    public HrCompanyConfDO getHrCompanyConf(int companyId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).buildQuery();
        HrCompanyConfDO DO= hrCompanyConfDao.getData(query);
        return DO;
    }
    /*
        那么获取公司的publisher
     */
    public List<Integer> getCompanyPublisher(int companyId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).buildQuery();
        List<HrCompanyAccountDO> list=hrCompanyAccountDao.getDatas(query);
        List<Integer> result=new ArrayList<Integer>();
        if(StringUtils.isEmptyList(list)){
            return result;
        }
        for (HrCompanyAccountDO DO:list){
            result.add(DO.getAccountId());
        }
        return result;
    }
    /*
        获取子公司下的team-id
     */
    public List<Integer> getSubCompanyTeam(List<Integer> list){
        Query query=new Query.QueryBuilder().where(new Condition("publisher",list.toArray(), ValueOp.IN)).and("status",0).buildQuery();
        return null;
    }
    /*
          获取母公司下team的id
     */
    public List<Integer> getCompanyTeamId(){

        return null;
    }
    /*
        获取公司下团队职位数量
     */
    public Map<Integer,Integer> getTeamPositionNum(List<Integer> list){
        return null;
    }

    /*
       获取公司发布职位的地址
     */
    public String getComapnyPositionCity(){

        return null;
    }
}
