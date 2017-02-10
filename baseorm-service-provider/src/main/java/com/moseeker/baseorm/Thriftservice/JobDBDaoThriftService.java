package com.moseeker.baseorm.Thriftservice;

import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.service.JobDBDao.Iface;
import com.moseeker.thrift.gen.position.struct.Position;

@Service
public class JobDBDaoThriftService implements Iface {
	
	@Autowired
	private JobApplicationDao applicationDao;
	
	@Autowired
	private JobPositionDao positionDao;

	@Override
	public List<Position> getPositions(CommonQuery query) throws TException {
		return positionDao.getPositions(query);
	}

	@Override
	public List<JobApplication> getApplications(CommonQuery query) throws TException {
		return applicationDao.getApplications(query);
	}

}
