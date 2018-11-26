package com.moseeker.position.service.position.job58.dto;

/**
 * @author cjm
 * @date 2018-11-22 9:47
 **/
public class Base58RequestDTO {
    public String app_key;
    public Long timestamp;
    public String access_token;

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

}
