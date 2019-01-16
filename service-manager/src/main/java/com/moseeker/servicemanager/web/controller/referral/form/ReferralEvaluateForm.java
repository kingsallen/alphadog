package com.moseeker.servicemanager.web.controller.referral.form;

import java.util.List;

/**
 * Created by moseeker on 2018/12/13.
 */
public class ReferralEvaluateForm {

    private int appid;
    private int postUserId;
    private int positionId;
    private int referralId;
    private List<String> referralReasons;
    private byte relationship;
    private String recomReasonText;
    private int presenteeUserId;

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public int getPostUserId() {
        return postUserId;
    }

    public void setPostUserId(int postUserId) {
        this.postUserId = postUserId;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public int getReferralId() {
        return referralId;
    }

    public void setReferralId(int referralId) {
        this.referralId = referralId;
    }

    public List<String> getReferralReasons() {
        return referralReasons;
    }

    public void setReferralReasons(List<String> referralReasons) {
        this.referralReasons = referralReasons;
    }

    public byte getRelationship() {
        return relationship;
    }

    public void setRelationship(byte relationship) {
        this.relationship = relationship;
    }

    public String getRecomReasonText() {
        return recomReasonText;
    }

    public void setRecomReasonText(String recomReasonText) {
        this.recomReasonText = recomReasonText;
    }

    public int getPresenteeUserId() {
        return presenteeUserId;
    }

    public void setPresenteeUserId(int presenteeUserId) {
        this.presenteeUserId = presenteeUserId;
    }
}
