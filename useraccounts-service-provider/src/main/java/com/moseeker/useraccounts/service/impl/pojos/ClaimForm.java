package com.moseeker.useraccounts.service.impl.pojos;

/**
 * 认领参数
 * @Author: jack
 * @Date: 2018/9/7
 */
public class ClaimForm {

    private int userId;
    private int referralRecordId;
    private String name;
    private String mobile;
    private String verifyCode;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
