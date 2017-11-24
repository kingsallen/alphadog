package com.moseeker.position.service.position.veryeast.refresh;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.constants.VeryEastConstant;
import com.moseeker.position.service.position.base.refresh.AbstractRabbitMQParamRefresher;
import com.moseeker.position.service.position.base.refresh.handler.ResultHandler;
import com.moseeker.position.service.position.veryeast.refresh.handler.VEResultHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class VeryEastParamRefresher extends AbstractRabbitMQParamRefresher {
    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private List<VEResultHandlerAdapter> refreshList;

    @Override
    public void addSendParam(JSONObject jsonSend) {
        jsonSend.put("moseeker_region",moseekerRegin());
    }

    @Override
    @RabbitListener(queues = {VeryEastConstant.PARAM_GET_QUEUE}, containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void receiveAndHandle(String json) {
        logger.info("receive json:{}" ,json);
        //调用所有处理策略
        refreshList.forEach(r->r.handle(json));
    }

    @Override
    public void addUserParam(JSONObject jsonSend) {
        jsonSend.put("user_name","");
        jsonSend.put("password","");
    }

    @Override
    public String exchange() {
        return VeryEastConstant.EXCHANGE;
    }
    @Override
    public String routingKey() {
        return VeryEastConstant.PARAM_SEND_ROUTING_KEY;
    }
    @Override
    public ChannelType getChannel() {
        return ChannelType.VERYEAST;
    }

}
