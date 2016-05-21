package com.moseeker.common.util;

public final class ConstantErrorCodeMessage {

	public static final String PROGRAM_EXHAUSTED = "{'status':-1,'系统繁忙，此时稍候再试!'}";	
	public static final String PROGRAM_EXCEPTION = "{'status':99999 ,'发生异常，此时稍候再试!'}";

	public static final String PROGRAM_DATA_EMPTY = "{'status':20010,'message':'请求数据为空！'}";
	public static final String PROGRAM_POST_FAILED = "{'status':20011,'message':'添加失败！'}";
	public static final String PROGRAM_PUT_FAILED = "{'status':20012,'message':'保存失败!'}";
	public static final String PROGRAM_DEL_FAILED = "{'status':20013,'message':'删除失败!'}";

	
	public static final String LOGIN_ACCOUNT_ILLEAGUE = "{'status':10010,'message':'用户名密码不匹配!'}";
	public static final String INVALID_SMS_CODE = "{'status':10011,'message':'无效验证码！'}";
	public static final String LOGIN_PASSWORD_UNLEGAL = "{'status':10012,'message':'修改密码失败：原密码错误!'}";
	public static final String LOGIN_UPDATE_PASSWORD_FAILED = "{'status':10013,'message':'修改密码失败！'}";
	public static final String LOGIN_MOBILE_NOTEXIST = "{'status':10014,'message':'手机号不存在！'}";
	public static final String USERACCOUNT_BIND_NONEED = "{'status':10015,'message':'手机号已经注册'}";
	public static final String USERACCOUNT_EXIST = "{'status':10016,'message':'帐号已存在!'}";
	public static final String USERACCOUNT_NOTEXIST = "{'status':10017,'message':'帐号不存在!'}";
	
	

}
