package com.moseeker.candidate.service.pos;

import java.util.Date;

/**
 * 候选人
 * Created by jack on 10/02/2017.
 */
public class CandidateCompany extends Model {

    private int id; // optional
    private int companyId; // optional
    private Date updateTime; // optional
    private byte status; // optional
    private boolean isRecommend; // optional
    private java.lang.String name; // optional
    private java.lang.String email; // optional
    private java.lang.String mobile; // optional
    private java.lang.String nickname; // optional
    private java.lang.String headimgurl; // optional
    private int sysUserId; // optional
    private byte clickFrom; // optional
    /**
     * 创建候选人实例
     */
    public CandidateCompany() {

    }

    /**
     * 创建候选人实例
     * @param companyID 公司编号
     * @param userID 用户编号
     */
    public CandidateCompany(int companyID, int userID) {
        this.companyId = companyID;
        this.sysUserId = userID;
    }

    @Override
    public void loadFromDB() {

    }

    @Override
    public void updateToDB() {

    }

    @Override
    public void saveToDB() {

    }

    @Override
    public void deleteFromDB() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public boolean isRecommend() {
        return isRecommend;
    }

    public void setRecommend(boolean recommend) {
        isRecommend = recommend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public int getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(int sysUserId) {
        this.sysUserId = sysUserId;
    }

    public byte getClickFrom() {
        return clickFrom;
    }

    public void setClickFrom(byte clickFrom) {
        this.clickFrom = clickFrom;
    }
}
