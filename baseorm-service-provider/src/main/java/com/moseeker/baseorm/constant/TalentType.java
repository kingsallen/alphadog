package com.moseeker.baseorm.constant;

/**
 * 人才库中人才的类型。分为收藏和上传两种
 * @Author: jack
 * @Date: 2018/8/6
 */
public enum TalentType {

    Collect((byte)0), Upload((byte)1);

    private byte value;

    private TalentType(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
