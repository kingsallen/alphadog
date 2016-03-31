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
	private CacheClient cacheClient;
	/**
	 * 日志帮助类
	 */
	private LogClient logClient;
	
	/**
	 * Session帮助类
	 */
	private SessionClient sessionClient;
	
	/**
	 * 工厂类对象
	 */
	private static volatile RedisClientFactory instance;
	
	/**
	 * Redis工厂创建方法
	 */
	private  RedisClientFactory () {
		cacheClient = CacheClient.getInstance();
		logClient = LogClient.getInstance();
		sessionClient = SessionClient.getInstance();
	}
	
	/**
	 * Redis工厂初始化方法
	 * @return RedisClientFactory
	 */
	public static RedisClientFactory getInstance() {
		if (instance == null) {
			synchronized (CacheClient.class) {
				if (instance == null) {
					instance = new RedisClientFactory();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 获取缓存帮助客户端
	 * @return CacheClient{@see com.moseeker.common.cache.CacheClient}
	 */
	public RedisClient getCache() {
		return cacheClient;
	}
	
	/**
	 * 获取日志帮助客户端
	 * @return
	 */
	public RedisClient getLog() {
		return logClient;
	}
	
	/**
	 * 获取日志帮助客户端
	 * @return
	 */
	public RedisClient getSession() {
		return sessionClient;
	}
}
