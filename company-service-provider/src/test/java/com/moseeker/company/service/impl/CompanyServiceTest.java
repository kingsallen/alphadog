package com.moseeker.company.service.impl;

import com.moseeker.company.config.AppConfig;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lucky8987 on 17/5/10.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
public class CompanyServiceTest {

//    @Autowired
    private CompanyService service;

    //@Test
    public void getResources() throws Exception {
        Response resources = service.getResources(new CommonQuery());
        System.out.println(resources);
    }

    //@Test
    public void getResource() throws Exception {
        Response resource = service.getResource(new CommonQuery());
        System.out.println(resource);
    }

    //@Test
    public void getAllCompanies() throws Exception {
        Response resource = service.getAllCompanies(new CommonQuery());
        System.out.println(resource);
    }

    //@Test
//    @Transactional
    public void add() throws Exception {
        Hrcompany company = new Hrcompany();
        company.setName("人间烟火");
        company.setSource(0);
        company.setIndustry("没有介绍");
        Response response = service.add(company);
        System.out.println(response);
    }

    //@Test
    public void getWechat() throws Exception {
        Response response = service.getWechat(2878, 0);
        System.out.println(response);
    }

//
//    @Test
//    public void getGroupCompanies() {
//        try {
//            List<CompanyForVerifyEmployee> employeeList = service.getGroupCompanies(3);
//            System.out.println(employeeList);
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        }
//    }
//
//    @Test
//    public void bindingSwitch() {
//        try {
//            Response response = service.bindingSwitch(3, 1);
//            System.out.println(response);
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//        }
//    }
    @Test
    public void getBannerTest() throws Exception{
    	int page=1;
    	int pagesize=10;
    	Response res=service.getPcBannerByPage(page, pagesize);
    	System.out.println(res);
    }

}