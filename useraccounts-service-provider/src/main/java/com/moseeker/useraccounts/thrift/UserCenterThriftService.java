package com.moseeker.useraccounts.thrift;

import java.util.List;

import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.common.exception.CommonException;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.SysBIZException;
import com.moseeker.thrift.gen.useraccounts.struct.*;
import com.moseeker.useraccounts.service.impl.vo.UserCenterInfoVO;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.thrift.gen.useraccounts.service.UserCenterService.Iface;
import com.moseeker.useraccounts.service.impl.UserCenterService;

@Service
public class UserCenterThriftService implements Iface {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserCenterService userCenterService;

	@Override
	public List<ApplicationRecordsForm> getApplications(int userId) throws TException {
		try {
			return userCenterService.getApplication(userId);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	@Override
	public ApplicationDetailVO getApplicationDetail(int userId, int appId) throws TException {
		try {
			return userCenterService.getApplicationDetail(userId, appId);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	@Override
	public List<FavPositionForm> getFavPositions(int userId) throws TException {
		try {
			return userCenterService.getFavPositions(userId);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	@Override
	public RecommendationVO getRecommendation(int userId, byte type, int pageNo, int pageSize) throws TException {
		try {
			return userCenterService.getRecommendations(userId, type, pageNo, pageSize);
		} catch (CommonException e) {
			throw ExceptionConvertUtil.convertCommonException(e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysBIZException();
		}
	}

	@Override
	public CenterUserInfo getCenterUserInfo(int userId, int companyId) throws BIZException, TException {
		UserCenterInfoVO infoVO = userCenterService.getCenterUserInfo(userId, companyId);
		CenterUserInfo info = new CenterUserInfo();
		BeanUtils.copyProperties(infoVO, info);
		return info;
	}

    @Override
    public RecommendationScoreVO getRecommendationV2(int userId, int companyId) throws BIZException, TException {
        return userCenterService.getRecommendationsV2(companyId, userId);
    }
}
