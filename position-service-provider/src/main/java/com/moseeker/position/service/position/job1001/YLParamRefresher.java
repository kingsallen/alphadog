package com.moseeker.position.service.position.job1001;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.position.service.position.job1001.refresh.handler.AbstractYLParamRefresher;
import org.springframework.stereotype.Component;

@Component
public class YLParamRefresher extends AbstractYLParamRefresher {
    @Override
    public String getConfigKey() {
        return "yl";
    }
}
