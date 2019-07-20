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

import java.util.*;

/**
 * Created by zztaiwll on 18/2/1.
 */
@Service
public class ProfileMiniService {

    private static Logger logger = LoggerFactory.getLogger(ProfileMiniService.class);
    @Autowired
    private UserHrAccountDao userHrAccountDao;
    @Autowired
    private TalentPoolEntity talentPoolEntity;
    SearchengineServices.Iface searchengineServices = ServiceManager.SERVICE_MANAGER.getService(SearchengineServices.Iface.class);

    @CounterIface
    public Map<String,Object> getProfileMini(Map<String,String> params) throws TException {
        String pageNumber=params.get("pageNum");
        String pageSize=params.get("pageSize");
        String keyword=params.get("keyword");
        String accountId=params.get("accountId");
        if(StringUtils.isNullOrEmpty(pageNumber)){
            pageNumber="1";
        }
        if(StringUtils.isNullOrEmpty(pageSize)){
            pageSize="10";
        }
        UserHrAccountRecord record=this.getAccountById(Integer.parseInt(accountId));
        if(record==null){
            return null;
        }
        String publisher=this.handlerAccountData(record);
        Map<String,String> map=this.handlerParamsData(pageNumber,pageSize,keyword,accountId,publisher);
        Map<String,Object> result=this.getProfileByEs(map);
        result=this.filterApplication(result,record);
        return result;
    }

    @CounterIface
    public  Map<String,Object> getProfileMiniSug(int accountId,String keyword,int page,int pageSize) throws TException {
        if(page == 0){
            page = 1;
        }
        if(pageSize == 0){
            pageSize = 10;
        }
        UserHrAccountRecord record=this.getAccountById(accountId);
        if(record==null || !StringUtils.isNotNullOrEmpty(keyword)){
            return null;
        }
        Map<String,String> map = this.handlerParams(accountId, record.getAccountType(), record.getCompanyId(), keyword, page, pageSize);
        Response res=searchengineServices.searchProfileSuggest(map);
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
     处理数据，过滤掉无用的申请
     */
    private Map<String,Object> filterApplication(Map<String,Object> result,UserHrAccountRecord record){
        if(result!=null&&!result.isEmpty()){
            int companyId=record.getCompanyId();
            int accountId=record.getId();
            if(result.get("users") != null) {
                List<Map<String, Object>> userList = (List<Map<String, Object>>) result.get("users");
                for (Map<String, Object> user : userList) {
                    List<Map<String, Object>> applistNew = new ArrayList<>();
                    Map<String, Object> userMap = (Map<String, Object>) user.get("user");
                    List<Map<String, Object>> appList = (List<Map<String, Object>>) userMap.get("applications");
                    for (Map<String, Object> app : appList) {
                        if (record.getAccountType() == 0) {
                            int appCompanyId = (int) app.get("companyId");
                            if (appCompanyId == companyId) {
                                applistNew.add(app);
                            }
                        } else {
                            int publisher = (int) app.get("publisher");
                            if (publisher == accountId) {
                                applistNew.add(app);
                            }
                        }

                    }
                    userMap.put("applications", applistNew);
                }
                result.put("accountType", record.getAccountType());
            }else{
                result=new HashMap<>();
                result.put("totalNum",0);
            }
        }else{
            result=new HashMap<>();
            result.put("totalNum",0);
        }
        result.put("accountType",record.getAccountType());
        return result;
    }
    /*
     请求es，获取参数
     */
    private Map<String,Object> getProfileByEs(Map<String,String> params) throws TException {
        Response  res=searchengineServices.userQuery(params);
        String data=res.getData();
        if(res.getStatus()==0&&data!=null&&org.apache.commons.lang.StringUtils.isNotBlank(data)){
            logger.info(res.getData());
            if("\"\"".equals(data)){
                return null;
            }
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
        if(StringUtils.isNotNullOrEmpty(keyword)){
            pofileMiniParams.put("keyword",keyword);
        }
        pofileMiniParams.put("publisher",publisher);
        pofileMiniParams.put("hr_account_id",accountId);
        return pofileMiniParams;
    }
    /*
     处理小程序传的账号的数据
     */
    public String handlerAccountData(UserHrAccountRecord record){

        if(record==null){
            return "";
        }
        int accountType=record.getAccountType();
        if(accountType==0){
            return this.getAccountIdByCompanyId(record.getCompanyId());
        }
        return record.getId()+"";
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

    /*
      获取请求es的参数
     */
    private Map<String,String> handlerParams(int accountId, int accountType, int company_id, String keyword,int pageNum,int pageSize){
        Map<String,String> map=new HashMap<>();
        map.put("company_id",String.valueOf(company_id));
        map.put("account_type",String.valueOf(accountType));
        map.put("hr_account_id",String.valueOf(accountId));
        map.put("keyWord",keyword);
        map.put("page_from",String.valueOf(pageNum));
        map.put("page_size",String.valueOf(pageSize));
        map.put("return_params","user.profiles.basic.name");
        map.put("flag","1");
        return map;
    }
}
