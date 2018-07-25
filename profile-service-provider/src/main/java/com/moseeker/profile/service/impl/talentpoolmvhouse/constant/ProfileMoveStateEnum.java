package com.moseeker.profile.service.impl.talentpoolmvhouse.constant;

/**
 * 简历搬家状态
 *
 * @author cjm
 * @date 2018-07-19 16:16
 **/
public enum ProfileMoveStateEnum {
    /**
     * 正在搬家
     */
    MOVING((byte)0, "获取中"),
    /**
     * 简历搬家已经成功
     */
    SUCCESS((byte)1, "已完成"),
    /**
     * 简历搬家失败
     */
    FAILED((byte)2, "获取失败");


    byte value;

    String name;

    ProfileMoveStateEnum(byte value, String name) {
        this.value = value;
        this.name = name;
    }

    public byte getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
