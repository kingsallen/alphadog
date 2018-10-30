package com.moseeker.servicemanager.web.controller.referral.vo;

import java.util.List;

/**
 * mobot推荐职位信息
 *
 * @author cjm
 * @date 2018-10-29 11:29
 **/
public class ReferralPositionForm {
    private List<Integer> ids;
    private Integer employeeId;
    private String mobile;
    private Integer appid;
    private String name;
    private Integer userId;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ReferralPositionForm{" +
                "ids=" + ids +
                ", employeeId=" + employeeId +
                ", mobile='" + mobile + '\'' +
                ", appid=" + appid +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                '}';
    }
}
