package com.moseeker.function.service.chaos.position;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangdi on 2017/6/26.
 */
public class PositionLiepinWithAccount implements Serializable {
    private String title;
    private List<List<String>> cities;
    private String address;
    private String occupation;
    private String department;
    private String salary_low;
    private String salary_high;
    private String salary_discuss;
    private String salary_month;
    private String workyears;
    private String degree;
    private String description;
    private String feedback_period;
    private String email;

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

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getSalary_discuss() {
        return salary_discuss;
    }

    public void setSalary_discuss(String salary_discuss) {
        this.salary_discuss = salary_discuss;
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

    public String getFeedback_period() {
        return feedback_period;
    }

    public void setFeedback_period(String feedback_period) {
        this.feedback_period = feedback_period;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
