package com.moseeker.common.redis;

import com.moseeker.common.redis.cache.CacheClient;
import com.moseeker.common.redis.elk.ElkClient;
import com.moseeker.common.redis.log.LogClient;
import com.moseeker.common.redis.session.SessionClient;

/**
 * 
 * RedisClient创建工厂 
 * <p>Company: MoSeeker</P>  
 * <p>date: Mar 31, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version Beta
 */
public class RedisClientFactory {

	/**
	 * 缓存帮助类
	 */
	private static CacheClient cacheClient;
	/**
	 * 日志帮助类
	 */
	private static LogClient logClient;
	
	/**
	 * Session帮助类
	 */
	private static SessionClient sessionClient;
	
	/**
	 * logredis输出到elk帮助类
	 */
	private static ElkClient elkClient;
	
	/**
	 * Redis工厂创建方法
	 */
	public  RedisClientFactory () {
		cacheClient = CacheClient.getInstance();
		logClient = LogClient.getInstance();
		sessionClient = SessionClient.getInstance();
	}
	
	/**
	 * 获取缓存帮助客户端
	 * @return CacheClient{@see com.moseeker.common.cache.CacheClient}
	 */
	public static RedisClient getCacheClient() {
		if(cacheClient == null) {
			cacheClient = CacheClient.getInstance();
		}
		return cacheClient;
	}
	
	/**
	 * 获取日志帮助客户端
	 * @return
	 */
	public static RedisClient getLogClient() {
		if(logClient == null) {
			logClient = LogClient.getInstance();
		}
		return logClient;
	}
	
	/**
	 * 获取会话帮助客户端
	 * @return
	 */
	public static RedisClient getSessionClient() {
		if(sessionClient == null) {
			sessionClient = SessionClient.getInstance();
		}
		return sessionClient;
	}
	
	/**
	 * 获取log-elk客户端
	 * @return
	 */
	public static RedisClient getElkClient() {
		if (elkClient == null) {
			elkClient = ElkClient.getInstance();
		}
		return elkClient;
	}
}
