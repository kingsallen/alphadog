package com.moseeker.baseorm.exception;

import com.moseeker.common.exception.CommonException;

/**
 * Created by YYF
 *
 * Date: 2017/7/25
 *
 * Project_name :alphadog
 */
public class CoditionException extends CommonException {



    public static final CoditionException SELECT_FIELD_NOEXIST = new CoditionException(70002, "查询字段不存在！");
    public static final CoditionException GROUPBY_FIELD_NOEXIST = new CoditionException(70003, "排序字段不存在！");
    public static final CoditionException ORDER_FIELD_NOEXIST = new CoditionException(70003, "排序字段不存在！");
    public static final CoditionException CONDITION_FIELD_NOEXIST = new CoditionException(70004, "字段不存在！");

    private final int code;

    protected CoditionException(int code, String message) {
        super(code, message);
        this.code = code;
    }

    public CoditionException setMess(String message) {
        return new CoditionException(code, message);
    }

    public int getCode() {
        return code;
    }

}
