package com.moseeker.mq.service.sms;

import com.moseeker.mq.config.AppConfig;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.mq.struct.SmsType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by lucky8987 on 17/5/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class SmsServiceTest {

    @Autowired
    private SmsService service;

    @Test
    public void sendSMS() throws Exception {
        Response response = service.sendSMS(SmsType.RANDOM_SMS, "13020287221", new HashMap<String, String>() {{
            put("code", "123456");
        }}, "0", "192.168.2.24");
        System.out.println(response);
    }

}