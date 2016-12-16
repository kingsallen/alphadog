package com.moseeker.apps.service;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.ProfileServices;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;
import com.moseeker.thrift.gen.useraccounts.struct.BindType;

@Service
public class UserBs {
	
	UseraccountsServices.Iface useraccountsServices = ServiceManager.SERVICEMANAGER
			.getService(UseraccountsServices.Iface.class);
	
	ProfileServices.Iface profileService = ServiceManager.SERVICEMANAGER.getService(ProfileServices.Iface.class);

	
	public Response bindOnAccount(int appid, String unionid, String code,
			String mobile, BindType bindType) throws TException {
		Response result = useraccountsServices.postuserbindmobile(appid, unionid, code, mobile, bindType);
		if (result.getStatus() == 0) {
			profileService.reCalculateUserCompleteness(0, mobile);
		}
		return result;
	}
}
