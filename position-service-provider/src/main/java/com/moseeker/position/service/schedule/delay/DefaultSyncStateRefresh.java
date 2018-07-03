package com.moseeker.position.service.schedule.delay;

import com.moseeker.common.constants.ChannelType;

/**
 * @author cjm
 * @date 2018-06-29 15:35
 **/
public class DefaultSyncStateRefresh extends AbstractSyncStateRefresh {

    public DefaultSyncStateRefresh(int thirdPartyPositionId) {
        super(thirdPartyPositionId);
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.NONE;
    }
}
