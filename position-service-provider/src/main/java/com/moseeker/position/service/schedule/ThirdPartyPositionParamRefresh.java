package com.moseeker.position.service.schedule;


import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.RefreshConstant;
import com.moseeker.position.service.position.base.refresh.AbstractRabbitMQParamRefresher;
import com.moseeker.position.service.position.base.refresh.ParamRefresher;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ThirdPartyPositionParamRefresh {
    Logger logger= LoggerFactory.getLogger(ThirdPartyPositionParamRefresh.class);

    @Autowired
    private List<AbstractRabbitMQParamRefresher> refreshList=new ArrayList<>();

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    //服务启动先刷新一次
    @PostConstruct
    public void init() throws BIZException {
        logger.info("Refresh third party position param when server start");
        refresh();
    }

    @Scheduled(cron = "0 0 1 * * SAT")
    public void refresh() throws BIZException {
        long check= redisClient.incrIfNotExist(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.REFRESH_THIRD_PARTY_PARAM.toString(), "");
        if (check>1) {
            //绑定中
            throw new BIZException(-1, "已经开始刷新");
        }
        redisClient.expire(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.REFRESH_THIRD_PARTY_PARAM.toString(), "", RefreshConstant.REFRESH_THIRD_PARTY_PARAM_TIMEOUT);

        refreshList.forEach(r->{
            try {
                r.refresh();
            }catch (Exception e){
                logger.error("refresh error");
            }
        });
        redisClient.del(AppId.APPID_ALPHADOG.getValue(),KeyIdentifier.REFRESH_THIRD_PARTY_PARAM.toString(),"");
    }

    @RabbitListener(queues = {RefreshConstant.PARAM_GET_QUEUE}, containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void handle(Message message) throws UnsupportedEncodingException {
        String json=new String(message.getBody(), "UTF-8");
        logger.info("receive json"+json );

        JSONObject obj=JSONObject.parseObject(json);
        int channel=obj.getJSONObject("data").getIntValue("channel");

        ChannelType channelType=ChannelType.instaceFromInteger(channel);

        if(channelType==null){
            logger.error("wrong channel type when handle refresh result");
        }else{
            for(AbstractRabbitMQParamRefresher refresher:refreshList){
                if(refresher.getChannelType()==channelType){
                    refresher.receiveAndHandle(json);
                    return;
                }
            }
            logger.error("no refresher to handle result");
        }
    }

}
