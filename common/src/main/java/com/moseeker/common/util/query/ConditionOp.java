package com.moseeker.common.util.query;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jack on 18/04/2017.
 */
public enum ConditionOp {

    AND(0),
    OR(1);

    private static Map<Integer, ConditionOp> conditionOpMap = new HashMap<>();

    static {
        for (ConditionOp conditionOp : values()) {
            conditionOpMap.put(conditionOp.getValue(), conditionOp);
        }
    }

    private final int value;

    private ConditionOp(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ConditionOp buildFromValue(int value) {
        return conditionOpMap.get(value);
    }
}
