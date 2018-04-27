package com.moseeker.function.service.chaos.base;

import com.moseeker.common.iface.IChannelType;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;

import java.util.Map;

public interface PositionEmailBuilder<P> extends IChannelType{
    String divider = "<br/>";

    String message(JobPositionDO moseekerPosition, HrThirdPartyPositionDO thirdPartyPosition, P position) throws BIZException;
}
