package com.moseeker.entity.biz;

import com.moseeker.baseorm.dao.profiledb.entity.ProfileWorkexpEntity;
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