package com.moseeker.baseorm.Thriftservice;

import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.configdb.AwardConfigTplDao;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.service.ConfigDBDao.Iface;
import com.moseeker.thrift.gen.dao.struct.AwardConfigTpl;

@Service
public class ConfigDBDaoThriftService implements Iface {
	
	@Autowired
	private AwardConfigTplDao awardConfigTplDao;

	@Override
	public List<AwardConfigTpl> getAwardConfigTpls(CommonQuery query) throws TException {
		return awardConfigTplDao.getAwardConfigTpls(query);
	}

}
