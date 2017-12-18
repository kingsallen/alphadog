package com.moseeker.position.service.position.job1001;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.position.service.position.job1001.refresh.handler.AbstractYLParamRefresher;
import org.springframework.stereotype.Component;

@Component
public class SHParamRefresher extends AbstractYLParamRefresher {
    @Override
    public void addUserParam(JSONObject jsonSend) {
        jsonSend.put("user_name",getConfig("sh.username"));
        jsonSend.put("password",getConfig("sh.password"));
        jsonSend.put("safe_code",getConfig("sh.safecode"));
        jsonSend.put("subsite",getSubSite());
    }

    @Override
    public String getSubSite() {
        return getConfig("sh.subsite");
    }
}
