package com.moseeker.position.service.position.jobsdb.pojo;

import com.moseeker.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PositionJobsDBForm {
    private List<String> summary;
    private List<List<String>> occupation;
    private List<String> address;
    private int salaryTop;
    private int salaryBottom;
    private int channel;
    private int careerLevel;
    private int educationLevel;
    private String keyword;
    private int salaryType;



    public PositionJobsDBForm(){
        summary =new ArrayList<>();
        summary.add("");
        summary.add("");
        summary.add("");

        occupation=new ArrayList<>();
        occupation.add(new ArrayList<>());
        occupation.add(new ArrayList<>());
        occupation.add(new ArrayList<>());
    }

    public int getCareerLevel() {
        return careerLevel;
    }

    public void setCareerLevel(int careerLevel) {
        this.careerLevel = careerLevel;
    }

    public int getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(int educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(int salaryType) {
        this.salaryType = salaryType;
    }

    public String getSummery1(){
        return summary.get(0);
    }

    public String getSummery2(){
        return summary.get(1);
    }

    public String getSummery3(){
        return summary.get(2);
    }

    public void setSummery1(String summery1){
        if(StringUtils.isNotNullOrEmpty(summery1)) {
            summary.set(0,summery1);
        }
    }

    public void setSummery2(String summery2){
        if(StringUtils.isNotNullOrEmpty(summery2)) {
            summary.set(1,summery2);
        }
    }

    public void setSummery3(String summery3){
        if(StringUtils.isNotNullOrEmpty(summery3)) {
            summary.set(2,summery3);
        }
    }

    public List<String> getOccupation1() {
        return StringUtils.isEmptyList(occupation) ? null:occupation.get(0);
    }

    public List<String> getOccupation2() {
        return StringUtils.isEmptyList(occupation) || occupation.size() <2 ? new ArrayList<>():occupation.get(1);
    }

    public List<String> getOccupation3() {
        return StringUtils.isEmptyList(occupation) || occupation.size() <3 ? new ArrayList<>():occupation.get(2);
    }

    public void setOccupation1(List<String> occupation1) {
        if(StringUtils.isEmptyList(occupation1)){
            return;
        }
        occupation.set(0,occupation1);
    }

    public void setOccupation2(List<String> occupation2) {
        if(StringUtils.isEmptyList(occupation2)){
            return;
        }
        occupation.set(1,occupation2);
    }

    public void setOccupation3(List<String> occupation3) {
        if(StringUtils.isEmptyList(occupation3)){
            return;
        }
        occupation.set(2,occupation3);
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public List<String> getSummary() {
        return summary;
    }

    public void setSummary(List<String> summary) {
        this.summary = summary;
    }

    public List<List<String>> getOccupation() {
        return occupation;
    }

    public void setOccupation(List<List<String>> occupation) {
        this.occupation = occupation;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
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
