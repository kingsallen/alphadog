package com.moseeker.position.service.position.veryeast.refresh.handler;

import com.moseeker.position.constants.VeryEastConstant;
import com.moseeker.position.service.position.base.refresh.handler.RedisResultHandler;
import org.springframework.stereotype.Component;

@Component
public class VERedisResultHandler extends RedisResultHandler implements VEResultHandlerAdapter {
    @Override
    protected String[] param() {
        return new String[]{"indate","salary","accommodation","degree","experience","computerLevel","languageType","languageLevel","workMode"};
    }

    @Override
    protected int appId() {
        return VeryEastConstant.APP_ID;
    }

    @Override
    protected String keyIdentifier() {
        return VeryEastConstant.REDIS_PARAM_KEY;
    }
}
