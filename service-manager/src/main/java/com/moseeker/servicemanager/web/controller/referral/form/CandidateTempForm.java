package com.moseeker.servicemanager.web.controller.referral.form;

public class CandidateTempForm {
    private Integer appid;
    private Integer userId;
    private Integer companyId;
    private Long shareTime;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getShareTime() {
        return shareTime;
    }

    public void setShareTime(Long shareTime) {
        this.shareTime = shareTime;
    }
}
