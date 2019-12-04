package com.moseeker.apps.thrift.service;

import com.moseeker.apps.service.ProfileBS;
import com.moseeker.apps.service.ProfileProcessBS;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.apps.profilebs.service.ProfileBS.Iface;
import com.moseeker.thrift.gen.common.struct.Response;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileBSThriftService implements Iface {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ProfileBS profileBS;
	@Autowired
	private ProfileProcessBS profileProcessBS;

	@Override
	public Response retrieveProfile(int positionId, int channel, String profile, String newchannel) throws TException {
		try {
			return profileBS.retrieveProfile(positionId, profile, channel, newchannel);
		}catch (Exception e){
			logger.info("简历回流:{}", e.getMessage());
			throw ExceptionUtils.convertException(e);
		}

	}

	public ProfileBS getProfileBS() {
		return profileBS;
	}

	public void setProfileBS(ProfileBS profileBS) {
		this.profileBS = profileBS;
	}

	@Override
	public Response profileProcess(int company_id, int progress_status, List<Integer> aids, int account_id) {
		// TODO Auto-generated method stub
		try{
			return profileProcessBS.processProfile(company_id, progress_status, aids, account_id);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			 return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}

	@Override
	public Response profileProcessAts(int progress_status, String aids) throws TException {
		// TODO Auto-generated method stub
		try{
			return profileProcessBS.processProfileAts(progress_status, aids);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}


}
