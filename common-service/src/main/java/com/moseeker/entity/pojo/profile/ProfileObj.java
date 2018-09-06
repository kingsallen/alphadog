
package com.moseeker.entity.pojo.profile;

import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.entity.pojo.resume.ResumeParseException;

import java.util.List;

/**
 * todo 可以直接转成使用表的pojo替代自定义的各个部分的数据对象
 */
public class ProfileObj {

    private List<Projectexps> projectexps;
    private List<Education> educations;
    private List<Skill> skills;
    private List<Workexps> workexps;
    private List<Language> languages;
    private List<Intentions> intentions;
    private User user;
    private Basic basic;
    private List<Credential> credentials;
    private List<ResumeParseException> exceptions;

    private ResumeObj resumeObj;

    public List<ResumeParseException> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<ResumeParseException> exceptions) {
        this.exceptions = exceptions;
    }

    public ResumeObj getResumeObj() {
        return resumeObj;
    }

    public void setResumeObj(ResumeObj resumeObj) {
        this.resumeObj = resumeObj;
    }

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

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public List<Language> getLanguages() {
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

    public void setCredentials(List<Credential> credentials) {
        this.credentials = credentials;
    }

    public List<Credential> getCredentials() {
        return credentials;
    }

}