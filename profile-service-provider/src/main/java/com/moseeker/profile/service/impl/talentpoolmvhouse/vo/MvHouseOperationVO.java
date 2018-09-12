package com.moseeker.profile.service.impl.talentpoolmvhouse.vo;

/**
 * 简历搬家操作记录VO
 * @author cjm
 * @date 2018-07-25 10:07
 **/
public class MvHouseOperationVO {
    private Byte channel;
    private String start_date;
    private String end_date;
    private Integer crawl_num;
    private Byte status;
    private String status_display;
    private String create_time;

    public Byte getChannel() {
        return channel;
    }

    public void setChannel(Byte channel) {
        this.channel = channel;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public Integer getCrawl_num() {
        return crawl_num;
    }

    public void setCrawl_num(Integer crawl_num) {
        this.crawl_num = crawl_num;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getStatus_display() {
        return status_display;
    }

    public void setStatus_display(String status_display) {
        this.status_display = status_display;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "MvHouseOperationVO{" +
                "channel=" + channel +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", crawl_num=" + crawl_num +
                ", status=" + status +
                ", status_display='" + status_display + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}
