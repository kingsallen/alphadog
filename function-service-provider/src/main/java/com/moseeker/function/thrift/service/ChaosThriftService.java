package com.moseeker.function.thrift.service;

import java.util.List;

import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronizationWithAccount;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.function.service.chaos.ChaosServiceImpl;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices.Iface;
import com.moseeker.thrift.gen.foundation.chaos.struct.ThirdPartyAccountStruct;

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
	public HrThirdPartyAccountDO binding(HrThirdPartyAccountDO hrThirdPartyAccount) throws TException {
		return chaosService.bind(hrThirdPartyAccount);
	}

	@Override
	public HrThirdPartyAccountDO synchronization(HrThirdPartyAccountDO thirdPartyAccount) throws BIZException, TException {
		return chaosService.synchronization(hrThirdPartyAccount);
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
