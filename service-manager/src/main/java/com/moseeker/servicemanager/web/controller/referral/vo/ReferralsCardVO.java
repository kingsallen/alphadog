package com.moseeker.servicemanager.web.controller.referral.vo;

import java.util.List;

/**
 * @Auther: Rays
 * @Date: 2019/7/20 16:02
 * @Description:
 */

public class ReferralsCardVO {

    private int isClaimed; 
    private String companyName; 
    private java.util.List<String> jobTitle; 
    private String failedTitle; 
    private String presenteeFirstName; 
    private String recomName; 
    private int presenteeId;

    public int getIsClaimed() {
        return isClaimed;
    }

    public void setIsClaimed(int isClaimed) {
        this.isClaimed = isClaimed;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<String> getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(List<String> jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getFailedTitle() {
        return failedTitle;
    }

    public void setFailedTitle(String failedTitle) {
        this.failedTitle = failedTitle;
    }

    public String getPresenteeFirstName() {
        return presenteeFirstName;
    }

    public void setPresenteeFirstName(String presenteeFirstName) {
        this.presenteeFirstName = presenteeFirstName;
    }

    public String getRecomName() {
        return recomName;
    }

    public void setRecomName(String recomName) {
        this.recomName = recomName;
    }

    public int getPresenteeId() {
        return presenteeId;
    }

    public void setPresenteeId(int presenteeId) {
        this.presenteeId = presenteeId;
    }
}
