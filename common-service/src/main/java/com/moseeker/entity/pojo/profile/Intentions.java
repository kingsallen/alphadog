
package com.moseeker.entity.pojo.profile;

import java.util.List;


public class Intentions {

    private List<Industries> industries;
    private String salaryCode;
    private int workstate;
    private List<Positions> positions;
    private List<City> cities;
    private int worktype;

    public void setIndustries(List<Industries> industries) {
        this.industries = industries;
    }

    public List<Industries> getIndustries() {
        return industries;
    }

    public void setSalaryCode(String salaryCode) {
        this.salaryCode = salaryCode;
    }

    public String getSalaryCode() {
        return salaryCode;
    }

    public void setWorkstate(int workstate) {
        this.workstate = workstate;
    }

    public int getWorkstate() {
        return workstate;
    }

    public void setPositions(List<Positions> positions) {
        this.positions = positions;
    }

    public List<Positions> getPositions() {
        return positions;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setWorktype(int worktype) {
        this.worktype = worktype;
    }

    public int getWorktype() {
        return worktype;
    }

}