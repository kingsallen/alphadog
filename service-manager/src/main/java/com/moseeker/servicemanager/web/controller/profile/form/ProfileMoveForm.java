package com.moseeker.servicemanager.web.controller.profile.form;

/**
 * 简历搬家form
 *
 * @author cjm
 * @date 2018-07-17 18:30
 **/
public class ProfileMoveForm {
    private Integer hrId;
    private Integer companyId;
    private Integer channel;
    private Integer crawlType;
    private String startDate;
    private String endDate;

    public Integer getHrId() {
        return hrId;
    }

    public void setHrId(Integer hrId) {
        this.hrId = hrId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getCrawlType() {
        return crawlType;
    }

    public void setCrawlType(Integer crawlType) {
        this.crawlType = crawlType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "ProfileMoveForm{" +
                "hrId=" + hrId +
                ", companyId=" + companyId +
                ", channel=" + channel +
                ", crawlType=" + crawlType +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
