package com.moseeker.servicemanager.web.controller.profile.form;

import java.util.List;

public class UserProfileForm {

    private int appid;
    private String name;
    private String mobile;
    private Integer position;

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
