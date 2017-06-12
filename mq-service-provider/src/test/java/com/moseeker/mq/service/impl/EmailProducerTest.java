package com.moseeker.mq.service.impl;

import com.moseeker.common.validation.sensitivewords.SpecialCharCheck;
import com.moseeker.mq.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by lucky8987 on 17/5/11.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
public class EmailProducerTest {

//    @Autowired
    private EmailProducer service;

    //@Test
    public void genUsername() throws Exception {
        String username = service.genUsername(1122611);
        System.out.println(username);
    }

}