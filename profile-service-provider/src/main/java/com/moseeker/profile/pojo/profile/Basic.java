
package com.moseeker.profile.pojo.profile;

import java.util.Date;


public class Basic {

    private String cityName;
    private String gender;
    private String name;
    private Date birth;
    private String selfIntroduction;

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Date getBirth() {
        return birth;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

}