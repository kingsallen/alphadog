package com.moseeker.position.service.position.veryeast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.constants.VeryEastConstant;
import com.moseeker.position.service.position.base.ParamRefreshHandler;
import com.moseeker.position.service.position.base.RabbitMQParamRefresh;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class VeryEastParamRefresh extends RabbitMQParamRefresh {
    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    DictCityDao cityDao;

    @Autowired
    private AmqpTemplate amqpTemplate;

    private List<ParamRefreshHandler> refreshList=new ArrayList<>();

    @Autowired
    public VeryEastParamRefresh(List<AbstractVeryEastParamHandler> list){
        refreshList.addAll(list);
    }

    @Override
    public void send() {
        JSONObject jsonSend=new JSONObject();
        JSONArray moseekerReginArray=new JSONArray();

        Map<Integer,DictCityDO> allCity = cityDao.getFullCity().stream().collect(Collectors.toMap(c->c.getCode(),c->c));

        for(DictCityDO c:cityDao.getFullCity()){
            JSONObject moseekerRegin=new JSONObject();

            List<String> chain=cityDao.getMoseekerLevels(c,allCity).stream().map(d->d.getName()).collect(Collectors.toList());

            moseekerRegin.put("code", Arrays.asList(c.getCode()));
            moseekerRegin.put("text",chain);
            moseekerReginArray.add(moseekerRegin);
        }

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
        refreshList.forEach(r->r.handler(json));
    }

    @Override
    public ChannelType getChannel() {
        return ChannelType.VERYEAST;
    }

}
