package com.moseeker.entity.pojos;

import java.util.List;

/**
 * Created by jack on 27/09/2017.
 */
public class ThirdPartyAccountExt {

    private int status;
    private String message;
    private Data data;

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

    public class City {
        private int amout;
        private String area;
        private String expiryDate;
        private int jobType;

        public int getAmout() {
            return amout;
        }

        public void setAmout(int amout) {
            this.amout = amout;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

        public int getJobType() {
            return jobType;
        }

        public void setJobType(int jobType) {
            this.jobType = jobType;
        }
    }

    public class Address {

        private String city;
        private String address;
        private int value;
        private String code;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
