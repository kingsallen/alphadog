package com.moseeker.useraccounts.service.impl.pojos;

/**
 * @author cjm
 * @date 2018-11-22 14:47
 **/
public class Job58BindUserInfo {
    private String openId;
    private String accessToken;
    private String refreshToken;

    public Job58BindUserInfo() {
    }

    public Job58BindUserInfo(String openId, String accessToken, String refreshToken) {
        this.openId = openId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
