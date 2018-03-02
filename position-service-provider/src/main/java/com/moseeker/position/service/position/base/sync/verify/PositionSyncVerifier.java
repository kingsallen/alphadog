package com.moseeker.position.service.position.base.sync.verify;

import com.moseeker.common.iface.IChannelType;

public interface PositionSyncVerifier<S> extends PositionSyncVerifyReceiver<S>,PositionSyncVerifyHandler<S>,IChannelType {
}
