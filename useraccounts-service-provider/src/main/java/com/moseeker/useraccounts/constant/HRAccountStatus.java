package com.moseeker.useraccounts.constant;

/**
 * HR 账号  是否激活常量
 * Created by jack on 15/11/2017.
 */
public enum HRAccountStatus {

    Disabled(0, "不可用"), Enabled(1, "正常");

    private HRAccountStatus(int type, String name) {
        this.status = type;
        this.name = name;
    }

    private int status;
    private String name;

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
