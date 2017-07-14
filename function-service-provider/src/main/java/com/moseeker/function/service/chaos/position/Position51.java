package com.moseeker.function.service.chaos.position;

import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;

import java.io.Serializable;

/**
 * Created by zhangdi on 2017/6/22.
 */
public class Position51 implements Serializable {

    private String title;
    private String category_main_code;
    private String category_main;
    private String category_sub_code;
    private String category_sub;
    private String quantity;
    private String degree_code;
    private String degree;
    private String experience_code;
    private String experience;
    private String salary_low;
    private String salary_high;
    private String description;
    private String pub_place_code;
    private int position_id;
    private String work_place;
    private String email;
    private String stop_date;
    private int channel;
    private String type_code;
    private String job_id;
    private String pub_place_name;
    private String department;
    private int account_id;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory_main_code() {
        return category_main_code;
    }

    public void setCategory_main_code(String category_main_code) {
        this.category_main_code = category_main_code;
    }

    public String getCategory_main() {
        return category_main;
    }

    public void setCategory_main(String category_main) {
        this.category_main = category_main;
    }

    public String getCategory_sub_code() {
        return category_sub_code;
    }

    public void setCategory_sub_code(String category_sub_code) {
        this.category_sub_code = category_sub_code;
    }

    public String getCategory_sub() {
        return category_sub;
    }

    public void setCategory_sub(String category_sub) {
        this.category_sub = category_sub;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDegree_code() {
        return degree_code;
    }

    public void setDegree_code(String degree_code) {
        this.degree_code = degree_code;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getExperience_code() {
        return experience_code;
    }

    public void setExperience_code(String experience_code) {
        this.experience_code = experience_code;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSalary_low() {
        return salary_low;
    }

    public void setSalary_low(String salary_low) {
        this.salary_low = salary_low;
    }

    public String getSalary_high() {
        return salary_high;
    }

    public void setSalary_high(String salary_high) {
        this.salary_high = salary_high;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPub_place_code() {
        return pub_place_code;
    }

    public void setPub_place_code(String pub_place_code) {
        this.pub_place_code = pub_place_code;
    }

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public String getWork_place() {
        return work_place;
    }

    public void setWork_place(String work_place) {
        this.work_place = work_place;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStop_date() {
        return stop_date;
    }

    public void setStop_date(String stop_date) {
        this.stop_date = stop_date;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getType_code() {
        return type_code;
    }

    public void setType_code(String type_code) {
        this.type_code = type_code;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getPub_place_name() {
        return pub_place_name;
    }

    public void setPub_place_name(String pub_place_name) {
        this.pub_place_name = pub_place_name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    @Override
    public String toString() {
        return "Position51SyncPojo{" +
                "title='" + title + '\'' +
                ", category_main_code='" + category_main_code + '\'' +
                ", category_main='" + category_main + '\'' +
                ", category_sub_code='" + category_sub_code + '\'' +
                ", category_sub='" + category_sub + '\'' +
                ", quantity='" + quantity + '\'' +
                ", degree_code='" + degree_code + '\'' +
                ", degree='" + degree + '\'' +
                ", experience_code='" + experience_code + '\'' +
                ", experience='" + experience + '\'' +
                ", salary_low='" + salary_low + '\'' +
                ", salary_high='" + salary_high + '\'' +
                ", description='" + description + '\'' +
                ", pub_place_code='" + pub_place_code + '\'' +
                ", position_id=" + position_id +
                ", work_place='" + work_place + '\'' +
                ", email='" + email + '\'' +
                ", stop_date='" + stop_date + '\'' +
                ", channel=" + channel +
                ", type_code='" + type_code + '\'' +
                ", job_id='" + job_id + '\'' +
                ", pub_place_name='" + pub_place_name + '\'' +
                ", department='" + department + '\'' +
                ", account_id=" + account_id +
                '}';
    }

    public static Position51 copyFromSyncPosition(ThirdPartyPositionForSynchronization positionInfo) {

        if (positionInfo == null) return null;

        Position51 position51 = new Position51();
        position51.setTitle(positionInfo.getTitle());
        if (positionInfo.getOccupation() != null && positionInfo.getOccupation().size() > 1) {
            position51.setCategory_main_code(positionInfo.getOccupation().get(positionInfo.getOccupation().size() - 2));
            position51.setCategory_sub_code(positionInfo.getOccupation().get(positionInfo.getOccupation().size() - 1));
        }
        position51.setQuantity(String.valueOf(positionInfo.getQuantity()));
        position51.setDegree_code(positionInfo.getDegree_code());
        position51.setDegree(positionInfo.getDegree());
        position51.setExperience(positionInfo.getExperience());
        position51.setExperience_code(positionInfo.getExperience_code());
        position51.setSalary_low(String.valueOf(positionInfo.getSalary_bottom()));
        position51.setSalary_high(String.valueOf(positionInfo.getSalary_top()));
        position51.setDescription(positionInfo.getDescription());
        position51.setPub_place_code(positionInfo.getPub_place_code());
        position51.setPub_place_name(positionInfo.getPub_place_name());
        position51.setPosition_id(positionInfo.getPosition_id());
        position51.setWork_place(positionInfo.getWork_place());
        position51.setEmail(positionInfo.getEmail());
        position51.setStop_date(positionInfo.getStop_date());
        position51.setChannel(positionInfo.getChannel());
        position51.setType_code(positionInfo.getType_code());
        position51.setJob_id(positionInfo.getJob_id());
        position51.setDepartment(positionInfo.getDepartment());
        position51.setAccount_id(positionInfo.getAccount_id());
        return position51;
    }
}