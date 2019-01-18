package com.moseeker.entity.pojos;

import java.util.List;

/**
 * Created by moseeker on 2019/1/18.
 */
public class KafkaNetworkResource {

    private String event;
    private String event_time;
    private int employee_id;
    private List<Integer> user_id;

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

    public List<Integer> getUser_id() {
        return user_id;
    }

    public void setUser_id(List<Integer> user_id) {
        this.user_id = user_id;
    }
}
