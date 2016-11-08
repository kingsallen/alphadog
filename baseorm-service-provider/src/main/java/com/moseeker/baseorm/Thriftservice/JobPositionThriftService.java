package com.moseeker.baseorm.Thriftservice;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.service.JobPositionService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.PositionServices.Iface;
@Service
public class JobPositionThriftService implements Iface {
	@Autowired
	private JobPositionService service;
	@Override
	public Response getResources(CommonQuery query) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getRecommendedPositions(int pid) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response verifyCustomize(int positionId) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getPositionById(int positionId) throws TException {
		// TODO Auto-generated method stub
		return service.getJobPositionById(positionId);
	}

}
