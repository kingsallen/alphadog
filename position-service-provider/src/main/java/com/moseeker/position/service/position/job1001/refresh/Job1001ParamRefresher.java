package com.moseeker.position.service.position.job1001.refresh;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.refresh.AbstractRabbitMQParamRefresher;
import com.moseeker.position.service.position.job1001.refresh.handler.YLResultHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Job1001ParamRefresher extends AbstractRabbitMQParamRefresher{
    Logger logger= LoggerFactory.getLogger(Job1001ParamRefresher.class);

    @Autowired
    List<YLResultHandlerAdapter> refreshList;

    @Override
    public void addSendParam(JSONObject jsonSend) {

    }

    @Override
    public void receiveAndHandle(String json) {
        logger.info("receive json:{}" ,json);
        //调用所有处理策略
        refreshList.forEach(r->r.handle(json));
    }

    @Override
    public void addUserParam(JSONObject jsonSend) {
        jsonSend.put("user_name",getConfig("job1001.username"));
        jsonSend.put("password",getConfig("job1001.password"));
        jsonSend.put("safe_code",getConfig("job1001.safecode"));
    }

    @Override
    public ChannelType getChannel() {
        return ChannelType.JOB1001;
    }
}
