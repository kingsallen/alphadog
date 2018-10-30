package com.moseeker.profile.service.impl.vo;

import java.util.List;

/**
 * 员工推荐信息，目前用于存在redis中作为缓存
 *
 * @author cjm
 * @date 2018-10-29 11:14
 **/
public class ReferralInfoCacheDTO {

    private Integer employeeId;
    private String name;
    private String mobile;
    private List<String> referralReasons;
    private Byte referralType;
    private Boolean employee;
    private Integer userId;

    public ReferralInfoCacheDTO(Integer employeeId, String name, String mobile, List<String> referralReasons, Byte referralType) {
        this.employeeId = employeeId;
        this.name = name;
        this.mobile = mobile;
        this.referralReasons = referralReasons;
        this.referralType = referralType;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<String> getReferralReasons() {
        return referralReasons;
    }

    public void setReferralReasons(List<String> referralReasons) {
        this.referralReasons = referralReasons;
    }

    public Byte getReferralType() {
        return referralType;
    }

    public void setReferralType(Byte referralType) {
        this.referralType = referralType;
    }

    public Boolean getEmployee() {
        return employee;
    }

    public void setEmployee(Boolean employee) {
        this.employee = employee;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ReferralInfoCacheDTO{" +
                "employeeId=" + employeeId +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", referralReasons=" + referralReasons +
                ", referralType=" + referralType +
                ", employee=" + employee +
                ", userId=" + userId +
                '}';
    }
}
