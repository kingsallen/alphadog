package com.moseeker.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by moseeker on 2018/2/2.
 */
public enum PositionTitleType {

    SHARE1(1, "【诚意招贤】{} 招聘 {}"), SHARE2(2, " 想和你一起上班】{} 招聘 {}"),  SHARE3(3, "【WE WANT U】{} 招聘 {}");

    private int value;
    private String name;

    private static Map<Integer, PositionTitleType> map = new HashMap<>();

    static {
        for (PositionTitleType csat : values()) {
            map.put(csat.getValue(), csat);
        }
    }

    private PositionTitleType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name;
    }

    public static PositionTitleType instanceFromByte(Integer value) {
        if (value != null && map.get(value.intValue()) != null) {
            return map.get(value.intValue());
        } else {
            return PositionTitleType.SHARE1;
        }
    }
}