package com.moseeker.position.service.position.carnoc.pojo;

import java.util.List;

public class PositionCarnoc {
    private String job_title;
    private String job_details;
    private List<String> job_type;
    private String email;
    private int job_number;
    private boolean recruit_school;
    private List<List<String>> city;
    private String degree;
    private String work_exp;

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

    public List<String> getJob_type() {
        return job_type;
    }

    public void setJob_type(List<String> job_type) {
        this.job_type = job_type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getJob_number() {
        return job_number;
    }

    public void setJob_number(int job_number) {
        this.job_number = job_number;
    }

    public boolean isRecruit_school() {
        return recruit_school;
    }

    public void setRecruit_school(boolean recruit_school) {
        this.recruit_school = recruit_school;
    }

    public List<List<String>> getCity() {
        return city;
    }

    public void setCity(List<List<String>> city) {
        this.city = city;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getWork_exp() {
        return work_exp;
    }

    public void setWork_exp(String work_exp) {
        this.work_exp = work_exp;
    }
}
