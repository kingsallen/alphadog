package com.moseeker.servicemanager.web.controller.company.vo;

/**
 * @Author: jack
 * @Date: 2018/11/15
 */
public class GDPRProtectedInfoVO {

    private int userId;
    private boolean trigger;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isTrigger() {
        return trigger;
    }

    public void setTrigger(boolean trigger) {
        this.trigger = trigger;
    }
}
