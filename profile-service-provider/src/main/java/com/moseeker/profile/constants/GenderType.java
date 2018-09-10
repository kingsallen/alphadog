package com.moseeker.profile.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jack
 * @Date: 2018/9/7
 */
public enum GenderType {

    Male(1), Female(2), Secret(3);

    GenderType(int value) {
        this.value = value;
    }

    private static Map<Integer, GenderType> storage = new HashMap<>();
    static {
        for (GenderType genderType : values()) {
            storage.put(genderType.getValue(), genderType);
        }
    }

    public static GenderType instanceFromValue(int value) {
        return storage.get(value);
    }

    private int value;

    public int getValue() {
        return value;
    }
}
