package com.moseeker.servicemanager.web.controller.referral.form;

/**
 * @author cjm
 * @date 2018-12-18 11:22
 **/
public class CheckEmployeeForm {

    private Integer parentChainId;
    private Integer recomUserId;
    private Integer presenteeUserId;
    private Integer appid;
    private Integer pid;

    public Integer getParentChainId() {
        return parentChainId;
    }

    public void setParentChainId(Integer parentChainId) {
        this.parentChainId = parentChainId;
    }

    public Integer getRecomUserId() {
        return recomUserId;
    }

    public void setRecomUserId(Integer recomUserId) {
        this.recomUserId = recomUserId;
    }

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getPresenteeUserId() {
        return presenteeUserId;
    }

    public void setPresenteeUserId(Integer presenteeUserId) {
        this.presenteeUserId = presenteeUserId;
    }
}
