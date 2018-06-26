package com.moseeker.company.bean.email;

import java.util.List;

/**
 * Created by zztaiwll on 18/6/26.
 */
public class TalentOtherInfo {
    private List<TalentOtherIndentityInfo> identity;
    private List<TalentOtherSchoolInfo> school;
    private List<TalentOtherSchoolWorkInfo> schoolWork;
    private List<TalentOtherInternshipInfo> internship;
    private List<TalentOtherCareerInfo> career;
    private String idPhoto;

    public List<TalentOtherIndentityInfo> getIdentity() {
        return identity;
    }

    public void setIdentity(List<TalentOtherIndentityInfo> identity) {
        this.identity = identity;
    }

    public List<TalentOtherSchoolInfo> getSchool() {
        return school;
    }

    public void setSchool(List<TalentOtherSchoolInfo> school) {
        this.school = school;
    }

    public List<TalentOtherSchoolWorkInfo> getSchoolWork() {
        return schoolWork;
    }

    public void setSchoolWork(List<TalentOtherSchoolWorkInfo> schoolWork) {
        this.schoolWork = schoolWork;
    }

    public List<TalentOtherInternshipInfo> getInternship() {
        return internship;
    }

    public void setInternship(List<TalentOtherInternshipInfo> internship) {
        this.internship = internship;
    }

    public List<TalentOtherCareerInfo> getCareer() {
        return career;
    }

    public void setCareer(List<TalentOtherCareerInfo> career) {
        this.career = career;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }
}
