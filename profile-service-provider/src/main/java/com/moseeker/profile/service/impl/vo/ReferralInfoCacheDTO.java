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
    private Integer userId;
    private String fileName;
    private Byte relationship;
    private String referralReasonText;

    public ReferralInfoCacheDTO(Integer employeeId, Integer companyId, String name, String mobile, List<String> referralReasons,
                                Byte referralType, String fileName, Byte relationship, String referralReasonText) {
        this.employeeId = employeeId;
        this.companyId = companyId;
        this.name = name;
        this.mobile = mobile;
        this.referralReasons = referralReasons;
        this.referralType = referralType;
        this.fileName = fileName;
        this.relationship = relationship;
        this.referralReasonText = referralReasonText;

    }

    public ReferralInfoCacheDTO() {
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

    public Byte getRelationship() {
        return relationship;
    }

    public void setRelationship(Byte relationship) {
        this.relationship = relationship;
    }

    public String getReferralReasonText() {
        return referralReasonText;
    }

    public void setReferralReasonText(String referralReasonText) {
        this.referralReasonText = referralReasonText;
    }
}
