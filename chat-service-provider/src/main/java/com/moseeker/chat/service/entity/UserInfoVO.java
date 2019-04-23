package com.moseeker.chat.service.entity;

public class UserInfoVO {
    private String moseekertoken;
    private int accountId;
    private boolean type;
    private String abbreviation;
    private String username;
    private String headImgUrl;

    public String getMoseekertoken() {
        return moseekertoken;
    }

    public void setMoseekertoken(String moseekertoken) {
        this.moseekertoken = moseekertoken;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }
}
