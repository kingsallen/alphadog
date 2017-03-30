package com.moseeker.useraccounts.thrift;

import java.util.List;

import com.moseeker.thrift.gen.useraccounts.struct.ApplicationDetailVO;
import com.moseeker.thrift.gen.useraccounts.struct.RecommendationVO;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.thrift.gen.useraccounts.service.UserCenterService.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.ApplicationRecordsForm;
import com.moseeker.thrift.gen.useraccounts.struct.FavPositionForm;
import com.moseeker.useraccounts.service.impl.UserCenterService;

@Service
public class UserCenterThriftService implements Iface {
	
	@Autowired
	private UserCenterService userCenterService;

	@Override
	public List<ApplicationRecordsForm> getApplications(int userId) throws TException {
		return userCenterService.getApplication(userId);
	}

	@Override
	public ApplicationDetailVO getApplicationDetail(int userId, int appId) throws TException {
		return userCenterService.getApplicationDetail(userId, appId);
	}

	@Override
	public List<FavPositionForm> getFavPositions(int userId) throws TException {
		return userCenterService.getFavPositions(userId);
	}

	@Override
	public RecommendationVO getRecommendation(int userId, byte type, int pageNo, int pageSize) throws TException {
		return userCenterService.getRecommendations(userId, type, pageNo, pageSize);
	}
}
