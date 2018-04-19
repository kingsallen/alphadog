package com.moseeker.searchengine.service.impl;

import com.moseeker.searchengine.config.AppConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by YYF
 *
 * Date: 2017/8/10
 *
 * Project_name :alphadog
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class SearchengineServiceTest {


    @Autowired
    private SearchengineService searchengineService;
    @Autowired
    private TalentpoolSearchengine talentpoolSearchengine;

    @Test
    public void updateEmployeeAwards() throws Exception {
        List<Integer> employeeIds = new ArrayList<>();
        employeeIds.add(446152);
        searchengineService.updateEmployeeAwards(employeeIds);
    }


    @Test
    public void deleteEmployeeDO() throws Exception {
        List<Integer> employeeIds = new ArrayList<>();
        employeeIds.add(2);
        employeeIds.add(1);
        searchengineService.deleteEmployeeDO(employeeIds);
    }

    @Test
    public void testSuggest(){
        Map<String,String> params=new HashMap<>();
        params.put("page_from","1");
        params.put("return_params","title");
        params.put("flag","1");
        params.put("company_id","4");
        params.put("keyWord","产品经理");
        params.put("page_size","16");
        Map<String,Object> result=searchengineService.getPositionSuggest(params);
        System.out.println(result.toString());
    }

    @Test
    public void testCompanyFilter(){
        List<Map<String,String>> list=new ArrayList<>();
        Map<String,String> params=new HashMap<>();
        params.put("origins","1,2,3");
        params.put("work_years","2,3");
        params.put("city_name","11,22,33");
        params.put("degree","4");
        params.put("keyWord","产品经理");
        params.put("past_position","xxxxxxx");
        params.put("is_recommend","1");
        params.put("company_id","39978");
        params.put("hr_id","82752");
        params.put("account_type","1");
        list.add(params);
        Map<String,String> params1=new HashMap<>();
        params1.put("origins","1,2,3");
        params1.put("work_years","2,3");
        params1.put("city_name","11,22,33");
        params1.put("degree","4");
        params1.put("keyWord","产品经理");
        params1.put("past_position","xxxxxxx");
        params1.put("is_recommend","1");
        params1.put("company_id","39978");
        list.add(params1);
        talentpoolSearchengine.getUserListByFilterIds(list,1,0);
    }
}