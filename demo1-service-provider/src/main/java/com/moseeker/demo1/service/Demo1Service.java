package com.moseeker.demo1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by jack on 2018/4/18.
 */
@Component
public class Demo1Service {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //@ConsumerEntry(messageName = "test", businessName = "demo1", index = 0)
    public String comsumerTest(String messageId, int id) {
        logger.info("messageId: {},  id:{} ", messageId, id);
        System.out.println("comsumerTest messageId:"+messageId+"  id:"+id);
        return messageId+" "+id;
    }
}
