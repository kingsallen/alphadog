package com.moseeker.function.service.impl;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.ThirdPartyAccountPojo;
import com.moseeker.function.service.ChaosService;
import com.moseeker.thrift.gen.common.struct.Response;

/**
 * 
 * 第三方渠道（比如51，智联）服务 
 * <p>Company: MoSeeker</P>  
 * <p>date: Nov 6, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class ChaosServiceImpl implements ChaosService {

	@Override
	public Response bind(ThirdPartyAccountPojo account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response fetchRemainNum(ThirdPartyAccountPojo account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response synchronizePosition(ThirdPartyAccountPojo account, String positionJson) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response refreshPosition(ThirdPartyAccountPojo account, int positionId, String jobNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response distortPosition(int positionId, ChannelType channel) {
		// TODO Auto-generated method stub
		return null;
	}

}
