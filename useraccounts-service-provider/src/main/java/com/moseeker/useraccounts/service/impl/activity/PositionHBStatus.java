package com.moseeker.useraccounts.service.impl.activity;

/**
 * 职位参与红包的状态
 * @Author: jack
 * @Date: 2018/11/7
 */
public enum  PositionHBStatus {

    NotInvited(0), RetransmitClick(1), RetransmitApply(2), CVPassed(4);

    PositionHBStatus(int value) {
        this.value = (byte) value;
    }

    public byte getValue() {
        return value;
    }

    private byte value;
}
