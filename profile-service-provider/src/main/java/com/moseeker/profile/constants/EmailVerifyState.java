package com.moseeker.profile.constants;

/**
 * 邮箱认证的状态
 * @Author: jack
 * @Date: 2018/9/6
 */
public enum EmailVerifyState {

    UnVerified(0, "未认证"), Verified(1, "已认证"), OldData(2, "老数据");

    private byte value;
    private String name;


    EmailVerifyState(int value, String name) {
        this.value = (byte)value;
        this.name = name;
    }

    public byte getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
