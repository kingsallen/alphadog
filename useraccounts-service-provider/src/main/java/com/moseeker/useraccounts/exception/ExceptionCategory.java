package com.moseeker.useraccounts.exception;

/**
 * Created by jack on 03/07/2017.
 */
public enum ExceptionCategory {

    USEREMPLOYEES_DATE_EMPTY(42006, "员工ID为空！"),
    USEREMPLOYEES_WRONG(42007, "员工ID设置错误！"),
    USEREMPLOYEES_EMPTY(42008, "员工数据未空！"),
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
