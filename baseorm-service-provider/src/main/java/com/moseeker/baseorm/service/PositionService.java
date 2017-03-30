package com.moseeker.baseorm.service;

import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;

public interface PositionService {
    public Response getJobOccupation(CommonQuery query);

    public Response getJobCustoms(CommonQuery query);
}
