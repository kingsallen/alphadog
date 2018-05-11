package com.moseeker.entity.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 邮箱账号的消费类型
 *
 * Created by jack on 2018/4/25.
 */
public enum EmailAccountConsumptionType {

    COMSUMPTION(1, "消费"), RECHARRGE(0, "充值");

    private byte value;
    private String name;

    private static Map<Byte, EmailAccountConsumptionType> storage = new HashMap<>();

    private EmailAccountConsumptionType(int value, String name) {
        this.value = (byte)value;
        this.name = name;
    }

    static {
        for (EmailAccountConsumptionType type : values()) {
            storage.put(type.value, type);
        }
    }

    public static EmailAccountConsumptionType instanceFromValue(int value) {
        return storage.get((byte)value);
    }

    public byte getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
