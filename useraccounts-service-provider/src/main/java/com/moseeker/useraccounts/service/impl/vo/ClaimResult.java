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
    private Integer apply_id;
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

    public Integer getApply_id() {
        return apply_id;
    }

    public void setApply_id(Integer apply_id) {
        this.apply_id = apply_id;
    }
}

