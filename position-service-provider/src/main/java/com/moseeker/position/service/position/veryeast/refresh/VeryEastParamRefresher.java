package com.moseeker.position.service.position.veryeast.refresh;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.constants.VeryEastConstant;
import com.moseeker.position.service.position.base.refresh.AbstractRabbitMQParamRefresher;
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
    private AmqpTemplate amqpTemplate;

    private List<VEResultHandlerAdapter> refreshList=new ArrayList<>();

    @Autowired
    public VeryEastParamRefresher(List<VEResultHandlerAdapter> list){
        refreshList.addAll(list);
    }

    @Override
    public void send() {
        JSONObject jsonSend=new JSONObject();
        JSONArray moseekerReginArray=moseekerRegin();

        jsonSend.put("channel",getChannel().getValue());
        jsonSend.put("moseeker_region",moseekerReginArray);

        String json=jsonSend.toJSONString();
        logger.info("VeryEast refresh param send RabbitMQ :"+json);

        amqpTemplate.send(VeryEastConstant.EXCHANGE,VeryEastConstant.PARAM_SEND_ROUTING_KEY, createMsg(json));
        logger.info("send RabbitMQ success");
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
    public ChannelType getChannel() {
        return ChannelType.VERYEAST;
    }

}
