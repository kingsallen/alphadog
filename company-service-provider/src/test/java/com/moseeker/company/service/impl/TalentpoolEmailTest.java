package com.moseeker.company.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.company.bean.email.EmailInviteBean;
import com.moseeker.company.config.AppConfig;
import com.moseeker.thrift.gen.common.struct.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zztaiwll on 18/4/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class TalentpoolEmailTest {
    @Autowired
    private TalentpoolEmailService talentpoolEmailService;

    @Test
    public void sendInviteEmail(){
        Map<String,String> params=new HashMap<>();
        java.util.List<Integer> userIdList=new ArrayList<>();
        userIdList.add(2191891);
        userIdList.add(2191521);
        userIdList.add(2191513);
        userIdList.add(2191505);
        userIdList.add(2191525);
        List<Integer> positionIdList=new ArrayList<>();
        positionIdList.add(1011417);
        positionIdList.add(1011421);
        positionIdList.add(1011422);
        positionIdList.add(1011423);
        positionIdList.add(1011581);
        int companyId=39978;
        int hrId=82752;
        int flag=0;
        int positionFlag=0;
        int result=talentpoolEmailService.talentPoolSendInviteToDelivyEmail(params,userIdList,positionIdList,companyId,hrId,flag,positionFlag);
        System.out.println(result) ;

    }
    @Test
    public void sendResumeEmail(){
        Map<String,String> params=new HashMap<>();
        java.util.List<Integer> userIdList=new ArrayList<>();
        userIdList.add(2191891);
        userIdList.add(2191521);
        userIdList.add(2191513);
        userIdList.add(2191505);
        userIdList.add(2191525);
        List<Integer> idList=new ArrayList<>();
        idList.add(43687);
        idList.add(44206);
        idList.add(44235);
        idList.add(44265);
        int companyId=39978;
        int hrId=82752;
        int flag=0;
        int positionFlag=0;
        int result=talentpoolEmailService.talentPoolSendResumeEmail(idList,params,userIdList,companyId,hrId,0);
        System.out.println(result) ;

    }
    @Test
    public void positionSendInviteEmail(){

    }
    @Test
    public void getData(){
        Map<String,Object> data=new HashMap<>();
        data.put("totalNum",10L);
        List<Map<String,Object>> dataList=new ArrayList<>();
        Map<String,Object> userData=new HashMap<>();
        Map<String,Object> users=new HashMap<>();
        Map<String,Object> profiles=new HashMap<>();
        Map<String,Object> profile=new HashMap<>();
        Map<String,Object> basic=new HashMap<>();
        basic.put("email","111@moseeker.com");
        basic.put("name","111");
        profile.put("user_id",111);
        profiles.put("basic",basic);
        profiles.put("profile",profile);
        users.put("profiles",profiles);
        userData.put("user",users);
        dataList.add(userData);

        Map<String,Object> userData1=new HashMap<>();
        Map<String,Object> users1=new HashMap<>();
        Map<String,Object> profiles1=new HashMap<>();
        Map<String,Object> profile1=new HashMap<>();
        Map<String,Object> basic1=new HashMap<>();
        basic1.put("email","222@moseeker.com");
        basic1.put("name","222");
        profile1.put("user_id",222);
        profiles1.put("basic",basic1);
        profiles1.put("profile",profile1);
        users1.put("profiles",profiles1);
        userData1.put("user",users1);
        dataList.add(userData1);
        Map<String,Object> userData2=new HashMap<>();
        Map<String,Object> users2=new HashMap<>();
        Map<String,Object> profiles2=new HashMap<>();
        Map<String,Object> profile2=new HashMap<>();
        Map<String,Object> basic2=new HashMap<>();
        basic2.put("email","333@moseeker.com");
        basic2.put("name","333");
        profile2.put("user_id",333);
        profiles2.put("basic",basic2);
        profiles2.put("profile",profile2);
        users2.put("profiles",profiles2);
        userData2.put("user",users2);
        dataList.add(userData2);
        data.put("users",dataList);
        Response res= ResponseUtils.success(data);
        System.out.println(res.getData());
    }

    @Test
    public void getSendResumeData(){
        Map<String,Object> data=new HashMap<>();
        data.put("totalNum",10L);
        List<Map<String,Object>> dataList=new ArrayList<>();
        Map<String,Object> userData=new HashMap<>();
        Map<String,Object> users=new HashMap<>();
        Map<String,Object> profiles=new HashMap<>();
        Map<String,Object> profile=new HashMap<>();
        Map<String,Object> basic=new HashMap<>();
        Map<String,Object> recent=new HashMap<>();
        List<Map<String,Object>> education=new ArrayList<>();

        basic.put("email","111@moseeker.com");
        basic.put("name","111");
        basic.put("heading","111adqwdqwqwqw");
        basic.put("gender_name","男");
        basic.put("city_name","北京");
        basic.put("highest_degree",5);
        profile.put("user_id",111);
        recent.put("end_until_now",0);
        recent.put("company_scale_name","请选择企业规模");
        recent.put("job_name","市场总监");
        recent.put("company_name","寻仟科技");
        recent.put("start_date","2012-09-01");
        recent.put("company_id",238902);
        recent.put("department_name","市场部");
        recent.put("company_new_name","寻仟科技");
        recent.put("job","市场总监");
        recent.put("company_scale",0);
        recent.put("company_property_name","asasas");
        recent.put("position_code",0);
        recent.put("position_name","ssdssd");
        recent.put("company_property",0);
        recent.put("id",420874);
        recent.put("achievement",0);
        recent.put("description","市场策划，活动安排");
        Map<String,Object> educationMap1=new HashMap<>();
        educationMap1.put("end_until_now",0);
        educationMap1.put("major_code",0);
        educationMap1.put("degree",5);
        educationMap1.put("end_date","2012-08-01");
        educationMap1.put("description","教育部门负责人");
        educationMap1.put("college_logo","upload/college/logo/10335.jpg");
        educationMap1.put("start_date","2010-07-01");
        educationMap1.put("major_name","计算机应用");
        educationMap1.put("college_code",10335);
        educationMap1.put("id",310057);
        educationMap1.put("college_name","浙江大学");
        education.add(educationMap1);
        Map<String,Object> educationMap2=new HashMap<>();
        educationMap2.put("end_until_now",0);
        educationMap2.put("major_code",0);
        educationMap2.put("degree",5);
        educationMap2.put("end_date","2012-08-01");
        educationMap2.put("description","教育部门负责人");
        educationMap2.put("college_logo","upload/college/logo/10335.jpg");
        educationMap2.put("start_date","2010-07-01");
        educationMap2.put("major_name","计算机应用");
        educationMap2.put("college_code",10335);
        educationMap2.put("id",310057);
        educationMap2.put("college_name","浙江大学");
        education.add(educationMap2);
        profiles.put("educations",education);
        profiles.put("recent_job",recent);
        profiles.put("basic",basic);
        profiles.put("profile",profile);
        users.put("profiles",profiles);
        List<Map<String,Object>> applist=new ArrayList<>();
        Map<String,Object> app1=new HashMap<>();
        app1.put("publisher",82752);
        app1.put("title","position1");
        Map<String,Object> app2=new HashMap<>();
        app2.put("publisher",82752);
        app2.put("title","position2");
        Map<String,Object> app3=new HashMap<>();
        app3.put("publisher",82752);
        app3.put("title","position3");
        applist.add(app1);
        applist.add(app2);
        applist.add(app3);
        users.put("applications",applist);
        users.put("age",28);
        userData.put("user",users);
        dataList.add(userData);

        Map<String,Object> userData1=new HashMap<>();
        Map<String,Object> users1=new HashMap<>();
        Map<String,Object> profiles1=new HashMap<>();
        Map<String,Object> profile1=new HashMap<>();
        Map<String,Object> basic1=new HashMap<>();
        Map<String,Object> recent1=new HashMap<>();
        List<Map<String,Object>> education1=new ArrayList<>();
        basic1.put("email","222@moseeker.com");
        basic1.put("name","222");
        basic1.put("heading","222adqwdqwqwqw");
        basic1.put("gender_name","女");
        basic1.put("city_name","上海");
        basic1.put("highest_degree",5);
        profile1.put("user_id",222);
        recent1.put("end_until_now",0);
        recent1.put("company_scale_name","请选择企业规模");
        recent1.put("job_name","市场总监");
        recent1.put("company_name","寻仟科技");
        recent1.put("start_date","2012-09-01");
        recent1.put("company_id",238902);
        recent1.put("department_name","市场部");
        recent1.put("company_new_name","寻仟科技");
        recent1.put("job","市场总监");
        recent1.put("company_scale",0);
        recent1.put("company_property_name","asasas");
        recent1.put("position_code",0);
        recent1.put("position_name","ssdssd");
        recent1.put("company_property",0);
        recent1.put("id",420874);
        recent1.put("achievement",0);
        recent1.put("description","市场策划，活动安排");
        Map<String,Object> educationMap11=new HashMap<>();
        educationMap11.put("end_until_now",0);
        educationMap11.put("major_code",0);
        educationMap11.put("degree",5);
        educationMap11.put("end_date","2012-08-01");
        educationMap11.put("description","教育部门负责人");
        educationMap11.put("college_logo","upload/college/logo/10335.jpg");
        educationMap11.put("start_date","2010-07-01");
        educationMap11.put("major_name","计算机应用");
        educationMap11.put("college_code",10335);
        educationMap11.put("id",310057);
        educationMap11.put("college_name","浙江大学");
        education1.add(educationMap11);
        Map<String,Object> educationMap12=new HashMap<>();
        educationMap12.put("end_until_now",0);
        educationMap12.put("major_code",0);
        educationMap12.put("degree",5);
        educationMap12.put("end_date","2012-08-01");
        educationMap12.put("description","教育部门负责人");
        educationMap12.put("college_logo","upload/college/logo/10335.jpg");
        educationMap12.put("start_date","2010-07-01");
        educationMap12.put("major_name","计算机应用");
        educationMap12.put("college_code",10335);
        educationMap12.put("id",310057);
        educationMap12.put("college_name","浙江大学");
        education1.add(educationMap12);
        profiles1.put("educations",education1);
        profiles1.put("basic",basic1);
        profile1.put("recent_job",recent1);
        profiles1.put("profile",profile1);
        users1.put("profiles",profiles1);
        List<Map<String,Object>> applist1=new ArrayList<>();
        Map<String,Object> app11=new HashMap<>();
        app11.put("publisher",82752);
        app11.put("title","position1");
        Map<String,Object> app12=new HashMap<>();
        app12.put("publisher",82752);
        app12.put("title","position2");
        Map<String,Object> app13=new HashMap<>();
        app13.put("publisher",82752);
        app13.put("title","position3");
        applist1.add(app11);
        applist1.add(app12);
        applist1.add(app13);
        users1.put("applications",applist1);
        users1.put("age",28);
        userData1.put("user",users1);
        dataList.add(userData1);
        Map<String,Object> userData2=new HashMap<>();
        Map<String,Object> users2=new HashMap<>();
        Map<String,Object> profiles2=new HashMap<>();
        Map<String,Object> profile2=new HashMap<>();
        Map<String,Object> basic2=new HashMap<>();
        Map<String,Object> recent2=new HashMap<>();
        List<Map<String,Object>> education2=new ArrayList<>();
        basic2.put("email","333@moseeker.com");
        basic2.put("name","333");
        basic2.put("heading","333adqwdqwqwqw");
        basic2.put("gender_name","女");
        basic2.put("city_name","武汉");
        profile2.put("user_id",333);
        recent2.put("end_until_now",0);
        recent2.put("company_scale_name","请选择企业规模");
        recent2.put("job_name","市场总监");
        recent2.put("company_name","寻仟科技");
        recent2.put("start_date","2012-09-01");
        recent2.put("company_id",238902);
        recent2.put("department_name","市场部");
        recent2.put("company_new_name","寻仟科技");
        recent2.put("job","市场总监");
        recent2.put("company_scale",0);
        recent2.put("company_property_name","asasas");
        recent2.put("position_code",0);
        recent2.put("position_name","ssdssd");
        recent2.put("company_property",0);
        recent2.put("id",420874);
        recent2.put("achievement",0);
        recent2.put("description","市场策划，活动安排");
        Map<String,Object> educationMap21=new HashMap<>();
        educationMap21.put("end_until_now",0);
        educationMap21.put("major_code",0);
        educationMap21.put("degree",5);
        educationMap21.put("end_date","2012-08-01");
        educationMap21.put("description","教育部门负责人");
        educationMap21.put("college_logo","upload/college/logo/10335.jpg");
        educationMap21.put("start_date","2010-07-01");
        educationMap21.put("major_name","计算机应用");
        educationMap21.put("college_code",10335);
        educationMap21.put("id",310057);
        educationMap21.put("college_name","浙江大学");
        education2.add(educationMap21);
        Map<String,Object> educationMap22=new HashMap<>();
        educationMap22.put("end_until_now",0);
        educationMap22.put("major_code",0);
        educationMap22.put("degree",5);
        educationMap22.put("end_date","2012-08-01");
        educationMap22.put("description","教育部门负责人");
        educationMap22.put("college_logo","upload/college/logo/10335.jpg");
        educationMap22.put("start_date","2010-07-01");
        educationMap22.put("major_name","计算机应用");
        educationMap22.put("college_code",10335);
        educationMap22.put("id",310057);
        educationMap22.put("college_name","浙江大学");
        education2.add(educationMap22);
        profiles2.put("educations",education2);
        profile2.put("recent_job",recent2);
        profiles2.put("basic",basic2);
        profiles2.put("profile",profile2);
        users2.put("profiles",profiles2);
        List<Map<String,Object>> applist2=new ArrayList<>();
        Map<String,Object> app21=new HashMap<>();
        app21.put("publisher",82752);
        app21.put("title","position1");
        Map<String,Object> app22=new HashMap<>();
        app22.put("publisher",82752);
        app22.put("title","position2");
        Map<String,Object> app23=new HashMap<>();
        app23.put("publisher",82752);
        app23.put("title","position3");
        applist2.add(app21);
        applist2.add(app22);
        applist2.add(app23);
        users2.put("applications",applist2);
        users2.put("age",28);
        userData2.put("user",users2);
        dataList.add(userData2);
        data.put("users",dataList);
        Response res= ResponseUtils.success(data);
        System.out.println(res.getData());
    }

    public void EmailDataTest(){
        EmailInviteBean bean=new EmailInviteBean();

    }
}
