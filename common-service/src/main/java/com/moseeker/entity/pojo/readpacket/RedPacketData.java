package com.moseeker.entity.pojo.readpacket;

/**
 * @Author: jack
 * @Date: 2018/10/22
 */
public class RedPacketData {

    private int userId;
    private int wechatId;
    private int companyId;

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

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
