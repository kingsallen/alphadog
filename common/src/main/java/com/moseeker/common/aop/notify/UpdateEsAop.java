package com.moseeker.common.aop.notify;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.annotation.notify.UpdateEs;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.cache.CacheClient;


@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class UpdateEsAop {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * es数据模板，tableName: 表名， user_id: 用户id
	 */
	String ms = "{'tableName':'%s'}";
	
	RedisClient client = CacheClient.getInstance();
	
	
	/**
	 * 线程池
	 */
	private static ExecutorService threadPool = new ThreadPoolExecutor(5, 15, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
	
	
	
	/**
	 * 将信息lpush放在redis中，方便es根据tableName及时更新user的索引信息
	 * @param jp
	 * @param returnValue
	 * @param ups
	 */
	@AfterReturning(value="@annotation(ups)", returning="returnValue")
	public void afterReturn(JoinPoint jp, Object returnValue, UpdateEs ups) {
		if(returnValue != null) {
			try {
				JSONObject jsb = JSONObject.parseObject(JSON.toJSONString(returnValue));
				if (jsb.getIntValue("status") == 0) {
					threadPool.execute(() -> {
						JSONObject result = JSON.parseObject(String.format(ms, ups.tableName()));
						result.put(ups.argsName(), jp.getArgs()[ups.argsIndex()]);
						client.lpush(Constant.APPID_ALPHADOG,
								"ES_REALTIME_UPDATE_INDEX_USER_IDS", result.toJSONString());
						log.info("lpush ES_REALTIME_UPDATE_INDEX_USER_IDS:{} success", result.toJSONString());
					});
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}
}
