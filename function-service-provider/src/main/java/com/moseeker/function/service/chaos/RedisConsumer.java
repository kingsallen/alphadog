package com.moseeker.function.service.chaos;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.util.StringUtils;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangdi on 2017/6/29.
 */
public abstract class RedisConsumer<T> {

    @Resource(name = "cacheClient")
    private RedisClient redisClient;


    public void loopTask(int appid, String key) {
        new Thread(() -> {
            while (true) {
                fetchToData(appid, key);
            }
        }).start();
    }

    protected void fetchToData(int appid, String key) {
        try {
            String redisString = fetchFromRedis(appid, key);
            if (StringUtils.isNotNullOrEmpty(redisString)) {
                onComplete(convertData(redisString));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e);
        }
    }

    /**
     * 监听职位同步完成队列
     *
     * @return
     */
    private String fetchFromRedis(int appid, String key) {
        List<String> result = redisClient.brpop(appid, key);
        if (result != null && result.size() > 0) {
            LoggerFactory.getLogger(this.getClass()).info("同步完成队列 : {}", result.get(1));
            return result.get(1);
        } else {
            return null;
        }
    }

    protected abstract T convertData(String redisString);

    /**
     * 获取到最终要处理的对象
     *
     * @param data
     */
    protected abstract void onComplete(T data);

}
