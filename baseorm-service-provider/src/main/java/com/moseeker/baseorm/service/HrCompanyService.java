package com.moseeker.baseorm.service;

import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;

public interface HrCompanyService {
	 public Response getCompanyConf(CommonQuery query);
}
