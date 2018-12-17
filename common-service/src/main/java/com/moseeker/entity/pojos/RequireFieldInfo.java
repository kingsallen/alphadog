package com.moseeker.entity.pojos;

import java.util.Set;

/**
 * Created by moseeker on 2018/11/26.
 */
public class RequireFieldInfo {

    public boolean gender;
    public boolean email;
    public boolean degree;
    public boolean companyName;
    public boolean position;
    public boolean city;

    public RequireFieldInfo() {
    }

    public RequireFieldInfo(Set<String> fieldSet) {
        if(fieldSet.contains("gender")){
            this.gender = true;
        }
        if(fieldSet.contains("email")){
            this.email = true;
        }
        if(fieldSet.contains("degree")){
            this.degree = true;
        }
        if(fieldSet.contains("companyBrand")){
            this.companyName = true;
        }
        if(fieldSet.contains("current_position")){
            this.position = true;
        }
        if(fieldSet.contains("location")){
            this.city = true;
        }
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public boolean isEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    public boolean isDegree() {
        return degree;
    }

    public void setDegree(boolean degree) {
        this.degree = degree;
    }

    public boolean isCompanyName() {
        return companyName;
    }

    public void setCompanyName(boolean companyName) {
        this.companyName = companyName;
    }

    public boolean isPosition() {
        return position;
    }

    public void setPosition(boolean position) {
        this.position = position;
    }

    public boolean isCity() {
        return city;
    }

    public void setCity(boolean city) {
        this.city = city;
    }
}
