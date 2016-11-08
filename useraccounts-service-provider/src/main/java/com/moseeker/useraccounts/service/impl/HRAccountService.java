package com.moseeker.useraccounts.service.impl;

import org.springframework.stereotype.Service;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.function.struct.ThirdPartyAccountPojo;
import com.moseeker.thrift.gen.thirdpart.service.OrmThirdPartService;

/**
 * 
 * B端帐号业务处理类
 * <p>
 * Company: MoSeeker
 * </P>
 * <p>
 * date: Nov 8, 2016
 * </p>
 * <p>
 * Email: wjf2255@gmail.com
 * </p>
 * 
 * @author wjf
 * @version
 */
@Service
public class HRAccountService {
	
	//OrmThirdPartService.Iface ormservice = ServiceManager.SERVICEMANAGER.getService(OrmThirdPartService.Iface.class);
	//OrmThirdPartService.Iface ormservice = ServiceManager.SERVICEMANAGER.getService(OrmThirdPartService.Iface.class);

	public Response bindThirdAccount(int userId, ThirdPartyAccountPojo thirdPartyAccount) {
		
		return null;
	}
}
