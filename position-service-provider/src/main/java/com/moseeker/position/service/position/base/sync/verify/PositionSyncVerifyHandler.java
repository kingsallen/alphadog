package com.moseeker.position.service.position.base.sync.verify;

import com.moseeker.thrift.gen.common.struct.BIZException;

/**
 * 职位同步验证处理接口
 * @param <I>
 */
public interface PositionSyncVerifyHandler<I> {
    void handler(I info) throws BIZException;
}
