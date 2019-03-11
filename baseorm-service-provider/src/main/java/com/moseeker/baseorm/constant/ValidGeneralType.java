package com.moseeker.baseorm.constant;


/*
*
* 基础属性
*
* */
public enum ValidGeneralType {

    valid((byte)1,"可用"), invalid((byte)0,"不可用");

    private Byte value;
    private String name;

    ValidGeneralType(Byte value, String name) {
        this.value = value;
        this.name = name;
    }

    public void setValue(Byte value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private ValidGeneralType(Byte value) {
        this.value = value;
    }

    public Byte getValue() {
        return value;
    }
}
