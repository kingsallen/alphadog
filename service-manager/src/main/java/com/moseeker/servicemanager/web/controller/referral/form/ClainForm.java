package com.moseeker.servicemanager.web.controller.referral.form;

/**
 * @Author: jack
 * @Date: 2018/9/7
 */
public class ClainForm {

    private int appid;
    private int user;
    private int referralRecordId;
    private String name;
    private String mobile;
    private String vcode;

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getReferralRecordId() {
        return referralRecordId;
    }

    public void setReferralRecordId(int referralRecordId) {
        this.referralRecordId = referralRecordId;
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

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }
}
