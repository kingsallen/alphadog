package com.moseeker.servicemanager.web.controller.referral.form;

/**
 * @Author: jack
 * @Date: 2018/9/17
 */
public class ProfileBinaryParserForm {

    private Integer appid;
    private String fileData;
    private String fileName;
    private String fileOriginName;
    private String fileAbsoluteName;
    private Integer employeeId;

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileOriginName() {
        return fileOriginName;
    }

    public void setFileOriginName(String fileOriginName) {
        this.fileOriginName = fileOriginName;
    }

    public String getFileAbsoluteName() {
        return fileAbsoluteName;
    }

    public void setFileAbsoluteName(String fileAbsoluteName) {
        this.fileAbsoluteName = fileAbsoluteName;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
}
