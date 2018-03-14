package com.moseeker.position.service.position.base.refresh.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.RefreshConstant;
import com.moseeker.common.iface.IChannelType;
import com.moseeker.common.util.StringUtils;
import com.moseeker.position.utils.PositionEmailNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 把部分参数存放到redis的处理策略
 */
public abstract class AbstractRedisResultHandler extends AbstractJsonResultHandler implements IChannelType,ResultProvider{
    Logger logger= LoggerFactory.getLogger(AbstractRedisResultHandler.class);

    @Resource(name = "cacheClient")
    protected RedisClient redisClient;
    @Autowired
    protected PositionEmailNotification emailNotification;

    protected abstract String[] param();

    /**
     * 生成redis的key
     * @return
     */
    protected String keyIdentifier(){
        return KeyIdentifier.THIRD_PARTY_ENVIRON_PARAM.toString();
    };

    @Override
    protected void handle(JSONObject obj) {


        String[] params=param();
        if(params == null || params.length==0){
            logger.info("no refresh result to redis");
            return;
        }

        String json=handleCacheValue(obj);

        logger.info("save refresh result to {} redis : {}",keyIdentifier(),json);
        redisClient.set(RefreshConstant.APP_ID,keyIdentifier(),String.valueOf(getChannelType().getValue()),json);
    }

    /**
     * 将chaos-environ传过来的需要缓存的数据 转为保存在redis中的数据
     * 默认就是chaos传什么，我们就保存什么
     * @param obj
     * @return
     */
    protected String handleCacheValue(JSONObject obj){
        JSONObject result=new JSONObject();
        for(String p:param()){
            result.put(p,obj.get(p));
        }

//        changeCheck(result);

        return result.toJSONString();
    }

    /**
     * 返回Redis中保存的environ参数
     * @return
     */
    @Override
    public String getRedisResult(){
        return redisClient.get(RefreshConstant.APP_ID,keyIdentifier(),String.valueOf(getChannelType().getValue()));
    }

    /**
     * 字段改变校验
     * @param newJson
     */
    protected void changeCheck(JSONObject newJson) {
        if(newJson==null){
            return;
        }
        String oldJsonStr=redisClient.get(RefreshConstant.APP_ID,keyIdentifier(),"");

        if(StringUtils.isNullOrEmpty(oldJsonStr)){
            emailNotification.sendUnMatchRedisJsonMail(newJson,new HashMap<>(),getChannelType());
            return;
        }

        JSONObject oldJson=JSON.parseObject(oldJsonStr);

        MapDifference<String,Object> difference=Maps.difference(oldJson,newJson);

        Map<String,Object> onlyInOld= difference.entriesOnlyOnLeft();
        Map<String,Object> onlyInNew= difference.entriesOnlyOnRight();

        if( (onlyInOld!=null && !onlyInOld.isEmpty())
                || (onlyInNew!=null && !onlyInNew.isEmpty())){
            emailNotification.sendUnMatchRedisJsonMail(onlyInNew,onlyInOld,getChannelType());
        }
    }
}
