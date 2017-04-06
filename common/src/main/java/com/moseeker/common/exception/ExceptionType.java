package com.moseeker.common.exception;

/**
 * Created by jack on 06/04/2017.
 */
public enum ExceptionType {

    PROGRAM_PARAM_VALIDATE_ERROR(90017, null);

    private ExceptionType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;       //状态码
    private String msg;     //错误消息

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
