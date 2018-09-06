package com.moseeker.useraccounts.service.impl.pojos;

import java.util.List;

/**
 * @Author: jack
 * @Date: 2018/9/6
 */
public class ReferralPositionInfo {

    private int id;
    private String title;
    private String companyName;
    private String companyAbbreviation;
    private int salaryBottom;
    private int salaryTop;
    private int experience;
    private boolean experienceAbove;
    private String team;
    private String logo;
    private List<City> cities;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAbbreviation() {
        return companyAbbreviation;
    }

    public void setCompanyAbbreviation(String companyAbbreviation) {
        this.companyAbbreviation = companyAbbreviation;
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

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public boolean isExperienceAbove() {
        return experienceAbove;
    }

    public void setExperienceAbove(boolean experienceAbove) {
        this.experienceAbove = experienceAbove;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
