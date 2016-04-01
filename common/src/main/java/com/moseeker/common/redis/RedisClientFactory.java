package com.moseeker.common.redis;

import com.moseeker.common.redis.cache.CacheClient;
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
	public static RedisClient getCache() {
		if(cacheClient == null) {
			cacheClient = CacheClient.getInstance();
		}
		return cacheClient;
	}
	
	/**
	 * 获取日志帮助客户端
	 * @return
	 */
	public RedisClient getLog() {
		if(logClient == null) {
			logClient = LogClient.getInstance();
		}
		return logClient;
	}
	
	/**
	 * 获取日志帮助客户端
	 * @return
	 */
	public RedisClient getSession() {
		if(sessionClient == null) {
			sessionClient = SessionClient.getInstance();
		}
		return sessionClient;
	}
}
