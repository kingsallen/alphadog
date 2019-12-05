package com.moseeker.servicemanager.web.controller;

/**
 * Created by moseeker on 2018/5/24.
 */

/**
 * Created by jack on 15/11/2017.
 */
public enum MessageType {

    PROGRAM_PARAM_NOTEXIST(90015, "参数不正确!"),
    PROGRAM_FILE_OVER_SIZE(90018,"文件过大!"),
    PROGRAM_FILE_NOT_SUPPORT(90019,"文件格式不支持！"),
    PROGRAM_FILE_BROKEN(90019,"此文件格式可能已经损坏！"),
    APPID_NOT_EXIST(90017, "请设置appid！");

    private MessageType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }
}