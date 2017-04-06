package com.moseeker.candidate.service.exception;

import com.moseeker.thrift.gen.common.struct.BIZException;

/**
 * 候选人服务异常信息
 * Created by jack on 06/04/2017.
 */
public enum CandidateException {

    PROGRAM_PARAM_VALIDATE_ERROR(90017, null);

    private CandidateException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BIZException buildException() {
        return buildException(code, msg);
    }

    public BIZException buildException(String msg) {
        return buildException(0, msg);
    }

    public BIZException buildException(int code) {
        return buildException(code, null);
    }

    public BIZException buildException(int code, String msg) {
        BIZException exception = new BIZException();
        exception.setCode(code);
        exception.setMessage(msg);
        return exception;
    }

    private int code;       //状态码
    private String msg;     //错误消息
}
