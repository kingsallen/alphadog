package com.moseeker.common.exception;

/**
 * Created by jack on 06/04/2017.
 */
public enum GlobalException {

    PROGRAM_PARAM_VALIDATE_ERROR(90017, null);

    private GlobalException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;       //状态码
    private String msg;     //错误消息
}
