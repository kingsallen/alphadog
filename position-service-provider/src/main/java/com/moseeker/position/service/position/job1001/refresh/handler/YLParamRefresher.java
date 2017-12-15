package com.moseeker.position.service.position.job1001.refresh.handler;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.RefreshConstant;
import com.moseeker.position.service.position.base.refresh.AbstractRabbitMQParamRefresher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class YLParamRefresher extends AbstractRabbitMQParamRefresher{
    Logger logger= LoggerFactory.getLogger(YLParamRefresher.class);

    @Autowired
    List<YLResultHandlerAdapter> refreshList;

    public abstract String getSubSite();

    @Override
    public void addSendParam(JSONObject jsonSend) {

    }

    @Override
    public void receiveAndHandle(String json) {
        //调用所有处理策略
        refreshList.forEach(r->r.handle(json));
    }


    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB1001;
    }
}
