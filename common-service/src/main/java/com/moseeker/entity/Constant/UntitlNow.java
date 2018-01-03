package com.moseeker.entity.Constant;

/**
 * 是否至今 常量
 * Created by jack on 09/11/2017.
 */
public enum UntitlNow {

    NotUntilNow(0, "否"), UntilNow(1, "是");

    private UntitlNow(int status, String name) {
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
