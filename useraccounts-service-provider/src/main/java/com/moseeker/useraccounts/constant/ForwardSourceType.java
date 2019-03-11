package com.moseeker.useraccounts.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jack
 * @Date: 2018/8/20
 */
public enum ForwardSourceType {

    Groupmessage(1, "朋友圈"), Singlemessage(2, "微信群"), Timeline(3, "个人转发");

    private ForwardSourceType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    private int value;
    private String name;

    private static Map<Integer, ForwardSourceType> storage = new HashMap<>();
    static {
        for (ForwardSourceType leaderBoardType : values()) {
            storage.put(leaderBoardType.getValue(), leaderBoardType);
        }
    }

    public static ForwardSourceType instanceFromValue(int value) {
        return storage.get(value);
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
