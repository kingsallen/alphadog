package com.moseeker.company.bean.email;

import com.moseeker.common.constants.Message;
import com.moseeker.company.bean.TalentWorkExpInfo;

import java.util.List;

/**
 * Created by zztaiwll on 18/4/24.
 */
public class TalentEmailForwardsResumeInfo {
    private String subject;
    private String companyAbbr;
    private String customText;
    private String officialAccountName;
    private String companyLogo;
    private String companyName;
    private String positionName;
    private String headimg;
    private String userName;
    private String industryName;
    private String genderName;
    private String cityName;
    private int age;
    private String marriage;
    private String resumeLink;
    private String weixinQrcode;
    private TalentBasicInfo basicInfo;
    private TalentIntentionInfo intention;
    private String introduction;
    private List<TalentWorkExpInfo> workExps;
    private List<TalentEducationInfo> eduExps;
    private List<TalentProjectExpsInfo> proExps;
    private List<TalentLanguagesInfo> languages;
    private List<String> skills;
    private List<String> credentials;
    private TalentWorksInfo works;
    private int userId;
    private List<Message> otherIdentity;
    private List<Message> otherSchool;
    private List<TalentOtherSchoolWorkInfo> otherSchoolWork;
    private List<TalentOtherInternshipInfo> otherInternship;
    private List<Message> otherCareer;
    private String otherIdPhoto;

    //新增两个字段的展示 by lcf
    private String mobile;//联系方式
    private String email;//电子邮件

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getResumeLink() {
        return resumeLink;
    }

    public void setResumeLink(String resumeLink) {
        this.resumeLink = resumeLink;
    }

    public TalentBasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(TalentBasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<TalentWorkExpInfo> getWorkExps() {
        return workExps;
    }

    public void setWorkExps(List<TalentWorkExpInfo> workExps) {
        this.workExps = workExps;
    }

    public List<TalentEducationInfo> getEduExps() {
        return eduExps;
    }

    public void setEduExps(List<TalentEducationInfo> eduExps) {
        this.eduExps = eduExps;
    }

    public List<TalentProjectExpsInfo> getProExps() {
        return proExps;
    }

    public void setProExps(List<TalentProjectExpsInfo> proExps) {
        this.proExps = proExps;
    }

    public List<TalentLanguagesInfo> getLanguages() {
        return languages;
    }

    public void setLanguages(List<TalentLanguagesInfo> languages) {
        this.languages = languages;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<String> credentials) {
        this.credentials = credentials;
    }

    public TalentWorksInfo getWorks() {
        return works;
    }

    public void setWorks(TalentWorksInfo works) {
        this.works = works;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public TalentIntentionInfo getIntention() {
        return intention;
    }

    public void setIntention(TalentIntentionInfo intention) {
        this.intention = intention;
    }

    public String getCompanyAbbr() {
        return companyAbbr;
    }

    public void setCompanyAbbr(String companyAbbr) {
        this.companyAbbr = companyAbbr;
    }

    public String getCustomText() {
        return customText;
    }

    public void setCustomText(String customText) {
        this.customText = customText;
    }

    public String getOfficialAccountName() {
        return officialAccountName;
    }

    public void setOfficialAccountName(String officialAccountName) {
        this.officialAccountName = officialAccountName;
    }

    public List<Message> getOtherIdentity() {
        return otherIdentity;
    }

    public void setOtherIdentity(List<Message> otherIdentity) {
        this.otherIdentity = otherIdentity;
    }

    public List<Message> getOtherSchool() {
        return otherSchool;
    }

    public void setOtherSchool(List<Message> otherSchool) {
        this.otherSchool = otherSchool;
    }

    public List<TalentOtherSchoolWorkInfo> getOtherSchoolWork() {
        return otherSchoolWork;
    }

    public void setOtherSchoolWork(List<TalentOtherSchoolWorkInfo> otherSchoolWork) {
        this.otherSchoolWork = otherSchoolWork;
    }

    public List<TalentOtherInternshipInfo> getOtherInternship() {
        return otherInternship;
    }

    public void setOtherInternship(List<TalentOtherInternshipInfo> otherInternship) {
        this.otherInternship = otherInternship;
    }

    public List<Message> getOtherCareer() {
        return otherCareer;
    }

    public void setOtherCareer(List<Message> otherCareer) {
        this.otherCareer = otherCareer;
    }

    public String getOtherIdPhoto() {
        return otherIdPhoto;
    }

    public void setOtherIdPhoto(String otherIdPhoto) {
        this.otherIdPhoto = otherIdPhoto;
    }

    public String getWeixinQrcode() {
        return weixinQrcode;
    }

    public void setWeixinQrcode(String weixinQrcode) {
        this.weixinQrcode = weixinQrcode;
    }
}
