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
    private String name;
    private String mobile;
    private List<Integer> positions;
    private int referralType;
    private List<String> referralReasons;
    private int relationship;
    private String recomReasonText;
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

    public List<Integer> getPositions() {
        return positions;
    }

    public void setPositions(List<Integer> positions) {
        this.positions = positions;
    }

    public int getReferralType() {
        return referralType;
    }

    public void setReferralType(int referralType) {
        this.referralType = referralType;
    }

    public List<String> getReferralReasons() {
        return referralReasons;
    }

    public void setReferralReasons(List<String> referralReasons) {
        this.referralReasons = referralReasons;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getRelationship() {
        return relationship;
    }

    public void setRelationship(int relationship) {
        this.relationship = relationship;
    }

    public String getRecomReasonText() {
        return recomReasonText;
    }

    public void setRecomReasonText(String recomReasonText) {
        this.recomReasonText = recomReasonText;
    }
}

