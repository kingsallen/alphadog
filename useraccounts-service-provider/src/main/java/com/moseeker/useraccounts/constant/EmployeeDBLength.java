package com.moseeker.useraccounts.constant;

/**
 * 员工数据长度限制
 *
 * @Author jack
 * @Date 2019/5/23 10:30 AM
 * @Version 1.0
 */
public enum EmployeeDBLength {

    /**
     * 姓名
     */
    CNAME(100),
    /**
     * 手机号码
     */
    MOBILE(20),
    /**
     * 邮箱
     */
    EMAIL(50),
    /**
     * 工号
     */
    CUSTOM_FIELD(64),
    ;

    EmployeeDBLength(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int getLengthLimit() {
        return value+1;
    }

    private int value;
}
