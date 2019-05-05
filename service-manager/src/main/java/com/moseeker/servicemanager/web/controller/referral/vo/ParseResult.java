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
    private Long mobile;
    private String filename;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
