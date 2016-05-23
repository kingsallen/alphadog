package com.moseeker.searchengine.service.impl;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices.Iface;;

@Service
public class SearchengineServiceImpl implements Iface {

	@Override
	public Response query(String keywords, String filter) throws TException {
		// TODO Auto-generated method stub
		return ResponseUtils.success("1234");
		//  ResponseUtils.fail
	}

	@Override
	public Response updateposition(String positionid) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

}
