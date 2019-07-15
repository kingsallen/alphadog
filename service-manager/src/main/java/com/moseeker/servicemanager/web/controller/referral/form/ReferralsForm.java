package com.moseeker.servicemanager.web.controller.referral.form;

import java.util.List;

/**
 * @Description TODO
 * @Author Rays
 * @DATE 2019-07-15
 **/
public class ReferralsForm {

    private int employeeId;
    private int appid;
    private String realname;
    private String mobile;
    private List<Integer> ids;
    private int referralType;
    private List<String> recomTags;
    private int relation;
    private String recomText;
    private String fileName;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public int getReferralType() {
        return referralType;
    }

    public void setReferralType(int referralType) {
        this.referralType = referralType;
    }

    public List<String> getRecomTags() {
        return recomTags;
    }

    public void setRecomTags(List<String> recomTags) {
        this.recomTags = recomTags;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public String getRecomText() {
        return recomText;
    }

    public void setRecomText(String recomText) {
        this.recomText = recomText;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

