package com.moseeker.application.domain.pojo;

/**
 * Created by moseeker on 2018/7/12.
 */
public class ReferralWxMsgPojo {

    private int applicationId;
    private String positionName;
    private int companyId;
    private String userName;
    private String signature;
    private int recomId;
    private boolean sendStatus;

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getRecomId() {
        return recomId;
    }

    public void setRecomId(int recomId) {
        this.recomId = recomId;
    }

    public boolean isSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(boolean sendStatus) {
        this.sendStatus = sendStatus;
    }
}
