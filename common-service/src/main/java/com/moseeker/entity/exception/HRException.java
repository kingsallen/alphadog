package com.moseeker.entity.exception;

import com.moseeker.common.exception.CommonException;

/**
 * HR异常
 */
public class HRException extends CommonException {

    public static final HRException USER_NOT_EXISTS = new HRException(42018, "HR不存在!");
    public static final HRException USER_UPDATEMOBILE_SAMEMOBILE = new HRException(42019, "新旧手机号码一致，无需修改!");

    private final int code;

    protected HRException(int code, String message) {
        super(code, message);
        this.code = code;
    }

    public HRException setMess(String message) {
        return new HRException(code, message);
    }

    public int getCode() {
        return code;
    }
}
