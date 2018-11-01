package com.moseeker.useraccounts.service.impl.vo;

import java.sql.Timestamp;

/**
 * Created by moseeker on 2018/11/1.
 */
public class ReferralProfileTab {

    private String positionTitle;
    private String sender;
    private Timestamp uploadTime;
    private String filePath;
    private int id;

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
