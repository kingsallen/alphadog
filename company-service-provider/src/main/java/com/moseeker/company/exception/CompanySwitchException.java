package com.moseeker.company.exception;

public class CompanySwitchException extends CompanyException {

    public static final CompanySwitchException MODULE_NAME_NOT_EXISTS = new CompanySwitchException(34015,"产品定义标识不存在!");
    public static final CompanySwitchException COMPANY_SWITCH_EXISTS = new CompanySwitchException(34016,"当前公司所已存在相同开关!");
    public static final CompanySwitchException SWITCH_NOT_EXISTS = new CompanySwitchException(34017,"未找到对应条件的开关!");

    public CompanySwitchException(int i, String s) {
        super(i, s);
    }
}
