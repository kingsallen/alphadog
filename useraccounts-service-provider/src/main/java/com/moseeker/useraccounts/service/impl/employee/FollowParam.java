package com.moseeker.useraccounts.service.impl.employee;

/**
 * 关注公众号事件参数
 * @Author: jack
 * @Date: 2018/8/19
 */
public class FollowParam {
    private int userId;
    private int wechatId;
    private long subscribeTime;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWechatId() {
        return wechatId;
    }

    public void setWechatId(int wechatId) {
        this.wechatId = wechatId;
    }

    public long getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(long subscribeTime) {
        this.subscribeTime = subscribeTime;
    }
}
