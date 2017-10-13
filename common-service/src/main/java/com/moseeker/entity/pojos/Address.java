package com.moseeker.entity.pojos;

/**
 * 上班地址
 * Created by jack on 28/09/2017.
 */
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
