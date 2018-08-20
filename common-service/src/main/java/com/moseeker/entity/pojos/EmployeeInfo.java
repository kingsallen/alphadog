package com.moseeker.entity.pojos;

import com.moseeker.baseorm.constant.EmployeeActiveState;

/**
 * @Author: jack
 * @Date: 2018/8/20
 */
public class EmployeeInfo {

    private int id;
    private String name;
    private String headImg;
    private int award;
    private EmployeeActiveState employeeActiveState;
    private int companyId;
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getAward() {
        return award;
    }

    public void setAward(int award) {
        this.award = award;
    }

    public EmployeeActiveState getEmployeeActiveState() {
        return employeeActiveState;
    }

    public void setEmployeeActiveState(EmployeeActiveState employeeActiveState) {
        this.employeeActiveState = employeeActiveState;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
