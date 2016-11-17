package com.moseeker.apps.service;

import java.util.Map;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.moseeker.apps.constants.ResultMessage;
import com.moseeker.common.constants.UserSource;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.application.service.JobApplicationServices;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.PositionDao;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;
import com.moseeker.thrift.gen.useraccounts.struct.User;
import com.mysql.jdbc.StringUtils;

public class ProfileBS {
	
	UseraccountsServices.Iface useraccountsServices = ServiceManager.SERVICEMANAGER
			.getService(UseraccountsServices.Iface.class);
	
	com.moseeker.thrift.gen.dao.service.UserDao.Iface userDao = ServiceManager.SERVICEMANAGER
			.getService(com.moseeker.thrift.gen.dao.service.UserDao.Iface.class);
	
	WholeProfileServices.Iface wholeProfileService = ServiceManager.SERVICEMANAGER
			.getService(WholeProfileServices.Iface.class);
	
	JobApplicationServices.Iface applicationService = ServiceManager.SERVICEMANAGER
			.getService(JobApplicationServices.Iface.class);
	
	PositionDao.Iface positionDao = ServiceManager.SERVICEMANAGER
			.getService(PositionDao.Iface.class);
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	

	@SuppressWarnings("unchecked")
	public Response retrieveProfile(int positionId, String profile, int channel) {
		
		if(positionId == 0 || StringUtils.isNullOrEmpty(profile)) {
			return ResultMessage.PROGRAM_PARAM_NOTEXIST.toResponse();
		}
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", String.valueOf(positionId));
		Position position = new Position();
		try {
			position = positionDao.getPosition(qu);
		} catch (TException e1) {
			e1.printStackTrace();
			logger.error(e1.getMessage(), e1);
			return ResultMessage.PROGRAM_EXHAUSTED.toResponse();
		} finally {
			//do nothing
		}
		if(position.getId() < 1) {
			return ResultMessage.POSITION_NOT_EXIST.toResponse();
		}
		Map<String, Object> resume = JSON.parseObject(profile);
		Map<String, Object> map = (Map<String, Object>) resume.get("user");
		String mobile = (String)map.get("mobile");
		try {
			//查询是否存在相同手机号码的C端帐号
			QueryUtil findRetrieveUserQU = new QueryUtil();
			findRetrieveUserQU.addEqualFilter("mobile", mobile);
			findRetrieveUserQU.addEqualFilter("source", String.valueOf(UserSource.RETRIEVE_PROFILE.getValue()));
			User user = userDao.getUser(findRetrieveUserQU);
			if(user.getId() > 0) {
				//查找该帐号是否有profile
				JobApplication application = new JobApplication();
				application.setPosition_id(positionId);
				application.setApplier_id(user.getId());
				if(useraccountsServices.ifExistProfile(mobile)) {
					Response improveProfile = wholeProfileService.improveProfile(JSON.toJSONString(resume));
					Response postApplicationResult = applicationService.postApplication(application);
				} else {
					Response response = wholeProfileService.createProfile(JSON.toJSONString(resume));
					Response postApplicationResult = applicationService.postApplication(application);
				}
			} else {
				//如果不存在C端帐号，创建帐号
				int userId = useraccountsServices.createRetrieveProfileUser(user);
				//创建profile
				if(userId > 0) {
					((Map<String, Object>)resume.get("user")).put("id", userId);
					Response response = wholeProfileService.createProfile(JSON.toJSONString(resume));
					//创建申请
					if(response.getStatus() == 0) {
						JobApplication application = new JobApplication();
						application.setPosition_id(positionId);
						application.setApplier_id(userId);
						Response postApplicationResult = applicationService.postApplication(application);
					}
				}
			}
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		
		UserUserRecord record = null;
		if (map != null) {
			record = BeanUtils.MapToRecord(map, UserUserRecord.class);
		}
		
		//如果有profile，进行profile合并
		
		//如果不存在profile，进行profile创建
		
		return ResponseUtils.success(null);
	}
	
}
