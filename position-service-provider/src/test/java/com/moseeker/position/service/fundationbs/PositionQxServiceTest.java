//package com.moseeker.position.service.fundationbs;
//
//import com.alibaba.fastjson.JSON;
//import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyAccount;
//import com.moseeker.baseorm.db.hrdb.tables.pojos.HrCompanyFeature;
//import com.moseeker.baseorm.util.BeanUtils;
//import com.moseeker.position.config.AppConfig;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by YYF
// *
// * Date: 2017/4/18
// *
// * Project_name :alphadog
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
//public class PositionQxServiceTest {
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Autowired
//    private PositionQxService positionQxService;
//
//
//    @Test
//    public void testAddFeature(){
//        int pid=1;
//        int fid=1;
//        positionQxService.updatePositionFeature(pid,fid);
//    }
//    @Test
//    public void testAddFeatures(){
//        int pid=1;
//        List<Integer> list=new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        positionQxService.updatePositionFeatureList(pid,list);
//    }
//
//    @Test
//    public void testGetFeatures() {
//        List<HrCompanyFeature> list = positionQxService.getPositionFeature(123971);
//        System.out.println(JSON.toJSONString(list));
//    }
//}