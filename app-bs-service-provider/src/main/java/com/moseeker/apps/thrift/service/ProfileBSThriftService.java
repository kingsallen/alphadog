package com.moseeker.apps.thrift.service;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.apps.service.ProfileBS;
import com.moseeker.thrift.gen.apps.profilebs.service.ProfileBS.Iface;
import com.moseeker.thrift.gen.common.struct.Response;

@Service
public class ProfileBSThriftService implements Iface {

	@Autowired
	private ProfileBS profileBS;
	
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


}
