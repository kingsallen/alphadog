package com.moseeker.mq.service.message;

/**
 * @ClassName FlexibleField
 * @Description TODO
 * @Author jack
 * @Date 2019/3/27 10:03 AM
 * @Version 1.0
 */
public class FlexibleField {

    private java.lang.String key;
    private java.lang.String name;
    private java.lang.String value;
    private boolean editable;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
