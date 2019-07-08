package com.moseeker.apps.service;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;
import com.moseeker.thrift.gen.useraccounts.struct.BindType;

@Service
@Transactional
public class UserBs {
	
	UseraccountsServices.Iface useraccountsServices = ServiceManager.SERVICE_MANAGER
			.getService(UseraccountsServices.Iface.class);
	
	WholeProfileServices.Iface wholeService = ServiceManager.SERVICE_MANAGER.getService(WholeProfileServices.Iface.class);
	@CounterIface
	public Response bindOnAccount(int appid, String unionid, String code,
			String mobile, BindType bindType,String countryCode) throws TException {
		Response result = useraccountsServices.postuserbindmobile(appid, unionid, code,countryCode, mobile, bindType);
		JSONObject data = JSON.parseObject(result.getData());
		int destId = NumberUtils.toInt(String.valueOf(data.remove("dest_id")), -1);
		int originId = NumberUtils.toInt(String.valueOf(data.remove("origin_id")), -1);
		result.setData(data.toString());
		if (result.getStatus() == 0 && destId != -1 && originId != -1) {
			wholeService.moveProfile(destId, originId);
		}
		return result;
	}
}
