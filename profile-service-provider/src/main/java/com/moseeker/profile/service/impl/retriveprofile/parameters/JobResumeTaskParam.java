package com.moseeker.profile.service.impl.retriveprofile.parameters;

/**
 * jobResume任务的参数
 * Created by jack on 19/07/2017.
 */
public class JobResumeTaskParam {

    private int appId;          //申请编号
    private String jobResume;   //自定义字段信息

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getJobResume() {
        return jobResume;
    }

    public void setJobResume(String jobResume) {
        this.jobResume = jobResume;
    }
}
