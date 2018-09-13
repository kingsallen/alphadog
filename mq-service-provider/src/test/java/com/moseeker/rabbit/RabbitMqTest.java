//package com.moseeker.rabbit;
//
//import com.moseeker.common.util.MD5Util;
//import com.moseeker.mq.config.AppConfig;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.Date;
//
///**
// * Created by lucky8987 on 17/6/28.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
//public class RabbitMqTest {
//    @Test
//   public  void test(){
//       String MDString= MD5Util.md5(39978+""+123212+""+new Date().getTime());
//       MDString=MDString.substring(8,24);
//       System.out.println(MDString);
//   }
//
//}
