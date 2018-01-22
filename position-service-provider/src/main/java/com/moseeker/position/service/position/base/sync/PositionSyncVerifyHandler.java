package com.moseeker.position.service.position.base.sync;

import com.moseeker.common.iface.IChannelType;
import com.moseeker.thrift.gen.common.struct.BIZException;

public interface PositionSyncVerifyHandler<P,I> extends IChannelType {
    void verifyHandler(P param) throws BIZException;

    void syncVerifyInfo(I info) throws BIZException;
}
