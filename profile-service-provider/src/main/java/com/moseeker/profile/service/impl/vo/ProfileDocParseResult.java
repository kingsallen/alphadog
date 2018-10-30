package com.moseeker.profile.service.impl.vo;

/**
 * 简历解析结果
 */
public class ProfileDocParseResult {
    
    private String file;
    private String name;
    private String mobile;
    private Integer userId;
    private Integer companyId;
    private boolean mobileeditable = true;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
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

    public boolean isMobileeditable() {
        return mobileeditable;
    }

    public void setMobileeditable(boolean mobileeditable) {
        this.mobileeditable = mobileeditable;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
