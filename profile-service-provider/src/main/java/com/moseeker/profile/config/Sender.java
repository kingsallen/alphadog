package com.moseeker.profile.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by zztaiwll on 18/4/23.
 */
@Component
public class Sender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void  send(String message) {
        MessageProperties msp = new MessageProperties();
        msp.setDelay(5000); // 延迟5s发送
        amqpTemplate.send("profile_company_tag_recom_exchange", "profilecompanytagrecom.#", MessageBuilder.withBody(message.getBytes()).andProperties(msp).build());
        System.out.println("send success...");
    }


}
