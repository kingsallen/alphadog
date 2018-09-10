package com.moseeker.entity.pojos;

import com.moseeker.baseorm.db.dictdb.tables.pojos.DictCity;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition;

import java.util.List;

/**
 * @Author: jack
 * @Date: 2018/9/6
 */
public class PositionInfo extends JobPosition {

    private String teamName;
    private List<DictCity> cities;

    private String companyName;
    private String companyAbbreviation;
    private String logo;

    public List<DictCity> getCities() {
        return cities;
    }

    public void setCities(List<DictCity> cities) {
        this.cities = cities;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
