package com.moseeker.entity.pojos;

import java.util.List;

/**
 * 第三方账号的扩展信息
 * Created by jack on 28/09/2017.
 */
public class Data {

    private int accountId;
    private int operationType;              //1 账号绑定  2. 职位发布  1出错需要告诉CS；2出错不需要告诉CS，但是需要告诉开发
    private List<String> companies;
    private List<String> departments;
    private List<City> cities;
    private List<Address> addresses;

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public List<String> getCompanies() {
        return companies;
    }

    public void setCompanies(List<String> companies) {
        this.companies = companies;
    }

    public List<String> getDepartments() {
        return departments;
    }

    public void setDepartments(List<String> departments) {
        this.departments = departments;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
