package com.moseeker.useraccounts.service.impl.vo;

/**
 * 认证结果
 *
 * @author cjm
 * @date 2018-11-03 14:44
 **/
public class ClaimResult {

    private Integer referralId;
    private Integer errCode;
    private String errmsg;

    public Integer getReferralId() {
        return referralId;
    }

    public void setReferralId(Integer referralId) {
        this.referralId = referralId;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}

