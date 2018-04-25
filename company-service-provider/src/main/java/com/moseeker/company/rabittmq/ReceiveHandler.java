package com.moseeker.company.rabittmq;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.util.StringUtils;
import com.moseeker.company.service.impl.CompanyTagService;
import com.moseeker.thrift.gen.dao.struct.logdb.LogDeadLetterDO;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by zztaiwll on 18/4/23.
 */
@Component
@PropertySource("classpath:common.properties")
public class ReceiveHandler {
    private static Logger log = LoggerFactory.getLogger(ReceiveHandler.class);
    @Autowired
    private CompanyTagService companyTagService;
    @RabbitListener(queues = "#{profileCompanyTagRecomQue.name}", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void profileCompanyTagRecomQueHandler(Message message, Channel channel) {
        String msgBody = "{}";
        try {
            msgBody = new String(message.getBody(), "UTF-8");
            if(StringUtils.isNotNullOrEmpty(msgBody)&&!"{}".equals(msgBody)){
                JSONObject jsonObject = JSONObject.parseObject(msgBody);
                Set<Integer> userIdSet= (Set<Integer>) jsonObject.get("user_ids");
                Set<Integer> companyIdSet=(Set<Integer>)jsonObject.get("company_ids");
                companyTagService.handlerProfileCompanyIds(userIdSet,companyIdSet);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
