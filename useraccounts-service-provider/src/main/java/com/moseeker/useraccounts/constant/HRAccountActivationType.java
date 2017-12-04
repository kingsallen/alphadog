package com.moseeker.useraccounts.constant;

/**
 * HR 账号  是否激活常量
 * Created by jack on 15/11/2017.
 */
public enum HRAccountActivationType {

    Unactived(0, "未激活"), Actived(1, "激活");

    private HRAccountActivationType(int type, String name) {
        this.value = type;
        this.name = name;
    }

    private int value;
    private String name;

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
