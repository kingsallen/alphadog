package com.moseeker.useraccounts.constant;

/**
 * 洗定义字段类型
 *
 * @Author jack
 * @Date 2019/6/19 1:16 PM
 * @Version 1.0
 */
public enum FieldType {

    /**
     * 部门
     */
    Department(0),
    /**
     * 职位
     */
    Position(1),
    /**
     * 城市
     */
    City(2);

    FieldType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private int value;
}
