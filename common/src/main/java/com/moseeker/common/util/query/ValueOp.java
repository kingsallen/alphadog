package com.moseeker.common.util.query;

import java.util.HashMap;
import java.util.Map;

/**
 * 将张弟设计的代码从thrift struct 转到common项目中
 * Created by jack on 18/04/2017.
 */
public enum ValueOp {

    EQ(0),
    NEQ(1),
    GT(2),
    GE(3),
    LT(4),
    LE(5),
    IN(6),
    NIN(7),
    BT(8),
    NBT(9),
    LIKE(10),
    NLIKE(11);

    private static Map<Integer, ValueOp> valueOpMap = new HashMap<>();

    static {
        for (ValueOp valueOp : values()) {
            valueOpMap.put(valueOp.getValue(), valueOp);
        }
    }

    private final int value;

    private ValueOp(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ValueOp buildFromValue(int value) {
        return valueOpMap.get(value);
    }
}
