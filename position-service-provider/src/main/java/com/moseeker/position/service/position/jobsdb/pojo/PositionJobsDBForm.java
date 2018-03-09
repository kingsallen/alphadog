package com.moseeker.position.service.position.jobsdb.pojo;

import com.moseeker.common.util.StringUtils;
import com.moseeker.position.service.position.veryeast.pojo.PositionVeryEastForm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PositionJobsDBForm {
    private List<String> summery;
    private List<List<String>> occupation;
    private int addressId;
    private String addressName;
    private int childAddressId;
    private String childAddressName;
    private int salaryTop;
    private int salaryBottom;

    {
        summery=new ArrayList<>();
        summery.add("");
        summery.add("");
        summery.add("");

        occupation=new ArrayList<>();
        occupation.add(new ArrayList<>());
        occupation.add(new ArrayList<>());
        occupation.add(new ArrayList<>());
    }

    public String getSummery1(){
        return summery.get(0);
    }

    public String getSummery2(){
        return summery.get(1);
    }

    public String getSummery3(){
        return summery.get(2);
    }

    public void setSummery1(String summery1){
        if(StringUtils.isNotNullOrEmpty(summery1)) {
            summery.set(0,summery1);
        }
    }

    public void setSummery2(String summery2){
        if(StringUtils.isNotNullOrEmpty(summery2)) {
            summery.set(1,summery2);
        }
    }

    public void setSummery3(String summery3){
        if(StringUtils.isNotNullOrEmpty(summery3)) {
            summery.set(2,summery3);
        }
    }

    public List<String> getOccupation1() {
        return occupation.get(0);
    }

    public List<String> getOccupation2() {
        return occupation.get(1);
    }

    public List<String> getOccupation3() {
        return occupation.get(2);
    }

    public void setOccupation1(List<String> occupation1) {
        occupation.set(0,occupation1);
    }

    public void setOccupation2(List<String> occupation2) {
        occupation.set(1,occupation2);
    }

    public void setOccupation3(List<String> occupation3) {
        occupation.set(2,occupation3);
    }

    public List<String> getSummery() {
        return summery;
    }

    public void setSummery(List<String> summery) {
        this.summery = summery;
    }

    public List<List<String>> getOccupation() {
        return occupation;
    }

    public void setOccupation(List<List<String>> occupation) {
        this.occupation = occupation;
    }

    public int getChildAddressId() {
        return childAddressId;
    }

    public void setChildAddressId(int childAddressId) {
        this.childAddressId = childAddressId;
    }

    public String getChildAddressName() {
        return childAddressName;
    }

    public void setChildAddressName(String childAddressName) {
        this.childAddressName = childAddressName;
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

    private int channel;

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
