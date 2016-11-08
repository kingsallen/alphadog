package com.moseeker.service;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.thirdpart.service.BindThirdPartService.Iface;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartParamer;

@Service
public class ThirdAccountOperatorService implements Iface{

	@Override
	public Response sendParamForChaos(ThirdPartParamer param) throws TException {
		// TODO Auto-generated method stub
		
		return null;
	}

}
