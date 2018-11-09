package com.moseeker.entity.pojo.profile.info;

import com.moseeker.common.constants.Message;
import java.util.List;

/**
 * Created by moseeker on 2018/6/26.
 */
public class ProfileEmailInfo {
    private String companyLogo;
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
    private List<Message> otherIdentity;
    private List<Message> otherSchool;
    private List<SchoolWork> otherSchoolWork;
    private List<Internship> otherInternship;
    private List<Message> otherCareer;
    private String otherIdPhoto;

    //新增联系方式和电子邮箱字段
    private String mobile;
    private String email;

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

    public List<SchoolWork> getOtherSchoolWork() {
        return otherSchoolWork;
    }

    public void setOtherSchoolWork(List<SchoolWork> otherSchoolWork) {
        this.otherSchoolWork = otherSchoolWork;
    }

    public List<Internship> getOtherInternship() {
        return otherInternship;
    }

    public void setOtherInternship(List<Internship> otherInternship) {
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
}
