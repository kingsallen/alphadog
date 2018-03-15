//package com.moseeker.company.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.moseeker.company.config.AppConfig;
//import com.moseeker.thrift.gen.common.struct.CommonQuery;
//import com.moseeker.thrift.gen.common.struct.Response;
//import com.moseeker.thrift.gen.company.struct.HrCompanyFeatureDO;
//import com.moseeker.thrift.gen.company.struct.Hrcompany;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by lucky8987 on 17/5/10.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
//public class CompanyServiceTest {
//
//    @Autowired
//    private CompanyService service;
//
//    //@Test
//    public void getResources() throws Exception {
//        Response resources = service.getResources(new CommonQuery());
//        System.out.println(resources);
//    }
//
//    //@Test
//    public void getResource() throws Exception {
//        Response resource = service.getResource(new CommonQuery());
//        System.out.println(resource);
//    }
//
//    //@Test
//    public void getAllCompanies() throws Exception {
//        Response resource = service.getAllCompanies(new CommonQuery());
//        System.out.println(resource);
//    }
//
//    //@Test
////    @Transactional
//    public void add() throws Exception {
//        Hrcompany company = new Hrcompany();
//        company.setName("人间烟火");
//        company.setSource(0);
//        company.setIndustry("没有介绍");
//        Response response = service.add(company);
//        System.out.println(response);
//    }
//
//    //@Test
//    public void getWechat() throws Exception {
//        Response response = service.getWechat(2878, 0);
//        System.out.println(response);
//    }
//
//    @Test
//    public void getBannerTest() throws Exception{
//    	int page=1;
//    	int pagesize=10;
//    	Response res=service.getPcBannerByPage(page, pagesize);
//    	System.out.println(res);
//    }
//
//    @Test
//    public void testGetTalentPooleStatus(){
//        int hrId=91372;
//        int companyId=242645;
//        int result=service.getTalentPoolSwitch(hrId,companyId);
//        System.out.println(result);
//    }
//
//    @Test
//    public void testGetFeature(){
//        int companyId=39978;
//        System.out.println(JSON.toJSONString(service.getCompanyFeatureByCompanyId(companyId)));
//    }
//    @Test
//    public void testGetFeatureById(){
//        int id=19;
//        System.out.println(JSON.toJSONString(service.getCompanyFeatureById(id)));
//    }
//    @Test
//    public void testAddFeature(){
//        HrCompanyFeatureDO DO=new HrCompanyFeatureDO();
//        DO.setCompany_id(1);
//        DO.setDisable(0);
//        DO.setFeature("1111111");
//        System.out.println(JSON.toJSONString(service.addCompanyFeature(DO)));
//    }
//    @Test
//    public void testAddFeatureList(){
//        List<HrCompanyFeatureDO> list=new ArrayList<>();
//        HrCompanyFeatureDO DO=new HrCompanyFeatureDO();
//        DO.setCompany_id(1);
//        DO.setDisable(0);
//        DO.setFeature("1111111");
//        list.add(DO);
//        HrCompanyFeatureDO DO1=new HrCompanyFeatureDO();
//        DO1.setCompany_id(1);
//        DO1.setDisable(0);
//        DO1.setFeature("222222222222");
//        list.add(DO1);
//        System.out.println(JSON.toJSONString(service.addCompanyFeatureList(list)));
//    }
//    @Test
//    public void testUpdateFeatureList(){
//        List<HrCompanyFeatureDO> list=new ArrayList<>();
//        HrCompanyFeatureDO DO=new HrCompanyFeatureDO();
//        DO.setId(489);
//        DO.setCompany_id(1);
//        DO.setDisable(1);
//        DO.setFeature(";dddd)ddd");
//        list.add(DO);
//        HrCompanyFeatureDO DO1=new HrCompanyFeatureDO();
//        DO1.setId(490);
//        DO1.setCompany_id(1);
//        DO1.setDisable(1);
//        DO1.setFeature("ssssss");
//        list.add(DO1);
//        System.out.println(JSON.toJSONString(service.updateCompanyFeatureList(list)));
//    }
//
//
//}