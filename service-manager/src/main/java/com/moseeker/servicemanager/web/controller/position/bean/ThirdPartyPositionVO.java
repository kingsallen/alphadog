package com.moseeker.servicemanager.web.controller.position.bean;

import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;

import java.io.Serializable;

/**
 * Created by zhangdi on 2017/6/23.
 */
public class ThirdPartyPositionVO implements Serializable {

    private int id;
    private int position_id;
    private String thirdPartPositionId;
    private int isSynchronization;
    private int isRefresh;
    private String syncTime;
    private String refreshTime;
    private String updateTime;
    private String address;
    private String occupation;
    private String syncFailReason;
    private int useCompanyAddress;
    private int thirdPartyAccountId;
    private int channel;
    private String department;
    private int salaryMonth;
    private int feedbackPeriod;
    private int salaryDiscuss;
    private int salaryBottom;
    private int salaryTop;
    private String accountId;
    private int companyId;
    private String companyName;
    private int addressId;
    private String addressName;
    private int departmentId;
    private String departmentName;

    public int practiceSalary;
    public byte practicePerWeek;
    public byte practiceSalaryUnit;


    public int getPracticeSalary() {
        return practiceSalary;
    }

    public void setPracticeSalary(int practiceSalary) {
        this.practiceSalary = practiceSalary;
    }

    public byte getPracticePerWeek() {
        return practicePerWeek;
    }

    public void setPracticePerWeek(byte practicePerWeek) {
        this.practicePerWeek = practicePerWeek;
    }

    public byte getPracticeSalaryUnit() {
        return practiceSalaryUnit;
    }

    public void setPracticeSalaryUnit(byte practiceSalaryUnit) {
        this.practiceSalaryUnit = practiceSalaryUnit;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public String getThirdPartPositionId() {
        return thirdPartPositionId;
    }

    public void setThirdPartPositionId(String thirdPartPositionId) {
        this.thirdPartPositionId = thirdPartPositionId;
    }

    public int getIsSynchronization() {
        return isSynchronization;
    }

    public void setIsSynchronization(int isSynchronization) {
        this.isSynchronization = isSynchronization;
    }

    public int getIsRefresh() {
        return isRefresh;
    }

    public void setIsRefresh(int isRefresh) {
        this.isRefresh = isRefresh;
    }

    public String getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(String syncTime) {
        this.syncTime = syncTime;
    }

    public String getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(String refreshTime) {
        this.refreshTime = refreshTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getSyncFailReason() {
        return syncFailReason;
    }

    public void setSyncFailReason(String syncFailReason) {
        this.syncFailReason = syncFailReason;
    }

    public int getUseCompanyAddress() {
        return useCompanyAddress;
    }

    public void setUseCompanyAddress(int useCompanyAddress) {
        this.useCompanyAddress = useCompanyAddress;
    }

    public int getThirdPartyAccountId() {
        return thirdPartyAccountId;
    }

    public void setThirdPartyAccountId(int thirdPartyAccountId) {
        this.thirdPartyAccountId = thirdPartyAccountId;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSalaryMonth() {
        return salaryMonth;
    }

    public void setSalaryMonth(int salaryMonth) {
        this.salaryMonth = salaryMonth;
    }

    public int getFeedbackPeriod() {
        return feedbackPeriod;
    }

    public void setFeedbackPeriod(int feedbackPeriod) {
        this.feedbackPeriod = feedbackPeriod;
    }

    public int getSalaryDiscuss() {
        return salaryDiscuss;
    }

    public void setSalaryDiscuss(int salaryDiscuss) {
        this.salaryDiscuss = salaryDiscuss;
    }

    public int getSalaryBottom() {
        return salaryBottom;
    }

    public void setSalaryBottom(int salaryBottom) {
        this.salaryBottom = salaryBottom;
    }

    public int getSalaryTop() {
        return salaryTop;
    }

    public void setSalaryTop(int salaryTop) {
        this.salaryTop = salaryTop;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public ThirdPartyPositionVO copyDO(HrThirdPartyPositionDO positionDO) {
        id = positionDO.id;
        position_id = positionDO.positionId;
        thirdPartPositionId = positionDO.thirdPartPositionId;
        isSynchronization = (byte) positionDO.isSynchronization;
        isRefresh = (byte) positionDO.isRefresh;
        syncTime = positionDO.syncTime;
        refreshTime = positionDO.refreshTime;
        updateTime = positionDO.updateTime;
        address = positionDO.address;
        occupation = positionDO.occupation;
        syncFailReason = positionDO.syncFailReason;
        useCompanyAddress = positionDO.useCompanyAddress;
        thirdPartyAccountId = positionDO.thirdPartyAccountId;
        channel = positionDO.channel;
        department = positionDO.department;
        salaryMonth = positionDO.salaryMonth;
        feedbackPeriod = positionDO.feedbackPeriod;
        salaryDiscuss = positionDO.salaryDiscuss;
        salaryBottom = positionDO.salaryBottom;
        salaryTop = positionDO.salaryTop;
        accountId = String.valueOf(positionDO.thirdPartyAccountId);

        companyId = positionDO.companyId;
        companyName = positionDO.companyName;
        addressId = positionDO.addressId;
        addressName =positionDO.addressName;
        departmentId = positionDO.departmentId;
        departmentName =positionDO.departmentName;

        practiceSalary = positionDO.practiceSalary;
        practicePerWeek = positionDO.practicePerWeek;
        practiceSalaryUnit = positionDO.practiceSalaryUnit;
        
        return this;
    }
}
