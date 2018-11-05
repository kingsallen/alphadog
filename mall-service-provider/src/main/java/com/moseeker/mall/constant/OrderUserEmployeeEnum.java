package com.moseeker.mall.constant;

/**
 * 订单中员工状态，用于告诉前端该员工当前信息
 *
 * @author cjm
 * @date 2018-10-23 11:04
 **/
public enum  OrderUserEmployeeEnum {
    /**
     * 未认证
     */
    VERTIFIED(0),
    /**
     * 已认证
     */
    UNVERTIFIED(1),
    /**
     * 已删除
     */
    DELETED(2);


    private int state;

    OrderUserEmployeeEnum(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
