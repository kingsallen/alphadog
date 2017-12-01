package com.moseeker.position.service.position.job1001.refresh;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.refresh.AbstractRabbitMQParamRefresher;
import com.moseeker.position.service.position.job1001.refresh.handler.YLResultHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Job1001ParamRefresher extends AbstractRabbitMQParamRefresher{

    @Autowired
    List<YLResultHandlerAdapter> list;

    @Override
    public void addSendParam(JSONObject jsonSend) {

    }

    @Override
    public void receiveAndHandle(String str) {

    }

    @Override
    public void addUserParam(JSONObject jsonSend) {
        jsonSend.put("user_name","");
        jsonSend.put("password","");
        jsonSend.put("safe_code","");
    }

    @Override
    public ChannelType getChannel() {
        return ChannelType.JOB1001;
    }
}
