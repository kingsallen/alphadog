package com.moseeker.consistencysuport.producer;

/**
 *
 * 消息类型
 *
 * Created by jack on 2018/4/19.
 */
public class MessageTypePojo {

    private String name;
    private String className;
    private String method;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
