package com.moseeker.company.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolCompanyTagDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolCompanyTagUserDao;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTag;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolCompanyTagUserRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.entity.TalentPoolEntity;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.struct.TalentpoolCompanyTagDO;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by zztaiwll on 18/4/9.
 */
@Service
public class CompanyTagService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TalentpoolCompanyTagUserDao talentpoolCompanyTagUserDao;
    @Autowired
    private TalentpoolCompanyTagDao talentpoolCompanyTagDao;
    @Resource(name = "cacheClient")
    private RedisClient client;
    @Autowired
    private JobApplicationDao jobApplicationDao;
    SearchengineServices.Iface service = ServiceManager.SERVICEMANAGER.getService(SearchengineServices.Iface.class);
    WholeProfileServices.Iface profileService = ServiceManager.SERVICEMANAGER.getService(WholeProfileServices.Iface.class);
    private static String COMPANYTAG_ES_STATUS="COMPANY_TAG_ES_STATUS";
    @Autowired
    private TalentPoolEntity talentPoolEntity;

    /*
      type=0 新增标签
      type=1 修改标签
      type=2 删除标签
     */
    public void handlerCompanyTag(List<Integer> tagIdList, int type) throws TException {
        if(type==2){//删除标签只需要执行删除操作即可
            talentpoolCompanyTagUserDao.deleteByTag(tagIdList);
        }else{
            //新增标签，不用调用删除原有表中的标签和人才的对应关系，只需要增加就可以
            Map<String,Object> map=talentpoolCompanyTagDao.getTagById(tagIdList.get(0));
            Map<String,String> params=new HashMap<>();
            if(map!=null&&!map.isEmpty()){
                for(String key:map.keySet()){
                    params.put(key,String.valueOf(map.get(key)));
                }
                List<Integer> userIdList=service.queryCompanyTagUserIdList(params);
                logger.info("=========================");
                logger.info(JSON.toJSONString(userIdList));
                logger.info("=========================");
                if(type==0){
                    if(!StringUtils.isEmptyList(userIdList)) {
                        List<TalentpoolCompanyTagUserRecord> list = new ArrayList<>();
                        for (Integer userId : userIdList) {
                            TalentpoolCompanyTagUserRecord record = new TalentpoolCompanyTagUserRecord();
                            record.setTagId(tagIdList.get(0));
                            record.setUserId(userId);
                            list.add(record);
                        }
                        talentpoolCompanyTagUserDao.batchAddTagAndUser(list);
                    }

                }else if(type==1){//修改标签需要把表中原有的数据全部删除，
                    talentpoolCompanyTagUserDao.deleteByTag(tagIdList);
                    if(!StringUtils.isEmptyList(userIdList)){
                        List<TalentpoolCompanyTagUserRecord> list = new ArrayList<>();
                        for (Integer userId : userIdList) {
                            TalentpoolCompanyTagUserRecord record = new TalentpoolCompanyTagUserRecord();
                            record.setTagId(tagIdList.get(0));
                            record.setUserId(userId);
                            list.add(record);
                        }
                        talentpoolCompanyTagUserDao.batchAddTagAndUser(list);
                    }

                }
        }

        //更新es中tag_id和人才的关系
        if(!StringUtils.isEmptyList(tagIdList)){
            for(Integer tagId:tagIdList){
                Map<String,Object> result=new HashMap<>();
                result.put("tag_id",tagId);
                result.put("type",type);
                client.lpush(Constant.APPID_ALPHADOG,
                        "ES_UPDATE_INDEX_COMPANYTAG_ID", JSON.toJSONString(result));
                //将这个的tag置位更新状态0是更新 1是更新完成
                client.set(Constant.APPID_ALPHADOG, COMPANYTAG_ES_STATUS,
                        String.valueOf(tagId), String.valueOf(0));
            }

        }
        }

    }
    public void handlerCompanyTagTalent(Set<Integer> idList,int companyId) throws TException {
        List<TalentpoolCompanyTagUserRecord> list=new ArrayList<>();
        List<TalentpoolCompanyTag> tagList=talentpoolCompanyTagDao.getCompanyTagByCompanyId(companyId,0,Integer.MAX_VALUE);
        if(!StringUtils.isEmptyList(tagList)){
            for(Integer userId:idList){
                Response res=profileService.getResource(userId,0,null);
                if(res.getStatus()==0&&StringUtils.isNotNullOrEmpty(res.getData())) {
                    Map<String, Object> profiles = JSON.parseObject(res.getData());
                    for(TalentpoolCompanyTag tag:tagList){
                        boolean isflag=this.validateProfileAndComapnyTag(profiles,userId,companyId,tag);
                        if(isflag){
                            TalentpoolCompanyTagUserRecord record=new TalentpoolCompanyTagUserRecord();
                            record.setUserId(userId);
                            record.setTagId(tag.getId());
                            list.add(record);
                        }
                    }
                }
            }
        }
        if(!StringUtils.isEmptyList(list)){
            talentpoolCompanyTagUserDao.addAllRecord(list);
            for(Integer userId:idList){
                Map<String,Object> result=new HashMap<>();
                result.put("user_id",userId);
                client.lpush(Constant.APPID_ALPHADOG,
                        "ES_UPDATE_INDEX_COMPANYTAG_ID", JSON.toJSONString(result));
            }
        }
    }
    public int getTagtalentNum(int hrId,int companyId,int tagId) throws TException {
        Map<String,String> params=new HashMap<>();
        int count=talentPoolEntity.valiadteMainAccount(hrId,companyId);
        if(count>0){
            List<Map<String,Object>> hrList=talentPoolEntity.getCompanyHrList(companyId);
            Set<Integer> hrIdList=talentPoolEntity.getIdListByUserHrAccountList(hrList);
            params.put("publisher",talentPoolEntity.convertToString(hrIdList));
            params.put("all_publisher","1");

        }else{
            params.put("publisher",hrId+"");
        }
        params.put("hr_id",hrId+"");
        params.put("tag_ids","talent");
        params.put("company_id",companyId+"");
        params.put("hr_account_id",hrId+"");
        int totalNum=service.talentSearchNum(params);
        return totalNum;
    }
    //获取企业标签是否正在执行
    public boolean getCompanyTagIsExecute(int tagId){
        String result=client.get(Constant.APPID_ALPHADOG, COMPANYTAG_ES_STATUS,
                String.valueOf(tagId));
        if(StringUtils.isNotNullOrEmpty(result)){
            if(Integer.parseInt(result)>0){
                return false;
            }else{
                return true;
            }

        }
        return false;
    }

    private boolean  validateProfileAndComapnyTag(Map<String,Object> profiles,int userId,int companyId,TalentpoolCompanyTag tag) throws TException {

        List<JobApplicationRecord> applist=new ArrayList<>();
        if(StringUtils.isNotNullOrEmpty(tag.getOrigins())||StringUtils.isNotNullOrEmpty(tag.getSubmitTime())||tag.getIsRecommend()>0) {
            if(tag.getIsRecommend()>0){
                applist=this.getJobApplicationByCompanyIdAndUserId(companyId,userId);
            }else{
                applist=getJobAppRecommendByCompanyIdAndUserId(companyId,userId);
            }

            boolean flag=this.validateApp(tag.getOrigins(),applist,profiles,tag.getSubmitTime());
            if(flag==false){
                return false;
            }
        }
        if(StringUtils.isNotNullOrEmpty(tag.getWorkYears())){
            //校验工作年限
            boolean flag=validateWorkYear(tag.getWorkYears(),profiles);
            if(flag==false){
                return false;
            }
        }
        if(StringUtils.isNotNullOrEmpty(tag.getCityCode())){
            boolean flag=this.validateCity(tag.getCityCode(),profiles);
            if(flag==false){
                return false;
            }
        }
        if(StringUtils.isNotNullOrEmpty(tag.getDegree())){
            boolean flag=this.validateDegree(tag.getDegree(),profiles);
            if(flag==false){
                return false;
            }

        }
        if(StringUtils.isNotNullOrEmpty(tag.getPastPosition())){
            int isPast=tag.getInLastJobSearchPosition();
            boolean flag=this.validatePastPosition(tag.getPastPosition(),isPast,profiles);
            if(flag==false){
                return false;
            }

        }
        if(StringUtils.isNotNullOrEmpty(tag.getCompanyName())){
            int isPast=tag.getInLastJobSearchCompany();
            boolean flag=this.validatePastCompany(tag.getCompanyName(),isPast,profiles);
            if(flag==false){
                return false;
            }
        }
        if(StringUtils.isNotNullOrEmpty(tag.getIntentionCityCode())){
            boolean flag=this.validateIntentionCity(tag.getIntentionCityCode(),profiles);
            if(flag==false){
                return false;
            }
        }
        if(StringUtils.isNotNullOrEmpty(tag.getIntentionSalaryCode())){
            boolean flag=this.validateIntentionSalaryCode(tag.getIntentionSalaryCode(),profiles);
            if(flag==false){
                return false;
            }
        }
        if(tag.getSex()!=2){
            boolean flag=this.validateSex(tag.getSex(),profiles);
            if(flag==false){
                return false;
            }
        }
        if(tag.getMinAge()!=0||tag.getMaxAge()!=0){
            boolean flag=this.validateAge(tag.getMinAge(),tag.getMaxAge(),profiles);
            if(flag==false){
                return false;
            }
        }

        return true;
    }
    /*
      校验曾经工作的公司
     */
    private boolean validatePastCompany(String pastCompanys,int isPast,Map<String,Object> profiles){
        if(profiles==null||profiles.isEmpty()){
            return false;
        }
        List<Map<String,Object>> workExpList= (List<Map<String, Object>>) profiles.get("workexps");
        if(StringUtils.isEmptyList(workExpList)){
            return false;
        }
        String[] array=pastCompanys.split(",");
        if(isPast==1){
            Map<String,Object> workExp=workExpList.get(0);
            if(workExp==null||workExp.isEmpty()){
                return false;
            }
            String companyName=(String)workExp.get("company_name");
            if(StringUtils.isNullOrEmpty(companyName)){
                return false;
            }
            for(String item:array){
                if(item.equals(companyName)){
                    return true;
                }
            }

        }else{
            for(String item:array){
                for(Map<String,Object> workExp:workExpList){
                    String companyName=(String)workExp.get("company_name");
                    if(item.equals(companyName)){
                        return true;
                    }
                }

            }
        }
        return false;
    }
    /*
    校验曾任职务
     */
    private boolean validatePastPosition(String pastPositions,int isPast,Map<String,Object> profiles){
        if(profiles==null||profiles.isEmpty()){
            return false;
        }
        List<Map<String,Object>> workExpList= (List<Map<String, Object>>) profiles.get("workexps");
        if(StringUtils.isEmptyList(workExpList)){
            return false;
        }
        String[] array=pastPositions.split(",");
        if(isPast==1){
            Map<String,Object> workExp=workExpList.get(0);
            if(workExp==null||workExp.isEmpty()){
                return false;
            }
            String position=(String)workExp.get("job");
            if(StringUtils.isNullOrEmpty(position)){
                return false;
            }
            for(String item:array){
                if(item.equals(position)){
                    return true;
                }
            }

        }else{
            for(String item:array){
                for(Map<String,Object> workExp:workExpList){
                    String position=(String)workExp.get("job");
                    if(item.equals(position)){
                        return true;
                    }
                }

            }
        }
        return false;

    }

    /*
      校验期望工资
     */
    private boolean validateIntentionSalaryCode(String salaryCodes,Map<String,Object> profiles){
        if(profiles==null||profiles.isEmpty()){
            return false;
        }
        List<Map<String,Object>> intentions= (List<Map<String, Object>>) profiles.get("intentions");
        if(StringUtils.isEmptyList(intentions)){
            return false;
        }
        String []array=salaryCodes.split(",");
        for(String item:array){
            int salaryCode= Integer.parseInt(item);
            for(Map<String,Object> intention:intentions){
                int code=(int)intention.get("salary_code");
                if(code==salaryCode){
                    return true;
                }
            }
        }
        return false;
    }
    /*
    校验期望城市
     */
    private boolean validateIntentionCity(String cityCodes,Map<String,Object> profiles){
        if(profiles==null||profiles.isEmpty()){
            return false;
        }
        List<Map<String,Object>> intentions= (List<Map<String, Object>>) profiles.get("intentions");
        if(StringUtils.isEmptyList(intentions)){
            return false;
        }
        String []array=cityCodes.split(",");
        for(String item:array){
            int cityCode= Integer.parseInt(item);
            for(Map<String,Object> intention:intentions){
                List<Map<String,Object>> citys=( List<Map<String,Object>>)intention.get("cities");
                if(!StringUtils.isEmptyList(citys)){
                    for(Map<String,Object> city:citys){
                        int code=(int)city.get("code") ;
                        if(code==cityCode){
                            return true;
                        }
                    }
                }
            }

        }

        return false;
    }

    /*
     校验学历
     */
    private boolean validateDegree(String degrees,Map<String,Object> profiles){
        String[] array=degrees.split(",");
        if(profiles==null||profiles.isEmpty()){
            return false;
        }
        List<Map<String,Object>> edutionExps= (List<Map<String, Object>>) profiles.get("educations");
        if(StringUtils.isEmptyList(edutionExps)){
            return false;
        }
        Map<String,Object> education=edutionExps.get(0);
        int degree= (int) education.get("degree");
        for(String item:array){
            if(StringUtils.isNotNullOrEmpty(item)&&Integer.parseInt(item)==degree){
                return true;
            }
        }

        return false;
    }
    /*
      校验性别
     */
    private boolean validateSex(int sex,Map<String,Object> profiles){
        if(profiles==null||profiles.isEmpty()){
            return false;
        }
        Map<String,Object> basic= (Map<String, Object>) profiles.get("basic");
        if(basic==null||basic.isEmpty()){
            return false;
        }
        int gender=(int)basic.get("gender");
        if(sex==gender){
            return true;
        }
        return false;
    }
    /*
    校验年龄
     */
    private boolean validateAge(int minAge,int maxAge,Map<String,Object> profiles ){
        if(profiles==null||profiles.isEmpty()){
            return false;
        }
        Map<String,Object> basic= (Map<String, Object>) profiles.get("basic");
        if(basic==null||basic.isEmpty()){
            return false;

        }
        String birth=(String)basic.get("birth");
        if(StringUtils.isNullOrEmpty(birth)){
            return false;
        }
        int time=Integer.parseInt(birth.substring(0,4));
        int age=new Date().getYear()-time+1;
        if(age>minAge&&age<maxAge){
            return true;
        }
        return false;
    }
    /*
    校验城市
     */
    private boolean validateCity(String cityCodes,Map<String,Object> profiles){
        if(profiles==null||profiles.isEmpty()){
            return false;
        }
        Map<String,Object> basic= (Map<String, Object>) profiles.get("basic");
        if(basic==null||basic.isEmpty()){
            return false;

        }
        int profileCityCode=(int)basic.get("city_code");
        if(profileCityCode==0){
            return false;
        }
        String []array=cityCodes.split(",");
        for(String item:array){
            if(profileCityCode==Integer.parseInt(item)){
                return true;
            }
        }
        return false;
    }
    /*
     获取提交的时间戳
     */
    private Long getLongTime(String submitTime){

        Date date=new Date();
        long time=Long.parseLong(submitTime);
        if(time==1){
            time=3L;
        }else if(time==2){
            time=7L;
        }else if(time==3){
            time=30L;
        }
        long datetime=date.getTime();
        long preTime=time*3600*24*1000;
        long longTime=datetime-preTime;
        return longTime;
    }
    /*
     校验工作年限
     */
    private boolean validateWorkYear(String workyears,Map<String,Object> profiles){
        if(profiles==null||profiles.isEmpty()){
            return false;
        }
        List<Map<String,Object>> workExpList= (List<Map<String, Object>>) profiles.get("workexps");
        if(StringUtils.isEmptyList(workExpList)){
            return false;
        }
        long end=0;
        long start=0;
        if(workExpList.size()>1){
            Map<String,Object> endExp=workExpList.get(0);
            Map<String,Object> startExp=workExpList.get(workExpList.size()-1);
            int now=(int)endExp.get("end_until_now");
            if(now==1){
                end=new Date().getTime();
            }else{
                end=new Date((String)endExp.get("end_date")).getTime();
            }
            start=new Date((String)startExp.get("start_date")).getTime();
        }else{
            Map<String,Object> workexp=workExpList.get(0);
            int now=(int)workexp.get("end_until_now");
            if(now==1){
                end=new Date().getTime();
            }else{
                end=new Date((String)workexp.get("end_date")).getTime();
            }
            start=new Date((String)workexp.get("start_date")).getTime();

        }
        int resultYear=(int)Math.ceil(((double)end-(double)start)/(3600*24*30*12));
        int year=Integer.parseInt(workyears);
        int min=0;
        int max=0;
        if(year==2){
            min=0;
            max=1;
        }else if(year==3){
            min=1;
            max=3;
        }else if(year==4){
            min=3;
            max=5;
        }else if(year==5){
            min=5;
            max=10;
        }else{
            min=10;
            max=100;
        }
        if(min<resultYear&&max>resultYear){
            return true;
        }
        return false;

    }

    /*
     校验该人才的来源
     */
    private boolean validateApp(String origins, List<JobApplicationRecord> applist,Map<String,Object> profiles,String submitTime ){
        if(StringUtils.isEmptyList(applist)){
            return false;
        }
        if(StringUtils.isNotNullOrEmpty(origins)){
            String[] originArray=origins.split(",");
            boolean flag=false;
            for(JobApplicationRecord record:applist){
                int appOrigin=record.getOrigin();
                flag=this.validateEqual(originArray,appOrigin);
                if(flag==true){
                    if(StringUtils.isNotNullOrEmpty(submitTime)){
                        long apptime=record.getSubmitTime().getTime();
                        long time=this.getLongTime(submitTime);
                        if(time<apptime){
                            flag=true;
                        }
                    }
                }
                if(flag==true){
                    break;
                }
            }
            if(flag==false){
                if(profiles==null||profiles.isEmpty()){
                    return false;
                }
                Map<String,Object> profile= (Map<String, Object>) profiles.get("profile");
                if(profile==null||profile.isEmpty()){
                    return false;
                }
                String profileOrigin=(String)profile.get("origin");
                if(StringUtils.isNullOrEmpty(profileOrigin)){
                    return false;
                }
                for(String item:originArray){
                    if(item.length()>10){
                        int length=item.length();
                        if(length<=profileOrigin.length()){
                            char profileChar=profileOrigin.charAt(profileOrigin.length()-length);
                            if(profileChar=='1'){
                                flag= true;
                            }
                        }

                    }
                }
                if(flag==false){
                    return false;
                }else{
                    for(JobApplicationRecord record:applist){
                        if(StringUtils.isNotNullOrEmpty(submitTime)){
                            long apptime=record.getSubmitTime().getTime();
                            long time=this.getLongTime(submitTime);
                            if(time<apptime){
                                flag=true;
                                break;
                            }
                        }
                    }
                    if(flag==false){
                        return false;
                    }
                }
                return true;
            }

        }
        if(StringUtils.isNotNullOrEmpty(submitTime)){
            for(JobApplicationRecord record:applist){
                if(StringUtils.isNotNullOrEmpty(submitTime)){
                    long apptime=record.getSubmitTime().getTime();
                    long time=this.getLongTime(submitTime);
                    if(time<apptime){
                        return true;
                    }
                }
            }
        }
        return true;


    }
    private boolean validateEqual(String[] array,int origin){
        if(array==null||array.length==0){
            return false;
        }
        for(String item:array){
            if(StringUtils.isNotNullOrEmpty(item)){
                if(Integer.parseInt(item)==origin){
                    return true;
                }
            }
        }
        return false;
    }
    /*
 获取这个人在这家公司下的所有投递
 */
    private List<JobApplicationRecord> getJobApplicationByCompanyIdAndUserId(int companyId,int userId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).and("applier_id",userId).buildQuery();
        List<JobApplicationRecord> list=jobApplicationDao.getRecords(query);
        return list;
    }
    private List<JobApplicationRecord> getJobAppRecommendByCompanyIdAndUserId(int companyId,int userId){
        Query query=new Query.QueryBuilder().where("company_id",companyId).and("applier_id",userId).and(new Condition("recommender_user_id",0, ValueOp.GT)).buildQuery();
        List<JobApplicationRecord> list=jobApplicationDao.getRecords(query);
        return list;
    }

}
