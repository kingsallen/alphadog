package com.moseeker.profile.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.thrift.TException;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.db.dictdb.tables.records.DictCollegeRecord;
import com.moseeker.db.dictdb.tables.records.DictConstantRecord;
import com.moseeker.db.dictdb.tables.records.DictCountryRecord;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.db.profiledb.tables.records.ProfileAttachmentRecord;
import com.moseeker.db.profiledb.tables.records.ProfileAwardsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.db.profiledb.tables.records.ProfileImportRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionCityRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionIndustryRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionPositionRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionRecord;
import com.moseeker.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.db.profiledb.tables.records.ProfileOtherRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorkexpRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorksRecord;
import com.moseeker.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.profile.dao.AttachmentDao;
import com.moseeker.profile.dao.AwardsDao;
import com.moseeker.profile.dao.CollegeDao;
import com.moseeker.profile.dao.CompanyDao;
import com.moseeker.profile.dao.ConstantDao;
import com.moseeker.profile.dao.CountryDao;
import com.moseeker.profile.dao.CredentialsDao;
import com.moseeker.profile.dao.CustomizeResumeDao;
import com.moseeker.profile.dao.EducationDao;
import com.moseeker.profile.dao.IntentionCityDao;
import com.moseeker.profile.dao.IntentionDao;
import com.moseeker.profile.dao.IntentionIndustryDao;
import com.moseeker.profile.dao.IntentionPositionDao;
import com.moseeker.profile.dao.JobPositionDao;
import com.moseeker.profile.dao.LanguageDao;
import com.moseeker.profile.dao.ProfileBasicDao;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.profile.dao.ProfileImportDao;
import com.moseeker.profile.dao.ProjectExpDao;
import com.moseeker.profile.dao.SkillDao;
import com.moseeker.profile.dao.UserDao;
import com.moseeker.profile.dao.UserSettingsDao;
import com.moseeker.profile.dao.WorkExpDao;
import com.moseeker.profile.dao.WorksDao;
import com.moseeker.profile.dao.entity.ProfileWorkexpEntity;
import com.moseeker.profile.dao.impl.IntentionRecord;
import com.moseeker.profile.service.impl.serviceutils.ProfileUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices.Iface;

@Service
public class WholeProfileServicesImpl implements Iface {

	Logger logger = LoggerFactory.getLogger(WholeProfileServicesImpl.class);
	ProfileUtils profileUtils = new ProfileUtils();

