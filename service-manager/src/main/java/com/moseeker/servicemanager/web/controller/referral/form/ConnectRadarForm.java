package com.moseeker.servicemanager.web.controller.referral.form;

/**
 * @author cjm
 * @date 2018-12-07 14:40
 **/
public class ConnectRadarForm {
    private Integer appid;
    private Integer recomUserId;
    private Integer nextUserId;
    private Integer chainId;
    private Integer parentId;

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public Integer getRecomUserId() {
        return recomUserId;
    }

    public void setRecomUserId(Integer recomUserId) {
        this.recomUserId = recomUserId;
    }

    public Integer getNextUserId() {
        return nextUserId;
    }

    public void setNextUserId(Integer nextUserId) {
        this.nextUserId = nextUserId;
    }

    public Integer getChainId() {
        return chainId;
    }

    public void setChainId(Integer chainId) {
        this.chainId = chainId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
