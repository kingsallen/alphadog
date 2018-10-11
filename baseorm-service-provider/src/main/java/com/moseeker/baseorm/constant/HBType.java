package com.moseeker.baseorm.constant;

/**
 * @Author: jack
 * @Date: 2018/10/11
 */
public enum HBType {

    EmployeeVerify((byte)0, "员工认证红包"),
    Recommend((byte)1, "推荐评价红包"),
    ReferralClick((byte)2, "转发被点击红包"),
    ReferralHire((byte)3, "转发被申请红包");

    HBType(byte value, String name) {
        this.value = value;
        this.name = name;
    }

    private byte value;
    private String name;

    public byte getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
