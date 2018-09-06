package com.moseeker.useraccounts.domain.pojo;

/**
 * @Author: jack
 * @Date: 2018/8/20
 */
public class ViewLeaderBoardVO {

    private int employeeId;
    private long viewTime;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public long getViewTime() {
        return viewTime;
    }

    public void setViewTime(long viewTime) {
        this.viewTime = viewTime;
    }
}
