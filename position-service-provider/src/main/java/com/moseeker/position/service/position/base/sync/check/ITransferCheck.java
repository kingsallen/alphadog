package com.moseeker.position.service.position.base.sync.check;

import com.moseeker.common.iface.IChannelType;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface ITransferCheck<T> extends IChannelType{
    boolean containsError(T t , JobPositionDO moseekerPosition);

    List<String> getError(T t, JobPositionDO moseekerPosition);
}
