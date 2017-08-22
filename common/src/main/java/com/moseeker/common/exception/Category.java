package com.moseeker.common.exception;

/**
 * 异常类型
 * Created by jack on 07/04/2017.
 */
public enum Category {

    PROGRAM_EXHAUSTED(-1, "系统繁忙，请稍候再试!"),
    PROGRAM_EXCEPTION(99999, "发生异常，请稍候再试!"),
    PROGRAM_DATA_EMPTY(90010, "请求数据为空"),
    PROGRAM_VALIDATE_REQUIRED(90011, "添加失败！"),
    PROGRAM_POST_FAILED(90012, "保存失败!"),
    PROGRAM_PUT_FAILED(90010, "请求数据为空"),
    PROGRAM_DEL_FAILED(90013, "删除失败!"),
    VALIDATE_FAILED(90014, null),
    PROGRAM_PARAM_NOTEXIST(90015, "参数不正确!"),
    PROGRAM_CONFIG_INCOMPLETE(90016, "配置信息丢失!"),
    PROGRAM_ELLEGAL_EXCEPTION(90018, "错误异常!"),
    COMPANY_DATA_EMPTY(90017, "公司信息不存在！");

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
