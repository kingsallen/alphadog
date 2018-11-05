package com.moseeker.useraccounts.service.impl.vo;

/**
 * 认证结果
 *
 * @author cjm
 * @date 2018-11-03 14:44
 **/
public class ClaimResult {

    private Integer referralId;
    private Integer positionId;
    private String title;
    private Boolean success;
    private String errmsg;

    public Integer getReferralId() {
        return referralId;
    }

    public void setReferralId(Integer referralId) {
        this.referralId = referralId;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}

