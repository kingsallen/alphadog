package com.moseeker.position.service.position.veryeast.refresh.handler;

import com.moseeker.common.constants.RefreshConstant;
import com.moseeker.position.service.position.base.refresh.handler.AbstractRedisResultHandler;
import org.springframework.stereotype.Component;

@Component
public class VERedisResultHandler extends AbstractRedisResultHandler implements VEResultHandlerAdapter {
    @Override
    protected String[] param() {
        return new String[]{"indate","salary","accommodation","degree","experience","computerLevel","languageType","languageLevel","workMode"};
    }


    @Override
    protected String keyIdentifier() {
        return RefreshConstant.VERY_EAST_REDIS_PARAM_KEY;
    }
}
