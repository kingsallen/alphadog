package com.moseeker.position.service.position.job1001.pojo;

import java.util.List;

public class PositionJob1001Form {
    private int channel;
    private String companyId;
    private String companyName;
    private List<String> occupation;
    private int salaryTop;
    private int salaryBottom;
    private String addressName;
    private int addressId;
    private String subsite;

    public String getSubsite() {
        return subsite;
    }

    public void setSubsite(String subsite) {
        this.subsite = subsite;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

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

    public List<String> getOccupation() {
        return occupation;
    }

    public void setOccupation(List<String> occupation) {
        this.occupation = occupation;
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
