package com.moseeker.baseorm.Thriftservice;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;

import com.moseeker.baseorm.service.ConfigService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.ConfigDao.Iface;

public class ConfigDaothriftService implements Iface{
	@Autowired
	private ConfigService configService;

	@Override
	public Response getConfigSysPointsConfTpls(CommonQuery query) throws TException {
		// TODO Auto-generated method stub
		return configService.getConfigSysPointsConfTpls(query);
	}

	@Override
	public Response getConfigSysPointsConfTpl(CommonQuery query) throws TException {
		// TODO Auto-generated method stub
		return configService.getConfigSysPointsConfTpl(query);
	}

	@Override
	public Response getRecruitProcesses(int companyId) throws TException {
		// TODO Auto-generated method stub
		return configService.getRecruitProcess(companyId);
	}

	

}
