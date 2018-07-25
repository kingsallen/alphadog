package com.moseeker.profile.service.impl.talentpoolmvhouse.vo;

/**
 * 简历搬家操作记录VO
 * @author cjm
 * @date 2018-07-25 10:07
 **/
public class MvHouseOperationVO {
    private Integer id;
    private Integer hrId;
    private Integer companyId;
    private Byte channel;
    private Byte crawlType;
    private String startDate;
    private String endDate;
    private Integer crawlNum;
    private Byte status;
    private String statusDisplay;
    private Integer currentEmailNum;
    private Integer totalEmailNum;
    private String createTime;
    private String updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHrId() {
        return hrId;
    }

    public void setHrId(int hrId) {
        this.hrId = hrId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public byte getChannel() {
        return channel;
    }

    public void setChannel(byte channel) {
        this.channel = channel;
    }

    public byte getCrawlType() {
        return crawlType;
    }

    public void setCrawlType(byte crawlType) {
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

    public int getCrawlNum() {
        return crawlNum;
    }

    public void setCrawlNum(int crawlNum) {
        this.crawlNum = crawlNum;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getStatusDisplay() {
        return statusDisplay;
    }

    public void setStatusDisplay(String statusDisplay) {
        this.statusDisplay = statusDisplay;
    }

    public int getCurrentEmailNum() {
        return currentEmailNum;
    }

    public void setCurrentEmailNum(int currentEmailNum) {
        this.currentEmailNum = currentEmailNum;
    }

    public int getTotalEmailNum() {
        return totalEmailNum;
    }

    public void setTotalEmailNum(int totalEmailNum) {
        this.totalEmailNum = totalEmailNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
