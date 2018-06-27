package com.moseeker.company.bean.email;

import com.moseeker.common.constants.Message;

import java.util.List;

/**
 * Created by zztaiwll on 18/6/26.
 */
public class TalentOtherInfo {
    private List<Message> identity;
    private List<Message> school;
    private List<TalentOtherSchoolWorkInfo> schoolWork;
    private List<TalentOtherInternshipInfo> internship;
    private List<Message> career;
    private String idPhoto;

    public List<Message> getIdentity() {
        return identity;
    }

    public void setIdentity(List<Message> identity) {
        this.identity = identity;
    }

    public List<Message> getSchool() {
        return school;
    }

    public void setSchool(List<Message> school) {
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

    public List<Message> getCareer() {
        return career;
    }

    public void setCareer(List<Message> career) {
        this.career = career;
    }

    public String getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }
}
