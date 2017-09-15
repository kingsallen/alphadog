package com.moseeker.function.service.chaos.position;

import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangdi on 2017/6/26.
 */
public class PositionLiepin implements Serializable {
    private String title;
    private List<List<String>> cities;
    private String address;
    private List<String> occupation;
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
    private String job_id;
    private String recruit_type;
    private String work_type;
    private String practice_salary;
    private String practice_salary_unit;
    private String practice_per_week;

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

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getRecruit_type() {
        return recruit_type;
    }

    public void setRecruit_type(String recruit_type) {
        this.recruit_type = recruit_type;
    }

    public String getWork_type() {
        return work_type;
    }

    public void setWork_type(String work_type) {
        this.work_type = work_type;
    }

    public String getPractice_salary() {
        return practice_salary;
    }

    public void setPractice_salary(String practice_salary) {
        this.practice_salary = practice_salary;
    }

    public String getPractice_salary_unit() {
        return practice_salary_unit;
    }

    public void setPractice_salary_unit(String practice_salary_unit) {
        this.practice_salary_unit = practice_salary_unit;
    }

    public String getPractice_per_week() {
        return practice_per_week;
    }

    public void setPractice_per_week(String practice_per_week) {
        this.practice_per_week = practice_per_week;
    }

    public static PositionLiepin copyFromSyncPosition(ThirdPartyPositionForSynchronization positionInfo) {

        PositionLiepin positionLiepin = new PositionLiepin();
        positionLiepin.setTitle(positionInfo.getTitle());
        positionLiepin.setCities(positionInfo.getCities());
        positionLiepin.setAddress(positionInfo.getWork_place());
        positionLiepin.setOccupation(positionInfo.getOccupation());
        positionLiepin.setDepartment(positionInfo.getDepartment());
        positionLiepin.setSalary_low(String.valueOf(positionInfo.getSalary_bottom() / 1000));
        positionLiepin.setSalary_high(String.valueOf(positionInfo.getSalary_top() / 1000));
        positionLiepin.setSalary_discuss(positionInfo.isSalary_discuss() ? "1" : "0");
        positionLiepin.setSalary_month(String.valueOf(positionInfo.getSalary_month()));
        positionLiepin.setWorkyears(positionInfo.getExperience());
        positionLiepin.setDegree(positionInfo.getDegree_code());
        positionLiepin.setDescription(positionInfo.getDescription());
        positionLiepin.setFeedback_period(String.valueOf(positionInfo.getFeedback_period()));
        positionLiepin.setEmail(positionInfo.getEmail());
        positionLiepin.setJob_id(positionInfo.getJob_id());
        positionLiepin.setRecruit_type(positionInfo.getRecruit_type());
        positionLiepin.setWork_type(positionInfo.getWork_type());
        positionLiepin.setPractice_salary(String.valueOf(positionInfo.getPractice_salary()));
        positionLiepin.setPractice_salary_unit(String.valueOf(positionInfo.getPractice_salary_unit()));
        positionLiepin.setPractice_per_week(String.valueOf(positionInfo.getPractice_per_week()));
        return positionLiepin;
    }
}
