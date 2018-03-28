package com.moseeker.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by moseeker on 2018/2/2.
 */
public enum PositionDescriptionType {

    SHARE1(1, "点击进入，3分钟即可完成申请"), SHARE2(2, "适宜的工作环境，融洽的合作团队，等你来。点击进入，3分钟即可完成申请"),  SHARE3(3, "在大公司混成苦逼，不如来小公司做个逗逼！点击进入 ，3分钟即可完成申请");

    private int value;
    private String name;

    private static Map<Integer, PositionDescriptionType> map = new HashMap<>();

    static {
        for (PositionDescriptionType csat : values()) {
            map.put(csat.getValue(), csat);
        }
    }

    private PositionDescriptionType(int value, String name) {
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

    public static PositionDescriptionType instanceFromByte(Integer value) {
        if (value != null && map.get(value.intValue()) != null) {
            return map.get(value.intValue());
        } else {
            return PositionDescriptionType.SHARE1;
        }
    }
}