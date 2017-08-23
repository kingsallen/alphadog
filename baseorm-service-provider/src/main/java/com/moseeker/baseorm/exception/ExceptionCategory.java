package com.moseeker.baseorm.exception;

/**
 * Created by YYF
 *
 * Date: 2017/7/18
 *
 * Project_name :alphadog
 */
public enum ExceptionCategory {


    SELECT_FIELD_NOEXIST(70001, "查询的{TABLE}表中{FIELD}字段不存在"),
    GROUPBY_FIELD_NOEXIST(70002, "查询的{TABLE}表中按{FIELD}分组的字段不存在"),
    ORDER_FIELD_NOEXIST(70003, "查询的{TABLE}按{FIELD}排序字段不存在"),
    CONDITION_FIELD_NOEXIST(70004, "查询的{TABLE}中的条件{FIELD}字段不存在");


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
