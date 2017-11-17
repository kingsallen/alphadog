package com.moseeker.entity.Constant;

/**
 * 是否至今 常量
 * Created by jack on 09/11/2017.
 */
public enum UnitlNow {

    UntilNow(0, "否"), NotUntilNow(1, "是");

    private UnitlNow(int status, String name) {
        this.status = status;
        this.name = name;
    }

    private int status;
    private String name;

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return name;
    }
}
