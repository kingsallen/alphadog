package com.moseeker.position.service.position.job1001;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.position.service.position.job1001.refresh.handler.YLParamRefresher;
import org.springframework.stereotype.Component;

@Component
public class Tmljob88ParamRefresher extends YLParamRefresher{
    @Override
    public void addUserParam(JSONObject jsonSend) {
        jsonSend.put("user_name",getConfig("tmjob88.username"));
        jsonSend.put("password",getConfig("tmjob88.password"));
        jsonSend.put("safe_code",getConfig("tmjob88.safecode"));
        jsonSend.put("subsite",getSubSite());
    }

    @Override
    public String getSubSite() {
        return getConfig("tmjob88.subsite");
    }
}
