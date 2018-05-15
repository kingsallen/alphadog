package com.moseeker.position.service.position.carnoc.refresh.handler;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.iface.IChannelType;
import com.moseeker.position.service.position.base.refresh.handler.ResultHandlerAdapter;

public interface CarnocResultHandlerAdapter extends ResultHandlerAdapter<String>,IChannelType {

    default ChannelType getChannelType(){
        return ChannelType.CARNOC;
    }

}
