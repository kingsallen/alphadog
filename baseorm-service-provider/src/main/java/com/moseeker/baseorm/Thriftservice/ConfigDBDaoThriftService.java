package com.moseeker.baseorm.Thriftservice;

import com.moseeker.baseorm.dao.configdb.AwardConfigTplDao;
import com.moseeker.baseorm.service.ConfigService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.ConfigDBDao.Iface;
import com.moseeker.thrift.gen.dao.struct.ConfigSysPointConfTplDO;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigDBDaoThriftService implements Iface {
	
	@Autowired
	private AwardConfigTplDao awardConfigTplDao;

	@Autowired
	private ConfigService configService;

	@Override
	public List<ConfigSysPointConfTplDO> getAwardConfigTpls(CommonQuery query) throws TException {
		return awardConfigTplDao.getAwardConfigTpls(query);
	}

	@Override
	public Response getConfigSysPointsConfTpls(CommonQuery query) throws TException {
		return configService.getConfigSysPointsConfTpls(query);
	}

	@Override
	public Response getConfigSysPointsConfTpl(CommonQuery query) throws TException {
		return configService.getConfigSysPointsConfTpl(query);
	}

	@Override
	public Response getRecruitProcesses(int companyId) throws TException {
		return configService.getRecruitProcess(companyId);
	}

}
