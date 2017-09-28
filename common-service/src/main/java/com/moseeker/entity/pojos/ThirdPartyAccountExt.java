package com.moseeker.entity.pojos;

/**
 * Created by jack on 27/09/2017.
 */
public class ThirdPartyAccountExt {

    private int status;
    private String message;
    private Data data;

    public Data createData() {
        return new Data();
    }

    public City createCity() {
        return new City();
    }

    public Address createAddress() {
        return new Address();
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
