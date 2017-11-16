package com.moseeker.position.service.position.veryeast;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.position.constants.VeryEastConstant;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 把部分参数存放到redis的处理策略
 */
@Component
public class RedisParamHandler extends AbstractVeryEastParamHandler{
    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    private static String param[]={"indate","salary","accommodation","degree","experience","computerLevel","languageType","languageLevel","workMode"};
    
    @Override
    public void handler(JSONObject obj) {
        JSONObject result=new JSONObject();

        for(String p:param){
            result.put(p,obj.get(p));
        }

        redisClient.set(VeryEastConstant.APP_ID, VeryEastConstant.REDIS_PARAM_KEY,"",result.toJSONString());
    }
}
