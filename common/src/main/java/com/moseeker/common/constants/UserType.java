package com.moseeker.common.constants;

/**
 * Created by zztaiwll on 18/6/27.
 */
public enum UserType {
    PC(0),
    HR(1);

    private int value;

    private UserType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public byte getValueToByte() {
        return (byte)this.value;
    }
}
