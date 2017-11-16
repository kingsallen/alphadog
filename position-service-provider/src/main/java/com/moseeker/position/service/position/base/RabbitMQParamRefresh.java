package com.moseeker.position.service.position.base;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.ParamRefresh;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;

public abstract class RabbitMQParamRefresh implements ParamRefresh {

    @Override
    public void refresh() {
        send();
    }

    public abstract void send();


    public abstract void receiveAndHandle(String json);

    public abstract ChannelType getChannel();

    public Message createMsg(String str){
        return MessageBuilder.withBody(str.getBytes()).build();
    }

}
