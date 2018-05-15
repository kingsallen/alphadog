package com.moseeker.position.service.position.carnoc.refresh;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.refresh.AbstractRabbitMQParamRefresher;
import com.moseeker.position.service.position.carnoc.refresh.handler.CarnocResultHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarnocParamRefresher extends AbstractRabbitMQParamRefresher {

    @Autowired
    private List<CarnocResultHandlerAdapter> refreshList;

    @Override
    public void addSendParam(JSONObject jsonSend) {
        jsonSend.put("moseeker_region",moseekerRegin());
    }

    @Override
    public void receiveAndHandle(String json) {
        //调用所有处理策略
        refreshList.forEach(r->r.handle(json));
    }

    @Override
    public void addUserParam(JSONObject jsonSend) {
        jsonSend.put("user_name",getConfig("carnoc.username"));
        jsonSend.put("password",getConfig("carnoc.password"));
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.CARNOC;
    }
}
