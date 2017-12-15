package com.moseeker.position.service.position.job1001;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.position.service.position.job1001.refresh.handler.YLParamRefresher;
import org.springframework.stereotype.Component;

@Component
public class Job021ParamRefresher extends YLParamRefresher {
    @Override
    public void addUserParam(JSONObject jsonSend) {
        jsonSend.put("user_name",getConfig("job021.username"));
        jsonSend.put("password",getConfig("job021.password"));
        jsonSend.put("safe_code",getConfig("job021.safecode"));
        jsonSend.put("subsite",getSubSite());
    }

    @Override
    public String getSubSite() {
        return getConfig("job021.subsite");
    }
}
