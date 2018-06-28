package com.moseeker.common.constants;

/**
 * Created by moseeker on 2018/6/26.
 */
public class Message {
    private String key;
    private String value;
    private int newline;
    private int lastline;
    public Message(){

    }
    public Message(String key, Object value,int newline,int lastline) {
        this.key = key;
        if(value != null) {
            this.value = (String) value;
        }
        this.newline=newline;
        this.lastline=lastline;
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

    public int getNewline() {
        return newline;
    }

    public void setNewline(int newline) {
        this.newline = newline;
    }

    public int getLastline() {
        return lastline;
    }

    public void setLastline(int lastline) {
        this.lastline = lastline;
    }
}
