package com.moseeker.profile.service.impl.retriveprofile;

import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.profile.service.impl.serviceutils.ProfilePojo;

/**
 * 简历回收参数
 * Created by jack on 10/07/2017.
 */
public class RetriveParam {

    private JobPositionRecord positionRecord;           //职位信息
    private ChannelType channelType;                    //渠道
    private ProfilePojo profilePojo;                    //简历数据
    private UserUserRecord userUserRecord;              //用户信息
    private int applicationId;                          //申请编号
    private int jobResumeId;                            //该职位的自定义信息编号
    //private
    private String jobResume;                           //自定义内容

    public UserUserRecord getUserUserRecord() {
        return userUserRecord;
    }

    public void setUserUserRecord(UserUserRecord userUserRecord) {
        this.userUserRecord = userUserRecord;
    }

    public JobPositionRecord getPositionRecord() {
        return positionRecord;
    }

    public void setPositionRecord(JobPositionRecord positionRecord) {
        this.positionRecord = positionRecord;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public void setChannelType(ChannelType channelType) {
        this.channelType = channelType;
    }

    public ProfilePojo getProfilePojo() {
        return profilePojo;
    }

    public void setProfilePojo(ProfilePojo profilePojo) {
        this.profilePojo = profilePojo;
    }

    public String getJobResume() {
        return jobResume;
    }

    public void setJobResume(String jobResume) {
        this.jobResume = jobResume;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getJobResumeId() {
        return jobResumeId;
    }

    public void setJobResumeId(int jobResumeId) {
        this.jobResumeId = jobResumeId;
    }
}
