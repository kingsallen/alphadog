//package com.moseeker.searchengine;
//
//import com.moseeker.searchengine.config.AppConfig;
//import com.moseeker.searchengine.service.impl.SearchengineService;
//import com.moseeker.thrift.gen.common.struct.Response;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
///**
// * Created by zztaiwll on 17/8/8.
// */
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes =AppConfig.class)
//public class SearchEngineTest {
//    @Autowired
//    private SearchengineService service;
//    @Test
//    public void searchTest()throws Exception{
//        /*
//        String keywords, String cities, String industries, String occupations, String scale,
//                          String employment_type, String candidate_source, String experience, String degree, String salary,
//                          String company_name, int page_from, int page_size, String child_company_name, String department, boolean order_by_priority, String custom
//         */
//        String keywords="";
//        String cities="";
//        String industries="";
//        String occupations="";
//        String scale="";
//        String employment_type="";
//        String candidate_source="";
//        String experience="";
//        String degree="";
//        String salary="";
//        String company_name="";
//        int page_from=0;
//        int page_size=10;
//        String child_company_name="";
//        String department="";
//        boolean order_by_priority=true;
//        String custom="";
//        Response result=service.query(keywords,cities,industries,occupations,scale,employment_type,candidate_source,experience,degree,salary,company_name,page_from,page_size,child_company_name,department,order_by_priority,custom);
//        System.out.println(result);
//
//    }
//
//
//}
