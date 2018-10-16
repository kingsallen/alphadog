package com.moseeker.common.constants;

public enum  EmployeeOperationIsSuccess {
    SUCCESS(0,"成功"),
    FAIL(1,"失败");

    Integer key;
    String value;

    EmployeeOperationIsSuccess(Integer key, String value) {
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
