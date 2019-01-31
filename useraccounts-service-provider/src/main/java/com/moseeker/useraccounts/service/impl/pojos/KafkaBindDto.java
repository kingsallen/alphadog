package com.moseeker.useraccounts.service.impl.pojos;


import java.util.List;

public class KafkaBindDto {
    protected String event;
    protected String event_time;
    protected List<Integer> user_id;
    protected Integer company_id;
    protected Integer position_id;

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

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public Integer getPosition_id() {
        return position_id;
    }

    public void setPosition_id(Integer position_id) {
        this.position_id = position_id;
    }

    public List<Integer> getUser_id() {
        return user_id;
    }

    public void setUser_id(List<Integer> user_id) {
        this.user_id = user_id;
    }
}
