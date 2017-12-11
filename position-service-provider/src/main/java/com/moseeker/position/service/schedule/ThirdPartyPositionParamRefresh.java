package com.moseeker.position.service.schedule;


import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.RefreshConstant;
import com.moseeker.position.service.position.base.refresh.AbstractRabbitMQParamRefresher;
import com.moseeker.position.service.position.base.refresh.ParamRefresher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class ThirdPartyPositionParamRefresh {
    Logger logger= LoggerFactory.getLogger(ThirdPartyPositionParamRefresh.class);

    @Autowired
    private List<AbstractRabbitMQParamRefresher> refreshList=new ArrayList<>();

    //服务启动先刷新一次
    @PostConstruct
    public void init(){
        logger.info("Refresh third party position param when server start");
        refresh();
    }

    @Scheduled(cron = "0 20 19 ? * MON")
    public void refresh(){
        refreshList.forEach(r->{
            try {
                r.refresh();
            }catch (Exception e){
                logger.error("refresh error");
            }
        });
    }

    @RabbitListener(queues = {RefreshConstant.PARAM_GET_QUEUE}, containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void handle(String json){
        logger.info("receive json:{}" ,json);

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
