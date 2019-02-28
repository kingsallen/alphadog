package com.moseeker.useraccounts.service.constant;

/**
 * 人脉连连看chain连接状态
 * @author cjm
 * @date 2018-12-14 13:51
 **/
public enum RadarStateEnum {
    /**
     * 已经存在连连看链路，但未开始连接
     */
    Unstarted(0),
    /**
     * 连连看已经链接结束
     */
    Finished(1),
    /**
     * 连连看正在链接中
     */
    Connecting(2);

    private int state;
    RadarStateEnum(int state){
        this.state = state;
    }

    public int getState() {
        return state;
    }
}
