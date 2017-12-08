package com.moseeker.useraccounts.constant;

/**
 * Created by jack on 15/11/2017.
 */
public enum HRAccountType {

    SupperAccount(0, "超级账号"), SubAccount(1, "子账号"), NormalAccount(2, "普通账号");

    private HRAccountType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
