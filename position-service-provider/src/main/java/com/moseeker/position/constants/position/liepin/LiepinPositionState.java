package com.moseeker.position.constants.position.liepin;

/**
 * 猎聘职位状态
 *
 * @author cjm
 * @date 2018-06-28 15:11
 **/
public enum LiepinPositionState {
    /*
    未发布状态
     */
    UNPUBLISH(0),
    /*
    发布成功状态
     */
    PUBLISH(1),;

    LiepinPositionState(int value) {
        this.value = value;
    }

    private int value;
    public int getValue(){
        return value;
    }
}
