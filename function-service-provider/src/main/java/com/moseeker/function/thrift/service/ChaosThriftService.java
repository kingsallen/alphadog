package com.moseeker.function.thrift.service;

import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.function.service.impl.CharOsServiceImpl;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.thirdpart.service.BindThirdPartService.Iface;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartParamer;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartPosition;

@Service
public class ChaosThriftService implements Iface{
	@Autowired
	private CharOsServiceImpl chaOsService;
	/*
	 * (non-Javadoc)
	 * @see com.moseeker.thrift.gen.thirdpart.service.BindThirdPartService.Iface#sendParamForChaos(com.moseeker.thrift.gen.thirdpart.struct.ThirdPartParamer)
	 * 通过chaos登陆，并且将登陆成功的账号密码存在第三方平台账号表里
	 */
	@Override
	public Response sendParamForChaos(ThirdPartParamer param) throws TException {
		// TODO Auto-generated method stub
		return chaOsService.getInformationFromChaos(param);
	}
	/*
	 * (non-Javadoc)
	 * @see com.moseeker.thrift.gen.thirdpart.service.BindThirdPartService.Iface#getreMindFromChaos(com.moseeker.thrift.gen.thirdpart.struct.ThirdPartParamer)
	 * 从chaos获取当前账号的点数，并且更新数据库
	 */
	@Override
	public Response getreMindFromChaos(ThirdPartParamer param) throws TException {
		// TODO Auto-generated method stub
		return chaOsService.getRemind(param);
	}

	
	

}
