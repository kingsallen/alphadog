package com.moseeker.servicemanager.web.controller.referral.vo;

/**
 * Created by moseeker on 2018/12/12.
 */
public class ContactPushInfo {

    public int userId; // optional
    public String username; // optional
    public int positionId; // optional
    public String positionName; // optional

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
