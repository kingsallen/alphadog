package com.moseeker.profile.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionCityRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionIndustryRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionPositionRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionRecord;
import com.moseeker.profile.dao.IntentionCityDao;
import com.moseeker.profile.dao.IntentionDao;
import com.moseeker.profile.dao.IntentionIndustryDao;
import com.moseeker.profile.dao.IntentionPositionDao;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.IntentionServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Intention;

@Service
public class ProfileIntentionServicesImpl extends JOOQBaseServiceImpl<Intention, ProfileIntentionRecord>
		implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileIntentionServicesImpl.class);

	@Autowired
	private IntentionDao dao;
	
	@Autowired
	private IntentionCityDao intentionCityDao;
	
	@Autowired
	private IntentionIndustryDao intentionIndustryDao;
	
	@Autowired
	private IntentionPositionDao intentionPositionDao;

	@Override
	public Response getResources(CommonQuery query) throws TException {
		try {
			List<ProfileIntentionRecord> records = dao.getResources(query);
			if(records != null && records.size() > 0) {
				List<Integer> intentionIds = new ArrayList<>();
				records.forEach(record -> {
					intentionIds.add(record.getId().intValue());
				});
				List<Intention> intentions = DBsToStructs(records);
				if(intentionIds.size() > 0) {
					List<ProfileIntentionCityRecord> cities = intentionCityDao.getIntentionCities(intentionIds);
					List<ProfileIntentionIndustryRecord> industries = intentionIndustryDao.getIntentionIndustries(intentionIds);
					List<ProfileIntentionPositionRecord> positions = intentionPositionDao.getIntentionPositions(intentionIds);
					for(Intention intention : intentions) {
						if(cities != null && cities.size() > 0) {
							Map<Integer, String> cityMap = new HashMap<>();
							for(ProfileIntentionCityRecord record : cities) {
								if(intention.getId() == record.getProfileIntentionId().intValue()) {
									cityMap.put(record.getCityCode().intValue(), record.getCityName());
								}
							}
							intention.setCities(cityMap);
						}
						if(industries != null && industries.size() > 0) {
							Map<Integer, String> industrMap = new HashMap<>();
							for(ProfileIntentionIndustryRecord record : industries) {
								if(intention.getId() == record.getProfileIntentionId().intValue()) {
									industrMap.put(record.getIndustryCode().intValue(), record.getIndustryName());
								}
							}
							intention.setIndustries(industrMap);
						}
						if(positions != null && positions.size() > 0) {
							Map<Integer, String> positionMap = new HashMap<>();
							for(ProfileIntentionIndustryRecord record : industries) {
								if(intention.getId() == record.getProfileIntentionId().intValue()) {
									positionMap.put(record.getIndustryCode().intValue(), record.getIndustryName());
								}
							}
							intention.setPositions(positionMap);
						}
					}
					return ResponseUtils.success(intentions);
				}
			}
		} catch (Exception e) {
			logger.error("getResources error", e);
			return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			//do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
	}

	@Override
	public Response getResource(CommonQuery query) throws TException {
		return super.getResource(query);
	}

	public IntentionDao getDao() {
		return dao;
	}

	public void setDao(IntentionDao dao) {
		this.dao = dao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	public IntentionCityDao getIntentionCityDao() {
		return intentionCityDao;
	}

	public void setIntentionCityDao(IntentionCityDao intentionCityDao) {
		this.intentionCityDao = intentionCityDao;
	}

	public IntentionIndustryDao getIntentionIndustryDao() {
		return intentionIndustryDao;
	}

	public void setIntentionIndustryDao(IntentionIndustryDao intentionIndustryDao) {
		this.intentionIndustryDao = intentionIndustryDao;
	}

	public IntentionPositionDao getIntentionPositionDao() {
		return intentionPositionDao;
	}

	public void setIntentionPositionDao(IntentionPositionDao intentionPositionDao) {
		this.intentionPositionDao = intentionPositionDao;
	}

	@Override
	protected Intention DBToStruct(ProfileIntentionRecord r) {
		return (Intention) BeanUtils.DBToStruct(Intention.class, r);
	}

	@Override
	protected ProfileIntentionRecord structToDB(Intention intention) throws ParseException {
		return (ProfileIntentionRecord) BeanUtils.structToDB(intention, ProfileIntentionRecord.class);
	}
}
