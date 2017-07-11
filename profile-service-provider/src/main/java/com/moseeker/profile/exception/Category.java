package com.moseeker.profile.exception;

/**
 * 异常类型
 * Created by jack on 09/07/2017.
 */
public enum Category {

    PROFILE_REPEAT_DATA(30010,"重复数据！"),
    PROFILE_DICT_CITY_NOTEXIST(31011,"城市字典不存在！"),
    PROFILE_DICT_POSITION_NOTEXIST(31012,"职位职能字典不存在！"),
    PROFILE_DICT_INDUSTRY_NOTEXIST(31013,"行业字典不存在！"),
    PROFILE_USER_NOTEXIST(31014,"用户信息不正确！"),
    PROFILE_ALLREADY_EXIST(31015,"个人profile已存在！"),
    PROFILE_ILLEGAL(31016,"profile数据不正确！"),
    PROFILE_POSITION_NOTEXIST(31017,"职位信息不正确！"),
    PROFILE_DICT_NATIONALITY_NOTEXIST(31018,"国家字典不存在！"),
    PROFILE_DATA_NULL(31019,"数据不存在！"),
    PROFILE_DICT_COLLEGE_NOTEXIST(30020,"院校字典不存在！"),
    PROFILE_DICT_MAJOR_NOTEXIST(30021,"专业字典不存在！"),
    PROFILE_OUTPUT_FAILED(30022,"导出失败！"),
    PROFILE_ALLREADY_NOT_EXIST(30023,"个人profile不存在！"),
    VALIDATION_USERNAME_REQUIRED(30024,"缺少手机号码这个必要参数！"),
    VALIDATION_USER_ILLEGAL_PARAM(30025, "用户信息错误错误！"),
    VALIDATION_SMS_FAILTURE(30026,"密码发送失败！"),
    VALIDATION_APPLICATION_UPPER_LIMIT(30027,"投递达到上线无法继续投递！"),
    VALIDATION_POSITION_NOT_EXIST(30028,"参数错误！"),
    VALIDATION_RETRIEVAL_EXCUTOR_NOT_CUSTOMED(30029,"未定制任何执行者！"),
    VALIDATION_RETRIEVAL_CHANNEL_NOT_CUSTOMED(30030,"渠道信息错误！");

    private Category(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
