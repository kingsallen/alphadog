package com.moseeker.candidate.service.vo;

/**
 * Created by moseeker on 2018/11/22.
 */
public class PositionLayerInfo {

    public int currentPositionCount;
    public int positionViewCount;
    public int profileCompleteness;
    public int isSubscribe;
    public String qrcode;
    public int type;
    public String name;
    public int positionWxLayerQrcode;
    public int positionWxLayerProfile;

    public int getCurrentPositionCount() {
        return currentPositionCount;
    }

    public void setCurrentPositionCount(int currentPositionCount) {
        this.currentPositionCount = currentPositionCount;
    }

    public int getPositionViewCount() {
        return positionViewCount;
    }

    public void setPositionViewCount(int positionViewCount) {
        this.positionViewCount = positionViewCount;
    }

    public int getProfileCompleteness() {
        return profileCompleteness;
    }

    public void setProfileCompleteness(int profileCompleteness) {
        this.profileCompleteness = profileCompleteness;
    }

    public int getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(int isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPositionWxLayerQrcode() {
        return positionWxLayerQrcode;
    }

    public void setPositionWxLayerQrcode(int positionWxLayerQrcode) {
        this.positionWxLayerQrcode = positionWxLayerQrcode;
    }

    public int getPositionWxLayerProfile() {
        return positionWxLayerProfile;
    }

    public void setPositionWxLayerProfile(int positionWxLayerProfile) {
        this.positionWxLayerProfile = positionWxLayerProfile;
    }
}
