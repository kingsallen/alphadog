package com.moseeker.baseorm.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jack
 * @Date: 2018/11/6
 */
public enum ActivityStatus {

    UnChecked(0), Checked(1), UnStart(2), Running(3), Pause(4), Finish(5), Deleted(-1);

    private byte value;

    ActivityStatus(int value) {
        this.value = (byte) value;
    }

    public byte getValue() {
        return value;
    }

    public static ActivityStatus instanceFromValue(byte value) {
        return storage.get(value);
    }

    private static Map<Byte, ActivityStatus> storage = new HashMap<>();
    static {
        for (ActivityStatus activityStatus : values()) {
            storage.put(activityStatus.getValue(), activityStatus);
        }
    }
}
