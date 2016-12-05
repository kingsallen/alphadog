package com.moseeker.apps.thrift.service;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.apps.service.PositionBS;
import com.moseeker.thrift.gen.apps.positionbs.service.PositionBS.Iface;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;
import com.moseeker.thrift.gen.common.struct.Response;

@Service
public class PositionBSThriftService implements Iface {
	
	@Autowired
	private PositionBS positionBS;

	/**
	 * 同步第三方职位
	 */
	@Override
	public Response synchronizePositionToThirdPartyPlatform(ThirdPartyPositionForm position) throws TException {
		return positionBS.synchronizePositionToThirdPartyPlatform(position);
	}
	
	/**
	 * 刷新职位
	 */
	@Override
	public Response refreshPositionToThirdPartyPlatform(int positionId, int channel) throws TException {
		return positionBS.refreshPosition(positionId, channel);
	}

	public PositionBS getPositionBS() {
		return positionBS;
	}

	public void setPositionBS(PositionBS positionBS) {
		this.positionBS = positionBS;
	}
}
