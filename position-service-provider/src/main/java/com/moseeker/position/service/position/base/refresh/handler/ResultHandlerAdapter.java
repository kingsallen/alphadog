package com.moseeker.position.service.position.base.refresh.handler;

import com.moseeker.common.constants.ChannelType;

public interface ResultHandlerAdapter<T> extends ResultHandler<T>{
    ChannelType channelType();
}
