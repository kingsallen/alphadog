package com.moseeker.position.service.position.job1001;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.position.service.position.job1001.refresh.handler.YLParamRefresher;
import org.springframework.stereotype.Component;

@Component
public class Jljob88ParamRefresher extends YLParamRefresher{
    @Override
    public void addUserParam(JSONObject jsonSend) {
        jsonSend.put("user_name",getConfig("jljob88.username"));
        jsonSend.put("password",getConfig("jljob88.password"));
        jsonSend.put("safe_code",getConfig("jljob88.safecode"));
        jsonSend.put("subsite",getSubSite());
    }


    @Override
    public String getSubSite() {
        return getConfig("jljob88.subsite");
    }
}
