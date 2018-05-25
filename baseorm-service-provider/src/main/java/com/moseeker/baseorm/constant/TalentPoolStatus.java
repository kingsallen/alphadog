package com.moseeker.baseorm.constant;

/**
 *
 * 人才库状态
 *
 * Created by jack on 2018/4/27.
 */
public enum TalentPoolStatus {

    Close(0, "未开启"), LowLevel(1, "普通人才库"), HighLevel(2, "高端人才库");

    private byte value;
    private String name;

    private TalentPoolStatus(int value, String name) {
        this.value = (byte) value;
        this.name = name;
    }

    public byte getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
