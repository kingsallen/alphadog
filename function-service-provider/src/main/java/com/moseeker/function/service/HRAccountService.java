package com.moseeker.function.service;

import org.springframework.stereotype.Service;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.application.service.JobApplicationServices;

@Service
public class HRAccountService {

	JobApplicationServices.Iface applicationService = ServiceManager.SERVICEMANAGER
			.getService(JobApplicationServices.Iface.class);
	
	/**
	 * 是否允许执行绑定
	 * @param userId
	 * @param channel
	 * @return 0表示允许，1表示已经绑定，2...
	 */
	@CounterIface
	public int allowBind(int userId, ChannelType channel) {
		
		
		
		return 0;
	}
}
