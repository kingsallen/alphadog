package com.moseeker.profile.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.thrift.TException;
import org.jooq.Record2;
import org.jooq.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.db.dictdb.tables.records.DictCountryRecord;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.profile.dao.CityDao;
import com.moseeker.profile.dao.CountryDao;
import com.moseeker.profile.dao.ProfileBasicDao;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.profile.dao.UserDao;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.Basic;

@Service
@CounterIface
public class ProfileBasicService extends JOOQBaseServiceImpl<Basic, ProfileBasicRecord> {
	
	Logger logger = LoggerFactory.getLogger(ProfileBasicService.class);
	
	@Override
	public Response getResources(CommonQuery query) throws TException {
		try {
			List<ProfileBasicRecord> records = dao.getResources(query);
			List<Basic> basics = DBsToStructs(records);
			if(basics != null && basics.size() > 0) {
				List<Integer> profileIds = new ArrayList<>();
				List<Integer> cityCodes = new ArrayList<>();
				List<Integer> contryIds = new ArrayList<>();
				basics.forEach(basic -> {
					//cityCodes.add(basic.getCity());
					if(basic.getCity_code() > 0 && StringUtils.isNullOrEmpty(basic.getCity_name()))
						cityCodes.add((int)basic.getCity_code());
					if(basic.getNationality_code() > 0 && StringUtils.isNullOrEmpty(basic.getNationality_name()))
						contryIds.add((int)basic.getNationality_code());
					profileIds.add(basic.getProfile_id());
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
				Result<Record2<Integer, String>> result = profileDao.findRealName(profileIds);
				if(result != null && result.size() > 0) {
					for(Basic basic : basics) {
						for(Record2<Integer, String> record2 : result) {
							if(basic.getProfile_id() == (int)record2.get(0)) {
								basic.setName((String)record2.get(1));
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
			if(record != null) {
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
				String realName = profileDao.findRealName(basic.getProfile_id());
				if(StringUtils.isNullOrEmpty(realName)) {
					basic.setName(realName);
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
			if(struct.getCity_code() > 0) {
				DictCityRecord city = cityDao.getCityByCode(struct.getCity_code());
				if(city != null) {
					struct.setCity_name(city.getName());
				} else {
					return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_CITY_NOTEXIST);
				}
			}
			if(struct.getNationality_code() > 0) {
				DictCountryRecord country = countryDao.getCountryByID(struct.getNationality_code());
				if(country != null) {
					struct.setNationality_name(country.getName());
				} else {
					return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_NATIONALITY_NOTEXIST);
				}
			}
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("profile_id", String.valueOf(struct.getProfile_id()));
			ProfileBasicRecord repeat = dao.getResource(qu);
			if(repeat != null) {
				return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_REPEAT_DATA);
			}
			ProfileBasicRecord record = structToDB(struct);
			int i = dao.postResource(record);
			if(i > 0) {
				
				updateUpdateTime(struct);
				
				if(!StringUtils.isNullOrEmpty(struct.getName())) {
					profileDao.updateRealName(record.getProfileId().intValue(), struct.getName());
				}
				/* 计算用户基本信息的简历完整度 */
				completenessImpl.reCalculateUserUser(struct.getProfile_id());
				return ResponseUtils.success(String.valueOf(i));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			//do nothing
		}
		
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
	}

	@Override
	public Response putResource(Basic struct) throws TException {
		try {
			if(struct.getCity_code() > 0) {
				DictCityRecord city = cityDao.getCityByCode(struct.getCity_code());
				if(city != null) {
					struct.setCity_name(city.getName());
				} else {
					return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_CITY_NOTEXIST);
				}
			}
			if(struct.getNationality_code() > 0) {
				DictCountryRecord country = countryDao.getCountryByID(struct.getNationality_code());
				if(country != null) {
					struct.setNationality_name(country.getName());
				} else {
					return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_NATIONALITY_NOTEXIST);
				}
			}
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("profile_id", String.valueOf(struct.getProfile_id()));
			ProfileBasicRecord repeat = dao.getResource(qu);
			if(repeat == null) {
				return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DATA_NULL);
			}
			ProfileBasicRecord record = structToDB(struct);
			int i = dao.putResource(record);
			if(i > 0) {
				
				updateUpdateTime(struct);
				
				if(!StringUtils.isNullOrEmpty(struct.getName())) {
					profileDao.updateRealName(record.getProfileId().intValue(), struct.getName());
				}
				
				/* 计算用户基本信息的简历完整度 */
				completenessImpl.reCalculateUserUser(struct.getProfile_id());
				completenessImpl.reCalculateProfileBasic(struct.getProfile_id());
				return ResponseUtils.success(String.valueOf(i));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			//do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
	}

	@Override
	public Response postResources(List<Basic> structs) throws TException {
		Response response = super.postResources(structs);
		if(structs != null && structs.size() > 0 && response.getStatus() == 0) {
			
			HashSet<Integer> profileIds = new HashSet<>();
			for(Basic basic : structs) {
				if(basic.getProfile_id() > 0) {
					profileIds.add(basic.getProfile_id());
				}
			}
			profileDao.updateUpdateTime(profileIds);
			profileIds.forEach(profileId -> {
				/* 计算用户基本信息的简历完整度 */
				completenessImpl.reCalculateUserUser(profileId);
				completenessImpl.reCalculateProfileBasic(profileId);
			});
		}
		return response;
	}

	@Override
	public Response putResources(List<Basic> structs) throws TException {
		Response response = super.putResources(structs);
		if(structs != null && structs.size() > 0 && response.getStatus() == 0) {
			
			HashSet<Integer> profileIds = new HashSet<>();
			for(Basic basic : structs) {
				if(basic.getProfile_id() > 0) {
					profileIds.add(basic.getProfile_id());
				}
			}
			profileDao.updateUpdateTime(profileIds);
			profileIds.forEach(profileId -> {
				/* 计算用户基本信息的简历完整度 */
				completenessImpl.reCalculateUserUser(profileId);
				completenessImpl.reCalculateProfileBasic(profileId);
			});
		}
		return response;
	}

	@Override
	public Response delResources(List<Basic> structs) throws TException {
		Response response = super.delResources(structs);
		if(structs != null && structs.size() > 0 && response.getStatus() == 0) {
			
			Set<Integer> profileIds = new HashSet<>();
			for(Basic basic : structs) {
				if(basic.getProfile_id() > 0) {
					profileIds.add(basic.getProfile_id());
				}
			}
			profileDao.updateUpdateTime(profileIds);
			profileIds.forEach(profileId -> {
				/* 计算用户基本信息的简历完整度 */
				completenessImpl.reCalculateUserUser(profileId);
				completenessImpl.reCalculateProfileBasic(profileId);
			});
		}
		return response;
	}

	@Override
	public Response delResource(Basic struct) throws TException {
		Response response = super.delResource(struct);
		if(response.getStatus() == 0) {
			
			updateUpdateTime(struct);
			
			/* 计算用户基本信息的简历完整度 */
			completenessImpl.reCalculateUserUser(struct.getProfile_id());
			completenessImpl.reCalculateProfileBasic(struct.getProfile_id());
		}
		return response;
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
	private ProfileDao profileDao;
	
	@Autowired
	private CityDao cityDao;
	
	@Autowired
	private CountryDao countryDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProfileCompletenessImpl completenessImpl;
	
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

	public ProfileDao getProfileDao() {
		return profileDao;
	}

	public void setProfileDao(ProfileDao profileDao) {
		this.profileDao = profileDao;
	}

	public ProfileCompletenessImpl getCompletenessImpl() {
		return completenessImpl;
	}

	public void setCompletenessImpl(ProfileCompletenessImpl completenessImpl) {
		this.completenessImpl = completenessImpl;
	}

	public Response reCalculateBasicCompleteness(int userId) throws TException {
		return null;
	}
	
	private void updateUpdateTime(Basic basic) {
		HashSet<Integer> profileIds = new HashSet<>();
		profileIds.add(basic.getProfile_id());
		profileDao.updateUpdateTime(profileIds);
	}
}
