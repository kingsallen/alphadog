package com.moseeker.demo1.service;

import com.moseeker.consistencysuport.consumer.ConsumerEntry;
import org.springframework.stereotype.Component;

/**
 * Created by jack on 2018/4/18.
 */
@Component
public class Demo1Service {

    @ConsumerEntry(messageName = "test", businessName = "demo1", index = 0)
    public String comsumerTest(String messageId, int id) {
        System.out.println("messageId:"+messageId +" id:"+id);
        return messageId+" "+id;
    }
}
