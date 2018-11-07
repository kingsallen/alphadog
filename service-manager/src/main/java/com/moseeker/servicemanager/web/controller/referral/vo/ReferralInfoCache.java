package com.moseeker.servicemanager.web.controller.referral.vo;

import java.util.List;

/**
 * @author cjm
 * @date 2018-11-02 9:40
 **/
public class ReferralInfoCache {

    private String mobile;
    private String name;
    private List<String> referralReasons;
    private String userId;
    private String fileName;
    private String referralType;

    public String getReferralType() {
        return referralType;
    }

    public void setReferralType(String referralType) {
        this.referralType = referralType;
    }

    @Override
    public String toString() {
        return "ReferralInfoCache{" +
                "mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", referralReasons=" + referralReasons +
                ", userId='" + userId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", referralType='" + referralType + '\'' +
                '}';
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getReferralReasons() {
        return referralReasons;
    }

    public void setReferralReasons(List<String> referralReasons) {
        this.referralReasons = referralReasons;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ReferralInfoCache() {
    }

}
