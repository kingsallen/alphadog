package com.moseeker.apps.thrift.service;

import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.apps.service.ProfileBS;
import com.moseeker.apps.service.ProfileProcessBS;
import com.moseeker.thrift.gen.apps.profilebs.service.ProfileBS.Iface;
import com.moseeker.thrift.gen.common.struct.Response;

@Service
public class ProfileBSThriftService implements Iface {

	@Autowired
	private ProfileBS profileBS;
	@Autowired
	private ProfileProcessBS profileProcessBS;
	
	@Override
	public Response retrieveProfile(int positionId, int channel, String profile) throws TException {
		return profileBS.retrieveProfile(positionId, profile, channel);
	}

	public ProfileBS getProfileBS() {
		return profileBS;
	}

	public void setProfileBS(ProfileBS profileBS) {
		this.profileBS = profileBS;
	}

	@Override
	public Response profileProcess(int company_id, int progress_status, String aids, int account_id) {
		// TODO Auto-generated method stub
		return profileProcessBS.processProfile(company_id,progress_status,aids,account_id);
	}


}
