package com.moseeker.position.service.position.job51.pojo;

import com.moseeker.baseorm.db.thirdpartydb.tables.pojos.ThirdpartyAccountCompanyAddress;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangdi on 2017/6/22.
 */
public class Position51 implements Serializable {

    private String title;
    private String company;
    private String department;
    private String quantity;
    private List<List<String>> cities;
    private ThirdpartyAccountCompanyAddress address;
    private String degree;
    private List<String> occupation;
    private String experience;
    private String type_code;
    private String salary_low;
    private String salary_high;
    private String description;
    private String email;

    public ThirdpartyAccountCompanyAddress getAddress() {
        return address;
    }

    public void setAddress(ThirdpartyAccountCompanyAddress address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public List<List<String>> getCities() {
        return cities;
    }

    public void setCities(List<List<String>> cities) {
        this.cities = cities;
    }

    public List<String> getOccupation() {
        return occupation;
    }

    public void setOccupation(List<String> occupation) {
        this.occupation = occupation;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getType_code() {
        return type_code;
    }

    public void setType_code(String type_code) {
        this.type_code = type_code;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Position51{" +
                "title='" + title + '\'' +
                ", company='" + company + '\'' +
                ", department='" + department + '\'' +
                ", quantity=" + quantity +
                ", cities=" + cities +
                ", address='" + address + '\'' +
                ", degree=" + degree +
                ", occupation=" + occupation +
                ", experience='" + experience + '\'' +
                ", type_code=" + type_code +
                ", salary_low=" + salary_low +
                ", salary_high=" + salary_high +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
