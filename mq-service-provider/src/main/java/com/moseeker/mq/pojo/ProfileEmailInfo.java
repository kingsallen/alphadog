package com.moseeker.mq.pojo;

import java.util.List;

/**
 * Created by moseeker on 2018/6/26.
 */
public class ProfileEmailInfo {
    private String comapnyLogo;
    private String companyName;
    private String positionName;
    private String headimg;
    private String userName;
    private String nationalityName;
    private String genderName;
    private String cityName;
    private String age;
    private String marriage;
    private String resumeLink;
    private String introduction;
    private BasicInfo basicInfo;
    private Intention intention;
    private List<WorkExps> workExps;
    private List<EduExps> eduExps;
    private List<ProExps> proExps;
    private List<String> skills;
    private List<Languages> languages;
    private List<String> credentials;
    private Works works;
    private OtherInfo other;

    public String getComapnyLogo() {
        return comapnyLogo;
    }

    public void setComapnyLogo(String comapnyLogo) {
        this.comapnyLogo = comapnyLogo;
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

    public String getNationalityName() {
        return nationalityName;
    }

    public void setNationalityName(String nationalityName) {
        this.nationalityName = nationalityName;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public Intention getIntention() {
        return intention;
    }

    public void setIntention(Intention intention) {
        this.intention = intention;
    }

    public List<WorkExps> getWorkExps() {
        return workExps;
    }

    public void setWorkExps(List<WorkExps> workExps) {
        this.workExps = workExps;
    }

    public List<EduExps> getEduExps() {
        return eduExps;
    }

    public void setEduExps(List<EduExps> eduExps) {
        this.eduExps = eduExps;
    }

    public List<ProExps> getProExps() {
        return proExps;
    }

    public void setProExps(List<ProExps> proExps) {
        this.proExps = proExps;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<Languages> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Languages> languages) {
        this.languages = languages;
    }

    public List<String> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<String> credentials) {
        this.credentials = credentials;
    }

    public Works getWorks() {
        return works;
    }

    public void setWorks(Works works) {
        this.works = works;
    }

    public OtherInfo getOther() {
        return other;
    }

    public void setOther(OtherInfo other) {
        this.other = other;
    }
}
