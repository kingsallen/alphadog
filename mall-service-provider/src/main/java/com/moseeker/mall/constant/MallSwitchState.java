package com.moseeker.mall.constant;

/**
 * 商城开关状态
 *
 * @author cjm
 * @date 2018-10-12 18:23
 **/
public enum MallSwitchState {
    /**
     * 从未开通状态
     */
    NEVER_OPEN(0),
    /**
     * 已开通
     */
    OPEN(1),
    /**
     * 已关闭
     */
    CLOSED(2);


    private int state;

    MallSwitchState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

}
