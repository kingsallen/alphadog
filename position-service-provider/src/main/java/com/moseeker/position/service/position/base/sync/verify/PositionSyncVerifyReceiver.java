package com.moseeker.position.service.position.base.sync.verify;

import com.moseeker.thrift.gen.common.struct.BIZException;

/**
 * 接受职位同步验证消息
 * @param <P>
 */
public interface PositionSyncVerifyReceiver<P> {
    void receive(P param) throws BIZException;
}
