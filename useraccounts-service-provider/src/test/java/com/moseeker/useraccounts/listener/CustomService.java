package com.moseeker.useraccounts.listener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jack on 28/12/2017.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
public class CustomService {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testListener() {

        System.out.println("testListener");
        Business business = new Business();
        business.setName("test");
        business.setId(1);
        CustomEvent customEvent = new CustomEvent(business);

        context.publishEvent(customEvent);
    }
}
