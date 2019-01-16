package com.moseeker.useraccounts.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(id = "test-consumer-group", containerFactory = "kafkaListenerContainerFactoryWithTestGroup", topics  =  {"test1"})
    public void test(ConsumerRecord<?,  ?> record)  {
        Object  message  =  record.value();
        System.out.println("test-consumer-group" + message);
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
