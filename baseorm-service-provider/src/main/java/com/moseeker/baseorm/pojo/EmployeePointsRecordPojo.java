package com.moseeker.baseorm.pojo;


import java.time.LocalDateTime;

/**
 * Created by YYF
 *
 * Date: 2017/8/11
 *
 * Project_name :alphadog
 */
public class EmployeePointsRecordPojo {

    private String timespan;
    private Integer award;
    private LocalDateTime last_update_time;


    public void setTimespan(String timespan) {
        this.timespan = timespan;
    }

    public Integer getAward() {
        return award;
    }

    public void setAward(Integer award) {
        this.award = award;
    }

    public String getTimespan() {
        return timespan;
    }

    public LocalDateTime getLast_update_time() {
        return last_update_time;
    }

    public void setLast_update_time(LocalDateTime last_update_time) {
        this.last_update_time = last_update_time;
    }
}