	@Override
	public Response getResource(int userId, int profileId, String uuid) throws TException {
		Response response = new Response();
		try {
			HashMap<String, Object> profile = new HashMap<String, Object>();

			ProfileProfileRecord profileRecord = profileDao.getProfileByIdOrUserIdOrUUID(userId, profileId, uuid);
			if (profileRecord != null) {
				CommonQuery query = new CommonQuery();
				HashMap<String, String> equalFilter = new HashMap<String, String>();
				equalFilter.put("profile_id", String.valueOf(profileRecord.getId()));
				query.setEqualFilter(equalFilter);

				CommonQuery profileQuery = new CommonQuery();
				HashMap<String, String> profileEqualFilter = new HashMap<String, String>();
				profileEqualFilter.put("id", String.valueOf(profileRecord.getId()));
				profileQuery.setEqualFilter(profileEqualFilter);
				
				List<DictConstantRecord> constantRecords = constantDao.getCitiesByParentCodes(Arrays.asList(3109, 2103, 3102, 2105, 3120, 3115, 3114, 3119, 3120));
				
				Map<String, Object> profileprofile = buildProfile(profileRecord, query, constantRecords);
				profile.put("profile", profileprofile);

				Map<String, Object> basic = buildBasic(profileRecord, query, constantRecords);
				profile.put("basic", basic);

				List<Map<String, Object>> workexps = buildWorkexps(profileRecord, query);
				profile.put("workexps", workexps);

				List<Map<String, Object>> educations = buildEducations(profileRecord, query);
				profile.put("educations", educations);

				List<Map<String, Object>> projectexps = buildProjectexps(profileRecord, query);
				profile.put("projectexps", projectexps);

				List<Map<String, Object>> languages = buildLanguage(profileRecord, query);
				profile.put("languages", languages);

				List<Map<String, Object>> skills = buildskills(profileRecord, query);
				profile.put("skills", skills);

				List<Map<String, Object>> credentials = buildsCredentials(profileRecord, query);
				profile.put("credentials", credentials);

				List<Map<String, Object>> awards = buildsAwards(profileRecord, query);
				profile.put("awards", awards);

				List<Map<String, Object>> works = buildsWorks(profileRecord, query);
				profile.put("works", works);

				List<Map<String, Object>> intentions = buildsIntentions(profileRecord, query, constantRecords);
				profile.put("intentions", intentions);

				List<ProfileAttachmentRecord> attachmentRecords = attachmentDao.getResources(query);
				List<Map<String, Object>> attachments = profileUtils.buildAttachments(profileRecord, attachmentRecords);
				profile.put("attachments", attachments);

				List<ProfileImportRecord> importRecords = profileImportDao.getResources(query);
				List<Map<String, Object>> imports = profileUtils.buildImports(profileRecord, importRecords);
				profile.put("imports", imports);

				List<ProfileOtherRecord> otherRecords = customizeResumeDao.getResources(query);
				List<Map<String, Object>> others = profileUtils.buildOthers(profileRecord, otherRecords);
				
				profile.put("others", others);
				return ResponseUtils.success(profile);
				// response.setData(gson.toJson(profile));
			} else {
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response.setStatus(0);
			response.setMessage(Constant.TIPS_ERROR);
			response.setData(Constant.NONE_JSON);
		} finally {
			// do nothing
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Response postResource(String profile, int userId) throws TException {
		if (!StringUtils.isNullOrEmpty(profile)) {
			Map<String, Object> resume = JSON.parseObject(profile);
			
			ProfileProfileRecord profileRecord = profileUtils.mapToProfileRecord((Map<String, Object>) resume.get("profile"));
			UserUserRecord userRecord = userDao.getUserById(userId);
			if(profileRecord == null) {
				profileRecord = new ProfileProfileRecord();
			}
			if (userRecord == null) {
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
			}
			profileRecord.setUuid(UUID.randomUUID().toString());
			profileRecord.setUserId(userRecord.getId());
			profileRecord.setDisable(UByte.valueOf(Constant.ENABLE));
			
			ProfileProfileRecord repeatProfileRecord = profileDao.getProfileByIdOrUserIdOrUUID(profileRecord.getUserId().intValue(), 0, null);
			if(repeatProfileRecord != null) {
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_ALLREADY_EXIST);
			}
			UserUserRecord crawlerUser = profileUtils.mapToUserUserRecord((Map<String, Object>) resume.get("user"));
			if((userRecord.getMobile() == null || userRecord.getMobile() == 0) && crawlerUser.getMobile() != null) {
				userRecord.setMobile(crawlerUser.getMobile());
			}
			if(crawlerUser != null && !StringUtils.isNullOrEmpty(crawlerUser.getName())) {
				userRecord.setName(crawlerUser.getName());
			}
			ProfileBasicRecord basicRecord = profileUtils.mapToBasicRecord((Map<String, Object>) resume.get("basic"));
			if(basicRecord != null && StringUtils.isNullOrEmpty(basicRecord.getName())) {
				basicRecord.setName(userRecord.getName());
			}
			List<ProfileAttachmentRecord> attachmentRecords = profileUtils.mapToAttachmentRecords(
					(List<Map<String, Object>>) resume.get("attachments"));
			List<ProfileAwardsRecord> awardsRecords = profileUtils.mapToAwardsRecords(
					(List<Map<String, Object>>) resume.get("awards"));
			List<ProfileCredentialsRecord> credentialsRecords = profileUtils.mapToCredentialsRecords(
					(List<Map<String, Object>>) resume.get("credentials"));
			List<ProfileEducationRecord> educationRecords = profileUtils.mapToEducationRecords(
					(List<Map<String, Object>>) resume.get("educations"));
			
			ProfileImportRecord importRecords = profileUtils.mapToImportRecord((Map<String, Object>) resume.get("import"),
					userRecord.getUsername());
			List<IntentionRecord> intentionRecords = profileUtils.mapToIntentionRecord(
					(List<Map<String, Object>>) resume.get("intentions"));
			List<ProfileLanguageRecord> languages = profileUtils.mapToLanguageRecord(
					(List<Map<String, Object>>) resume.get("languages"));
			ProfileOtherRecord otherRecord = profileUtils.mapToOtherRecord((Map<String, Object>) resume.get("other"));
			List<ProfileProjectexpRecord> projectExps = profileUtils.mapToProjectExpsRecords(
					(List<Map<String, Object>>) resume.get("projectexps"));
			List<ProfileSkillRecord> skillRecords = profileUtils.mapToSkillRecords(
					(List<Map<String, Object>>) resume.get("skills"));
			List<ProfileWorkexpEntity> workexpRecords = profileUtils.mapToWorkexpRecords(
					(List<Map<String, Object>>) resume.get("workexps"));
			List<ProfileWorksRecord> worksRecords = profileUtils.mapToWorksRecords(
					(List<Map<String, Object>>) resume.get("works"));

			int id = profileDao.saveProfile(profileRecord, basicRecord, attachmentRecords, awardsRecords,
					credentialsRecords, educationRecords, importRecords, intentionRecords, languages, otherRecord,
					projectExps, skillRecords, workexpRecords, worksRecords, userRecord);
			if(id > 0) {
				return ResponseUtils.success(String.valueOf(id));
			}
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Response importCV(String profile, int userId) throws TException {
		
		Map<String, Object> resume = JSON.parseObject(profile);
		ProfileProfileRecord profileRecord = profileUtils.mapToProfileRecord((Map<String, Object>) resume.get("profile"));
		if(profileRecord == null) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_ILLEGAL);
		}
		UserUserRecord userRecord = userDao.getUserById(profileRecord.getUserId().intValue());
		if(userRecord == null) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
		}
		//ProfileProfileRecord profileRecord = profileUtils.mapToProfileRecord((Map<String, Object>) resume.get("user_user"));
		List<ProfileProfileRecord> oldProfile = profileDao.getProfilesByIdOrUserIdOrUUID(userId, 0, null);
		
		profileRecord.setUuid(UUID.randomUUID().toString());
		profileRecord.setUserId(userRecord.getId());
		profileRecord.setSource(UInteger.valueOf(Constant.PROFILE_SOURCE_IMPORT));
		profileRecord.setDisable(UByte.valueOf(Constant.ENABLE));
		
		ProfileBasicRecord basicRecord = profileUtils.mapToBasicRecord((Map<String, Object>) resume.get("basic"));
		List<ProfileAttachmentRecord> attachmentRecords = profileUtils.mapToAttachmentRecords(
				(List<Map<String, Object>>) resume.get("attachments"));
		List<ProfileAwardsRecord> awardsRecords = profileUtils.mapToAwardsRecords(
				(List<Map<String, Object>>) resume.get("awards"));
		List<ProfileCredentialsRecord> credentialsRecords = profileUtils.mapToCredentialsRecords(
				(List<Map<String, Object>>) resume.get("credentials"));
		List<ProfileEducationRecord> educationRecords = profileUtils.mapToEducationRecords(
				(List<Map<String, Object>>) resume.get("educations"));
		ProfileImportRecord importRecords = profileUtils.mapToImportRecord((Map<String, Object>) resume.get("import"),
				userRecord.getUsername());
		List<IntentionRecord> intentionRecords = profileUtils.mapToIntentionRecord(
				(List<Map<String, Object>>) resume.get("intentions"));
		List<ProfileLanguageRecord> languages = profileUtils.mapToLanguageRecord(
				(List<Map<String, Object>>) resume.get("languages"));
		ProfileOtherRecord otherRecord = profileUtils.mapToOtherRecord((Map<String, Object>) resume.get("other"));
		List<ProfileProjectexpRecord> projectExps = profileUtils.mapToProjectExpsRecords(
				(List<Map<String, Object>>) resume.get("projectexps"));
		List<ProfileSkillRecord> skillRecords = profileUtils.mapToSkillRecords(
				(List<Map<String, Object>>) resume.get("skills"));
		List<ProfileWorkexpEntity> workexpRecords = profileUtils.mapToWorkexpRecords(
				(List<Map<String, Object>>) resume.get("workexps"));
		List<ProfileWorksRecord> worksRecords = profileUtils.mapToWorksRecords(
				(List<Map<String, Object>>) resume.get("works"));
		int id = profileDao.saveProfile(profileRecord, basicRecord, attachmentRecords, awardsRecords,
				credentialsRecords, educationRecords, importRecords, intentionRecords, languages, otherRecord,
				projectExps, skillRecords, workexpRecords, worksRecords, userRecord);
		if(id > 0) {
			if(oldProfile != null && oldProfile.size() > 0) {
				for(ProfileProfileRecord record : oldProfile) {
					clearProfile(record.getId().intValue());
				}
			}
			return ResponseUtils.success(id);
		} else {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
		}
	}

	private int clearProfile(int profileId) {
		return profileDao.deleteProfile(profileId);
	}

	@Override
	public Response verifyRequires(int userId, int positionId) throws TException {
		UserUserRecord userRecord = userDao.getUserById(userId);
		JobPositionRecord positionRecord = jobPositionDao.getPositionById(positionId);
		if(userRecord == null) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
		}
		if(positionRecord == null) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_POSITION_NOTEXIST);
		}
		if(positionRecord.getAppCvConfigId() != null && positionRecord.getAppCvConfigId().intValue() > 0) {
			return ResponseUtils.success(true);
		} else {
			return ResponseUtils.success(false);
		}
	}

	private List<Map<String, Object>> buildsIntentions(ProfileProfileRecord profileRecord, CommonQuery query, List<DictConstantRecord> constantRecords) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			List<ProfileIntentionRecord> records = intentionDao.getResources(query);
			if (records != null && records.size() > 0) {
				List<Integer> intentionIds = new ArrayList<>();
				records.forEach(record -> {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", record.getId().intValue());
					map.put("worktype", record.getWorktype().intValue());
					for(DictConstantRecord constantRecord : constantRecords) {
						if(constantRecord.getParentCode().intValue() == 2103 && constantRecord.getCode().intValue() == record.getWorktype().intValue()) {
							map.put("worktype_name", constantRecord.getName());
							break;
						}
					}
					map.put("workstate", record.getWorkstate().intValue());
					for(DictConstantRecord constantRecord : constantRecords) {
						if(constantRecord.getParentCode().intValue() == 3102 && constantRecord.getCode().intValue() == record.getWorkstate().intValue()) {
							map.put("workstate_name", constantRecord.getName());
							break;
						}
					}
					map.put("salary_type", record.getSalaryType().intValue());
					for(DictConstantRecord constantRecord : constantRecords) {
						if(constantRecord.getParentCode().intValue() == 2105 && constantRecord.getCode().intValue() == record.getSalaryType().intValue()) {
							map.put("salary_type_name", constantRecord.getName());
							break;
						}
					}
					map.put("salary_code", record.getSalaryCode().intValue());
					for(DictConstantRecord constantRecord : constantRecords) {
						if(constantRecord.getParentCode().intValue() == 3114 && constantRecord.getCode().intValue() == record.getSalaryCode().intValue()) {
							map.put("salary_code_name", constantRecord.getName());
							break;
						}
					}
					map.put("tag", record.getTag());
					map.put("consider_venture_company_opportunities",
							record.getConsiderVentureCompanyOpportunities().intValue());
					for(DictConstantRecord constantRecord : constantRecords) {
						if(constantRecord.getParentCode().intValue() == 3120 && constantRecord.getCode().intValue() == record.getSalaryCode().intValue()) {
							map.put("consider_venture_company_opportunities_name", constantRecord.getName());
							break;
						}
					}
					intentionIds.add(record.getId().intValue());
					list.add(map);
				});
				List<ProfileIntentionCityRecord> cityRecords = intentionCityDao.getIntentionCities(intentionIds);
				List<ProfileIntentionIndustryRecord> industryRecords = intentionIndustryDao
						.getIntentionIndustries(intentionIds);
				List<ProfileIntentionPositionRecord> positionRecords = intentionPositionDao
						.getIntentionPositions(intentionIds);
				list.forEach(map -> {
					if (cityRecords != null) {
						List<Map<String, Object>> cities = new ArrayList<>();
						cityRecords.forEach(cityRecord -> {
							Map<String, Object> cityMap = new HashMap<>();
							cityMap.put("city_code", cityRecord.getCityCode().intValue());
							cityMap.put("city_name", cityRecord.getCityName());
							cities.add(cityMap);
						});
						map.put("cities", cities);
					}
					if (industryRecords != null) {
						List<Map<String, Object>> industries = new ArrayList<>();
						industryRecords.forEach(record -> {
							Map<String, Object> industryMap = new HashMap<>();
							industryMap.put("industry_code", record.getIndustryCode().intValue());
							industryMap.put("industry_name", record.getIndustryName());
							industries.add(industryMap);
						});
						map.put("industries", industries);
					}
					if (positionRecords != null) {
						List<Map<String, Object>> positions = new ArrayList<>();
						positionRecords.forEach(record -> {
							Map<String, Object> positionMap = new HashMap<>();
							positionMap.put("position_name", record.getPositionName());
							positionMap.put("position_code", record.getPositionCode().intValue());
							positions.add(positionMap);
						});
						map.put("positions", positions);
					}
				});
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return list;
	}

	private List<Map<String, Object>> buildsWorks(ProfileProfileRecord profileRecord, CommonQuery query) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			List<ProfileWorksRecord> records = worksDao.getResources(query);
			if (records != null && records.size() > 0) {
				records.forEach(record -> {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", record.getId().intValue());
					map.put("name", record.getName());
					map.put("cover", record.getCover());
					map.put("url", record.getUrl());
					map.put("description", record.getDescription());
					list.add(map);
				});
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return list;
	}

	private List<Map<String, Object>> buildsAwards(ProfileProfileRecord profileRecord, CommonQuery query) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			List<ProfileAwardsRecord> records = awardsDao.getResources(query);
			if (records != null && records.size() > 0) {
				records.forEach(record -> {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", record.getId().intValue());
					map.put("name", record.getName());
					if (record.getRewardDate() != null) {
						map.put("reward_date", DateUtils.dateToNormalDate(record.getRewardDate()));
					}
					list.add(map);
				});
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return list;
	}

	private List<Map<String, Object>> buildsCredentials(ProfileProfileRecord profileRecord, CommonQuery query) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			List<ProfileCredentialsRecord> records = credentialsDao.getResources(query);
			if (records != null && records.size() > 0) {
				records.forEach(record -> {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", record.getId().intValue());
					map.put("name", record.getName());
					map.put("organization", record.getOrganization());
					map.put("code", record.getCode());
					map.put("url", record.getUrl());
					if (record.getGetDate() != null) {
						map.put("get_date", DateUtils.dateToNormalDate(record.getGetDate()));
					}
					map.put("score", record.getScore());
					if (record.getCreateTime() != null) {
						map.put("create_time", DateUtils.dateToShortTime(record.getCreateTime()));
					}
					if (record.getUpdateTime() != null) {
						map.put("update_time", DateUtils.dateToShortTime(record.getUpdateTime()));
					}
					list.add(map);
				});
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return list;
	}

	private List<Map<String, Object>> buildskills(ProfileProfileRecord profileRecord, CommonQuery query) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			List<ProfileSkillRecord> records = skillDao.getResources(query);
			if (records != null && records.size() > 0) {
				records.forEach(record -> {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", record.getId().intValue());
					map.put("name", record.getName());
					map.put("level", record.getLevel().intValue());
					list.add(map);
				});
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return list;
	}

	private List<Map<String, Object>> buildLanguage(ProfileProfileRecord profileRecord, CommonQuery query) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			List<ProfileLanguageRecord> records = languageDao.getResources(query);
			if (records != null && records.size() > 0) {
				records.forEach(record -> {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", record.getId().intValue());
					map.put("name", record.getName());
					map.put("level", record.getLevel().intValue());
					list.add(map);
				});
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return list;
	}

	private List<Map<String, Object>> buildProjectexps(ProfileProfileRecord profileRecord, CommonQuery query) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			List<ProfileProjectexpRecord> records = projectExpDao.getResources(query);
			if (records != null && records.size() > 0) {
				records.forEach(record -> {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", record.getId().intValue());
					map.put("name", record.getName());
					map.put("company_name", record.getCompanyName());
					if (record.getStart() != null) {
						map.put("start_date", DateUtils.dateToNormalDate(record.getStart()));
					}
					if (record.getEnd() != null) {
						map.put("end_date", DateUtils.dateToNormalDate(record.getEnd()));
					}
					map.put("end_until_now", record.getEndUntilNow().intValue());
					map.put("description", record.getDescription());
					map.put("member", record.getMember());
					map.put("role", record.getRole());
					list.add(map);
				});
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return list;
	}

	private List<Map<String, Object>> buildEducations(ProfileProfileRecord profileRecord, CommonQuery query) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			List<ProfileEducationRecord> records = educationDao.getResources(query);

			if (records != null && records.size() > 0) {
				List<Integer> collegeCodes = new ArrayList<>();
				records.forEach(record -> {
					collegeCodes.add(record.getCollegeCode());
				});
				List<DictCollegeRecord> collegeRecords = collegeDao.getCollegesByIDs(collegeCodes);
				records.forEach(record -> {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", record.getId().intValue());
					map.put("college_name", record.getCollegeName());
					map.put("college_code", record.getCollegeCode());
					map.put("college_logo", record.getCollegeLogo());
					/* 如果college_code合法，有限选择字典里的图片 */
					if (record.getCollegeCode() != null && record.getCollegeCode().intValue() > 0
							&& collegeRecords.size() > 0) {
						for (DictCollegeRecord collegeRecord : collegeRecords) {
							if (collegeRecord.getCode().intValue() == record.getCollegeCode().intValue()
									&& !StringUtils.isNullOrEmpty(collegeRecord.getLogo())) {
								map.put("college_logo", collegeRecord.getLogo());
								map.put("college_name", collegeRecord.getName());
								break;
							}
						}
					}
					map.put("major_name", record.getMajorName());
					map.put("major_code", record.getMajorCode());
					map.put("degree", record.getDegree().intValue());
					if (record.getStart() != null) {
						map.put("start_date", DateUtils.dateToNormalDate(record.getStart()));
					}
					if (record.getEnd() != null) {
						map.put("end_date", DateUtils.dateToNormalDate(record.getEnd()));
					}
					map.put("end_until_now", record.getEndUntilNow().intValue());
					map.put("description", record.getDescription());
					list.add(map);
				});
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return list;
	}

	private List<Map<String, Object>> buildWorkexps(ProfileProfileRecord profileRecord, CommonQuery query) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			List<ProfileWorkexpRecord> records = workExpDao.getResources(query);
			if (records != null && records.size() > 0) {
				List<Integer> companyIds = new ArrayList<>();
				records.forEach(record -> {
					companyIds.add(record.getCompanyId().intValue());
				});
				List<HrCompanyRecord> companyRecords = companyDao.getCompaniesByIds(companyIds);
				records.forEach(record -> {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", record.getId().intValue());
					if (companyRecords != null && companyRecords.size() > 0) {
						for (HrCompanyRecord company : companyRecords) {
							if (record.getCompanyId().intValue() == company.getId().intValue()) {
								map.put("company_name", company.getName());
								map.put("company_logo", company.getLogo());
								map.put("company_id", company.getId().intValue());
								break;
							}
						}
					}
					map.put("position_name", record.getPositionName());
					map.put("position_code", record.getPositionCode().intValue());
					map.put("department_name", record.getDepartmentName());
					map.put("job", record.getJob());
					// map.put("logo", record.getlo)
					if (record.getStart() != null) {
						map.put("start_date", DateUtils.dateToNormalDate(record.getStart()));
					}
					if (record.getEnd() != null) {
						map.put("end_date", DateUtils.dateToNormalDate(record.getEnd()));
					}
					map.put("end_until_now", record.getEndUntilNow().intValue());
					map.put("description", record.getDescription());
					list.add(map);
				});
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return list;
	}

	private Map<String, Object> buildBasic(ProfileProfileRecord profileRecord, CommonQuery query, List<DictConstantRecord> constantRecords) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ProfileBasicRecord basicRecord = profileBasicDao.getResource(query);
			UserUserRecord userRecord = userDao.getUserById(profileRecord.getUserId().intValue());
			ProfileWorkexpRecord lastWorkExp = workExpDao.getLastWorkExp(profileRecord.getId().intValue());
			UserSettingsRecord userSettingsRecord = userSettingsDao
					.getUserSettingsById(profileRecord.getUserId().intValue());

			HrCompanyRecord company = null;
			if (lastWorkExp != null) {
				QueryUtil queryUtils = new QueryUtil();
				queryUtils.addEqualFilter("id", lastWorkExp.getCompanyId().toString());
				company = companyDao.getResource(queryUtils);
			}
			if (userSettingsRecord != null) {
				map.put("banner_url", userSettingsRecord.getBannerUrl());
				map.put("privacy_policy", userSettingsRecord.getPrivacyPolicy().intValue());
			}

			if (userRecord != null) {
				map.put("headimg", userRecord.getHeadimg());
				map.put("mobile", userRecord.getMobile());
				map.put("email", userRecord.getEmail());
			}
			if (lastWorkExp != null) {
				if (company != null) {
					map.put("company_id", company.getId().intValue());
					map.put("company_name", company.getName());
					map.put("company_scale", company.getScale());
				}
				map.put("industry_name", lastWorkExp.getIndustryName());
				map.put("industry_code", lastWorkExp.getIndustryCode().intValue());
				map.put("position_name", lastWorkExp.getPositionName());
			}
			if (basicRecord != null) {
				map.put("update_time", DateUtils.dateToShortTime(profileRecord.getUpdateTime()));
				map.put("completeness", profileRecord.getCompleteness().intValue());
				map.put("uuid", profileRecord.getUuid());
				map.put("name", basicRecord.getName());
				map.put("city_name", basicRecord.getCityName());
				map.put("city_code", basicRecord.getCityCode().intValue());
				if(basicRecord.getGender() != null) {
					map.put("gender", basicRecord.getGender().intValue());
					for(DictConstantRecord constantRecord : constantRecords) {
						if(constantRecord.getParentCode().intValue() == 3109 && constantRecord.getCode().intValue() == basicRecord.getGender().intValue()) {
							map.put("gender_name", constantRecord.getName());
							break;
						}
					}
				}
				map.put("nationality_name", basicRecord.getNationalityName());
				map.put("nationality_code", basicRecord.getNationalityCode().intValue());
				DictCountryRecord countryRecord = countryDao.getCountryByID(basicRecord.getNationalityCode());
				if (countryRecord != null) {
					map.put("icon_class", countryRecord.getIconClass());
				}
				map.put("motto", basicRecord.getMotto());
				if (basicRecord.getBirth() != null) {
					map.put("birth", DateUtils.dateToNormalDate(basicRecord.getBirth()));
				}
				map.put("self_introduction", basicRecord.getSelfIntroduction());

				map.put("qq", basicRecord.getQq());
				map.put("weixin", basicRecord.getWeixin());
				map.put("profile_id", basicRecord.getProfileId().intValue());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {

		}
		return map;
	}
	
	private Map<String, Object> buildProfile(ProfileProfileRecord profileRecord, CommonQuery query, List<DictConstantRecord> constantRecords) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", profileRecord.getId().intValue());
		if(profileRecord.getUuid() != null) {
			map.put("uuid", profileRecord.getUuid());
		}
		if(profileRecord.getLang() != null) {
			map.put("lang", profileRecord.getLang().intValue());
		}
		if(profileRecord.getSource() != null) {
			map.put("source", profileRecord.getLang().intValue());
			if(constantRecords != null && constantRecords.size() > 0) {
				for(DictConstantRecord constantRecord : constantRecords) {
					if(constantRecord.getParentCode().intValue() == 3119) {
						if(profileRecord.getLang().intValue() == constantRecord.getCode().intValue()) {
							map.put("source_name", constantRecord.getName());
							break;
						}
					}
				}
			}
		}
		if(profileRecord.getCompleteness() != null) {
			map.put("completeness", profileRecord.getCompleteness().intValue());
		}
		if(profileRecord.getUserId() != null) {
			map.put("user_id", profileRecord.getUserId().intValue());
		}
		if(profileRecord.getCreateTime() != null) {
			map.put("create_time", DateUtils.dateToShortTime(profileRecord.getCreateTime()));
		}
		if(profileRecord.getUpdateTime() != null) {
			map.put("update_time", DateUtils.dateToShortTime(profileRecord.getUpdateTime()));
		}
		return map;
	}
	
	@Autowired
	private ConstantDao constantDao;
	
	@Autowired
	private CustomizeResumeDao customizeResumeDao;
	
	@Autowired
	private JobPositionDao jobPositionDao;

	@Autowired
	private UserSettingsDao userSettingsDao;

	@Autowired
	private IntentionCityDao intentionCityDao;

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private IntentionPositionDao intentionPositionDao;

	@Autowired
	private IntentionIndustryDao intentionIndustryDao;

	@Autowired
	private AwardsDao awardsDao;

	@Autowired
	private CollegeDao collegeDao;

	@Autowired
	private CredentialsDao credentialsDao;

	@Autowired
	private CountryDao countryDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private AttachmentDao attachmentDao;

	@Autowired
	private WorksDao worksDao;

	@Autowired
	private EducationDao educationDao;

	@Autowired
	private IntentionDao intentionDao;

	@Autowired
	private LanguageDao languageDao;

	@Autowired
	private ProfileBasicDao profileBasicDao;

	@Autowired
	private ProfileDao profileDao;

	@Autowired
	private ProfileImportDao profileImportDao;

	@Autowired
	private ProjectExpDao projectExpDao;

	@Autowired
	private SkillDao skillDao;

	@Autowired
	private WorkExpDao workExpDao;

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public AttachmentDao getAttachmentDao() {
		return attachmentDao;
	}

	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	public WorksDao getWorksDao() {
		return worksDao;
	}

	public void setWorksDao(WorksDao worksDao) {
		this.worksDao = worksDao;
	}

	public EducationDao getEducationDao() {
		return educationDao;
	}

	public void setEducationDao(EducationDao educationDao) {
		this.educationDao = educationDao;
	}

	public IntentionDao getIntentionDao() {
		return intentionDao;
	}

	public void setIntentionDao(IntentionDao intentionDao) {
		this.intentionDao = intentionDao;
	}

	public LanguageDao getLanguageDao() {
		return languageDao;
	}

	public void setLanguageDao(LanguageDao languageDao) {
		this.languageDao = languageDao;
	}

	public ProfileBasicDao getProfileBasicDao() {
		return profileBasicDao;
	}

	public void setProfileBasicDao(ProfileBasicDao profileBasicDao) {
		this.profileBasicDao = profileBasicDao;
	}

	public ProfileDao getProfileDao() {
		return profileDao;
	}

	public void setProfileDao(ProfileDao profileDao) {
		this.profileDao = profileDao;
	}

	public ProfileImportDao getProfileImportDao() {
		return profileImportDao;
	}

	public void setProfileImportDao(ProfileImportDao profileImportDao) {
		this.profileImportDao = profileImportDao;
	}

	public ProjectExpDao getProjectExpDao() {
		return projectExpDao;
	}

	public void setProjectExpDao(ProjectExpDao projectExpDao) {
		this.projectExpDao = projectExpDao;
	}

	public SkillDao getSkillDao() {
		return skillDao;
	}

	public void setSkillDao(SkillDao skillDao) {
		this.skillDao = skillDao;
	}

	public WorkExpDao getWorkExpDao() {
		return workExpDao;
	}

	public void setWorkExpDao(WorkExpDao workExpDao) {
		this.workExpDao = workExpDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public CountryDao getCountryDao() {
		return countryDao;
	}

	public void setCountryDao(CountryDao countryDao) {
		this.countryDao = countryDao;
	}

	public CredentialsDao getCredentialsDao() {
		return credentialsDao;
	}

	public void setCredentialsDao(CredentialsDao credentialsDao) {
		this.credentialsDao = credentialsDao;
	}

	public AwardsDao getAwardsDao() {
		return awardsDao;
	}

	public void setAwardsDao(AwardsDao awardsDao) {
		this.awardsDao = awardsDao;
	}

	public IntentionCityDao getIntentionCityDao() {
		return intentionCityDao;
	}

	public void setIntentionCityDao(IntentionCityDao intentionCityDao) {
		this.intentionCityDao = intentionCityDao;
	}

	public IntentionPositionDao getIntentionPositionDao() {
		return intentionPositionDao;
	}

	public void setIntentionPositionDao(IntentionPositionDao intentionPositionDao) {
		this.intentionPositionDao = intentionPositionDao;
	}

	public IntentionIndustryDao getIntentionIndustryDao() {
		return intentionIndustryDao;
	}

	public void setIntentionIndustryDao(IntentionIndustryDao intentionIndustryDao) {
		this.intentionIndustryDao = intentionIndustryDao;
	}

	public CompanyDao getCompanyDao() {
		return companyDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	public CustomizeResumeDao getCustomizeResumeDao() {
		return customizeResumeDao;
	}

	public void setCustomizeResumeDao(CustomizeResumeDao customizeResumeDao) {
		this.customizeResumeDao = customizeResumeDao;
	}

	public UserSettingsDao getUserSettingsDao() {
		return userSettingsDao;
	}

	public void setUserSettingsDao(UserSettingsDao userSettingsDao) {
		this.userSettingsDao = userSettingsDao;
	}

	public CollegeDao getCollegeDao() {
		return collegeDao;
	}

	public void setCollegeDao(CollegeDao collegeDao) {
		this.collegeDao = collegeDao;
	}

	public JobPositionDao getJobPositionDao() {
		return jobPositionDao;
	}

	public void setJobPositionDao(JobPositionDao jobPositionDao) {
		this.jobPositionDao = jobPositionDao;
	}

	public ConstantDao getConstantDao() {
		return constantDao;
	}

	public void setConstantDao(ConstantDao constantDao) {
		this.constantDao = constantDao;
	}
}
