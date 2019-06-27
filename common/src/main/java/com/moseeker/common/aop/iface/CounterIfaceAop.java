package com.moseeker.common.aop.iface;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.annotation.iface.CounterInfo;
import com.moseeker.common.util.ConfigPropertiesUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ltf 接口统计 Aop 2016年10月31日
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CounterIfaceAop implements Ordered {

    private Logger log = LoggerFactory.getLogger(getClass());

    private final String redisKey = "log_0_interface";

    JedisPool jedisPool;

    @PostConstruct
    public void init() {
        ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil.getInstance();
        String host = propertiesUtils.get("redis.elk.host", String.class);
        Integer port = propertiesUtils.get("redis.elk.port", Integer.class);
        jedisPool = new JedisPool(host, port);
    }

    /**
     * 切入点
     */
    private static final String POINCUT = "@within(com.moseeker.common.annotation.iface.CounterIface) || @annotation(com.moseeker.common.annotation.iface.CounterIface)";

    /**
     * 线程池
     */
    private static ExecutorService threadPool = new ThreadPoolExecutor(5, 15, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    ThreadLocal<CounterInfo> counterLocal = new ThreadLocal<CounterInfo>() {
        @Override
        protected CounterInfo initialValue() {
            return new CounterInfo();
        }
    };

    /**
     * enter before
     *
     * @param call
     */
    @Before(value = POINCUT)
    public void doBefore(JoinPoint call) {
        counterLocal.get().setArgs(Arrays.toString(call.getArgs()));
        counterLocal.get().setClassName(call.getTarget().getClass().getName());
        counterLocal.get().setMethod(call.getSignature().getName());
        counterLocal.get().setStartTime(new Date().getTime());
    }

    /**
     * throws exception
     */
    @AfterThrowing(value = POINCUT)
    public void afterThrowing() {
        counterLocal.get().setStatus("fail");
        counterLocal.get().setEndTime(new Date().getTime());
        counterLocal.get().setTime(counterLocal.get().getEndTime() - counterLocal.get().getStartTime());
        save(JSONObject.toJSONString(counterLocal.get()));
    }


    /**
     * return after
     */
    @AfterReturning(value = POINCUT)
    public void afterReturn(JoinPoint call) {
        counterLocal.get().setStatus("success");
        counterLocal.get().setEndTime(new Date().getTime());
        counterLocal.get().setTime(counterLocal.get().getEndTime() - counterLocal.get().getStartTime());
        save(JSONObject.toJSONString(counterLocal.get()));
    }

    public void save(String jsonStr) {
        threadPool.execute(() -> {
            try (Jedis client = jedisPool.getResource()) {
                client.lpush(redisKey, jsonStr);
            } catch (Exception e) {
                log.error("redis Connection refused");
            }
        });
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
