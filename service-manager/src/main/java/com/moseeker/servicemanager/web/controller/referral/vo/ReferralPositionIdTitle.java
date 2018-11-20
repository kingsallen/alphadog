package com.moseeker.servicemanager.web.controller.referral.vo;

/**
 * @author cjm
 * @date 2018-11-02 10:54
 **/
public class ReferralPositionIdTitle {
    private Integer positionId;
    private String title;

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

    public ReferralPositionIdTitle() {
    }

    public ReferralPositionIdTitle(Integer positionId, String title) {
        this.positionId = positionId;
        this.title = title;
    }
}
