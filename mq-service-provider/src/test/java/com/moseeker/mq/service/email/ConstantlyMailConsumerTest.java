package com.moseeker.mq.service.email;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.email.config.EmailContent;
import com.moseeker.common.email.mail.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 11/04/2017.
 */
public class ConstantlyMailConsumerTest {

    public static void main(String[] args) {

        Message message = new Message();
        message.setAppId(0);
        message.setEventType(0);
        message.setLocation(0);
        message.getParams().put("# send_date #", "2017-04-11");
        message.getParams().put("# verified_url #", "https://\n" +
                "/www.moseeker.com/email/confirm/code/wjRuiyjAcTBzJIt3UNxxHL1jAICqi0V06wjS0x5q1eNJpledCDylqjvUPG-pENu_0z_-rb9Az7YSWBedndikWLZGha6BSXiPffY0VSLTybzNdotJdqQYvd32tH4VlHLj");
        message.getParams().put("# username #", "翁剑飞");

        EmailContent emailContent = new EmailContent();
        emailContent.setCharset("UTF-8");
        List<String> recipients = new ArrayList<>();
        recipients.add("wengjianfei@moseeker.com");
        emailContent.setRecipients(recipients);
        emailContent.setSenderDisplay("仟寻Moseeker");
        emailContent.setSenderName("info@moseeker.net");
        emailContent.setSubType("html");
        emailContent.setSubject("邮箱认证");

        message.setEmailContent(emailContent);

//        RedisClient redisClient = RedisClientFactory.getCacheClient();
//        redisClient.lpush(Constant.APPID_ALPHADOG, Constant.MQ_MESSAGE_EMAIL_BIZ, JSON.toJSONString(message));
    }

}