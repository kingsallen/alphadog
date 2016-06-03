package com.moseeker.profile.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
import com.moseeker.db.dictdb.tables.records.DictCountryRecord;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.profile.dao.CityDao;
import com.moseeker.profile.dao.CountryDao;
import com.moseeker.profile.dao.ProfileBasicDao;
import com.moseeker.profile.dao.UserDao;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.BasicServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Basic;

@Service
public class ProfileBasicServicesImpl extends JOOQBaseServiceImpl<Basic, ProfileBasicRecord> implements Iface {
	
	Logger logger = LoggerFactory.getLogger(ProfileBasicServicesImpl.class);
	
	@Override
	public Response getResources(CommonQuery query) throws TException {
		try {
			List<ProfileBasicRecord> records = dao.getResources(query);
			List<Basic> basics = DBsToStructs(records);
			if(basics != null && basics.size() > 0) {
				List<Integer> cityCodes = new ArrayList<>();
				List<Integer> contryIds = new ArrayList<>();
				basics.forEach(basic -> {
					//cityCodes.add(basic.getCity());
					if(basic.getCity_code() > 0 && StringUtils.isNullOrEmpty(basic.getCity_name()))
						cityCodes.add((int)basic.getCity_code());
					if(basic.getNationality_code() > 0 && StringUtils.isNullOrEmpty(basic.getNationality_name()))
						contryIds.add((int)basic.getNationality_code());
				});
				//城市
				List<DictCityRecord> cities = cityDao.getCitiesByCodes(cityCodes);
				if(cities != null && cities.size() > 0) {
					for(Basic basic : basics) {
						for(DictCityRecord record : cities) {
							if(basic.getCity_code() == record.getCode().intValue()) {
								basic.setCity_name(record.getName());
								break;
							}
						}
					}
				}
				//国旗
				List<DictCountryRecord> countries = countryDao.getCountresByIDs(cityCodes);
				if(countries != null && countries.size() > 0) {
					for(Basic basic : basics) {
						for(DictCountryRecord record : countries) {
							if(basic.getNationality_code() == record.getId().intValue()) {
								basic.setNationality_name(record.getName());
								break;
							}
						}
					}
				}
				return ResponseUtils.success(basics);
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
		try {
			ProfileBasicRecord record = dao.getResource(query);
			if(record == null) {
				Basic basic = DBToStruct(record);
				if(basic.getCity_code() > 0 && StringUtils.isNullOrEmpty(basic.getCity_name())) {
					DictCityRecord city = cityDao.getCityByCode(basic.getCity_code());
					if(city != null) {
						basic.setCity_name(city.getName());
					}
				}
				if(basic.getNationality_code() > 0 && StringUtils.isNullOrEmpty(basic.getNationality_name())) {
					DictCountryRecord country = countryDao.getCountryByID(basic.getNationality_code());
					if(country != null) {
						basic.setNationality_name(country.getName());
					}
				}
				return ResponseUtils.success(basic);
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
	public Response postResource(Basic struct) throws TException {
		try {
			ProfileBasicRecord record = structToDB(struct);
			if(record.getCityCode() != null && record.getCityCode().intValue() > 0) {
				DictCityRecord city = cityDao.getCityByCode(record.getCityCode().intValue());
				if(city != null) {
					record.setCityName(city.getName());
				}
			}
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
	}

	@Override
	public Response putResource(Basic struct) throws TException {
		return super.putResource(struct);
	}

	@Override
	protected Basic DBToStruct(ProfileBasicRecord r) {
		return (Basic)BeanUtils.DBToStruct(Basic.class, r);
	}

	@Override
	protected ProfileBasicRecord structToDB(Basic basic)
			throws ParseException {
		ProfileBasicRecord record = (ProfileBasicRecord)BeanUtils.structToDB(basic, ProfileBasicRecord.class);
		return record;
	}
	
	@Autowired
	private ProfileBasicDao dao;
	
	@Autowired
	private CityDao cityDao;
	
	@Autowired
	private CountryDao countryDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	protected void initDao() {
		super.dao = this.dao;
	}
	
	public ProfileBasicDao getDao() {
		return dao;
	}

	public void setDao(ProfileBasicDao dao) {
		this.dao = dao;
	}

	public CityDao getCityDao() {
		return cityDao;
	}

	public void setCityDao(CityDao cityDao) {
		this.cityDao = cityDao;
	}

	public CountryDao getCountryDao() {
		return countryDao;
	}

	public void setCountryDao(CountryDao countryDao) {
		this.countryDao = countryDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
