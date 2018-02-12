package com.moseeker.position.service.position.job51.refresh.handler;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.refresh.handler.ResultHandlerAdapter;

public interface Job51ResultHandlerAdapter extends ResultHandlerAdapter<String> {
    @Override
    default ChannelType getChannelType(){
        return ChannelType.JOB51;
    }
}
