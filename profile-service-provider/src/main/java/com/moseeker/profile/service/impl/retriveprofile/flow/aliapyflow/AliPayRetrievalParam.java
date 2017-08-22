package com.moseeker.profile.service.impl.retriveprofile.flow.aliapyflow;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.service.impl.retriveprofile.ExecutorParam;

import java.util.Map;

/**
 * Ali 简历回收全局参数
 * Created by jack on 20/07/2017.
 */
public class AliPayRetrievalParam extends ExecutorParam {

    private String jobResume;

    @Override
    public void parseParameter(Map<String, Object> parameter, ChannelType channelType) throws CommonException {
        super.parseParameter(parameter, channelType);
        if (this.getOriginParam().get("jobResumeOther") != null) {
            jobResume = JSON.toJSONString(getOriginParam().get("jobResumeOther"));
            setJobResume(jobResume);
        }
    }

    public String getJobResume() {
        return jobResume;
    }

    public void setJobResume(String jobResume) {
        this.jobResume = jobResume;
    }

}
