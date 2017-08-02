package com.moseeker.entity.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jack on 10/04/2017.
 */
public enum EmployeeType {
    AUTH_SUCCESS(0), AUTH_CANCLE(1), AUTH_FAILAIN(2), AUTH_PENDING(3);

    private int value;

    private static Map<Integer, EmployeeType> employeeTypePool = new HashMap<>();

    static {
        for (EmployeeType employeeType : EmployeeType.values()) {
            employeeTypePool.put(employeeType.getValue(), employeeType);
        }
    }

    private EmployeeType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public EmployeeType buildFromValue(int value) {
        if (employeeTypePool.containsKey(value)) {
            return employeeTypePool.get(value);
        } else {
            return null;
        }
    }
}
