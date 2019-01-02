package com.moseeker.useraccounts.service.impl.pojos;

/**
 * Created by moseeker on 2018/12/29.
 */
public class RadarUserVO {

    public int userId;
    public String nickname;
    public int viewCount;
    public int seekRecommend;
    public int depth;
    public String headimgurl;
    public String positionTitle;
    public int positionId;
    public String forwardName;
    public boolean forwardSourceWx;
    public String clickTime;

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

    public int getSeekRecommend() {
        return seekRecommend;
    }

    public void setSeekRecommend(int seekRecommend) {
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

    public String getClickTime() {
        return clickTime;
    }

    public void setClickTime(String clickTime) {
        this.clickTime = clickTime;
    }
}
