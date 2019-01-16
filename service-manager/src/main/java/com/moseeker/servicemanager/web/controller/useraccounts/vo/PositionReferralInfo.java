package com.moseeker.servicemanager.web.controller.useraccounts.vo;

/**
 * Created by moseeker on 2018/12/10.
 */
public class PositionReferralInfo {

    public int userId; // optional
    public String employeeName; // optional
    public int employeeId; // optional
    public String positionName; // optional
    public int positionId; // optional
    private String employeeIcon;
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public String getEmployeeIcon() {
        return employeeIcon;
    }

    public void setEmployeeIcon(String employeeIcon) {
        this.employeeIcon = employeeIcon;
    }
}
