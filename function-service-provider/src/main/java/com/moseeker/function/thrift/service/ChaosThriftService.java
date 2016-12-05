package com.moseeker.function.thrift.service;

import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.function.service.chaos.ChaosServiceImpl;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices.Iface;
import com.moseeker.thrift.gen.foundation.chaos.struct.ThirdPartyAccountStruct;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronizationWithAccount;

/**
 * 
 * chaos服务对接 
 * <p>Company: MoSeeker</P>  
 * <p>date: Nov 8, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
@Service
public class ChaosThriftService implements Iface{
	
	@Autowired
	private ChaosServiceImpl chaosService;
	
	@Override
	public Response binding(String username, String password, String member_name, byte channel) throws TException {
		return chaosService.bind(username, password, member_name, channel);
	}

	@Override
	public ThirdPartyAccountStruct synchronization(ThirdPartyAccountStruct thirdPartyAccount) throws TException {
		return chaosService.synchronization(thirdPartyAccount);
	}

	@Override
	public Response synchronizePosition(List<ThirdPartyPositionForSynchronizationWithAccount> positions)
			throws TException {
		return chaosService.synchronizePosition(positions);
	}

	@Override
	public Response refreshPosition(ThirdPartyPositionForSynchronizationWithAccount position) throws TException {
		// TODO Auto-generated method stub
		return chaosService.refreshPosition(position);
	}
}
