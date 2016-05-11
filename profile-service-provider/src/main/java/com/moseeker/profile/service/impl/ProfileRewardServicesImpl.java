package com.moseeker.profile.service.impl;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.records.ProfileRewardRecord;
import com.moseeker.profile.dao.RewardDao;
import com.moseeker.thrift.gen.profile.service.RewardServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Reward;

@Service
public class ProfileRewardServicesImpl extends JOOQBaseServiceImpl<Reward, ProfileRewardRecord>
		implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileRewardServicesImpl.class);

	@Autowired
	private RewardDao dao;

	public RewardDao getDao() {
		return dao;
	}

	public void setDao(RewardDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected Reward DBToStruct(ProfileRewardRecord r) {
		return (Reward) BeanUtils.DBToStruct(Reward.class, r);
	}

	@Override
	protected ProfileRewardRecord structToDB(Reward reward) throws ParseException {
		return (ProfileRewardRecord) BeanUtils.structToDB(reward, ProfileRewardRecord.class);
	}
}
