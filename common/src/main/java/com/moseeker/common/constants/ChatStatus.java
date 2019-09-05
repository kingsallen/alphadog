package com.moseeker.common.constants;

/**
 * 聊天消息状态
 */
public enum ChatStatus {

    DEFAULT((byte)0),
    NEED_HR_ANSWER((byte)1),
    HR_ANSWERED((byte)2);

    ChatStatus(byte value) {
        this.value = value;
    }

    private byte value;

    public byte value(){
        return value;
    }

}
