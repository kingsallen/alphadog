package com.moseeker.entity.biz;

import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTag;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by moseeker on 2018/4/13.
 */
@Component
public class CompanyFilterTagValidation {

    @Autowired
    private JobApplicationDao jobApplicationDao;

    public boolean  validateProfileAndComapnyTag(Map<String, Object> profiles, int userId, int companyId, Map<String, Object> tag) throws TException {

        List<JobApplicationRecord> applist=new ArrayList<>();
        if((tag.get("origins") != null && StringUtils.isNotNullOrEmpty((String)tag.get("origins")))
                ||(tag.get("submitTime") != null && StringUtils.isNotNullOrEmpty((String)tag.get("submitTime")))
                ||(tag.get("isRecommend") != null && (int)tag.get("isRecommend")>0)) {
            if((int)tag.get("isRecommend")>0){
                applist=this.getJobApplicationByCompanyIdAndUserId(companyId,userId);
            }else{
                applist=getJobAppRecommendByCompanyIdAndUserId(companyId,userId);
            }

            boolean flag=this.validateApp((String)tag.get("origins"),applist,profiles,(String)tag.get("submitTime"));
            if(flag==false){
                return false;
            }
        }
        if(tag.get("workYears") != null && StringUtils.isNotNullOrEmpty((String) tag.get("workYears"))){
            //校验工作年限
            boolean flag=validateWorkYear((String) tag.get("workYears"),profiles);
            if(flag==false){
                return false;
            }
        }
        if(tag.get("cityCode") != null && StringUtils.isNotNullOrEmpty((String) tag.get("cityCode"))){
            boolean flag=this.validateCity((String) tag.get("cityCode"),profiles);
            if(flag==false){
                return false;
            }
        }
        if(tag.get("degree") != null && StringUtils.isNotNullOrEmpty((String) tag.get("degree"))){
            boolean flag=this.validateDegree((String) tag.get("degree"),profiles);
            if(flag==false){
                return false;
            }

        }
        if(tag.get("pastPosition") != null && StringUtils.isNotNullOrEmpty((String) tag.get("pastPosition"))
                && tag.get("inLastJobSearchPosition") != null){
            int isPast=(int)tag.get("inLastJobSearchPosition") ;
            boolean flag=this.validatePastPosition((String) tag.get("pastPosition"),isPast,profiles);
            if(flag==false){
                return false;
            }

        }
        if(tag.get("companyName") != null && StringUtils.isNotNullOrEmpty((String) tag.get("companyName"))
                && tag.get("inLastJobSearchCompany") != null){
            int isPast=(int)tag.get("inLastJobSearchCompany");
            boolean flag=this.validatePastCompany((String) tag.get("companyName"),isPast,profiles);
            if(flag==false){
                return false;
            }
        }
        if(tag.get("intentionCityCode") != null && StringUtils.isNotNullOrEmpty((String) tag.get("intentionCityCode"))){
            boolean flag=this.validateIntentionCity((String) tag.get("intentionCityCode"),profiles);
            if(flag==false){
                return false;
            }
        }
        if(tag.get("intentionSalaryCode") != null && StringUtils.isNotNullOrEmpty((String) tag.get("intentionSalaryCode"))){
            boolean flag=this.validateIntentionSalaryCode((String) tag.get("intentionSalaryCode"),profiles);
            if(flag==false){
                return false;
            }
        }
        if(tag.get("sex") != null && (int)tag.get("sex")>0){
            boolean flag=this.validateSex((int)tag.get("sex"),profiles);
            if(flag==false){
                return false;
            }
        }
        if((tag.get("minAge") != null && (int)tag.get("minAge")!=0)
                ||(tag.get("maxAge") != null && (int)tag.get("maxAge")!=0)){
            boolean flag=this.validateAge((int)tag.get("minAge"),(int)tag.get("maxAge"),profiles);
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
