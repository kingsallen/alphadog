package com.moseeker.position.service.position.jobsdb.refresh.handler;

import com.moseeker.common.constants.RefreshConstant;
import com.moseeker.position.service.position.base.refresh.handler.AbstractRedisResultHandler;
import org.springframework.stereotype.Component;

@Component
public class JobsDBRedisResultHandler extends AbstractRedisResultHandler implements JobsDBResultHandlerAdapter {
    @Override
    protected String[] param() {
        return new String[]{"salaryTop","salaryBottom","addressName"};
    }

    @Override
    protected String keyIdentifier() {
        return RefreshConstant.VERY_EAST_REDIS_PARAM_KEY;
    }
}
