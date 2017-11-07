
package com.moseeker.entity.pojo.profile;

public class Workexps {

    private int endUntilNow;
    private String description;
    private String endDate;
    private Company company;
    private String departmentName;
    private String job;
    private String startDate;
    private int type;

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}