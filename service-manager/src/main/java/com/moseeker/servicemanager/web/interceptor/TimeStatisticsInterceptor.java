package com.moseeker.servicemanager.web.interceptor;

import com.alibaba.fastjson.JSON;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            MDC.put("url", request.getRequestURI());
            MDC.put("method", request.getMethod());
            MDC.put("ipAddr", request.getRemoteAddr());

            long startTime = (long) request.getAttribute("startTime");
            long consumerTime = System.currentTimeMillis() - startTime;
            MDC.put("runTime", consumerTime + "ms");
            logger.info("接口运行时间：{}, param: {}", consumerTime + "ms", JSON.toJSONString(MDC.getCopyOfContextMap()));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            logger.error("TimeStatisticsInterceptor.afterCompletion error:{}", e.getMessage());
        }finally {
            MDC.clear();
        }
    }
}
