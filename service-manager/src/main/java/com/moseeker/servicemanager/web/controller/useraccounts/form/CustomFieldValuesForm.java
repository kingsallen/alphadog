package com.moseeker.servicemanager.web.controller.useraccounts.form;

import java.util.List;

/**
 * @Author: jack
 * @Date: 2018/10/19
 */
public class CustomFieldValuesForm {

    private Integer appid;
    private Integer companyId;
    private Integer userId;
    private List<EmployeeExtInfo> customFieldValues;

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<EmployeeExtInfo> getCustomFieldValues() {
        return customFieldValues;
    }

    public void setCustomFieldValues(List<EmployeeExtInfo> customFieldValues) {
        this.customFieldValues = customFieldValues;
    }
}
