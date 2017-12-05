package com.moseeker.entity.Constant;

/**
 * 职位状态常量
 * Created by jack on 09/11/2017.
 */
public enum  JobStatus {

    Actived(0, "有效"), Deleted(1, "有效"), TakeOff(2, "撤下");

    private JobStatus(int status, String name) {
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
