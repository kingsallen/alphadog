package com.moseeker.position.service.position.job1001.refresh.handler;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.refresh.handler.ResultHandlerAdapter;

public interface YLResultHandlerAdapter extends ResultHandlerAdapter<String> {
    @Override
    default ChannelType channelType(){
        return ChannelType.JOB1001;
    }
}
