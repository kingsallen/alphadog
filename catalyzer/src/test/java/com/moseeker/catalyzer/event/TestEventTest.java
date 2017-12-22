package com.moseeker.catalyzer.event;

import com.moseeker.catalyzer.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jack on 13/12/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class TestEventTest {

    @Autowired
    private ApplicationContext applicationContext;
    @Test
    public void testPublishEvent() {
        applicationContext.publishEvent(new TestEvent("TestEventTest"));
    }
}