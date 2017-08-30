
package com.moseeker.profile.pojo.profile;

import java.util.List;


public class ProfileObj {

    private List<Projectexps> projectexps;
    private List<Education> educations;
    private List<Skill> skills;
    private List<Workexps> workexps;
    private List<Languages> languages;
    private List<Intentions> intentions;
    private User user;
    private Basic basic;
    private List<Credentials> credentials;

    public void setProjectexps(List<Projectexps> projectexps) {
        this.projectexps = projectexps;
    }

    public List<Projectexps> getProjectexps() {
        return projectexps;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setWorkexps(List<Workexps> workexps) {
        this.workexps = workexps;
    }

    public List<Workexps> getWorkexps() {
        return workexps;
    }

    public void setLanguages(List<Languages> languages) {
        this.languages = languages;
    }

    public List<Languages> getLanguages() {
        return languages;
    }

    public void setIntentions(List<Intentions> intentions) {
        this.intentions = intentions;
    }

    public List<Intentions> getIntentions() {
        return intentions;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public Basic getBasic() {
        return basic;
    }

    public void setCredentials(List<Credentials> credentials) {
        this.credentials = credentials;
    }

    public List<Credentials> getCredentials() {
        return credentials;
    }

}