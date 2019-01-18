package com.moseeker.useraccounts.kafka;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.Constant;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    private Logger logger = LoggerFactory.getLogger(KafkaListeners.class);

    @KafkaListener(id = Constant.KAFKA_GROUP_ID, containerFactory = "kafkaListenerContainerFactory", topics  =  {"test1"})
    public void test(ConsumerRecord<?,  ?> record)  {
        Object  message  =  record.value();
        JSONObject jsonObject = JSONObject.parseObject(String.valueOf(message));
        logger.info("test_group" + message);
    }

//    @KafkaListener(id = "test-consumer-group1", containerFactory = "kafkaListenerContainerFactoryWithTest1Group", topics  =  {"test1"})
//    public void test1(ConsumerRecord<?,  ?> record)  {
//        Object  message  =  record.value();
//        System.out.println("test-consumer-group1" + message);
//    }
//
//    @KafkaListener(containerFactory = "kafkaListenerContainerFactoryWithTestGroup", topics  =  {"test1"})
//    public void testno(ConsumerRecord<?,  ?> record)  {
//        Object  message  =  record.value();
//        System.out.println("testno" + message);
//    }

}
