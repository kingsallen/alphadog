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
    private Integer companyId;
    private String name;
    private String mobile;
    private List<String> referralReasons;
    private Byte referralType;
    private Boolean employee;
    private Integer referralId;
    private Integer userId;
    private String fileName;

    public ReferralInfoCacheDTO(Integer employeeId, Integer companyId, String name, String mobile,
                                List<String> referralReasons, Byte referralType, String fileName) {
        this.employeeId = employeeId;
        this.companyId = companyId;
        this.name = name;
        this.mobile = mobile;
        this.referralReasons = referralReasons;
        this.referralType = referralType;
        this.fileName = fileName;
    }

    public ReferralInfoCacheDTO() {
    }

    public Integer getReferralId() {
        return referralId;
    }

    public void setReferralId(Integer referralId) {
        this.referralId = referralId;
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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "ReferralInfoCacheDTO{" +
                "employeeId=" + employeeId +
                ", companyId=" + companyId +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", referralReasons=" + referralReasons +
                ", referralType=" + referralType +
                ", employee=" + employee +
                ", referralId=" + referralId +
                ", userId=" + userId +
                ", fileName='" + fileName + '\'' +
                '}';
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
