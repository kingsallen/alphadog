package com.moseeker.entity.exception;

/**
 * Created by jack on 03/07/2017.
 */
public enum ExceptionCategory {

    EMPLOYEE_HASBEENDELETEOR(60001, "员工已经被删除或者不存在！"),
    EMPLOYEE_IS_UNBIND(60002, "员工不存在或者已经取消认证！"),
    PROGRAM_EXCEPTION(99999, "发生异常，请稍候再试！"),;


    private ExceptionCategory(int code, String msg) {
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
