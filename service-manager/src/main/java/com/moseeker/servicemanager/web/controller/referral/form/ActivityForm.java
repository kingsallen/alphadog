package com.moseeker.servicemanager.web.controller.referral.form;

import java.util.List;

/**
 * @Author: jack
 * @Date: 2018/11/6
 */
public class ActivityForm {

    private Integer appid;
    private List<Integer> amounts;
    private Integer target;
    private String startTime;
    private String endTime;
    private Double totalAmount;
    private Double rangMin;
    private Double rangMax;
    private Integer probability;
    private Integer dType;
    private String headline;
    private String headlineFailure;
    private String shareTitle;
    private String shareDesc;
    private String shareImg;
    private Integer status;
    private Integer checked;
    private Integer estimatedTotal;
    private Integer actualTotal;
    private List<Integer> positionIds;

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public List<Integer> getAmounts() {
        return amounts;
    }

    public void setAmounts(List<Integer> amounts) {
        this.amounts = amounts;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getRangMin() {
        return rangMin;
    }

    public void setRangMin(Double rangMin) {
        this.rangMin = rangMin;
    }

    public Double getRangMax() {
        return rangMax;
    }

    public void setRangMax(Double rangMax) {
        this.rangMax = rangMax;
    }

    public Integer getProbability() {
        return probability;
    }

    public void setProbability(Integer probability) {
        this.probability = probability;
    }

    public Integer getdType() {
        return dType;
    }

    public void setdType(Integer dType) {
        this.dType = dType;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getHeadlineFailure() {
        return headlineFailure;
    }

    public void setHeadlineFailure(String headlineFailure) {
        this.headlineFailure = headlineFailure;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareDesc() {
        return shareDesc;
    }

    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }

    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Integer getEstimatedTotal() {
        return estimatedTotal;
    }

    public void setEstimatedTotal(Integer estimatedTotal) {
        this.estimatedTotal = estimatedTotal;
    }

    public Integer getActualTotal() {
        return actualTotal;
    }

    public void setActualTotal(Integer actualTotal) {
        this.actualTotal = actualTotal;
    }

    public List<Integer> getPositionIds() {
        return positionIds;
    }

    public void setPositionIds(List<Integer> positionIds) {
        this.positionIds = positionIds;
    }
}
