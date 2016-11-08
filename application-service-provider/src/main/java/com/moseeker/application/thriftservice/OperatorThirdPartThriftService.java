package com.moseeker.application.thriftservice;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.application.service.OperatorThirdPartService;
import com.moseeker.thrift.gen.application.service.OperatorThirdPartService.Iface;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdpartToredis;
@Service
public class OperatorThirdPartThriftService implements Iface{
	@Autowired
	private OperatorThirdPartService service;

	@Override
	public Response addPositionToRedis(ThirdpartToredis param) throws TException {
		// TODO Auto-generated method stub
		return service.addPostitonToRedis(param);
	}

	@Override
	public Response updatePositionToRedis(ThirdpartToredis param) throws TException {
		// TODO Auto-generated method stub
		return service.updatePostitonToRedis(param);
	}
	
}
