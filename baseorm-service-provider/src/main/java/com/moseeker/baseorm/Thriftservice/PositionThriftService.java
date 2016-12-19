package com.moseeker.baseorm.Thriftservice;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.moseeker.baseorm.service.PositionService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.PositionDao.Iface;
/*
 * auth:zzt
 * time:2016-11-17
 * function:对外提供job_custom和 job_occupation的查询
 */
@Service
public class PositionThriftService implements Iface{

	@Autowired
	private PositionService position;
	//获取job_custom
	@Override
	public Response getJobCustoms(CommonQuery query) throws TException {
		// TODO Auto-generated method stub
		return position.getJobCustoms(query);
	}
	//获取job_occupation
	@Override
	public Response getJobOccupations(CommonQuery query) throws TException {
		// TODO Auto-generated method stub
		return position.getJobOccupation(query);
	}
	

}
