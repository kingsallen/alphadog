package com.moseeker.profile.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.thrift.TException;
import org.jooq.types.UByte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.db.dictdb.tables.records.DictIndustryRecord;
import com.moseeker.db.dictdb.tables.records.DictPositionRecord;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorkexpRecord;
import com.moseeker.profile.constants.ValidationMessage;
import com.moseeker.profile.dao.CityDao;
import com.moseeker.profile.dao.CompanyDao;
import com.moseeker.profile.dao.IndustryDao;
import com.moseeker.profile.dao.PositionDao;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.profile.dao.WorkExpDao;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.WorkExp;

@Service
@CounterIface
public class ProfileWorkExpService extends JOOQBaseServiceImpl<WorkExp, ProfileWorkexpRecord> {

	Logger logger = LoggerFactory.getLogger(ProfileWorkExpService.class);

	@Autowired
	private WorkExpDao dao;

	@Autowired
	private IndustryDao industryDao;

	@Autowired
	private PositionDao positionDao;

	@Autowired
	private CityDao cityDao;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private ProfileDao profileDao;
	
	@Autowired
	private ProfileCompletenessImpl completenessImpl;

	@Override
	public Response getResources(CommonQuery query) throws TException {
		try {
			// 按照结束时间倒序
			query.setSortby("end_until_now,start");
			query.setOrder("desc,desc");
			
			List<ProfileWorkexpRecord> records = dao.getResources(query);
			if (records != null && records.size() > 0) {
				List<WorkExp> workExps = DBsToStructs(records);
				List<Integer> industryCodes = new ArrayList<>();
				List<Integer> cityCodes = new ArrayList<>();
				List<Integer> companyIds = new ArrayList<>();
				List<Integer> positionCodes = new ArrayList<>();
				records.forEach(record -> {
					if (record.getIndustryCode() != null && record.getIndustryCode().intValue() > 0
							&& StringUtils.isNullOrEmpty(record.getIndustryName())) {
						industryCodes.add(record.getIndustryCode().intValue());
					}
					if (record.getCityCode() != null && record.getCityCode().intValue() > 0
							&& StringUtils.isNullOrEmpty(record.getCityName())) {
						cityCodes.add(record.getCityCode().intValue());
					}
					if(record.getCompanyId() != null && record.getCompanyId().intValue() > 0) {
						companyIds.add(record.getCompanyId().intValue());
					}
					if(record.getPositionCode() != null && record.getPositionCode().intValue() > 0) {
						positionCodes.add(record.getPositionCode().intValue());
					}
				});
				if (workExps.size() > 0) {
					List<DictIndustryRecord> industryRecords = industryDao.getIndustriesByCodes(industryCodes);
					List<DictCityRecord> cityRecords = cityDao.getCitiesByCodes(cityCodes);
					List<HrCompanyRecord> companies = companyDao.getCompaniesByIds(companyIds);
					List<DictPositionRecord> positionRecords = positionDao.getPositionsByCodes(positionCodes);
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
						if(companies != null && companies.size() > 0) {
							for(HrCompanyRecord company : companies) {
								if(workExp.getCompany_id() == company.getId().intValue()) {
									workExp.setCompany_name(company.getName());
									workExp.setCompany_logo(company.getLogo());
									break;
								}
							}
						}
						if(positionRecords != null && positionRecords.size() > 0) {
							for(DictPositionRecord position : positionRecords) {
								if(workExp.getPosition_code() == position.getCode().intValue()) {
									workExp.setPosition_name(position.getName());
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
		try {
			ProfileWorkexpRecord record = dao.getResource(query);
			if (record != null) {
				DictIndustryRecord industryRecord = industryDao.getIndustryByCode(record.getIndustryCode().intValue());
				DictCityRecord cityRecord = cityDao.getCityByCode(record.getCityCode().intValue());
				HrCompanyRecord company = companyDao.getCompanyById(record.getCompanyId().intValue());
				DictPositionRecord positionRecord = positionDao.getPositionByCode(record.getPositionCode().intValue());
				WorkExp workExp = DBToStruct(record);
				if(industryRecord != null) {
					workExp.setIndustry_name(industryRecord.getName());
				}
				if(cityRecord != null) {
					workExp.setCity_name(cityRecord.getName());
				}
				if(company != null) {
					workExp.setCompany_name(company.getName());
					workExp.setCompany_logo(company.getLogo());
				}
				if(positionRecord != null) {
					workExp.setPosition_name(positionRecord.getName());
				}
				return ResponseUtils.success(workExp);
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
	public Response postResource(WorkExp struct) throws TException {
		ValidationMessage<WorkExp> vm = verifyWorkExp(struct);
		if(!vm.isPass()) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}'}", vm.getResult()));
		}
		int i = 0;
		try {
			if(struct.getCity_code() > 0) {
				DictCityRecord cityRecord = cityDao.getCityByCode(struct.getCity_code());
				if(cityRecord != null) {
					struct.setCity_name(cityRecord.getName());
				} else {
					return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_CITY_NOTEXIST);
				}
			}
			if(struct.getPosition_code() > 0) {
				DictPositionRecord positionRecord = positionDao.getPositionByCode(struct.getPosition_code());
				if(positionRecord != null) {
					struct.setPosition_name(positionRecord.getName());
				} else {
					return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_POSITION_NOTEXIST);
				}
			}
			if(struct.getIndustry_code() > 0) {
				DictIndustryRecord industryRecord = industryDao.getIndustryByCode(struct.getIndustry_code());
				if(industryRecord != null) {
					struct.setIndustry_name(industryRecord.getName());
				} else {
					return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_INDUSTRY_NOTEXIST);
				}
			}
			if(struct.getCompany_id() > 0) {
				HrCompanyRecord company = companyDao.getCompanyById(struct.getCompany_id());
				if(company == null) {
					return 	ResponseUtils.fail(ConstantErrorCodeMessage.HRCOMPANY_NOTEXIST);
				} 
			} else {
				if(!StringUtils.isNullOrEmpty(struct.getCompany_name())) {
					QueryUtil qu = new QueryUtil();
					qu.addEqualFilter("name", struct.getCompany_name());
					HrCompanyRecord company = companyDao.getResource(qu);
					if(company != null) {
						struct.setCompany_id(company.getId().intValue());
					} else {
						HrCompanyRecord newCompany = new HrCompanyRecord();
						newCompany.setName(struct.getCompany_name());
						if(!StringUtils.isNullOrEmpty(struct.getCompany_logo())) {
							newCompany.setLogo(struct.getCompany_logo());
						}
						newCompany.setType(UByte.valueOf(Constant.COMPANY_TYPE_FREE));
						newCompany.setSource(UByte.valueOf(struct.getSource()));
						int companyId = companyDao.postResource(newCompany);
						struct.setCompany_id(companyId);
					}
				}
			}
			
			ProfileWorkexpRecord record = structToDB(struct);
			i = dao.postResource(record);
			
			if ( i > 0 ){
				Set<Integer> profileIds = new HashSet<>();
				profileIds.add(struct.getProfile_id());
				profileDao.updateUpdateTime(profileIds);
				/* 计算用户基本信息的简历完整度 */
				completenessImpl.reCalculateProfileWorkExp(struct.getProfile_id(), struct.getId());
				return ResponseUtils.success(String.valueOf(i));
			}	

		} catch (Exception e) {
			logger.error("postResource error", e);
			return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			//do nothing
		}
		
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
	}

	@Override
	public Response putResource(WorkExp struct) throws TException {
		try {
			if(struct.getCity_code() > 0) {
				DictCityRecord cityRecord = cityDao.getCityByCode(struct.getCity_code());
				if(cityRecord != null) {
					struct.setCity_name(cityRecord.getName());
				} else {
					return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_CITY_NOTEXIST);
				}
			}
			if(struct.getPosition_code() > 0) {
				DictPositionRecord positionRecord = positionDao.getPositionByCode(struct.getPosition_code());
				if(positionRecord != null) {
					struct.setPosition_name(positionRecord.getName());
				} else {
					return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_POSITION_NOTEXIST);
				}
			}
			if(struct.getIndustry_code() > 0) {
				DictIndustryRecord industryRecord = industryDao.getIndustryByCode(struct.getIndustry_code());
				if(industryRecord != null) {
					struct.setIndustry_name(industryRecord.getName());
				} else {
					return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_DICT_INDUSTRY_NOTEXIST);
				}
			}
			if(struct.getCompany_id() > 0) {
				HrCompanyRecord company = companyDao.getCompanyById(struct.getCompany_id());
				if(company == null) {
					return 	ResponseUtils.fail(ConstantErrorCodeMessage.HRCOMPANY_NOTEXIST);
				}
			} else {
				if(!StringUtils.isNullOrEmpty(struct.getCompany_name())) {
					QueryUtil qu = new QueryUtil();
					qu.addEqualFilter("name", struct.getCompany_name());
					HrCompanyRecord company = companyDao.getResource(qu);
					if(company != null) {
						struct.setCompany_id(company.getId().intValue());
					} else {
						HrCompanyRecord newCompany = new HrCompanyRecord();
						newCompany.setName(struct.getCompany_name());
						if(!StringUtils.isNullOrEmpty(struct.getCompany_logo())) {
							newCompany.setLogo(struct.getCompany_logo());
						}
						newCompany.setType(UByte.valueOf(Constant.COMPANY_TYPE_FREE));
						newCompany.setSource(UByte.valueOf(struct.getSource()));
						int companyId = companyDao.postResource(newCompany);
						struct.setCompany_id(companyId);
					}
				}
			}
			Response response = super.putResource(struct);
			if(response.getStatus() == 0) {
				updateUpdateTime(struct);
				/* 计算用户基本信息的简历完整度 */
				completenessImpl.reCalculateProfileWorkExp(struct.getProfile_id(), struct.getId());
			}
			return response;
		} catch (Exception e) {
			logger.error("postResource error", e);
			return 	ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			//do nothing
		}
	}
	
	@Override
	public Response postResources(List<WorkExp> structs) throws TException {
		if(structs != null && structs.size() > 0) {
			Iterator<WorkExp> wei = structs.iterator();
			while(wei.hasNext()) {
				WorkExp workExp = wei.next();
				ValidationMessage<WorkExp> vm = verifyWorkExp(workExp);
				if(!vm.isPass()) {
					wei.remove();
				}
			}
		}
		Response response = super.postResources(structs);
		if(structs != null && structs.size() > 0 && response.getStatus() == 0) {
			Set<Integer> profileIds = new HashSet<>();
			for(WorkExp struct : structs) {
				profileIds.add(struct.getProfile_id());
				/* 计算用户基本信息的简历完整度 */
				completenessImpl.reCalculateProfileWorkExp(struct.getProfile_id(), struct.getId());
			}
			profileDao.updateUpdateTime(profileIds);
		}
		return response;
	}

	@Override
	public Response putResources(List<WorkExp> structs) throws TException {
		Response response = super.putResources(structs);
		if(response.getStatus() == 0 && structs != null && structs.size() > 0) {
			for(WorkExp struct : structs) {
				updateUpdateTime(structs);
				/* 计算用户基本信息的简历完整度 */
				completenessImpl.reCalculateProfileWorkExpUseWorkExpId(struct.getId());
			}
		}
		return response;
	}

	@Override
	public Response delResources(List<WorkExp> structs) throws TException {
		QueryUtil qu = new QueryUtil();
		StringBuffer sb = new StringBuffer("[");
		structs.forEach(struct -> {
			sb.append(struct.getId());
			sb.append(",");
		});
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		qu.addEqualFilter("id", sb.toString());

		List<ProfileWorkexpRecord> workExpRecords = null;
		try {
			workExpRecords = dao.getResources(qu);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		Set<Integer> profileIds = new HashSet<>();
		if (workExpRecords != null && workExpRecords.size() > 0) {
			workExpRecords.forEach(workExp -> {
				profileIds.add(workExp.getProfileId().intValue());
			});
		}
		Response response = super.delResources(structs);
		if(response.getStatus() == 0 && profileIds != null && profileIds.size() > 0) {
			profileIds.forEach(profileId -> {
				updateUpdateTime(structs);
				/* 计算用户基本信息的简历完整度 */
				completenessImpl.reCalculateProfileWorkExp(profileId, 0);
			});
		}
		return response;
	}

	@Override
	public Response delResource(WorkExp struct) throws TException {
		Response response = super.delResource(struct);
		if(response.getStatus() == 0) {
			updateUpdateTime(struct);
			/* 计算用户基本信息的简历完整度 */
			completenessImpl.reCalculateProfileWorkExp(struct.getProfile_id(), struct.getId());
		}
		return response;
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

	public CompanyDao getCompanyDao() {
		return companyDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	public void setCityDao(CityDao cityDao) {
		this.cityDao = cityDao;
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

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}
	
	public ValidationMessage<WorkExp> verifyWorkExp(WorkExp workExp) {
		ValidationMessage<WorkExp> vm = new ValidationMessage<>();
		if(workExp.getCompany_id() == 0 && StringUtils.isNullOrEmpty(workExp.getCompany_name())) {
			vm.addFailedElement("就职公司", "未填写就职公司");
		}
		if(StringUtils.isNullOrEmpty(workExp.getJob())) {
			vm.addFailedElement("职位名称", "未填写职位名称");
		}
		if(StringUtils.isNullOrEmpty(workExp.getStart_date())) {
			vm.addFailedElement("开始时间", "未填写开始时间");
		}
		if(StringUtils.isNullOrEmpty(workExp.getDescription())) {
			vm.addFailedElement("职位描述", "未对该职位做详细描述");
		}
		return vm;
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
	
	private void updateUpdateTime(List<WorkExp> workExps) {
		Set<Integer> workExpIds = new HashSet<>();
		workExps.forEach(workExp -> {
			workExpIds.add(workExp.getId());
		});
		dao.updateProfileUpdateTime(workExpIds);
	}

	private void updateUpdateTime(WorkExp workExp) {
		List<WorkExp> workExps = new ArrayList<>();
		workExps.add(workExp);
		updateUpdateTime(workExps);
	}
}
