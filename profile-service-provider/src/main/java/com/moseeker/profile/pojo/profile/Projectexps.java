
package com.moseeker.profile.pojo.profile;

import java.util.Date;


public class Projectexps {

    private int endUntilNow;
    private String name;
    private String endDate;
    private String responsibility;
    private String companyName;
    private String startDate;
    private String description;

    public void setEndUntilNow(int endUntilNow) {
        this.endUntilNow = endUntilNow;
    }

    public int getEndUntilNow() {
        return endUntilNow;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}