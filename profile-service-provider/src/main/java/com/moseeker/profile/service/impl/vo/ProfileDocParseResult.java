package com.moseeker.profile.service.impl.vo;

/**
 * 简历解析结果
 */
public class ProfileDocParseResult {
    
    private String file;
    private String name;
    private String mobile;
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
}
