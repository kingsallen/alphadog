package com.moseeker.position.service.position.job51.refresh;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.refresh.AbstractRabbitMQParamRefresher;
import com.moseeker.position.service.position.job51.refresh.handler.Job51ResultHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Job51ParamRefresher extends AbstractRabbitMQParamRefresher {
    Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    List<Job51ResultHandlerAdapter>  refreshList;

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
        jsonSend.put("member_name",getConfig("job51.membername"));
        jsonSend.put("user_name",getConfig("job51.username"));
        jsonSend.put("password",getConfig("job51.password"));
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB51;
    }
}
