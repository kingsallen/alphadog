package com.moseeker.entity.exception;

import com.moseeker.common.exception.CommonException;

/**
 * @Author: jack
 * @Date: 2018/8/19
 */
public class EmployeeException extends CommonException {

    public static final EmployeeException EMPLOYEE_WECHAT_NOT_EXISTS = new EmployeeException(42500, "公众号信息错误!");
    public static final EmployeeException EMPLOYEE_NOT_EXISTS= new EmployeeException(42501, "员工信息不存在!");

    public EmployeeException(int code, String message) {
        super(code, message);
    }
}
