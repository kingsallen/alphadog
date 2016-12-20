package com.moseeker.apps.service;

import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;
import com.moseeker.thrift.gen.useraccounts.struct.BindType;

@Service
public class UserBs {
	
	UseraccountsServices.Iface useraccountsServices = ServiceManager.SERVICEMANAGER
			.getService(UseraccountsServices.Iface.class);
	
	WholeProfileServices.Iface wholeService = ServiceManager.SERVICEMANAGER.getService(WholeProfileServices.Iface.class);
	
	public Response bindOnAccount(int appid, String unionid, String code,
			String mobile, BindType bindType) throws TException {
		Response result = useraccountsServices.postuserbindmobile(appid, unionid, code, mobile, bindType);
		if (result.getStatus() == 0) {
			Map<String, Object> data = JSON.parseObject(result.getData());
			wholeService.moveProfile(NumberUtils.toInt(String.valueOf(data.get("id")), -1), NumberUtils.toInt(String.valueOf(data.get("parentid")), -1));
		}
		return result;
	}
}
