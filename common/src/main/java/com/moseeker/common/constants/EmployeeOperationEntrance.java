package com.moseeker.common.constants;

public enum  EmployeeOperationEntrance {
    IMEMPLOYEE(102,"我是员工");

    Integer key;
    String value;

    EmployeeOperationEntrance(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
