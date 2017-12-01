package com.moseeker.position.service.position.job1001.refresh.handler;

import com.moseeker.position.constants.RefreshConstant;
import com.moseeker.position.service.position.base.refresh.handler.AbstractRedisResultHandler;
import com.moseeker.position.service.position.veryeast.refresh.handler.VEResultHandlerAdapter;
import org.springframework.stereotype.Component;

@Component
public class YLRedisResultHandler extends AbstractRedisResultHandler implements VEResultHandlerAdapter {
    @Override
    protected String[] param() {
        return new String[]{"subsite"};
    }

    @Override
    protected int appId() {
        return RefreshConstant.APP_ID;
    }

    @Override
    protected String keyIdentifier() {
        return RefreshConstant.JOB1001_REDIS_PARAM_KEY;
    }
}
