package com.moseeker.servicemanager.web.controller.referral.form;

import java.util.List;

/**
 * @author cjm
 * @date 2018-10-29 19:24
 **/
public class BatchClaimForm {
    private Integer appid;
    private Integer user;
    private List<Integer> referralRecordIds;
    private String name;
    private String mobile;
    private String vcode;

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public List<Integer> getReferralRecordIds() {
        return referralRecordIds;
    }

    public void setReferralRecordIds(List<Integer> referralRecordIds) {
        this.referralRecordIds = referralRecordIds;
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
