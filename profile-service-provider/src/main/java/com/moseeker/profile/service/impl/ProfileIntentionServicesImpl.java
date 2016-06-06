package com.moseeker.profile.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.thrift.TException;
import org.jooq.types.UInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.db.dictdb.tables.records.DictIndustryRecord;
import com.moseeker.db.dictdb.tables.records.DictPositionRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionCityRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionIndustryRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionPositionRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionRecord;
import com.moseeker.profile.dao.CityDao;
import com.moseeker.profile.dao.IndustryDao;
import com.moseeker.profile.dao.IntentionCityDao;
import com.moseeker.profile.dao.IntentionDao;
import com.moseeker.profile.dao.IntentionIndustryDao;
import com.moseeker.profile.dao.IntentionPositionDao;
import com.moseeker.profile.dao.PositionDao;
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
	private CityDao dictCityDao;
	
	@Autowired
	private IndustryDao dictIndustryDao;
	
	@Autowired
	private PositionDao dictPositionDao;

	@Autowired
	private IntentionIndustryDao intentionIndustryDao;

	@Autowired
	private IntentionPositionDao intentionPositionDao;

	@Override
	public Response getResources(CommonQuery query) throws TException {
		try {
			List<Map<String, Object>> intentionsMap = new ArrayList<>();
			List<ProfileIntentionRecord> records = dao.getResources(query);
			if (records != null && records.size() > 0) {
				List<Integer> intentionIds = new ArrayList<>();
				records.forEach(record -> {
					intentionIds.add(record.getId().intValue());
				});
				//List<Intention> intentions = DBsToStructs(records);
				if (intentionIds.size() > 0) {
					List<ProfileIntentionCityRecord> cities = intentionCityDao.getIntentionCities(intentionIds);
					List<ProfileIntentionIndustryRecord> industries = intentionIndustryDao
							.getIntentionIndustries(intentionIds);
					List<ProfileIntentionPositionRecord> positions = intentionPositionDao
							.getIntentionPositions(intentionIds);
					for (ProfileIntentionRecord intention : records) {
						Map<String, Object> intentionMap = new HashMap<>();
						intentionMap.put("id", intention.getId().intValue());
						intentionMap.put("profile_id", intention.getProfileId().intValue());
						if(intention.getWorktype() != null) {
							intentionMap.put("worktype", intention.getWorktype().intValue());
						} else {
							intentionMap.put("worktype", null);
						}
						if(intention.getWorkstate() != null) {
							intentionMap.put("workstate", intention.getWorkstate().intValue());
						} else {
							intentionMap.put("workstate", null);
						}
						if(intention.getSalaryType() != null) {
							intentionMap.put("salary_type", intention.getSalaryType().intValue());
						} else {
							intentionMap.put("salary_type", null);
						}
						if(intention.getSalaryCode() != null) {
							intentionMap.put("salary_code", intention.getSalaryCode().intValue());
						} else {
							intentionMap.put("salary_code", null);
						}
						intentionMap.put("tag", intention.getTag());
						if(intention.getConsiderVentureCompanyOpportunities() != null) {
							intentionMap.put("consider_venture_company_opportunities", intention.getConsiderVentureCompanyOpportunities().intValue());
						} else {
							intentionMap.put("consider_venture_company_opportunities", null);
						}
						if(intention.getCreateTime() != null) {
							intentionMap.put("create_time", DateUtils.dateToShortTime(intention.getCreateTime()));
						} else {
							intentionMap.put("create_time", null);
						}
						if(intention.getUpdateTime() != null) {
							intentionMap.put("update_time", DateUtils.dateToShortTime(intention.getUpdateTime()));
						} else {
							intentionMap.put("update_time", null);
						}
						List<Map<String, Object>> cityMaps = new ArrayList<>();
						if (cities != null && cities.size() > 0) {
							for (ProfileIntentionCityRecord record : cities) {
								if (intention.getId().intValue() == record.getProfileIntentionId().intValue()) {
									Map<String, Object> cityMap = new HashMap<>();
									cityMap.put("city_code", record.getCityCode().intValue());
									cityMap.put("city_name", record.getCityName());
									cityMaps.add(cityMap);
								}
							}
						}
						intentionMap.put("cities", cityMaps);
						
						List<Map<String,Object>> industryMaps = new ArrayList<>();
						if (industries != null && industries.size() > 0) {
							Map<String, Object> industryMap = new HashMap<>();
							for (ProfileIntentionIndustryRecord record : industries) {
								if (intention.getId().intValue() == record.getProfileIntentionId().intValue()) {
									industryMap.put("industry_code", record.getIndustryCode().intValue());
									industryMap.put("industry_name", record.getIndustryName());
									industryMaps.add(industryMap);
								}
							}
						}
						intentionMap.put("industries", industryMaps);
						
						List<Map<String, Object>> positionMaps = new ArrayList<>();
						if (positions != null && positions.size() > 0) {
							for (ProfileIntentionPositionRecord record : positions) {
								if (intention.getId().intValue() == record.getProfileIntentionId().intValue()) {
									Map<String, Object> positionMap = new HashMap<>();
									positionMap.put("position_code", record.getPositionCode().intValue());
									positionMap.put("position_name", record.getPositionName());
									positionMaps.add(positionMap);
								}
							}
						}
						intentionMap.put("positions", positionMaps);
						
						intentionsMap.add(intentionMap);
					}
					return ResponseUtils.success(intentionsMap);
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
	public Response postResource(Intention struct) throws TException {
		ProfileIntentionRecord record = null;
		try {
			record = structToDB(struct);
			ProfileIntentionRecord repeatIntention = null;
			if(struct.isSetProfile_id()) {
				QueryUtil query = new QueryUtil();
				query.setPage(1);
				query.setPer_page(1);
				query.addEqualFilter("profile_id", String.valueOf(struct.getProfile_id()));
				repeatIntention = dao.getResource(query);
			}
			if(repeatIntention != null) {
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_REPEAT_DATA);
			}
			int intentionId = dao.postResource(record);
			if(intentionId > 0) {
				updateIntentionCity(struct, intentionId);
				updateIntentionIndustry(struct, intentionId);
				updateIntentionPosition(struct, intentionId);
				return ResponseUtils.success(String.valueOf(intentionId));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			// do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
	}
	
	@Override
	public Response putResource(Intention struct) throws TException {
		ProfileIntentionRecord record = null;
		try {
			record = structToDB(struct);
			int intentionId = dao.putResource(record);
			if(intentionId > 0) {
				updateIntentionCity(struct, record.getId().intValue());
				updateIntentionIndustry(struct, record.getId().intValue());
				updateIntentionPosition(struct, record.getId().intValue());
				return ResponseUtils.success(String.valueOf(intentionId));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			// do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
	}

	@Override
	public Response delResource(Intention struct) throws TException {
		ProfileIntentionRecord record = null;
		try {
			record = structToDB(struct);
			int intentionId = dao.delResource(record);
			if(intentionId > 0) {
				ProfileIntentionCityRecord intentionCityRecord = new ProfileIntentionCityRecord();
				intentionCityRecord.setProfileIntentionId(UInteger.valueOf(struct.getId()));
				intentionCityDao.delResource(intentionCityRecord);
				ProfileIntentionPositionRecord intentionPositionRecord = new ProfileIntentionPositionRecord();
				intentionPositionRecord.setProfileIntentionId(UInteger.valueOf(struct.getId()));
				intentionPositionDao.delResource(intentionPositionRecord);
				ProfileIntentionIndustryRecord intentionIndustryRecord = new ProfileIntentionIndustryRecord();
				intentionIndustryRecord.setProfileIntentionId(UInteger.valueOf(struct.getId()));
				intentionIndustryDao.delResource(intentionIndustryRecord);
				return ResponseUtils.success(String.valueOf(intentionId));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
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

	private void updateIntentionPosition(Intention struct, int intentionId) throws Exception {
		QueryUtil positionQuery = new QueryUtil();
		positionQuery.setPage(1);
		positionQuery.setPer_page(Integer.MAX_VALUE);
		List<DictPositionRecord> positionRecordList = dictPositionDao.getResources(positionQuery);

		QueryUtil selectedPositionQuery = new QueryUtil();
		selectedPositionQuery.addEqualFilter("profile_intention_id", String.valueOf(intentionId));
		List<ProfileIntentionPositionRecord> selectedPositionRecords = intentionPositionDao.getResources(selectedPositionQuery);

		// 待添加的期望工作行业
		List<ProfileIntentionPositionRecord> toBeAddList = new ArrayList<>();

		if(struct.getPositions() != null) {
			Map<String, Integer> positions = struct.getPositions();
			for (Entry<String, Integer> entry : positions.entrySet()) {
				boolean exist = false; //在已选择的期望城市中查找分离出待添加的期望城市
				if (selectedPositionRecords != null && selectedPositionRecords.size() > 0) {
					Iterator<ProfileIntentionPositionRecord> it = selectedPositionRecords.iterator();
					while(it.hasNext()) {
						ProfileIntentionPositionRecord selectedPositionRecord = it.next();
						if (isSameOfPositionRecord(entry, selectedPositionRecord)) {
							exist = true;
							it.remove();
							break;
						}
					}
				}
				if(!exist) {
					if(entry.getValue() != null && entry.getValue().intValue() > 0) {
						
						DictPositionRecord legalRecord = null;
						for(DictPositionRecord dictPositionRecord : positionRecordList) {
							if(dictPositionRecord.getCode().intValue() == entry.getValue().intValue()) {
								legalRecord = dictPositionRecord;
								break;
							}
						}
						if(legalRecord != null) {
							ProfileIntentionPositionRecord tobeAddPositionRecord = new ProfileIntentionPositionRecord();
							tobeAddPositionRecord.setPositionCode(legalRecord.getCode());
							tobeAddPositionRecord.setPositionName(legalRecord.getName());
							tobeAddPositionRecord.setProfileIntentionId(UInteger.valueOf(intentionId));
							toBeAddList.add(tobeAddPositionRecord);
						}
					} else {
						//如果没有key的情况下，只有name有值才有意义
						if(!StringUtils.isNullOrEmpty(entry.getKey())) {
							ProfileIntentionPositionRecord tobeAddPositionRecord = new ProfileIntentionPositionRecord();
							tobeAddPositionRecord.setPositionCode(UInteger.valueOf(0));
							tobeAddPositionRecord.setPositionName(entry.getKey());
							tobeAddPositionRecord.setProfileIntentionId(UInteger.valueOf(intentionId));
							toBeAddList.add(tobeAddPositionRecord);
						}
					}
				}
			}
		}
		if(toBeAddList.size() > 0) {
			intentionPositionDao.postResources(toBeAddList);
		}
		if(selectedPositionRecords != null && selectedPositionRecords.size() > 0) {
			intentionPositionDao.delResources(selectedPositionRecords);
		}
	}

	private void updateIntentionIndustry(Intention struct, int intentionId) throws Exception {
		QueryUtil industryQuery = new QueryUtil();
		industryQuery.setPage(1);
		industryQuery.setPer_page(Integer.MAX_VALUE);
		List<DictIndustryRecord> industryRecordList = dictIndustryDao.getResources(industryQuery);

		QueryUtil selectedIndustryQuery = new QueryUtil();
		selectedIndustryQuery.addEqualFilter("profile_intention_id", String.valueOf(intentionId));
		List<ProfileIntentionIndustryRecord> selectedIndustryRecords = intentionIndustryDao.getResources(selectedIndustryQuery);

		// 待添加的期望工作行业
		List<ProfileIntentionIndustryRecord> toBeAddList = new ArrayList<>();

		if(struct.getIndustries() != null) {
			Map<String, Integer> industries = struct.getIndustries();
			for (Entry<String, Integer> entry : industries.entrySet()) {
				boolean exist = false; //在已选择的期望城市中查找分离出待添加的期望城市
				if (selectedIndustryRecords != null && selectedIndustryRecords.size() > 0) {
					Iterator<ProfileIntentionIndustryRecord> it = selectedIndustryRecords.iterator();
					while(it.hasNext()) {
						ProfileIntentionIndustryRecord selectedIndustryRecord = it.next();
						if (isSameOfIndustryRecord(entry, selectedIndustryRecord)) {
							exist = true;
							it.remove();
							break;
						}
					}
				}
				if(!exist) {
					if(entry.getValue() != null && entry.getValue().intValue() > 0) {
						
						DictIndustryRecord legalRecord = null;
						for(DictIndustryRecord dictIndustryRecord : industryRecordList) {
							if(dictIndustryRecord.getCode().intValue() == entry.getValue().intValue()) {
								legalRecord = dictIndustryRecord;
								break;
							}
						}
						if(legalRecord != null) {
							ProfileIntentionIndustryRecord tobeAddIndustryRecord = new ProfileIntentionIndustryRecord();
							tobeAddIndustryRecord.setIndustryCode(legalRecord.getCode());
							tobeAddIndustryRecord.setIndustryName(legalRecord.getName());
							tobeAddIndustryRecord.setProfileIntentionId(UInteger.valueOf(intentionId));
							toBeAddList.add(tobeAddIndustryRecord);
						}
					} else {
						//如果没有key的情况下，只有name有值才有意义
						if(!StringUtils.isNullOrEmpty(entry.getKey())) {
							ProfileIntentionIndustryRecord tobeAddIndustryRecord = new ProfileIntentionIndustryRecord();
							tobeAddIndustryRecord.setIndustryCode(UInteger.valueOf(0));
							tobeAddIndustryRecord.setIndustryName(entry.getKey());
							tobeAddIndustryRecord.setProfileIntentionId(UInteger.valueOf(intentionId));
							toBeAddList.add(tobeAddIndustryRecord);
						}
					}
				}
			}
		}
		if(toBeAddList.size() > 0) {
			intentionIndustryDao.postResources(toBeAddList);
		}
		if(selectedIndustryRecords != null && selectedIndustryRecords.size() > 0) {
			intentionIndustryDao.delResources(selectedIndustryRecords);
		}
	}

	/**
	 * 修改意向城市
	 * @param struct
	 * @param intentionId
	 * @throws Exception
	 */
	private void updateIntentionCity(Intention struct, int intentionId) throws Exception {
		QueryUtil cityQuery = new QueryUtil();
		cityQuery.setPage(1);
		cityQuery.setPer_page(Integer.MAX_VALUE);
		List<DictCityRecord> cityRecordList = dictCityDao.getResources(cityQuery);

		QueryUtil selectedCityQuery = new QueryUtil();
		selectedCityQuery.addEqualFilter("profile_intention_id", String.valueOf(intentionId));
		List<ProfileIntentionCityRecord> selectedCityRecords = intentionCityDao.getResources(selectedCityQuery);

		// 待添加的期望工作城市
		List<ProfileIntentionCityRecord> toBeAddList = new ArrayList<>();

		if(struct.getCities() != null) {
			Map<String, Integer> cities = struct.getCities();
			for (Entry<String, Integer> entry : cities.entrySet()) {
				boolean exist = false; //在已选择的期望城市中查找分离出待添加的期望城市
				if (selectedCityRecords != null && selectedCityRecords.size() > 0) {
					Iterator<ProfileIntentionCityRecord> it = selectedCityRecords.iterator();
					while(it.hasNext()) {
						ProfileIntentionCityRecord selectedCityRecord = it.next();
						if (isSameOfCityRecord(entry, selectedCityRecord)) {
							exist = true;
							it.remove();
							break;
						}
					}
				}
				if(!exist) {
					if(entry.getValue() != null && entry.getValue().intValue() > 0) {
						
						DictCityRecord legalRecord = null;
						for(DictCityRecord dictCityRecord : cityRecordList) {
							if(dictCityRecord.getCode().intValue() == entry.getValue().intValue()) {
								legalRecord = dictCityRecord;
								break;
							}
						}
						if(legalRecord != null) {
							ProfileIntentionCityRecord tobeAddCityRecord = new ProfileIntentionCityRecord();
							tobeAddCityRecord.setCityCode(legalRecord.getCode());
							tobeAddCityRecord.setCityName(legalRecord.getName());
							tobeAddCityRecord.setProfileIntentionId(UInteger.valueOf(intentionId));
							toBeAddList.add(tobeAddCityRecord);
						}
					} else {
						//如果没有key的情况下，只有name有值才有意义
						if(!StringUtils.isNullOrEmpty(entry.getKey())) {
							ProfileIntentionCityRecord tobeAddCityRecord = new ProfileIntentionCityRecord();
							tobeAddCityRecord.setCityCode(UInteger.valueOf(0));
							tobeAddCityRecord.setCityName(entry.getKey());
							tobeAddCityRecord.setProfileIntentionId(UInteger.valueOf(intentionId));
							toBeAddList.add(tobeAddCityRecord);
						}
					}
				}
			}
		}
		if(toBeAddList.size() > 0) {
			intentionCityDao.postResources(toBeAddList);
		}
		if(selectedCityRecords != null && selectedCityRecords.size() > 0) {
			intentionCityDao.delResources(selectedCityRecords);
		}
	}
	
	private boolean isSameOfPositionRecord(Entry<String, Integer> entry,
			ProfileIntentionPositionRecord selectedPositionRecord) {
		if (((entry.getValue() == null || entry.getValue().intValue() == 0)
				&& (selectedPositionRecord.getPositionCode() == null || selectedPositionRecord.getPositionCode().intValue() == 0))
				&& (entry.getKey() != null && entry.getKey().equals(selectedPositionRecord.getPositionName()))) {
			return true;
		} else if (entry.getValue().intValue() > 0
				&& entry.getValue().intValue() == selectedPositionRecord.getPositionCode().intValue()) {
			return true;
		}
		return false;
	}

	private boolean isSameOfCityRecord(Entry<String, Integer> entry, ProfileIntentionCityRecord selectedCityRecord) {
		if (((entry.getValue() == null || entry.getValue().intValue() == 0)
				&& (selectedCityRecord.getCityCode() == null || selectedCityRecord.getCityCode().intValue() == 0))
				&& (entry.getKey() != null && entry.getKey().equals(selectedCityRecord.getCityName()))) {
			return true;
		} else if (entry.getValue().intValue() > 0
				&& entry.getValue().intValue() == selectedCityRecord.getCityCode().intValue()) {
			return true;
		}
		return false;
	}
	
	private boolean isSameOfIndustryRecord(Entry<String, Integer> entry,
			ProfileIntentionIndustryRecord selectedIndustryRecord) {
		if (((entry.getValue() == null || entry.getValue().intValue() == 0)
				&& (selectedIndustryRecord.getIndustryCode() == null || selectedIndustryRecord.getIndustryCode().intValue() == 0))
				&& (entry.getKey() != null && entry.getKey().equals(selectedIndustryRecord.getIndustryName()))) {
			return true;
		} else if (entry.getValue().intValue() > 0
				&& entry.getValue().intValue() == selectedIndustryRecord.getIndustryCode().intValue()) {
			return true;
		}
		return false;
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

	public CityDao getDictCityDao() {
		return dictCityDao;
	}

	public void setDictCityDao(CityDao dictCityDao) {
		this.dictCityDao = dictCityDao;
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
