package com.moseeker.profile.service.impl;

import java.text.ParseException;
import java.util.UUID;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.profile.dao.CompletenessDao;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.profile.dao.UserDao;
import com.moseeker.profile.dao.UserSettingsDao;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.ProfileServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Profile;

@Service
public class ProfileServicesImpl extends JOOQBaseServiceImpl<Profile, ProfileProfileRecord> implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileProjectExpServicesImpl.class);

	@Autowired
	protected ProfileDao dao;

	@Autowired
	protected UserDao userDao;

	@Autowired
	protected CompletenessDao completenessDao;
	
	@Autowired
	private UserSettingsDao settingDao;
	
	@Autowired
	private ProfileCompletenessImpl completenessImpl;

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	public ProfileDao getDao() {
		return dao;
	}

	public void setDao(ProfileDao dao) {
		this.dao = dao;
	}

	@Override
	public Response postResource(Profile struct) throws TException {
		struct.setUuid(UUID.randomUUID().toString());
		if (!struct.isSetDisable()) {
			struct.setDisable((short) Constant.ENABLE);
		}
		if (struct.getUser_id() > 0) {
			UserUserRecord user = userDao.getUserById(struct.getUser_id());
			if (user == null) {
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
			}
		} else {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
		}
		return super.postResource(struct);
	}

	@Override
	public Response getCompleteness(int userId, String uuid, int profileId) throws TException {
		int totalComplementness = completenessImpl.getCompleteness(userId, uuid, profileId);
		return ResponseUtils.success(totalComplementness);
	}
	
	@Override
	public Response reCalculateUserCompleteness(int userId, String mobile) throws TException {
		completenessImpl.reCalculateUserUserByUserIdOrMobile(userId, mobile);
		int totalComplementness = completenessImpl.getCompleteness(userId, null, 0);
		return ResponseUtils.success(totalComplementness);
	}
	
	@Override
	public Response reCalculateUserCompletenessBySettingId(int id) throws TException {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", String.valueOf(id));
		try {
			UserSettingsRecord record = settingDao.getResource(qu);
			if(record != null) {
				completenessImpl.reCalculateUserUserByUserIdOrMobile(record.getUserId().intValue(), null);
				int totalComplementness = completenessImpl.getCompleteness(record.getUserId().intValue(), null, 0);
				return ResponseUtils.success(totalComplementness);
			} else {
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
		}
	}

	@Override
	protected Profile DBToStruct(ProfileProfileRecord r) {
		return (Profile) BeanUtils.DBToStruct(Profile.class, r);
	}

	@Override
	protected ProfileProfileRecord structToDB(Profile profile) throws ParseException {
		return (ProfileProfileRecord) BeanUtils.structToDB(profile, ProfileProfileRecord.class);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public CompletenessDao getCompletenessDao() {
		return completenessDao;
	}

	public void setCompletenessDao(CompletenessDao completenessDao) {
		this.completenessDao = completenessDao;
	}
}
