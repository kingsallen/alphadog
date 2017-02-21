package com.moseeker.useraccounts.service.impl.biztools;

/**
 * 投递类型
 * Created by jack on 21/02/2017.
 */
public enum ApplyType {

    PROFILE(0), EMAIL(1);

    private int value;   //0表示 profile投递，1表示email投递

    private ApplyType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }


}
