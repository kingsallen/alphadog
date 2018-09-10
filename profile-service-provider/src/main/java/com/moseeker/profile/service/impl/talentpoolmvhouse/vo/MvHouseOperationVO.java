package com.moseeker.profile.service.impl.talentpoolmvhouse.vo;

/**
 * 简历搬家操作记录VO
 * @author cjm
 * @date 2018-07-25 10:07
 **/
public class MvHouseOperationVO {
    private Byte channel;
    private String startDate;
    private String endDate;
    private Integer crawlNum;
    private Byte status;
    private String statusDisplay;

    public byte getChannel() {
        return channel;
    }

    public void setChannel(byte channel) {
        this.channel = channel;
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

    @Override
    public String toString() {
        return "MvHouseOperationVO{" +
                "channel=" + channel +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", crawlNum=" + crawlNum +
                ", status=" + status +
                ", statusDisplay='" + statusDisplay + '\'' +
                '}';
    }
}
