package com.moseeker.position.service.position.base.sync.verify;

import com.moseeker.thrift.gen.common.struct.BIZException;

public interface PositionSyncVerifyReceiver<P> {
    void receive(P param) throws BIZException;
}
