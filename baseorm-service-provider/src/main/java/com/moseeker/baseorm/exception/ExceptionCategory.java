package com.moseeker.baseorm.exception;

/**
 * Created by YYF
 *
 * Date: 2017/7/18
 *
 * Project_name :alphadog
 */
public enum ExceptionCategory {


    SELECT_FIELD_NOEXIST(70001, "查询的字段不存在"),
    GROUPBY_FIELD_NOEXIST(70002, "分组字段不存在"),
    ORDER_FIELD_NOEXIST(70003, "排序字段不存在"),
    CONDITION_FIELD_NOEXIST(70004, "Condition字段不存在");


    ExceptionCategory(int code, String msg) {
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
