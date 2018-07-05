package com.moseeker.entity.pojo.profile.info;

import com.moseeker.common.constants.Message;
import java.util.List;

/**
 * Created by moseeker on 2018/6/26.
 */
public class OtherInfo {
    private List<Message> identity;
    private List<Message> school;
    private List<SchoolWork> schoolWork;
    private List<Internship> internship;
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

    public List<SchoolWork> getSchoolWork() {
        return schoolWork;
    }

    public void setSchoolWork(List<SchoolWork> schoolWork) {
        this.schoolWork = schoolWork;
    }

    public List<Internship> getInternship() {
        return internship;
    }

    public void setInternship(List<Internship> internship) {
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
