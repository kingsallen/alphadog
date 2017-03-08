package com.moseeker.common.log;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.util.ConfigPropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by jack on 08/03/2017.
 */
public enum ELKLog {

    ELK_LOG;

    private JedisPool jedisPool;

    private final String redisKey = "log_0_info";

    private Logger logger = LoggerFactory.getLogger(ELKLog.class);

    /**
     * 线程池
     */
    private static ExecutorService threadPool = new ThreadPoolExecutor(5, 15, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    private ELKLog() {
        ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil.getInstance();
        String host = propertiesUtils.get("redis.elk.host", String.class);
        Integer port = propertiesUtils.get("redis.elk.port", Integer.class);
        jedisPool = new JedisPool(host, port);
    }

    public void log(LogVO logVO) {
        String jsonStr = JSON.toJSONString(logVO);
        threadPool.execute(() -> {
            try(Jedis client = jedisPool.getResource()){
                client.lpush(redisKey, jsonStr);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
        logger.info("counterInfo:{}", jsonStr);
    }
}
