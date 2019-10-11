package com.moseeker.servicemanager.web.controller.referral.vo;

/**
 * @ClassName ParseResult
 * @Description TODO
 * @Author jack
 * @Date 2019/5/5 9:57 PM
 * @Version 1.0
 */
public class ParseResult {
    private String name;
    private String mobile;
    private String filename;
    private String email ;
    public String pinyinName; // optional
    public String pinyinSurname; // optional

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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPinyinName() {
        return pinyinName;
    }

    public void setPinyinName(String pinyinName) {
        this.pinyinName = pinyinName;
    }

    public String getPinyinSurname() {
        return pinyinSurname;
    }

    public void setPinyinSurname(String pinyinSurname) {
        this.pinyinSurname = pinyinSurname;
    }
}
