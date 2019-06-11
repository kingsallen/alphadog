package com.moseeker.useraccounts.constant;

/**
 * 员工来源
 *
 * @Author jack
 * @Date 2019/5/23 9:12 AM
 * @Version 1.0
 */
public enum EmployeeSource {

    /**
     * 麦当劳Joywork对接过来的员工数据
     */
    Joywork(12);

    EmployeeSource(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private int value;
}
