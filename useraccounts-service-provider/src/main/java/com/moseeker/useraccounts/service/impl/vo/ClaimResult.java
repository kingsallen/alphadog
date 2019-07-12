package com.moseeker.useraccounts.service.impl.vo;

/**
 * 认证结果
 *
 * @author cjm
 * @date 2018-11-03 14:44
 **/
public class ClaimResult {

    private Integer referral_id;
    private Integer position_id;
    private Integer app_id;
    private String title;
    private Boolean success;
    private String errmsg;

    public Integer getReferral_id() {
        return referral_id;
    }

    public void setReferral_id(Integer referral_id) {
        this.referral_id = referral_id;
    }

    public Integer getPosition_id() {
        return position_id;
    }

    public void setPosition_id(Integer position_id) {
        this.position_id = position_id;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
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

    public Integer getApp_id() {
        return app_id;
    }

    public void setApp_id(Integer app_id) {
        this.app_id = app_id;
    }
}

