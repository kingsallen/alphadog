package com.moseeker.common.constants;

/**
 * Created by moseeker on 2018/6/26.
 */
public class Message {
    private String key;
    private String value;
    public Message(){

    }
    public Message(String key, Object value) {
        this.key = key;
        if(value != null)
            this.value = (String)value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
