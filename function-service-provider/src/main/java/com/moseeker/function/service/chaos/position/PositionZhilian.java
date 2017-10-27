package com.moseeker.function.service.chaos.position;

import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangdi on 2017/6/26.
 */
public class PositionZhilian implements Serializable {
    private String title;
    private List<List<String>> cities;
    private String address;
    private List<String> occupation;
    private String salary_low;
    private String salary_high;
    private String salary_month;
    private String workyears;
    private String degree;
    private String description;
    private String email;
    private String job_id;
    private String count;
    private String company;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<List<String>> getCities() {
        return cities;
    }

    public void setCities(List<List<String>> cities) {
        this.cities = cities;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getOccupation() {
        return occupation;
    }

    public void setOccupation(List<String> occupation) {
        this.occupation = occupation;
    }

    public String getSalary_low() {
        return salary_low;
    }

    public void setSalary_low(String salary_low) {
        this.salary_low = salary_low;
    }

    public String getSalary_high() {
        return salary_high;
    }

    public void setSalary_high(String salary_high) {
        this.salary_high = salary_high;
    }

    public String getSalary_month() {
        return salary_month;
    }

    public void setSalary_month(String salary_month) {
        this.salary_month = salary_month;
    }

    public String getWorkyears() {
        return workyears;
    }

    public void setWorkyears(String workyears) {
        this.workyears = workyears;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public static PositionZhilian copyFromSyncPosition(ThirdPartyPositionForSynchronization positionInfo) {

        PositionZhilian positionLiepin = new PositionZhilian();
        positionLiepin.setTitle(positionInfo.getTitle());
        positionLiepin.setCities(positionInfo.getCities());
        positionLiepin.setAddress(positionInfo.getWork_place());
        positionLiepin.setOccupation(positionInfo.getOccupation());
        positionLiepin.setSalary_low(String.valueOf(positionInfo.getSalary_bottom()));
        positionLiepin.setSalary_high(String.valueOf(positionInfo.getSalary_top()));
        positionLiepin.setWorkyears(positionInfo.getExperience_code());
        positionLiepin.setDegree(positionInfo.getDegree_code());
        positionLiepin.setDescription(positionInfo.getDescription());
        positionLiepin.setEmail(positionInfo.getEmail());
        positionLiepin.setJob_id(positionInfo.getJob_id());
        positionLiepin.setCount(String.valueOf(positionInfo.getQuantity()));
        positionLiepin.setCompany(positionInfo.getCompany_name());
        return positionLiepin;
    }
}
