package com.moseeker.useraccounts.context.constant;

/**
 * 职位状态
 * Created by jack on 22/01/2018.
 */
public enum  PositionStatus {

    ACTIVED(0, "有效"), DELETED(1, "删除"), BANNED(2, "撤下");

    private PositionStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }

    private final int value;
    private final String name;

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
