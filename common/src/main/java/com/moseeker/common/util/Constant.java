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
}
