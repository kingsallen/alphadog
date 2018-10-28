package com.moseeker.position.service.position.job58.dto;

import com.moseeker.position.pojo.BasePositionDTO;

import java.util.Map;

/**
 * @author cjm
 * @date 2018-10-26 15:06
 **/
public class Base58PositionDTO extends BasePositionDTO {

    private String app_key;
    private String timestamp;
    private String access_token;
    private String openid;
    private Map<String, Object> paras;

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Map<String, Object> getParas() {
        return paras;
    }

    public void setParas(Map<String, Object> paras) {
        this.paras = paras;
    }

    @Override
    public String toString() {
        return "Base58PositionDTO{" +
                "app_key='" + app_key + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", access_token='" + access_token + '\'' +
                ", openid='" + openid + '\'' +
                ", paras=" + paras +
                '}';
    }
}
