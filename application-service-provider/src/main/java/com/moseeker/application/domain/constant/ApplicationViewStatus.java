package com.moseeker.application.domain.constant;

/**
 * 申请查看状态
 * Created by jack on 18/01/2018.
 */
public enum ApplicationViewStatus {

    VIEWED(0), UNVIEWED(1);

    private ApplicationViewStatus(int status) {
        this.status = status;
    }

    private int status;

    public int getStatus() {
        return status;
    }
}
