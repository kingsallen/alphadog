package com.moseeker.common.constants;

public enum SmsChannelType {
    ALIBABA((byte)1,"alibaba"),
    CHUANGLAN((byte)2,"创蓝253")
    ;

    SmsChannelType(Byte code, String text) {
        this.code = code;
        this.text = text;
    }

    Byte code;
    String text;

    public Byte getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
