package com.moseeker.servicemanager.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.ConfigPropertiesUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TimeStatisticsInterceptor
 * 接口访问时间统计拦截器
 *
 * @Author: lee
 * @Date: 2019/10/29
 */
@Component
public class TimeStatisticsInterceptor implements HandlerInterceptor {

    org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String redisKey = "log_0_interface";
    JedisPool jedisPool;
    // 线程池
    private static ExecutorService threadPool = new ThreadPoolExecutor(5, 15, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("StatisticsStartTime", startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    @CounterIface
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            //初始化redis集群
            if (jedisPool == null) {
                ConfigPropertiesUtil propertiesUtils = ConfigPropertiesUtil.getInstance();
                String host = propertiesUtils.get("redis.elk.host", String.class);
                Integer port = propertiesUtils.get("redis.elk.port", Integer.class);
                jedisPool = new JedisPool(host, port);
            }

            Map<String, Object> logMap = new HashMap<>();
            logMap.put("url", request.getRequestURI());
            logMap.put("method", request.getMethod());
            logMap.put("ipAddr", InetAddress.getLocalHost().getHostAddress());
            logMap.put("http_code", response.getStatus());

            long startTime = (long) request.getAttribute("StatisticsStartTime");
            long consumerTime = System.currentTimeMillis() - startTime;
            logMap.put("runTime", consumerTime);
            logMap.put("application", "service-manager");
            logger.info("TimeStatisticsInterceptor.afterCompletion : {}", JSON.toJSONString(logMap));
            logMap.put("message", "接口运行时间:" + consumerTime);
            this.save(JSONObject.toJSONString(logMap));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            logger.error("TimeStatisticsInterceptor.afterCompletion error:{}", e.getMessage());
        }
    }

    public void save(String jsonStr) {
        threadPool.execute(() -> {
            try (Jedis client = jedisPool.getResource()) {
                client.lpush(redisKey, jsonStr);
            } catch (Exception e) {
                logger.error("redis Connection refused");
            }
        });
    }
}
