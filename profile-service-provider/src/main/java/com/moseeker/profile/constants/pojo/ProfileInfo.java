package com.moseeker.profile.constants.pojo;

import com.moseeker.common.constants.Message;
import java.util.List;

/**
 * Created by moseeker on 2018/6/26.
 */
public class ProfileInfo {
    private List<Message> other_identity;
    private List<Message> other_school;
    private List<SchoolWork> other_schoolWork;
    private List<Internship> other_internship;
    private List<Message> other_career;
    private String other_idPhoto;

    public List<Message> getOther_identity() {
        return other_identity;
    }

    public void setOther_identity(List<Message> other_identity) {
        this.other_identity = other_identity;
    }

    public List<Message> getOther_school() {
        return other_school;
    }

    public void setOther_school(List<Message> other_school) {
        this.other_school = other_school;
    }

    public List<SchoolWork> getOther_schoolWork() {
        return other_schoolWork;
    }

    public void setOther_schoolWork(List<SchoolWork> other_schoolWork) {
        this.other_schoolWork = other_schoolWork;
    }

    public List<Internship> getOther_internship() {
        return other_internship;
    }

    public void setOther_internship(List<Internship> other_internship) {
        this.other_internship = other_internship;
    }

    public List<Message> getOther_career() {
        return other_career;
    }

    public void setOther_career(List<Message> other_career) {
        this.other_career = other_career;
    }

    public String getOther_idPhoto() {
        return other_idPhoto;
    }

    public void setOther_idPhoto(String other_idPhoto) {
        this.other_idPhoto = other_idPhoto;
    }
}
