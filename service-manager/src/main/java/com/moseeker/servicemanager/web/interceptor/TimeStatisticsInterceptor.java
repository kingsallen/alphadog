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
            logMap.put("ipAddr", getIpAddress(request));

            long startTime = (long) request.getAttribute("StatisticsStartTime");
            long consumerTime = System.currentTimeMillis() - startTime;
            logMap.put("runTime", consumerTime);
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

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     *
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
