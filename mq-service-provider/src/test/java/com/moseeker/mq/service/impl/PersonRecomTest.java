//package com.moseeker.mq.service.impl;
//
//import com.moseeker.entity.PersonaRecomEntity;
//import com.moseeker.mq.config.AppConfig;
//import org.apache.thrift.TException;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
///**
// * Created by zztaiwll on 18/5/30.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
//public class PersonRecomTest {
//
//    @Autowired
//    private PersonaRecomEntity personaRecomEntity;
//    @Test
//    public void handlePersonaRecomDataTest() throws TException {
//        int userId=2197822;
//        int companyId=248355;
//        int type=0;
//        String positionIds="1914962,1914990,1914970,1914968,1914964,1915023,1914987,1914974,1914966,1914971";
//        personaRecomEntity.handlePersonaRecomData(userId,positionIds,companyId,type);
//    }
//}
