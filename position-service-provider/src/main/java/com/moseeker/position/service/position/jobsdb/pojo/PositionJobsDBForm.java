package com.moseeker.position.service.position.jobsdb.pojo;

import com.moseeker.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PositionJobsDBForm {
    private String summery1;
    private String summery2;
    private String summery3;
    private List<List<String>> occupation;
    private int addressId;
    private String addressName;
    private int salaryTop;
    private int salaryBottom;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    private int channel;


    public String getSummery1() {
        return summery1;
    }

    public void setSummery1(String summery1) {
        this.summery1 = summery1;
    }

    public String getSummery2() {
        return summery2;
    }

    public void setSummery2(String summery2) {
        this.summery2 = summery2;
    }

    public String getSummery3() {
        return summery3;
    }

    public void setSummery3(String summery3) {
        this.summery3 = summery3;
    }

    public List<List<String>> getOccupation() {
        return occupation;
    }

    public void setOccupation(List<List<String>> occupation) {
        this.occupation = occupation;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public int getSalaryTop() {
        return salaryTop;
    }

    public void setSalaryTop(int salaryTop) {
        this.salaryTop = salaryTop;
    }

    public int getSalaryBottom() {
        return salaryBottom;
    }

    public void setSalaryBottom(int salaryBottom) {
        this.salaryBottom = salaryBottom;
    }
}
