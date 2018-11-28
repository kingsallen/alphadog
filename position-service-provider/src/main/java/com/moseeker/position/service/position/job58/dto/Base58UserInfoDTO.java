package com.moseeker.position.service.position.job58.dto;

/**
 * @author cjm
 * @date 2018-11-22 20:50
 **/
public class Base58UserInfoDTO extends Base58RequestDTO {
    protected String openid;

    public Base58UserInfoDTO() {
    }

    public Base58UserInfoDTO(String appKey, Long timeStamp, String accessToken, String openId) {
        this.openid = openId;
        this.access_token = accessToken;
        this.app_key = appKey;
        this.timestamp = timeStamp;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
