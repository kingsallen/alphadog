package com.moseeker.baseorm.service;

import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.Response;

public interface ConfigService {
 public Response getConfigSysPointsConfTpl(Query query);
 public Response getConfigSysPointsConfTpls(Query query);
 public Response getRecruitProcess(int CompanyId);
}
