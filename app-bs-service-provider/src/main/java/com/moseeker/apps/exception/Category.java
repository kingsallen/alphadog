package com.moseeker.apps.exception;

/**
 * Created by jack on 09/07/2017.
 */
public enum Category {

    VALIDATION_USERNAME_REQUIRED(61000,"缺少手机号码这个必要参数！"),
    VALIDATION_USER_ILLEGAL_PARAM(61001, "用户业务处理参数类型错误！");

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
