package com.moseeker.consistencysuport.constant;

/**
 *
 * 消息状态
 * 未完成 0 完成 1
 *
 * Created by jack on 11/04/2018.
 */
public enum  MessageState {

    UnFinish((byte)0), Finish((byte)1);

    private MessageState(byte value) {
        this.value = value;
    }

    private byte value;

    public byte getValue() {
        return value;
    }
}
