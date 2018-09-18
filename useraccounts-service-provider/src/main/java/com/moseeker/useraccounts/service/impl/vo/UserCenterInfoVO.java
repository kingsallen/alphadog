package com.moseeker.useraccounts.service.impl.vo;

/**
 * @Author: jack
 * @Date: 2018/9/15
 */
public class UserCenterInfoVO {

    private int userId;
    private int employeeId;
    private String name;
    private String headimg;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }
}
