package com.moseeker.servicemanager.web.controller.referral.vo;

import java.util.List;

/**
 * @author cjm
 * @date 2018-11-02 9:40
 **/
public class ReferralInfoCache {

    private String mobile;
    private String name;
    private List<ReferralPositionIdTitle> positions;
    private String title;
    private List<String> referralReasons;
    private String userId;
    private String fileName;

    @Override
    public String toString() {
        return "ReferralInfoCache{" +
                "mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", positions=" + positions +
                ", title='" + title + '\'' +
                ", referralReasons=" + referralReasons +
                ", userId='" + userId + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }

    public List<ReferralPositionIdTitle> getPositions() {
        return positions;
    }

    public void setPositions(List<ReferralPositionIdTitle> positions) {
        this.positions = positions;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getReferralReasons() {
        return referralReasons;
    }

    public void setReferralReasons(List<String> referralReasons) {
        this.referralReasons = referralReasons;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public ReferralInfoCache() {
    }

}
