package com.moseeker.servicemanager.web.controller.referral.form;

import java.util.List;

/**
 * 员工推荐简历的表单信息
 * @Author: jack
 * @Date: 2018/9/5
 */
public class ReferralForm {

    private int appid;
    private String name;
    private String mobile;
    private int position;
    private byte referralType;
    private List<String> referralReasons;

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public byte getReferralType() {
        return referralType;
    }

    public void setReferralType(byte referralType) {
        this.referralType = referralType;
    }

    public List<String> getReferralReasons() {
        return referralReasons;
    }

    public void setReferralReasons(List<String> referralReasons) {
        this.referralReasons = referralReasons;
    }
}
