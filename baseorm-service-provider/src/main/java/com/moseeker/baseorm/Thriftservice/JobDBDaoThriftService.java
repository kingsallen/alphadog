package com.moseeker.baseorm.Thriftservice;

import java.util.List;

import com.moseeker.thrift.gen.dao.struct.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.JobPositionDO;
import com.moseeker.thrift.gen.position.struct.Position;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.service.JobDBDao.Iface;

@Service
public class JobDBDaoThriftService implements Iface {
	
	@Autowired
	private JobApplicationDao applicationDao;
	
	@Autowired
	private JobPositionDao positionDao;

	@Override
	public List<JobPositionDO> getPositions(CommonQuery query) throws TException {
		return positionDao.getPositions(query);
	}

	@Override
	public JobPositionDO getPosition(CommonQuery query) throws TException {
		return null;
	}

	@Override
	public List<JobApplicationDO> getApplications(CommonQuery query) throws TException {
		return applicationDao.getApplications(query);
	}

}
