package com.moseeker.servicemanager.web.controller.application.form;

/**
 * 申请记录的查询表单
 * @Author: jack
 * @Date: 2018/9/14
 */
public class ApplicationForm {
    private Integer userId;
    private Integer companyId;
    private Integer appid;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }
}
