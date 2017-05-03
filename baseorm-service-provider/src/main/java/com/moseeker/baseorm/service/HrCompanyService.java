package com.moseeker.baseorm.service;

import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.Response;

public interface HrCompanyService {
	 public Response getCompanyConf(Query query);
}
