package com.moseeker.useraccounts.service.impl.biztools;

import java.util.HashMap;
import java.util.Map;

/**
 * 奖金阶段
 * @Author: jack
 * @Date: 2018/9/28
 */
public enum  BonusStage {

    Hired(3, "内推入职");

    private int value;
    private String name;

    BonusStage(int value, String name) {
        this.value = value;
        this.name = name;
    }

    private static Map<Integer, BonusStage> storage = new HashMap<>();

    static {
        for (BonusStage bonusStage : values()) {
            storage.put(bonusStage.getValue(), bonusStage);
        }
    }

    public static BonusStage instanceFromValue(int value) {
        return storage.get(value);
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getDescription(double value) {
        if (value >= 0) {
            return "【"+name+"】";
        } else {
            return "【撤销"+name+"】";
        }
    }
}
