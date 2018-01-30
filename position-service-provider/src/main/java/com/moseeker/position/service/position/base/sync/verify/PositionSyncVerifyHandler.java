package com.moseeker.position.service.position.base.sync.verify;

import com.moseeker.common.iface.IChannelType;
import com.moseeker.thrift.gen.common.struct.BIZException;

public interface PositionSyncVerifyHandler<I> {
    void handler(I info) throws BIZException;
}
