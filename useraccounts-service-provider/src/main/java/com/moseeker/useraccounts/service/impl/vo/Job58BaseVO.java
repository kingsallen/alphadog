package com.moseeker.useraccounts.service.impl.vo;

/**
 * 58同城请求用户信息公共入参
 * @author cjm
 * @date 2018-11-22 14:43
 **/
public class Job58BaseVO {
    private String openid;
    private String access_token;
    private Long timestamp;
    private String app_key;

    public Job58BaseVO() {
    }

    public Job58BaseVO(String openid, String accessToken, Long timestamp, String appKey) {
        this.openid = openid;
        this.access_token = accessToken;
        this.timestamp = timestamp;
        this.app_key = appKey;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String accessToken) {
        this.access_token = accessToken;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String appKey) {
        this.app_key = appKey;
    }
}
