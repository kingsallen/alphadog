package com.moseeker.position.pojo;


import java.util.List;

public class KafkaBindDto {
    protected String event;
    protected String event_time;
    protected List<PositionStatus> data;
    protected Integer company_id;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public List<PositionStatus> getData() {
        return data;
    }

    public void setData(List<PositionStatus> data) {
        this.data = data;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }
}
