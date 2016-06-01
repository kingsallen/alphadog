package com.moseeker.common.util;

public final class ConstantErrorCodeMessage {

    // 系统共通ERRCODE说明定义 9字头
    public static final String PROGRAM_EXHAUSTED = "{'status':-1,'message':'系统繁忙，请稍候再试!'}";
    public static final String PROGRAM_EXCEPTION = "{'status':99999 ,'message':'发生异常，请稍候再试!'}";

    public static final String PROGRAM_DATA_EMPTY = "{'status':90010,'message':'请求数据为空！'}";
    public static final String PROGRAM_VALIDATE_REQUIRED = "{'status':90014,'message':'参数{0}是必填项！'}";
    public static final String PROGRAM_POST_FAILED = "{'status':90011,'message':'添加失败！'}";
    public static final String PROGRAM_PUT_FAILED = "{'status':90012,'message':'保存失败!'}";
    public static final String PROGRAM_DEL_FAILED = "{'status':90013,'message':'删除失败!'}";

    // 用户服务ERRCODE说明定义 1字头
    public static final String LOGIN_ACCOUNT_ILLEAGUE = "{'status':10010,'message':'用户名密码不匹配!'}";
    public static final String INVALID_SMS_CODE = "{'status':10011,'message':'无效验证码！'}";
    public static final String LOGIN_PASSWORD_UNLEGAL = "{'status':10012,'message':'修改密码失败：原密码错误!'}";
    public static final String LOGIN_UPDATE_PASSWORD_FAILED = "{'status':10013,'message':'修改密码失败！'}";
    public static final String LOGIN_MOBILE_NOTEXIST = "{'status':10014,'message':'手机号不存在！'}";
    public static final String USERACCOUNT_BIND_NONEED = "{'status':10015,'message':'手机号已经注册'}";;
    public static final String USERACCOUNT_EXIST = "{'status':10016,'message':'帐号已存在!'}";
    public static final String USERACCOUNT_NOTEXIST = "{'status':10017,'message':'帐号不存在!'}";
    public static final String USER_FAV_POSITION_FAILED = "{'status':10018,'message':'获取我感兴趣失败!'}";

    public static final String HR_ACCOUNT_SIGNUP_VALIDATE_SOURCE = "{'status':12001,'message':'source >=1 && <=5!'}";

    // 职位服务ERRCODE说明定义 2字头

    // PROFILE服务ERRCODE说明定义 3字头
    public static final String PROFILE_REPEAT_DATA = "{'status':30010,'message':'重复数据!'}";
    public static final String PROFILE_DICT_CITY_NOTEXIST = "{'status':31011,'message':'城市字典不存在!'}";
    public static final String PROFILE_DICT_POSITION_NOTEXIST = "{'status':31012,'message':'职位职能字典不存在!'}";
    public static final String PROFILE_DICT_INDUSTRY_NOTEXIST = "{'status':31013,'message':'行业字典不存在!'}";
    public static final String PROFILE_USER_NOTEXIST = "{'status':31014,'message':'用户信息不正确!'}";
    public static final String PROFILE_ALLREADY_EXIST= "{'status':31015,'message':'个人profile已存在!'}";
    public static final String PROFILE_ILLEGAL= "{'status':31015,'message':'profile数据不正确!'}";
    public static final String PROFILE_POSITION_NOTEXIST = "{'status':31014,'message':'职位信息不正确!'}";

    // 申请服务ERRCODE说明定义 4字头
    public static final String APPLICATION_VALIDATE_COUNT_CHECK = "{'status':41001,'message':'超出申请次数限制!'}";
}
