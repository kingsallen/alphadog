package com.moseeker.position.service.position.liepin.refresh.handler;


import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.refresh.handler.ResultHandlerAdapter;

public interface LiepinResultHandlerAdapter extends ResultHandlerAdapter<String> {
    @Override
    default ChannelType getChannelType(){
        return ChannelType.LIEPIN;
    }
}
