package com.moseeker.profile.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.TalentPoolEntity;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zztaiwll on 18/2/1.
 */
@Service
public class ProfileMiniService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserHrAccountDao userHrAccountDao;
    @Autowired
    private TalentPoolEntity talentPoolEntity;
    SearchengineServices.Iface searchengineServices = ServiceManager.SERVICEMANAGER.getService(SearchengineServices.Iface.class);

    @CounterIface
    public Map<String,Object> getProfileMini(Map<String,String> params) throws TException {
        String pageNumber=params.get("pageNum");
        String pageSize=params.get("pageSize");
        String keyword=params.get("keyword");
        String accountId=params.get("accountId");
        if(StringUtils.isNullOrEmpty(pageNumber)){
            pageNumber="0";
        }
        if(StringUtils.isNullOrEmpty(pageSize)){
            pageSize="15";
        }
        String publisher=this.handlerAccountData(accountId);
        Map<String,String> map=this.handlerParamsData(pageNumber,pageSize,keyword,accountId,publisher);
        Map<String,Object> result=this.getProfileByEs(map);
        return result;
    }
    /*
     请求es，获取参数
     */
    private Map<String,Object> getProfileByEs(Map<String,String> params) throws TException {
        Response  res=searchengineServices.userQuery(params);
        if(res.getStatus()==0&&res.getData()!=null&&StringUtils.isNotNullOrEmpty(res.getData())){
            logger.info(res.getData());
            Map<String,Object> result= JSON.parseObject(res.getData(),Map.class);
            result=StringUtils.convertUnderKeyToCamel(result);
            return result;
        }
        return null;
    }
    /*
     处理一下参数，生成请求es服务的参数
     */
    private Map<String,String> handlerParamsData(String pageNumber,String pageSize,String keyword,String accountId,String publisher){

        Map<String,String> pofileMiniParams=new HashMap<>();
        pofileMiniParams.put("page_number",pageNumber);
        pofileMiniParams.put("page_size",pageSize);
        pofileMiniParams.put("keyword",keyword);
        pofileMiniParams.put("publisher",publisher);
        pofileMiniParams.put("hr_account_id",accountId);
        return pofileMiniParams;
    }
    /*
     处理小程序传的账号的数据
     */
    public String handlerAccountData(String accountId){
        UserHrAccountRecord record=this.getAccountById(Integer.parseInt(accountId));
        if(record==null){
            return "";
        }
        int accountType=record.getAccountType();
        if(accountType==0){
            return this.getAccountIdByCompanyId(record.getCompanyId());
        }
        return accountId;
    }
    /*
     根据传入的id查询账号的内容
     */
    private UserHrAccountRecord getAccountById(int accountId){
        Query query=new Query.QueryBuilder().where("id",accountId).and("activation",1).and("disable",1).buildQuery();
        UserHrAccountRecord userHrAccountRecord=userHrAccountDao.getRecord(query);
        return userHrAccountRecord;
    }
    /*
        获取公司下所有账号
     */
    private String getAccountIdByCompanyId(int companyId){
        List<Map<String,Object>> list=talentPoolEntity.getCompanyHrList(companyId);
        Set<Integer> idSet=talentPoolEntity.getIdListByUserHrAccountList(list);
        if(StringUtils.isEmptySet(idSet)){
            return "";
        }
        String accountIds="";
        for(Integer aid:idSet){
            accountIds+=aid+",";
        }
        accountIds=accountIds.substring(0,accountIds.lastIndexOf(","));
        return accountIds;
    }
}
