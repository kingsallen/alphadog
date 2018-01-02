package com.moseeker.position.service.position.job1001.refresh.handler;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.refresh.AbstractRabbitMQParamRefresher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractYLParamRefresher extends AbstractRabbitMQParamRefresher{
    Logger logger= LoggerFactory.getLogger(AbstractYLParamRefresher.class);

    @Autowired
    List<YLResultHandlerAdapter> refreshList;

    public abstract String getConfigKey();

    @Override
    public void addSendParam(JSONObject jsonSend) {

    }

    @Override
    public void addUserParam(JSONObject jsonSend) {
        String configKey=getConfigKey();
        jsonSend.put("user_name",getConfig(configKey+".username"));
        jsonSend.put("password",getConfig(configKey+".password"));
        jsonSend.put("safe_code",getConfig(configKey+".safecode"));
        jsonSend.put("subsite",getConfig(configKey+".subsite"));
    }

    public String getSubsite(){
        return getConfig(getConfigKey()+".subsite");
    }

    public int getSeed(){
        return Integer.parseInt(getConfig(getConfigKey()+".seed"));
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
