
package com.moseeker.profile.pojo.profile;

import java.util.Date;


public class Workexps {

    private int endUntilNow;
    private String description;
    private String endDate;
    private Company company;
    private String departmentName;
    private String job;
    private Date startDate;

    public void setEndUntilNow(int endUntilNow) {
        this.endUntilNow = endUntilNow;
    }

    public int getEndUntilNow() {
        return endUntilNow;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJob() {
        return job;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

}