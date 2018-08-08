package com.moseeker.servicemanager.web.controller.application.form;

import java.util.List;

/**
 * 员工主动推荐表单信息
 * @Author: jack
 * @Date: 2018/8/6
 */
public class EmployeeProxyApplyForm {

    private int appid;
    private List<Integer> positionIds;
    private Integer applierId;
    private Integer referenceId;

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public List<Integer> getPositionIds() {
        return positionIds;
    }

    public void setPositionIds(List<Integer> positionIds) {
        this.positionIds = positionIds;
    }

    public Integer getApplierId() {
        return applierId;
    }

    public void setApplierId(Integer applierId) {
        this.applierId = applierId;
    }

    public Integer getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
    }
}
