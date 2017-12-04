package com.moseeker.position.service.position.veryeast.refresh.handler;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.refresh.handler.AbstractRegionResultHandler;
import org.springframework.stereotype.Component;

@Component
public class VERegionResultHandler extends AbstractRegionResultHandler implements VEResultHandlerAdapter {

    @Override
    public ChannelType channelType() {
        return ChannelType.VERYEAST;
    }
}
