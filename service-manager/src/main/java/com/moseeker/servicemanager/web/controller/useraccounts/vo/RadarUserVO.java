package com.moseeker.servicemanager.web.controller.useraccounts.vo;

import java.sql.Timestamp;

/**
 * Created by moseeker on 2018/12/29.
 */
public class RadarUserVO {

    public int userId;
    public String nickname;
    public int viewCount;
    public boolean seekRecommend;
    public int depth;
    public String headimgurl;
    public String positionTitle;
    public int positionId;
    public String forwardName;
    public int referralId;
    public boolean forwardSourceWx;
    public Timestamp clickTime;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public boolean isSeekRecommend() {
        return seekRecommend;
    }

    public void setSeekRecommend(boolean seekRecommend) {
        this.seekRecommend = seekRecommend;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
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

    public String getForwardName() {
        return forwardName;
    }

    public void setForwardName(String forwardName) {
        this.forwardName = forwardName;
    }

    public boolean isForwardSourceWx() {
        return forwardSourceWx;
    }

    public void setForwardSourceWx(boolean forwardSourceWx) {
        this.forwardSourceWx = forwardSourceWx;
    }

    public Timestamp getClickTime() {
        return clickTime;
    }

    public void setClickTime(Timestamp clickTime) {
        this.clickTime = clickTime;
    }
}
