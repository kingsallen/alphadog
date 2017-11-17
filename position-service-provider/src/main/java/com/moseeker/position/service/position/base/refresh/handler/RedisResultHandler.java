package com.moseeker.position.service.position.base.refresh.handler;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.redis.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 把部分参数存放到redis的处理策略
 */
public abstract class RedisResultHandler extends AbstractJsonResultHandler {
    Logger logger= LoggerFactory.getLogger(RedisResultHandler.class);

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    protected abstract String[] param();
    protected abstract int appId();
    protected abstract String keyIdentifier();

    @Override
    protected void handle(JSONObject obj) {
        JSONObject result=new JSONObject();

        String[] params=param();
        if(params == null || params.length==0){
            logger.info("no refresh result to redis");
            return;
        }

        for(String p:param()){
            result.put(p,obj.get(p));
        }

        String json=result.toJSONString();
        logger.info("save refresh result to {} redis : {}",keyIdentifier(),json);
        redisClient.set(appId(),keyIdentifier(),"",json);
    }
}
