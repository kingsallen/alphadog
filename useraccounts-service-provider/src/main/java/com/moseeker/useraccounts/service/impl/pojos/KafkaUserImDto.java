package com.moseeker.useraccounts.service.impl.pojos;

/**
 * Created by huangxia on 2019/10/22.
 */
public class KafkaUserImDto {
    //protected String event;
    protected String time;
    //private int companyId ;
    private int userId ;
    private int employeeId ;
    private Integer positionId ;
    private Integer psc ;// parent share chain

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getPsc() {
        return psc;
    }

    public void setPsc(Integer psc) {
        this.psc = psc;
    }
}
