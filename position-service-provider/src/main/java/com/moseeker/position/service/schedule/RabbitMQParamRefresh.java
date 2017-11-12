package com.moseeker.position.service.schedule;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class RabbitMQParamRefresh implements ParamRefresh{

    @Override
    public void refresh() {
        send();
    }

    public abstract void send();


    public abstract void receiveAndHandle(String json);
    public abstract String exchange();
    public abstract String rountineKey();
    public abstract String receiveQueue();

    public abstract ChannelType getChannel();

    public Message createMsg(String json){
        return MessageBuilder.withBody(json.getBytes()).build();
    }

}
