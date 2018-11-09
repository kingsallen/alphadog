package com.moseeker.baseorm.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 红包活动审核状态
 * @Author: jack
 * @Date: 2018/11/6
 */
public enum  ActivityCheckState {
    UnChecked(0), Checked(1), Cancel(2);

    private byte value;

    ActivityCheckState(int v) {
        this.value = (byte)v;
    }

    public byte getValue() {
        return value;
    }

    public static ActivityCheckState instanceFromValue(byte value) {
        return storage.get(value);
    }

    private static Map<Byte, ActivityCheckState> storage = new HashMap<>();
    static {
        for (ActivityCheckState activityCheckState : values()) {
            storage.put(activityCheckState.getValue(), activityCheckState);
        }
    }
}
