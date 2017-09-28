package com.moseeker.useraccounts.service.thirdpartyaccount;

import com.alibaba.fastjson.JSON;
import com.moseeker.useraccounts.config.AppConfig;
import com.moseeker.useraccounts.pojo.BindResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by jack on 28/09/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ThirdPartyAccountMessageHandlerTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void bindAccountQueue() throws Exception {

        BindResult result = new BindResult();
        result.setStatus(0);
        result.setMessage("success");

        BindResult.Account account = result.createAccount();
        account.setAccountId(8);

        result.setAccount(account);
        String str = JSON.toJSONString(result);
        amqpTemplate.send("chaos.bind.response.exchange", "chaos.bind.response", MessageBuilder.withBody(str.getBytes()).build());
    }

    @Test
    public void presetHandler() throws Exception {

    }

}