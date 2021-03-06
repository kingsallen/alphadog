package com.moseeker.position.service.position.jobsdb.pojo;

import com.moseeker.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PositionJobsDB {
    private String job_title;

    private String job_details;

    private String email;

    private List<String> summary_points;

    private List<List<String>> job_functions;

    private List<String> work_location;

    private List<String> employment_type;

    private int salary_top;

    private int salary_bottom;

    private List<String> benefits;

    private int career_level;

    private int education_level;

    private int experience;

    private String keyword;

    private int salary_type;

    public int getCareer_level() {
        return career_level;
    }

    public void setCareer_level(int career_level) {
        this.career_level = career_level;
    }

    public int getEducation_level() {
        return education_level;
    }

    public void setEducation_level(int education_level) {
        this.education_level = education_level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getSalary_type() {
        return salary_type;
    }

    public void setSalary_type(int salary_type) {
        this.salary_type = salary_type;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getJob_details() {
        return job_details;
    }

    public void setJob_details(String job_details) {
        this.job_details = job_details;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getSummary_points() {
        return summary_points;
    }

    public void setSummary_points(List<String> summary_points) {
        this.summary_points = summary_points;
    }

    public List<List<String>> getJob_functions() {
        return job_functions;
    }

    public void setJob_functions(List<List<String>> job_functions) {
        this.job_functions = job_functions;
    }

    public List<String> getWork_location() {
        return work_location;
    }

    public void setWork_location(List<String> work_location) {
        this.work_location = work_location;
    }

    public List<String> getEmployment_type() {
        return employment_type;
    }

    public void setEmployment_type(List<String> employment_type) {
        this.employment_type = employment_type;
    }

    public int getSalary_top() {
        return salary_top;
    }

    public void setSalary_top(int salary_top) {
        this.salary_top = salary_top;
    }

    public int getSalary_bottom() {
        return salary_bottom;
    }

    public void setSalary_bottom(int salary_bottom) {
        this.salary_bottom = salary_bottom;
    }

    public List<String> getBenefits() {
        return benefits;
    }

    public void setBenefits(List<String> benefits) {
        this.benefits = benefits;
    }
}
