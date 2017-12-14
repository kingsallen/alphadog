package com.moseeker.position.service.schedule;


import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.RefreshConstant;
import com.moseeker.position.service.position.base.refresh.AbstractRabbitMQParamRefresher;
import com.moseeker.position.service.position.base.refresh.ParamRefresher;
import com.moseeker.position.service.position.base.refresh.RefresherFactory;
import com.moseeker.position.utils.PositionEmailNotification;
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

    @Autowired
    RefresherFactory refresherFactory;

    @Autowired
    PositionEmailNotification emailNotification;

    //服务启动先刷新一次
    public void init() throws BIZException {
        logger.info("Refresh third party position param when server start");
        refresh();
    }

    @Scheduled(cron = "0 0 1 * * SAT")
    public void refresh() throws BIZException {
        long check= redisClient.incrIfNotExist(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.REFRESH_THIRD_PARTY_PARAM.toString(), "");
        if (check>1) {
            //绑定中
            logger.info("已经开始刷新");
            return;
        }
        redisClient.expire(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.REFRESH_THIRD_PARTY_PARAM.toString(), "", RefreshConstant.REFRESH_THIRD_PARTY_PARAM_TIMEOUT);

        refreshList.forEach(r->{
            try {
                r.refresh();
            }catch (Exception e){
                logger.error("refresh error {}",e.getMessage());
            }
        });
        redisClient.del(AppId.APPID_ALPHADOG.getValue(),KeyIdentifier.REFRESH_THIRD_PARTY_PARAM.toString(),"");
    }

    @RabbitListener(queues = {RefreshConstant.PARAM_GET_QUEUE}, containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void handle(Message message) {
        String json="";
        AbstractRabbitMQParamRefresher refresher=null;
        try {
            json=new String(message.getBody(), "UTF-8");
            logger.info("receive json" );

            JSONObject obj=JSONObject.parseObject(json);
            Integer status=obj.getInteger("status");
            if(status!=0){
                logger.error("refresh error message : {} ,json :{}",obj.getString("message"),json);
                return;
            }
            int channel=obj.getJSONObject("data").getIntValue("channel");
            ChannelType channelType=ChannelType.instaceFromInteger(channel);
            if(channelType==null){
                throw new RuntimeException("no matched ChannelType when refresh "+channel);
            }


            if(refresherFactory.hasRabbitMQParamRefresher(channelType)){
                refresher=refresherFactory.getRabbitMQParamRefresher(channelType);
                refresher.receiveAndHandle(json);
            }else{
                logger.error("no refresher to handle result {}",channel);
            }

        }catch (Exception e){
            logger.error("handle refresh result Error : {}, message :{}",e.getMessage(),json);
            emailNotification.sendRefreshFailureMail(json,refresher,e);
        }
    }

}
