package com.moseeker.company.exception;

import com.moseeker.common.exception.CommonException;

/**
 * @Author: jack
 * @Date: 2018/11/15
 */
public class CompanyException extends CommonException {

    public static final CompanyException COMPANY_HR_NOT_EXISTS = new CompanyException(34013,"HR信息不正确!");
    public static final CompanyException COMPANY_NOT_EXISTS = new CompanyException(34014,"公司不存在!");

    public CompanyException(int i, String s) {
        super(i, s);
    }
}
