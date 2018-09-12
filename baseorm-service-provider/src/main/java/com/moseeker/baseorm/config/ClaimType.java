package com.moseeker.baseorm.config;

/**
 * @Author: jack
 * @Date: 2018/9/12
 */
public enum ClaimType {
    UnClaim((byte)0, "未认领"), Claimed((byte)1, "认领");

    ClaimType(byte value, String name) {
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
