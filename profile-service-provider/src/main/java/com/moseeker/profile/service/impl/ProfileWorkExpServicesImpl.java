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
import com.moseeker.common.util.StringUtils;
import com.moseeker.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.db.dictdb.tables.records.DictIndustryRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorkexpRecord;
import com.moseeker.profile.dao.CityDao;
import com.moseeker.profile.dao.IndustryDao;
import com.moseeker.profile.dao.PositionDao;
import com.moseeker.profile.dao.WorkExpDao;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.WorkExpServices.Iface;
import com.moseeker.thrift.gen.profile.struct.WorkExp;

@Service
public class ProfileWorkExpServicesImpl extends JOOQBaseServiceImpl<WorkExp, ProfileWorkexpRecord> implements Iface {

	Logger logger = LoggerFactory.getLogger(ProfileWorkExpServicesImpl.class);

	@Autowired
	private WorkExpDao dao;

	@Autowired
	private IndustryDao industryDao;

	@Autowired
	private PositionDao positionDao;

	@Autowired
	private CityDao cityDao;

	@Override
	public Response getResources(CommonQuery query) throws TException {
		try {
			List<ProfileWorkexpRecord> records = dao.getResources(query);
			if (records != null && records.size() > 0) {
				List<WorkExp> workExps = DBsToStructs(records);
				List<Integer> industryCodes = new ArrayList<>();
				List<Integer> cityCodes = new ArrayList<>();
				records.forEach(record -> {
					if (record.getIndustryCode() != null && record.getIndustryCode().intValue() > 0
							&& StringUtils.isNullOrEmpty(record.getIndustryName())) {
						industryCodes.add(record.getIndustryCode().intValue());
					}
					if (record.getCityCode() != null && record.getCityCode().intValue() > 0
							&& StringUtils.isNullOrEmpty(record.getCityName())) {
						cityCodes.add(record.getCityCode().intValue());
					}
				});
				List<WorkExp> WorkExps = DBsToStructs(records);
				if (WorkExps.size() > 0) {
					List<DictIndustryRecord> industryRecords = industryDao.getIndustriesByCodes(industryCodes);
					List<DictCityRecord> cityRecords = cityDao.getCitiesByCodes(cityCodes);
					for (WorkExp workExp : workExps) {
						if (industryRecords != null && industryRecords.size() > 0) {
							for (DictIndustryRecord industry : industryRecords) {
								if (workExp.getIndustry_code() == industry.getCode().intValue()) {
									workExp.setIndustry_name(industry.getName());
									break;
								}
							}
						}
						if(cityRecords != null && cityRecords.size() > 0) {
							for(DictCityRecord city : cityRecords) {
								if(workExp.getCity_code() == city.getCode().intValue()) {
									workExp.setCity_name(city.getName());
									break;
								}
							}
						}
					}
					return ResponseUtils.success(workExps);
				}
			}
		} catch (Exception e) {
			logger.error("getResources error", e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			// do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
	}

	@Override
	public Response getResource(CommonQuery query) throws TException {
		return super.getResource(query);
	}

	public WorkExpDao getDao() {
		return dao;
	}

	public void setDao(WorkExpDao dao) {
		this.dao = dao;
	}

	public IndustryDao getIndustryDao() {
		return industryDao;
	}

	public void setIndustryDao(IndustryDao industryDao) {
		this.industryDao = industryDao;
	}

	public PositionDao getPositionDao() {
		return positionDao;
	}

	public void setPositionDao(PositionDao positionDao) {
		this.positionDao = positionDao;
	}

	public CityDao getCityDao() {
		return cityDao;
	}

	public void setCityDao(CityDao cityDao) {
		this.cityDao = cityDao;
	}

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected WorkExp DBToStruct(ProfileWorkexpRecord r) {
		Map<String, String> equalRules = new HashMap<>();
		equalRules.put("start_date", "start");
		equalRules.put("end_date", "end");
		return (WorkExp) BeanUtils.DBToStruct(WorkExp.class, r, equalRules);
	}

	@Override
	protected ProfileWorkexpRecord structToDB(WorkExp workExp) throws ParseException {
		Map<String, String> equalRules = new HashMap<>();
		equalRules.put("start_date", "start");
		equalRules.put("end_date", "end");
		return (ProfileWorkexpRecord) BeanUtils.structToDB(workExp, ProfileWorkexpRecord.class, equalRules);
	}
}
