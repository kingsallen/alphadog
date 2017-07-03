package com.moseeker.company.exception;

/**
 * Created by jack on 03/07/2017.
 */
public enum ExceptionCategory {

    COMPANY_NOT_BELONG_GROUPCOMPANY(32010, "并非是集团公司！"),
    PROGRAM_DATA_EMPTY(90010, "公司信息不存在！"),
    PROGRAM_EXCEPTION(99999, "发生异常，请稍候再试！");

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
