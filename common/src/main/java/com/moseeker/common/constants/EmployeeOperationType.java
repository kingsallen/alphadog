package com.moseeker.common.constants;

public enum  EmployeeOperationType {

    EMPLOYEEVALID(0,"员工认证"),
    RESUMERECOMMEND(1,"推荐简历");

    Integer key;
    String value;

    EmployeeOperationType(Integer key, String value) {
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
