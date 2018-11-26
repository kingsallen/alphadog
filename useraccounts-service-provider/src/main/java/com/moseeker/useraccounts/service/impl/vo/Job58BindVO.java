package com.moseeker.useraccounts.service.impl.vo;

/**
 * @author cjm
 * @date 2018-11-22 14:15
 **/
public class Job58BindVO {
    private String code;
    private Long timestamp;
    private String app_key;

    public Job58BindVO(String code, Long timestamp, String app_key) {
        this.code = code;
        this.timestamp = timestamp;
        this.app_key = app_key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }
}
