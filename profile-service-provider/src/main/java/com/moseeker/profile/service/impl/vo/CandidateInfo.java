package com.moseeker.profile.service.impl.vo;

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 候选人关键信息
 * @Author: jack
 * @Date: 2018/9/6
 */
public class CandidateInfo {

    private int appid;
    private int position;
    private String name;
    private byte gender;
    private String mobile;
    private String email;
    private String company;
    private String job;
    private List<String> reasons;

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = StringUtils.deleteWhitespace(name);
        }
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        if (mobile != null) {
            this.mobile = StringUtils.deleteWhitespace(mobile);
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null) {
            this.email = StringUtils.deleteWhitespace(email);
        }
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        if (company != null) {
            this.company = StringUtils.deleteWhitespace(company);
        }
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        if (job != null) {
            this.job = StringUtils.deleteWhitespace(job);
        }
    }

    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(List<String> reasons) {
        if (reasons != null && reasons.size() > 0) {
            this.reasons = reasons
                    .stream()
                    .filter(reason -> StringUtils.isNotBlank(reason))
                    .map(s -> StringUtils.deleteWhitespace(s))
                    .collect(Collectors.toList());
        }
        this.reasons = reasons;
    }
}
