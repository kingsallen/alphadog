package com.moseeker.company.bean.email;

import com.moseeker.company.bean.TalentWorkExpInfo;
import com.moseeker.company.bean.email.TalentEducationInfo;

import java.util.List;

/**
 * Created by zztaiwll on 18/4/24.
 */
public class TalentEmailForwardsResumeInfo {
    private String companyAbbr;
    private String companyLogo;
    private String coworkerName;
    private String customText;
    private String heading;
    private String userName;
    private String genderName;
    private String cityName;
    private String degreeName;
    private List<TalentEducationInfo> educationList;
    private List<TalentWorkExpInfo> workexps;
    private String weixinQrcode;
    private String officialAccountName;
    private String email;
    private int userId;
    private int birth;
    private String positionName;
    private String companyName;
    private String rcpt;
    private String hrName;
    private String profileFullUrl;

    public String getCompanyAbbr() {
        return companyAbbr;
    }

    public void setCompanyAbbr(String companyAbbr) {
        this.companyAbbr = companyAbbr;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCoworkerName() {
        return coworkerName;
    }

    public void setCoworkerName(String coworkerName) {
        this.coworkerName = coworkerName;
    }

    public String getCustomText() {
        return customText;
    }

    public void setCustomText(String customText) {
        this.customText = customText;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    public List<TalentEducationInfo> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<TalentEducationInfo> educationList) {
        this.educationList = educationList;
    }

    public List<TalentWorkExpInfo> getWorkexps() {
        return workexps;
    }

    public void setWorkexps(List<TalentWorkExpInfo> workexps) {
        this.workexps = workexps;
    }

    public String getWeixinQrcode() {
        return weixinQrcode;
    }

    public void setWeixinQrcode(String weixinQrcode) {
        this.weixinQrcode = weixinQrcode;
    }

    public String getOfficialAccountName() {
        return officialAccountName;
    }

    public void setOfficialAccountName(String officialAccountName) {
        this.officialAccountName = officialAccountName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBirth() {
        return birth;
    }

    public void setBirth(int birth) {
        this.birth = birth;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRcpt() {
        return rcpt;
    }

    public void setRcpt(String rcpt) {
        this.rcpt = rcpt;
    }

    public String getHrName() {
        return hrName;
    }

    public void setHrName(String hrName) {
        this.hrName = hrName;
    }

    public String getProfileFullUrl() {
        return profileFullUrl;
    }

    public void setProfileFullUrl(String profileFullUrl) {
        this.profileFullUrl = profileFullUrl;
    }
}
