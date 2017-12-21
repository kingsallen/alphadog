package com.moseeker.common.util;

import com.moseeker.common.constants.Constant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

/**
 * Created by moseeker on 2017/12/20.
 */
public class MandrillMailSendTest {
    @Test
    public void sendEmailTest(){
        MandrillMailSend send = new MandrillMailSend();
        Map<String, Object> params = new HashMap<>();
        params.put("templateName", Constant.RESUME_INFORM_HR);
        Map<String, Object> map = new HashMap<>();
        map.put("company_name", "仟寻");
        map.put("position_name", "java工程师");
        map.put("heading", "https://cdn.moseeker.com/weixin/images/avatar-default.png");
        map.put("user_name", "刘旭辉");
        String educations = "";
        List<Map<String, String>> eduList = new ArrayList<>();
        Map<String, String> educationMap1 = new HashMap<>();
        educationMap1.put("startTime", "2015-09");
        educationMap1.put("endTime", "至今");
        educationMap1.put("college_name", "上海大学");
        educationMap1.put("major_name", "计算机工程");
        educationMap1.put("degree","硕士");
        eduList.add(educationMap1);
        Map<String, String> educationMap = new HashMap<>();
        educationMap.put("startTime", "2011-09");
        educationMap.put("endTime", "2015-06");
        educationMap.put("college_name", "上海大学");
        educationMap.put("major_name", "计算机工程");
        educationMap.put("degree","本科");
        eduList.add(educationMap);
        map.put("educationList", eduList);

        map.put("degree_name", "硕士");
            map.put("gender_name", "男");
            map.put("city_name", "上海");
            map.put("birth", "25岁");

        List<Map<String, String>> workList = new ArrayList<>();
                Map<String, String> workMap = new HashMap<>();
                workMap.put("workStartTime", "2016-10");

                workMap.put("workEndTime", "至今");
                workMap.put("workJob", "三顾人才");
                workMap.put("workCompany", "皮凯网络");
                workList.add(workMap);

        map.put("workList", workList);
        params.put("mergeVars", map);

        String subject = "java工程师-刘旭辉-职位申请通知";
        params.put("subject", subject);
        params.put("to_name", "张三");
        params.put("to_email", "wanglintao@moseeker.com");
        send.sendEmail(params, "MGF7plOGhGsZ3xocZDTwoQ");
    }

    @Test
    public void sendEmail2Test(){
        MandrillMailSend send = new MandrillMailSend();
        Map<String, Object> params = new HashMap<>();
        params.put("templateName", Constant.ANNEX_RESUME_INFORM_HR);
        Map<String, Object> map = new HashMap<>();
        map.put("company_name", "仟寻");
        map.put("position_name", "java工程师");
        map.put("heading", "https://cdn.moseeker.com/weixin/images/avatar-default.png");
        map.put("user_name", "刘旭辉");
        map.put("profile_full_url", "https://cdn.moseeker.com/weixin/images/avatar-default.png");
        params.put("mergeVars", map);
        String subject = "java工程师-刘旭辉-职位申请通知";
        params.put("subject", subject);
        params.put("to_name", "张三");
        params.put("to_email", "wanglintao@moseeker.com");
        send.sendEmail(params, "MGF7plOGhGsZ3xocZDTwoQ");
    }
}
