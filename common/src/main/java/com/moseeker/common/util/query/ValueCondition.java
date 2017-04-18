package com.moseeker.common.util.query;

/**
 * 将张弟设计的代码从thrift struct 转到common项目中
 * Created by jack on 18/04/2017.
 */
public class ValueCondition {

    private String field;
    private Object value;
    private ValueOp valueOp;

    public ValueCondition(String field, Object value, ValueOp valueOp) {
        this.field = field;
        this.value = value;
        this.valueOp = valueOp;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
