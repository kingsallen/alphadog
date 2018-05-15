package com.moseeker.position.service.position.veryeast.refresh;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.refresh.AbstractRabbitMQParamRefresher;
import com.moseeker.position.service.position.veryeast.refresh.handler.VEResultHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class VeryEastParamRefresher extends AbstractRabbitMQParamRefresher {
    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private List<VEResultHandlerAdapter> refreshList;

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
        jsonSend.put("user_name",getConfig("veryeast.username"));
        jsonSend.put("password",getConfig("veryeast.password"));
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.VERYEAST;
    }

}
