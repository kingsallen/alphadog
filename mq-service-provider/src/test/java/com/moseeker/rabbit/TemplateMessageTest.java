//package com.moseeker.rabbit;
//
//import com.moseeker.entity.MessageTemplateEntity;
//import com.moseeker.entity.PersonaRecomEntity;
//import com.moseeker.mq.config.AppConfig;
//import com.moseeker.thrift.gen.mq.struct.MessageTemplateNoticeStruct;
//import org.apache.thrift.TException;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
///**
// * Created by zztaiwll on 17/11/7.
// */
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
//public class TemplateMessageTest {
//
//    @Autowired
//    private PersonaRecomEntity personaRecomEntity;
//
//    @Autowired
//    private MessageTemplateEntity messageTemplateEntity;
//
//    //测试推荐职位入库
//    @Test
//    public void personaRecomPositionTest() throws TException {
//        int result=personaRecomEntity.handlePersonaRecomData(2192148,"");
//        System.out.println("==============="+result+"===============");
//    }
//    //测试获取推送模板内容
//    @Test
//    public void sendRecomTemplate(){
//        MessageTemplateNoticeStruct messageTemplate=messageTemplateEntity.handlerTemplate(2192148,2891,59,2,"www.baidu.com","xxxx","xxxx");
//        System.out.println("=======================================================");
//        System.out.println(messageTemplate);
//        System.out.println("=======================================================");
//    }
//    //测试获取推送模板内容
//    @Test
//    public void sendFansTemplate(){
//        MessageTemplateNoticeStruct messageTemplate=messageTemplateEntity.handlerTemplate(2192148,2891,57,1,"www.baidu.com",null,null);
//        System.out.println("=======================================================");
//        System.out.println(messageTemplate);
//        System.out.println("=======================================================");
//    }
//
//
//}
