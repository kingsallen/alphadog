package com.moseeker.position.service.position.base.sync;

import com.moseeker.common.iface.IChannelType;

public interface PositionSyncVerifyHandler<P,I> extends IChannelType {
    void verifyHandler(P param);

    void syncVerifyInfo(I info);
}
