/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 部门申请配置表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JobApplicationConf_20170612 implements Serializable {

    private static final long serialVersionUID = 1672617289;

    private Integer   id;
    private Integer   companyId;
    private Integer   appnotice;
    private Integer   appnoticeTpl;
    private String    appReply;
    private Integer   emailAppnotice;
    private String    emailAppreply;
    private Integer   smsAppnoticeId;
    private String    smsAppnoticePrefix;
    private String    smsAppnoticeSignature;
    private Integer   smsDelay;
    private Integer   forwardClickReward;
    private Integer   forwardClickRewardTpl;
    private Integer   forwardAppReward;
    private Integer   forwardAppRewardTpl;
    private Byte      emailResumeConf;
    private Timestamp createTime;
    private Timestamp updateTime;

    public JobApplicationConf_20170612() {}

    public JobApplicationConf_20170612(JobApplicationConf_20170612 value) {
        this.id = value.id;
        this.companyId = value.companyId;
        this.appnotice = value.appnotice;
        this.appnoticeTpl = value.appnoticeTpl;
        this.appReply = value.appReply;
        this.emailAppnotice = value.emailAppnotice;
        this.emailAppreply = value.emailAppreply;
        this.smsAppnoticeId = value.smsAppnoticeId;
        this.smsAppnoticePrefix = value.smsAppnoticePrefix;
        this.smsAppnoticeSignature = value.smsAppnoticeSignature;
        this.smsDelay = value.smsDelay;
        this.forwardClickReward = value.forwardClickReward;
        this.forwardClickRewardTpl = value.forwardClickRewardTpl;
        this.forwardAppReward = value.forwardAppReward;
        this.forwardAppRewardTpl = value.forwardAppRewardTpl;
        this.emailResumeConf = value.emailResumeConf;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public JobApplicationConf_20170612(
        Integer   id,
        Integer   companyId,
        Integer   appnotice,
        Integer   appnoticeTpl,
        String    appReply,
        Integer   emailAppnotice,
        String    emailAppreply,
        Integer   smsAppnoticeId,
        String    smsAppnoticePrefix,
        String    smsAppnoticeSignature,
        Integer   smsDelay,
        Integer   forwardClickReward,
        Integer   forwardClickRewardTpl,
        Integer   forwardAppReward,
        Integer   forwardAppRewardTpl,
        Byte      emailResumeConf,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.companyId = companyId;
        this.appnotice = appnotice;
        this.appnoticeTpl = appnoticeTpl;
        this.appReply = appReply;
        this.emailAppnotice = emailAppnotice;
        this.emailAppreply = emailAppreply;
        this.smsAppnoticeId = smsAppnoticeId;
        this.smsAppnoticePrefix = smsAppnoticePrefix;
        this.smsAppnoticeSignature = smsAppnoticeSignature;
        this.smsDelay = smsDelay;
        this.forwardClickReward = forwardClickReward;
        this.forwardClickRewardTpl = forwardClickRewardTpl;
        this.forwardAppReward = forwardAppReward;
        this.forwardAppRewardTpl = forwardAppRewardTpl;
        this.emailResumeConf = emailResumeConf;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getAppnotice() {
        return this.appnotice;
    }

    public void setAppnotice(Integer appnotice) {
        this.appnotice = appnotice;
    }

    public Integer getAppnoticeTpl() {
        return this.appnoticeTpl;
    }

    public void setAppnoticeTpl(Integer appnoticeTpl) {
        this.appnoticeTpl = appnoticeTpl;
    }

    public String getAppReply() {
        return this.appReply;
    }

    public void setAppReply(String appReply) {
        this.appReply = appReply;
    }

    public Integer getEmailAppnotice() {
        return this.emailAppnotice;
    }

    public void setEmailAppnotice(Integer emailAppnotice) {
        this.emailAppnotice = emailAppnotice;
    }

    public String getEmailAppreply() {
        return this.emailAppreply;
    }

    public void setEmailAppreply(String emailAppreply) {
        this.emailAppreply = emailAppreply;
    }

    public Integer getSmsAppnoticeId() {
        return this.smsAppnoticeId;
    }

    public void setSmsAppnoticeId(Integer smsAppnoticeId) {
        this.smsAppnoticeId = smsAppnoticeId;
    }

    public String getSmsAppnoticePrefix() {
        return this.smsAppnoticePrefix;
    }

    public void setSmsAppnoticePrefix(String smsAppnoticePrefix) {
        this.smsAppnoticePrefix = smsAppnoticePrefix;
    }

    public String getSmsAppnoticeSignature() {
        return this.smsAppnoticeSignature;
    }

    public void setSmsAppnoticeSignature(String smsAppnoticeSignature) {
        this.smsAppnoticeSignature = smsAppnoticeSignature;
    }

    public Integer getSmsDelay() {
        return this.smsDelay;
    }

    public void setSmsDelay(Integer smsDelay) {
        this.smsDelay = smsDelay;
    }

    public Integer getForwardClickReward() {
        return this.forwardClickReward;
    }

    public void setForwardClickReward(Integer forwardClickReward) {
        this.forwardClickReward = forwardClickReward;
    }

    public Integer getForwardClickRewardTpl() {
        return this.forwardClickRewardTpl;
    }

    public void setForwardClickRewardTpl(Integer forwardClickRewardTpl) {
        this.forwardClickRewardTpl = forwardClickRewardTpl;
    }

    public Integer getForwardAppReward() {
        return this.forwardAppReward;
    }

    public void setForwardAppReward(Integer forwardAppReward) {
        this.forwardAppReward = forwardAppReward;
    }

    public Integer getForwardAppRewardTpl() {
        return this.forwardAppRewardTpl;
    }

    public void setForwardAppRewardTpl(Integer forwardAppRewardTpl) {
        this.forwardAppRewardTpl = forwardAppRewardTpl;
    }

    public Byte getEmailResumeConf() {
        return this.emailResumeConf;
    }

    public void setEmailResumeConf(Byte emailResumeConf) {
        this.emailResumeConf = emailResumeConf;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("JobApplicationConf_20170612 (");

        sb.append(id);
        sb.append(", ").append(companyId);
        sb.append(", ").append(appnotice);
        sb.append(", ").append(appnoticeTpl);
        sb.append(", ").append(appReply);
        sb.append(", ").append(emailAppnotice);
        sb.append(", ").append(emailAppreply);
        sb.append(", ").append(smsAppnoticeId);
        sb.append(", ").append(smsAppnoticePrefix);
        sb.append(", ").append(smsAppnoticeSignature);
        sb.append(", ").append(smsDelay);
        sb.append(", ").append(forwardClickReward);
        sb.append(", ").append(forwardClickRewardTpl);
        sb.append(", ").append(forwardAppReward);
        sb.append(", ").append(forwardAppRewardTpl);
        sb.append(", ").append(emailResumeConf);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
