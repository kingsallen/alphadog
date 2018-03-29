package com.moseeker.entity.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.baseorm.dao.profiledb.entity.ProfileWorkexpEntity;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.baseorm.util.BeanUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by jack on 09/11/2017.
 */
public class ProfileUtilsTest {
    @Test
    public void test(){

        String json="[\n" +
                "    {\n" +
                "      \"end_date\": \"2018-02-01\",\n" +
                "      \"end_until_now\": 1,\n" +
                "      \"responsibility\": \"\",\n" +
                "      \"company_name\": \"上海天天有限公司\",\n" +
                "      \"name\": \"项目天天有\",\n" +
                "      \"description\": \"发生的发>生的发生的\",\n" +
                "      \"start_date\": \"2006-03-01\"\n" +
                "    }\n" +
                "  ]";

        TypeReference<List<Map<String, Object>>> typeReference=new TypeReference<List<Map<String, Object>>>(){};

        List<Map<String, Object>> array=JSON.parseObject(json,typeReference);;

        List<ProfileProjectexpRecord> list=new ProfileUtils().mapToProjectExpsRecords(array);

        System.out.println(list);

        /*Map<String, Object> basic=new HashMap<>();
        basic.put("birth","1988/03/26");
        ProfileBasicRecord result=BeanUtils.MapToRecord(basic, ProfileBasicRecord.class);
        System.out.println(result);*/
    }


    private static List<Map<String, Object>> workexps = new ArrayList<Map<String, Object>>(){{
        Map<String, Object> workExp = new HashMap<>();
        workExp.put("job", "职位");
        workExp.put("startDate", "2017-01-01");
        workExp.put("endDate", "2017-09-01");
        workExp.put("description", "描述");

        Map<String, Object> company = new HashMap<>();
        company.put("company_name", "公司名称");
        workExp.put("company", company);
        add(workExp);


        Map<String, Object> workExp1 = new HashMap<>();
        workExp1.put("job", "职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位职位");
        workExp1.put("startDate", "2017-01-01");
        workExp1.put("endDate", "2017-09-01");
        workExp1.put("description", "描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述");

        workExp1.put("company", company);
        add(workExp1);

    }};

    @Test
    public void mapToWorkExpRecords() throws Exception {


        ProfileUtils profileUtils = new ProfileUtils();

        List<ProfileWorkexpEntity> profileWorkExpEntityList = profileUtils.mapToWorkexpRecords(workexps, 0);
        assertEquals("职位", profileWorkExpEntityList.get(0).getJob());
    }

    @Test
    public void mapToWorkExpRecords1() throws Exception {

        ProfileUtils profileUtils = new ProfileUtils();

        List<ProfileWorkexpEntity> profileWorkExpEntityList = profileUtils.mapToWorkexpRecords(workexps, 0);
        assertEquals(900, profileWorkExpEntityList.get(1).getDescription().length());
        assertEquals(100, profileWorkExpEntityList.get(1).getJob().length());
    }

}