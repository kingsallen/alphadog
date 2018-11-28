package com.moseeker.servicemanager.web.controller.referral.vo;

import java.util.List;

/**
 * Created by moseeker on 2018/11/23.
 */
public class ReferralReasonInfo {

    public int id;
    public List<String> referralReasons;
    public int relationship;
    public String recomReasonText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getReferralReasons() {
        return referralReasons;
    }

    public void setReferralReasons(List<String> referralReasons) {
        this.referralReasons = referralReasons;
    }

    public int getRelationship() {
        return relationship;
    }

    public void setRelationship(int relationship) {
        this.relationship = relationship;
    }

    public String getRecomReasonText() {
        return recomReasonText;
    }

    public void setRecomReasonText(String recomReasonText) {
        this.recomReasonText = recomReasonText;
    }
}
