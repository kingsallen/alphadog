//package com.moseeker.company.service.impl;
//
//import com.moseeker.company.config.AppConfig;
//import org.apache.thrift.TException;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by zztaiwll on 17/8/15.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
//public class CompanyPcServiceTest {
//    @Autowired
//    private CompanyPcService companyPcService;
//    //获取公司信息
//    @Test
//    public void companyDetailsTest()throws Exception{
//        Map<String,Object> result=companyPcService.getCompanyDetail(39978);
//        System.out.println(result+"======");
//    }
//    //获取团队列表
//    @Test
//    public void teamListTest() throws Exception {
//        Map<String,Object> result=companyPcService.getTeamListinfo(39978,1,10);
//        System.out.println(result+"======");
//    }
//    //获取团队详情
//    @Test
//    public void teamDetailsTest() throws Exception {
//        Map<String,Object> result=companyPcService.getTeamDetails(103,1);
//        System.out.println(result+"======");
//    }
//    //获取公司信息
//    @Test
//    public void companyInfo() throws Exception {
//        Map<String,Object> result=companyPcService.getCompanyMessage(139183);
//        System.out.println(result+"======");
//    }
//    @Test
//    public void getNoJdCompanyInfoTest() throws Exception {
//        Map<String,Object> result=companyPcService.getCompanyDetail(2878);
//        System.out.println(result+"======");
//    }
//    @Test
//    public void getJdSubCompanyInfoTest() throws Exception {
//        Map<String,Object> result=companyPcService.getCompanyDetail(41305);
//        System.out.println(result+"======");
//    }
//    @Test
//    public void getNoJdSubCompanyInfoTest() throws Exception {
//        Map<String,Object> result=companyPcService.getCompanyDetail(39785);
//        System.out.println(result+"======");
//    }
//}
