package com.moseeker.baseorm.service;

import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;

public interface PositionService {
    public Response getJobOccupation(Query query);

    public Response getJobCustoms(Query query);
}
