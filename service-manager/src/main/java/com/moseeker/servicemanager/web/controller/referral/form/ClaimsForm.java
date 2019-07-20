package com.moseeker.servicemanager.web.controller.referral.form;

/**
 * @Auther: Rays
 * @Date: 2019/7/20 16:59
 * @Description:
 */

public class ClaimsForm {

    private int appid;
    private String rkey;
    private String name;
    private String mobile;
    private Integer userId;

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public String getRkey() {
        return rkey;
    }

    public void setRkey(String rkey) {
        this.rkey = rkey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
