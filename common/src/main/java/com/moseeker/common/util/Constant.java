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
public final class Constant {
	
	private Constant() throws AssertionError {
		throw new AssertionError();
	};
	
	public static final String DASVALIDATE_SENSITIVEWORDS_ILLEGAL = "敏感词校验失败";
	
	public static final int logConfigType = 3;
	public static final int cacheConfigType = 1;
	public static final int sessionConfigType = 2;
	
	public static final int REDIS_CONNECT_ERROR_APPID = 0;
	public static final String REDIS_CONNECT_ERROR_EVENTKEY = "REDIS_CONNECT_ERROR";
	
	public static final String TIPS_SUCCESS = "success";

	public static final String TIPS_ERROR = "error";

	public static final String NONE_JSON = "{}";
}
