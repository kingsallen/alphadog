package com.moseeker.useraccounts.service.impl.vo;

/**
 * @author cjm
 * @date 2018-11-23 15:20
 **/
public class Job58TokenRefreshVO extends Job58BaseVO {

    protected String refresh_token;

    public Job58TokenRefreshVO(String openid, String accessToken, Long timestamp, String appKey, String refreshToken) {
        super(openid, accessToken, timestamp, appKey);
        this.refresh_token = refreshToken;
    }


    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refreshToken) {
        this.refresh_token = refreshToken;
    }
}
