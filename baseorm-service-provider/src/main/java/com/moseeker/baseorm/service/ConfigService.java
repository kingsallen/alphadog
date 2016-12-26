package com.moseeker.baseorm.service;

import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;

public interface ConfigService {
 public Response getConfigSysPointsConfTpl(CommonQuery query);
 public Response getConfigSysPointsConfTpls(CommonQuery query);
 public Response getRecruitProcess(int CompanyId);
}
