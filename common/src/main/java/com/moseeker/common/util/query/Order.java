package com.moseeker.common.util.query;

import java.util.HashMap;
import java.util.Map;

/**
 * 将张弟设计的代码从thrift struct 转到common项目中
 * Created by jack on 18/04/2017.
 */
public enum Order {
    ASC(0),
    DESC(1);

    private final int value;

    private static Map<Integer, Order> orderMap = new HashMap<>();

    static {
        for (Order order : values()) {
            orderMap.put(order.getValue(), order);
        }
    }

    private Order(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Order buildFromValue(int value) {
        return orderMap.get(value);
    }
}
