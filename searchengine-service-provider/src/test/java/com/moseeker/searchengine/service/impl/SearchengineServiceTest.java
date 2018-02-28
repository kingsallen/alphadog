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
}