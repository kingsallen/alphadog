package com.moseeker.company.exception;

import com.moseeker.common.exception.CommonException;

/**
 * @Author: jack
 * @Date: 2018/11/15
 */
public class CompanyException extends CommonException {

    public static final CompanyException COMPANY_HR_NOT_EXISTS = new CompanyException(34013,"HR信息不正确!");
    public static final CompanyException COMPANY_NOT_EXISTS = new CompanyException(34014,"公司不存在!");
    public static final CompanyException WORKWX_CORPID_OR_SERCRET_ERROR = new CompanyException(33009, "企业微信corpid或secret无效");
    public static final CompanyException WORKWX_COMPANY_ID_NOT_EXIST = new CompanyException(33010, "对应公司企业微信配置不存在");
    public CompanyException(int i, String s) {
        super(i, s);
    }
}
