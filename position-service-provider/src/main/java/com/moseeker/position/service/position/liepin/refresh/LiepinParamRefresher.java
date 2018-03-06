package com.moseeker.position.service.position.liepin.refresh;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.refresh.AbstractRabbitMQParamRefresher;
import com.moseeker.position.service.position.liepin.refresh.handler.LiepinResultHandlerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LiepinParamRefresher extends AbstractRabbitMQParamRefresher {

    @Autowired
    private List<LiepinResultHandlerAdapter> refreshList;

    @Override
    public void addSendParam(JSONObject jsonSend) {

    }

    @Override
    public void receiveAndHandle(String json) {
        //调用所有处理策略
        refreshList.forEach(r->r.handle(json));
    }

    @Override
    public void addUserParam(JSONObject jsonSend) {
        jsonSend.put("user_name",getConfig("liepin.username"));
        jsonSend.put("password",getConfig("liepin.password"));
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.LIEPIN;
    }
}
