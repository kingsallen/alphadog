package com.moseeker.servicemanager.web.controller.referral.form;

import java.util.List;

/**
 * 候选人关键信息
 * @Author: jack
 * @Date: 2018/9/6
 */
public class CandidateInfo {

    private int appid;
    private int position;
    private String name;
    private byte gender;
    private String mobile;
    private String email;
    private String company;
    private String job;
    private List<String> referralReasons;
    private int degree;
    private int city;
    private String recomReasonText;
    private byte relationship;
    private byte referralType;

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public List<String> getReferralReasons() {
        return referralReasons;
    }

    public void setReferralReasons(List<String> referralReasons) {
        this.referralReasons = referralReasons;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getRecomReasonText() {
        return recomReasonText;
    }

    public void setRecomReasonText(String recomReasonText) {
        this.recomReasonText = recomReasonText;
    }

    public byte getRelationship() {
        return relationship;
    }

    public void setRelationship(byte relationship) {
        this.relationship = relationship;
    }

    public byte getReferralType() {
        return referralType;
    }

    public void setReferralType(byte referralType) {
        this.referralType = referralType;
    }
}
