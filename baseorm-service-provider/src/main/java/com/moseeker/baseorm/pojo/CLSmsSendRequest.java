package com.moseeker.baseorm.pojo;

/**
 * Created by liuxuhui on 2019/3/8.
 */
public class CLSmsSendRequest {

    private String account;
    private String password;
    private String msg;
    private String params;
    private String sendtime;
    private String report;
    private String extend;
    private String uid;

    public CLSmsSendRequest(String account, String password, String msg, String params) {
        this.account = account;
        this.password = password;
        this.msg = msg;
        this.params = params;
    }

    public CLSmsSendRequest(String account, String password, String msg, String params, String sendtime, String report, String extend, String uid) {
        this.account = account;
        this.password = password;
        this.msg = msg;
        this.params = params;
        this.sendtime = sendtime;
        this.report = report;
        this.extend = extend;
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
