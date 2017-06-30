package com.moseeker.rabbit;

import com.moseeker.mq.config.AppConfig;
import com.moseeker.mq.rabbit.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lucky8987 on 17/6/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class RabbitMqTest {

    @Autowired
    private Sender sender;

    @Test
    public void testHello() throws InterruptedException {
        sender.send("vincent");
    }
}
