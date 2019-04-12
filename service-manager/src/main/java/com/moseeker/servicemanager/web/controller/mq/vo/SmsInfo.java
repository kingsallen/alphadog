package com.moseeker.servicemanager.web.controller.mq.vo;

import com.moseeker.thrift.gen.mq.struct.SmsType;

import java.util.Map;

/**
 * Created by liuxuhui on 2019/3/8.
 */
public class SmsInfo {
    private int smsType;
    private String mobile;
    private Map<String, String> data;
    private String sys;
    private String ip;

    public int getSmsType() {
        return smsType;
    }

    public void setSmsType(int smsType) {
        this.smsType = smsType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
