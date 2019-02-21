package com.moseeker.entity.pojos;

import java.util.List;

/**
 * Created by moseeker on 2019/1/18.
 */
public class KafkaNetworkResource {

    private String event;
    private String event_time;
    private int employee_id;
    private int company_id;
    private List<UserPositionInfo> data;

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

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public List<UserPositionInfo> getData() {
        return data;
    }

    public void setData(List<UserPositionInfo> data) {
        this.data = data;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }


}
