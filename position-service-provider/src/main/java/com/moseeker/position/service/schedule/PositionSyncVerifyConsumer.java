package com.moseeker.position.service.schedule;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.PositionSyncVerify;
import com.moseeker.position.service.position.base.PositionFactory;
import com.moseeker.position.service.position.base.sync.verify.PositionSyncVerifyHandler;
import com.moseeker.position.utils.PositionEmailNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PositionSyncVerifyConsumer {
    Logger logger= LoggerFactory.getLogger(PositionSyncVerifyConsumer.class);


    @Autowired
    PositionEmailNotification emailNotification;


    @Autowired
    PositionFactory verifyHandlerFactory;

    @RabbitListener(queues = {PositionSyncVerify.MOBILE_VERIFY_QUEUE}, containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void handle(Message message) {
        String json="";

        try {
            json=new String(message.getBody(), "UTF-8");
            logger.info("receive json {}",json );

            JSONObject obj=JSONObject.parseObject(json);

            int channel=obj.getIntValue("channel");

            ChannelType channelType=ChannelType.instaceFromInteger(channel);

            PositionSyncVerifyHandler verifyHandler=verifyHandlerFactory.getVerifyHandlerInstance(channelType);

            verifyHandler.verifyHandler(json);

            logger.info("handle json success json: {}",json);
        }catch (Exception e){
            logger.error("handle refresh result Error : {}, message :{}",e.getMessage(),json);
            emailNotification.sendVerifyFailureMail(json, null, e);
        }
    }

}
