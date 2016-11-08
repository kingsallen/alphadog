package com.moseeker.baseorm.service;

import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;

public interface JobPositionService {
	public Response getJobPositionById(int id);
	public Response getJobPostion(CommonQuery query);
}
