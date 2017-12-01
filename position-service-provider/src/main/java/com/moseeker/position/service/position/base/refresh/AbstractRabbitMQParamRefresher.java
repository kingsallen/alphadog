package com.moseeker.position.service.position.base.refresh;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.constants.RefreshConstant;
import com.moseeker.position.service.position.base.refresh.handler.ResultHandler;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractRabbitMQParamRefresher implements ParamRefresher {
    Logger logger= LoggerFactory.getLogger(AbstractRabbitMQParamRefresher.class);

    @Autowired
    DictCityDao cityDao;

    @Autowired
    private AmqpTemplate amqpTemplate;

    //发送消息去RabbitMQ
    public abstract void addSendParam(JSONObject jsonSend);
    //接受并处理刷新结果，需要加上{@RabbitListener}和{@RabbitHandler}两个注解
    public abstract void receiveAndHandle(String str);
    public abstract void addUserParam(JSONObject jsonSend);

    public abstract ChannelType getChannel();

    @Override
    public void refresh() {
        JSONObject jsonSend=new JSONObject();

        jsonSend.put("account_id",1);
        jsonSend.put("channel",getChannel().getValue());
        jsonSend.put("moseeker_region",moseekerRegin());

        addUserParam(jsonSend);
        addSendParam(jsonSend);

        String json=jsonSend.toJSONString();
        logger.info("refresh param send RabbitMQ channel : {}",getChannel().getValue());

        amqpTemplate.send(exchange(),routingKey(), createMsg(json));
        logger.info("send RabbitMQ success");
    }

    public Message createMsg(String str){
        return MessageBuilder.withBody(str.getBytes()).build();
    }

    /**
     * 查询出MoSeeker的城市
     * @return 一个储存JSONObject的JSONArray，每个JSONObject都有两个字段，code和text，对应完整的城市code链和name链
     * 例如 {"code":[130000,130100,130111],"text":["河北省","石家庄","栾城区"]}
     */
    public JSONArray moseekerRegin(){
        JSONArray moseekerReginArray=new JSONArray();

        Map<Integer,DictCityDO> allCity = cityDao.getFullCity().stream().collect(Collectors.toMap(c->c.getCode(), c->c));

        for(DictCityDO c:cityDao.getFullCity()){
            JSONObject moseekerRegin=new JSONObject();

            List<String> chain=cityDao.getMoseekerLevels(c,allCity).stream().map(d->d.getName()).collect(Collectors.toList());

            moseekerRegin.put("code", Arrays.asList(c.getCode()));
            moseekerRegin.put("text",chain);
            moseekerReginArray.add(moseekerRegin);
        }
        logger.info("refresh moseekerRegin {}",moseekerReginArray.size());
        return moseekerReginArray;
    }

    public String exchange() {
        return RefreshConstant.EXCHANGE;
    }
    public String routingKey() {
        return RefreshConstant.PARAM_SEND_ROUTING_KEY;
    }
}
