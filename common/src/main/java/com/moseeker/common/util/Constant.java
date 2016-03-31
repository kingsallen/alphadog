package com.moseeker.common.util;

/**
 * 
 * Common项目常用配置信息
 *  
 * @date Mar 26, 2016
 * @company moseeker
 * @author wjf
 * @email wjf2255@gmail.com
 */
public class Constant {
	
	/* 缓存配置表 */
	public static final String CACHE_URL = "jdbc:mysql://192.168.31.66:3306/";
	public static final String CACHE_USERNAME = "www";
	public static final String CACHE_PASSWORD = "moseeker.com";
	
	/* 缓存服务器配置 */
	public static final String cashe_host = "";
	public static final String cashe_port = "";
	
	/*zookeeper配置*/
	public static final int ZOOKEEPER_PORT = 2181;
	public static final String ZOOKEEPER_ADDRESS = "127.0.0.1";
	
	/* 敏感词配置*/
	public static final String SENSITIVE_WORDS = "sensitiveWords";
	public static final String DASVALIDATE_SENSITIVEWORDS_ILLEGAL = "敏感词校验失败";
	
	/* redis log */
	// log_redis_host log_redis_port cache_redis_host cache_redis_port session_redis_host session_redis_port
	// logConfigKeyName logConfigTimeOut logConfigType cacheConfigKeyName cacheConfigTimeOut cacheConfigType sessionConfigKeyName sessionConfigTimeOut sessionConfigType
}
