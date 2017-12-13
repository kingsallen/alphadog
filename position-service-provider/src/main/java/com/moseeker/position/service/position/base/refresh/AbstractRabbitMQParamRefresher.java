package com.moseeker.position.service.position.base.refresh;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.iface.IChannelType;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.constants.RefreshConstant;
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

public abstract class AbstractRabbitMQParamRefresher implements ParamRefresher,IChannelType {
    Logger logger= LoggerFactory.getLogger(AbstractRabbitMQParamRefresher.class);



    @Autowired
    DictCityDao cityDao;

    @Autowired
    private AmqpTemplate amqpTemplate;

    //发送消息去RabbitMQ
    public abstract void addSendParam(JSONObject jsonSend);
    //接受并处理刷新结果，需要加上{@RabbitListener}和{@RabbitHandler}两个注解
    public abstract void receiveAndHandle(String json);
    public abstract void addUserParam(JSONObject jsonSend);

    private int account_id=1;

    @Override
    public void refresh() {
        JSONObject jsonSend=new JSONObject();

        jsonSend.put("account_id",account_id++);
        jsonSend.put("channel",getChannelType().getValue());
        jsonSend.put("moseeker_region",moseekerRegin());

        addUserParam(jsonSend);
        addSendParam(jsonSend);

        String json=jsonSend.toJSONString();
        logger.info("refresh param send RabbitMQ channel : {}",getChannelType().getValue());

        amqpTemplate.convertAndSend(exchange(),routingKey(), json);
        logger.info("send RabbitMQ success");
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

    protected String getConfig(String key) {
        try {
            ConfigPropertiesUtil configUtils = ConfigPropertiesUtil.getInstance();
            configUtils.loadResource("setting.properties");
            return configUtils.get(key, String.class);
        } catch (Exception e) {
            logger.info("get setting error");
            e.printStackTrace();
        }
        return null;
    }

    public String exchange() {
        return RefreshConstant.EXCHANGE;
    }
    public String routingKey() {
        return RefreshConstant.PARAM_SEND_ROUTING_KEY;
    }
}
