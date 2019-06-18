package com.moseeker.servicemanager.web.controller.useraccounts.vo;


public class McdUserType {
    int userId;
    String email;
    String cname;
    String mobile;
    String customFiled;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCustomFiled() {
        return customFiled;
    }

    public void setCustomFiled(String customFiled) {
        this.customFiled = customFiled;
    }



}
