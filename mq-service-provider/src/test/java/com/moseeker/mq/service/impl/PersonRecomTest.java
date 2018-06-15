//package com.moseeker.mq.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.moseeker.baseorm.dao.logdb.LogAiRecomDao;
//import com.moseeker.baseorm.db.logdb.tables.records.LogAiRecomRecord;
//import com.moseeker.entity.PersonaRecomEntity;
//import com.moseeker.mq.config.AppConfig;
//import org.apache.thrift.TException;
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
// * Created by zztaiwll on 18/5/30.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
//public class PersonRecomTest {
//
//    @Autowired
//    private LogAiRecomDao logAiRecomDao;
//    @Test
//    public void handlePersonaRecomDataTest() throws TException {
//        List<LogAiRecomRecord> list=new ArrayList<>();
//        LogAiRecomRecord logAiRecomRecord=new LogAiRecomRecord();
//        logAiRecomRecord.setId(0);
//        logAiRecomRecord.setUserId(1);
//        logAiRecomRecord.setWxId(11);
//        logAiRecomRecord.setCompanyId(39978);
//        list.add(logAiRecomRecord);
//        LogAiRecomRecord logAiRecomRecord1=new LogAiRecomRecord();
//        logAiRecomRecord1.setId(0);
//        logAiRecomRecord1.setUserId(2);
//        logAiRecomRecord1.setWxId(11);
//        logAiRecomRecord1.setCompanyId(39978);
//
//        list.add(logAiRecomRecord1);
//        List<Integer> result=logAiRecomDao.batchAdd(list);
//        System.out.println(JSON.toJSONString(result));
//        LogAiRecomRecord result1=logAiRecomDao.addRecord(logAiRecomRecord);
//        System.out.println(result1.toString());
//    }
//}
