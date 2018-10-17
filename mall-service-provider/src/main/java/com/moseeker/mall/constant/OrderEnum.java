package com.moseeker.mall.constant;

/**
 * 订单状态
 *
 * @author cjm
 * @date 2018-10-16 11:47
 **/
public enum OrderEnum {
    /**
     * 未发放
     */
    UNCONFIRM(0),
    /**
     * 已发放
     */
    CONFIRM(1),
    /**
     * 已拒绝发放
     */
    REFUSED(2),
    /**
     * 全部状态，只在搜索时使用
     */
    All(9)
    ;


    private int state;

    OrderEnum(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
