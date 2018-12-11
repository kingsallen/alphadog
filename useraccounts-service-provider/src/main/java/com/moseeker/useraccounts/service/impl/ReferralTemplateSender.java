package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by moseeker on 2018/12/10.
 */
@Component
public class ReferralTemplateSender {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String SEEK_REFERRAL_EXCHNAGE = "referral_seek_exchange";
    private static final String SEEK_REFERRAL_CHANGE_ROUTINGKEY = "KEY.SEEK_REFERRAL";

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void publishSeekReferralEvent(int postUserId, int referralId, int userId, int positionId){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("position_name", positionId);
        jsonObject.put("post_user_id", postUserId);
        jsonObject.put("referral_id", referralId);
        jsonObject.put("user_id", userId);
        logger.info("publishSeekReferralEvent json:{}", jsonObject);
        amqpTemplate.sendAndReceive(SEEK_REFERRAL_EXCHNAGE,
                SEEK_REFERRAL_CHANGE_ROUTINGKEY, MessageBuilder.withBody(jsonObject.toJSONString().getBytes())
                        .build());


    }
}
