package com.moseeker.application.domain.constant;

/**
 * 申请的拒绝状态的数值
 * Created by jack on 07/02/2018.
 */
public enum ApplicationRefuseState {

    Refuse(4, "拒绝");

    private ApplicationRefuseState(int state, String name) {
        this.state = state;
        this.name = name;
    }

    private int state;
    private String name;

    public int getState() {
        return state;
    }

    public String getName() {
        return name;
    }
}
