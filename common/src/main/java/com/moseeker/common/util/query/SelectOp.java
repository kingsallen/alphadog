package com.moseeker.common.util.query;

import java.util.HashMap;
import java.util.Map;

/**
 * 将张弟设计的代码从thrift struct 转到common项目中
 * Created by jack on 18/04/2017.
 */
public enum SelectOp {

    FIELD(0),
    DISTINCT(1),
    COUNT(2),
    COUNT_DISTINCT(3),
    SUM(4),
    AVG(5),
    MAX(6),
    MIN(7),
    UCASE(8),
    LCASE(9),
    LEN(10),
    ROUND(11),
    TRIM(12);

    private static Map<Integer, SelectOp> selectOpMap = new HashMap<>();

    static {
        for (SelectOp selectOp : values()) {
            selectOpMap.put(selectOp.getValue(), selectOp);
        }
    }

    private final int value;

    private SelectOp(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public SelectOp buildFromValue(int value) {
        return selectOpMap.get(value);
    }
}
