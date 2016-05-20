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
	
	public static final int ENABLE = 1;
	public static final int DISABLE = 0;
	
	public static final int REDIS_CONNECT_ERROR_APPID = 0;
	public static final String REDIS_CONNECT_ERROR_EVENTKEY = "REDIS_CONNECT_ERROR";
	
	public static final String TIPS_SUCCESS = "success";

	public static final String TIPS_ERROR = "error";

	public static final String NONE_JSON = "{}";
	
	public static final String LOGIN_ACCOUNT_UNLEGAL = "{'status':10010,'message':'username and password do not match!'}";
	public static final String LOGIN_VALIDATION_CODE_UNLEGAL = "{'status':10011,'message':'mobile validation code failed!'}";
	public static final String LOGIN_PASSWORD_UNLEGAL = "{'status':10012,'message':'failed to change password: old password doesn't match!'}";
	public static final String LOGIN_UPDATE_PASSWORD_FAILED = "{'status':10013,'message':'update password failed!'}";
	public static final String LOGIN_MOBILE_NOTEXIST = "{'status':10014,'message':'mobile doesn't exist!'}";
	public static final String USERACCOUNT_BIND_NONEED = "{'status':10015,'message':'binding is not necessary'}";;
	
	public static final String PROGRAM_DATA_EMPTY = "{'status':20010,'message':'data empty!'}";
	public static final String PROGRAM_POST_FAILED = "{'status':20011,'message':'post data failed!'}";
	public static final String PROGRAM_PUT_FAILED = "{'status':20012,'message':'put data failed!'}";
	public static final String PROGRAM_DEL_FAILED = "{'status':20013,'message':'del data failed!'}";
	public static final String PROGRAM_EXHAUSTED = "{'status':-1,'系统繁忙，此时稍候再试!'}";
}
