package com.moseeker.profile.service.impl.vo;

/**
 * 简历解析结果
 */
public class ProfileDocParseResult {
    
    private String file;
    private String name;
    private String mobile;
    private boolean mobileeditable = true;
    private String email;
    private String pinyinName;
    private String pinyinSurname;

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
