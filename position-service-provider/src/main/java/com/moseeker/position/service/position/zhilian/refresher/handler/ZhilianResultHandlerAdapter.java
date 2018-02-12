package com.moseeker.position.service.position.zhilian.refresher.handler;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.refresh.handler.ResultHandlerAdapter;

public interface ZhilianResultHandlerAdapter extends ResultHandlerAdapter<String> {
    @Override
    default ChannelType getChannelType(){
        return ChannelType.ZHILIAN;
    }
}
