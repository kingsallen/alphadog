package com.moseeker.position.service.schedule.delay.refresh;

import com.moseeker.common.constants.ChannelType;
import org.springframework.stereotype.Component;

/**
 * @author cjm
 * @date 2018-06-29 15:35
 **/
@Component
public class DefaultSyncStateRefresh extends AbstractSyncStateRefresh {

    @Override
    public ChannelType getChannelType() {
        return ChannelType.NONE;
    }
}
