package com.moseeker.profile.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.moseeker.thrift.gen.common.struct.Order;
import com.moseeker.thrift.gen.common.struct.OrderBy;
import org.apache.thrift.TException;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.db.dictdb.tables.records.DictCollegeRecord;
import com.moseeker.db.dictdb.tables.records.DictConstantRecord;
import com.moseeker.db.dictdb.tables.records.DictCountryRecord;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.db.profiledb.tables.records.*;
import com.moseeker.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.profile.dao.*;
import com.moseeker.profile.dao.entity.ProfileWorkexpEntity;
import com.moseeker.profile.dao.impl.IntentionRecord;
import com.moseeker.profile.service.impl.serviceutils.ProfilePojo;
import com.moseeker.profile.service.impl.serviceutils.ProfileUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Future;

@Service
@CounterIface
public class WholeProfileService {

	Logger logger = LoggerFactory.getLogger(WholeProfileService.class);
	ProfileUtils profileUtils = new ProfileUtils();

	ThreadPool pool = ThreadPool.Instance;

	public Response getResource(int userId, int profileId, String uuid) throws TException {
		logger.info("WholeProfileService getResource");
		logger.info("WholeProfileService getResource start : {}",new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));
		Response response = new Response();
		try {
			HashMap<String, Object> profile = new HashMap<String, Object>();

			ProfileProfileRecord profileRecord = profileDao.getProfileByIdOrUserIdOrUUID(userId, profileId, uuid);

			logger.info("WholeProfileService getResource after  profileDao.getProfileByIdOrUserIdOrUUID: {}",new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

			if (profileRecord != null) {
				if (profileRecord.getCompleteness().intValue() == 0
						|| profileRecord.getCompleteness().intValue() == 10) {
					int completeness = completenessImpl.getCompleteness(profileRecord.getUserId().intValue(),
							profileRecord.getUuid(), profileRecord.getId().intValue());
					profileRecord.setCompleteness(UByte.valueOf(completeness));
				}
				QueryUtil query = new QueryUtil();
				HashMap<String, String> equalFilter = new HashMap<String, String>();
				equalFilter.put("profile_id", String.valueOf(profileRecord.getId()));
				query.setPageSize(Integer.MAX_VALUE);
				query.setEqualFilter(equalFilter);

				logger.info("WholeProfileService getResource before  constantDao.getCitiesByParentCodes : {}",new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

				List<DictConstantRecord> constantRecords = constantDao
						.getCitiesByParentCodes(Arrays.asList(3109, 3105, 3102, 2105, 3120, 3115, 3114, 3119, 3120));

				logger.info("WholeProfileService getResource after constantDao.getCitiesByParentCodes : {}",new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

				Map<String, Object> profileprofile = buildProfile(profileRecord, query, constantRecords);
				profile.put("profile", profileprofile);

				logger.info("WholeProfileService getResource after buildProfile : {}",new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

				Future<Map<String, Object>> basicFuture = pool.startTast(() -> buildBasic(profileRecord, query, constantRecords));
				Future<List<Map<String, Object>>> workexpsFuture = pool.startTast(() -> buildWorkexps(profileRecord, query));
				Future<List<Map<String, Object>>> educationsFuture = pool.startTast(() -> buildEducations(profileRecord, query));
				Future<List<Map<String, Object>>> projectexpsFuture = pool.startTast(() -> buildProjectexps(profileRecord, query));
				Future<List<Map<String, Object>>> buildLanguageFuture = pool.startTast(() -> buildLanguage(profileRecord, query));
				Future<List<Map<String, Object>>> buildskillsFuture = pool.startTast(() -> buildskills(profileRecord, query));
				Future<List<Map<String, Object>>> buildsCredentialsFuture = pool.startTast(() -> buildsCredentials(profileRecord, query));
				Future<List<Map<String, Object>>> buildsAwardsFuture = pool.startTast(() -> buildsAwards(profileRecord, query));
				Future<List<Map<String, Object>>> buildsWorksFuture = pool.startTast(() -> buildsWorks(profileRecord, query));
				Future<List<Map<String, Object>>> intentionsFuture = pool.startTast(() -> profileUtils.buildsIntentions(profileRecord, query,
						constantRecords, intentionDao, intentionCityDao, intentionIndustryDao, intentionPositionDao,
						dictCityDao, dictIndustryDao, dictPositionDao));
				Future<List<ProfileAttachmentRecord>> attachmentRecordsFuture = pool.startTast(() -> attachmentDao.getResources(query));
				Future<List<ProfileImportRecord>> importRecordsFuture = pool.startTast(() -> profileImportDao.getResources(query));
				Future<List<ProfileOtherRecord>> otherRecordsFuture = pool.startTast(() -> customizeResumeDao.getResources(query));

				Map<String, Object> basic = basicFuture.get();
				profile.put("basic", basic);

				logger.info("WholeProfileService getResource basicFuture.get() : {}",new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

				List<Map<String, Object>> workexps = workexpsFuture.get();
				profile.put("workexps", workexps);

				logger.info("WholeProfileService getResource workexpsFuture.get() : {}",new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

				List<Map<String, Object>> educations = educationsFuture.get();
				profile.put("educations", educations);

				logger.info("WholeProfileService getResource educationsFuture.get() : {}",new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

				List<Map<String, Object>> projectexps = projectexpsFuture.get();
				profile.put("projectexps", projectexps);

				logger.info("WholeProfileService getResource projectexpsFuture.get() : {}",new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

				List<Map<String, Object>> languages = buildLanguageFuture.get();
				profile.put("languages", languages);

				logger.info("WholeProfileService getResource buildLanguageFuture.get() : {}",new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

				List<Map<String, Object>> skills = buildskillsFuture.get();
				profile.put("skills", skills);

				logger.info("WholeProfileService getResource buildskillsFuture.get() : {}",new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

				List<Map<String, Object>> credentials = buildsCredentialsFuture.get();
				profile.put("credentials", credentials);

				logger.info("WholeProfileService getResource buildsCredentialsFuture.get() : {}",new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

				List<Map<String, Object>> awards = buildsAwardsFuture.get();
				profile.put("awards", awards);

				logger.info("WholeProfileService getResource buildsAwardsFuture.get() : {}",new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

				List<Map<String, Object>> works = buildsWorksFuture.get();
				profile.put("works", works);

				logger.info("WholeProfileService getResource buildsWorksFuture.get() : {}",new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

				List<Map<String, Object>> intentions = intentionsFuture.get();
				profile.put("intentions", intentions);

				logger.info("WholeProfileService getResource intentionsFuture.get() : {}",new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

				List<ProfileAttachmentRecord> attachmentRecords = attachmentRecordsFuture.get();
				List<Map<String, Object>> attachments = profileUtils.buildAttachments(profileRecord, attachmentRecords);
				profile.put("attachments", attachments);

				logger.info("WholeProfileService getResource attachmentRecordsFuture.get() : {}",new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

				List<ProfileImportRecord> importRecords = importRecordsFuture.get();
				List<Map<String, Object>> imports = profileUtils.buildImports(profileRecord, importRecords);
				profile.put("imports", imports);

				logger.info("WholeProfileService getResource importRecordsFuture.get() : {}",new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

				List<ProfileOtherRecord> otherRecords = otherRecordsFuture.get();
				List<Map<String, Object>> others = profileUtils.buildOthers(profileRecord, otherRecords);

				logger.info("WholeProfileService getResource done : {}",new DateTime().toString("yyyy-MM-dd HH:mm:ss SSS"));

				profile.put("others", others);
				return ResponseUtils.success(profile);
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
	public Response postResource(String profile, int userId) throws TException {
		if (!StringUtils.isNullOrEmpty(profile)) {
			Map<String, Object> resume = JSON.parseObject(profile);

			ProfileProfileRecord profileRecord = profileUtils
					.mapToProfileRecord((Map<String, Object>) resume.get("profile"));
			UserUserRecord userRecord = userDao.getUserById(userId);
			if (profileRecord == null) {
				profileRecord = new ProfileProfileRecord();
			}
			if (userRecord == null) {
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
			}
			profileRecord.setUuid(UUID.randomUUID().toString());
			profileRecord.setUserId(userRecord.getId());
			profileRecord.setDisable(UByte.valueOf(Constant.ENABLE));

			ProfileProfileRecord repeatProfileRecord = profileDao
					.getProfileByIdOrUserIdOrUUID(profileRecord.getUserId().intValue(), 0, null);
			if (repeatProfileRecord != null) {
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_ALLREADY_EXIST);
			}
			UserUserRecord crawlerUser = null;
			try {
				crawlerUser = profileUtils.mapToUserUserRecord((Map<String, Object>) resume.get("user"));
			} catch (Exception e1) {
				logger.error(e1.getMessage(), e1);
			}
			if ((userRecord.getMobile() == null || userRecord.getMobile() == 0) && crawlerUser != null
					&& crawlerUser.getMobile() != null) {
				userRecord.setMobile(crawlerUser.getMobile());
			}
			if (StringUtils.isNullOrEmpty(userRecord.getName()) && crawlerUser != null
					&& !StringUtils.isNullOrEmpty(crawlerUser.getName())) {
				userRecord.setName(crawlerUser.getName());
			}
			if (StringUtils.isNullOrEmpty(userRecord.getHeadimg()) && crawlerUser != null
					&& !StringUtils.isNullOrEmpty(crawlerUser.getHeadimg())) {
				userRecord.setHeadimg(crawlerUser.getHeadimg());
			}
			if (StringUtils.isNullOrEmpty(userRecord.getEmail()) && crawlerUser != null
					&& !StringUtils.isNullOrEmpty(crawlerUser.getEmail())) {
				userRecord.setEmail(crawlerUser.getEmail());
			}
			ProfileBasicRecord basicRecord = null;
			try {
				basicRecord = profileUtils.mapToBasicRecord((Map<String, Object>) resume.get("basic"));
			} catch (Exception e1) {
				logger.error(e1.getMessage(), e1);
			}
			if (StringUtils.isNullOrEmpty(userRecord.getName()) && basicRecord != null
					&& !StringUtils.isNullOrEmpty(basicRecord.getName())) {
				userRecord.setName(basicRecord.getName());
			}
			List<ProfileAttachmentRecord> attachmentRecords = null;
			try {
				attachmentRecords = profileUtils
						.mapToAttachmentRecords((List<Map<String, Object>>) resume.get("attachments"));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			List<ProfileAwardsRecord> awardsRecords = null;
			try {
				awardsRecords = profileUtils.mapToAwardsRecords((List<Map<String, Object>>) resume.get("awards"));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			List<ProfileCredentialsRecord> credentialsRecords = null;
			try {
				credentialsRecords = profileUtils
						.mapToCredentialsRecords((List<Map<String, Object>>) resume.get("credentials"));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			List<ProfileEducationRecord> educationRecords = null;
			try {
				educationRecords = profileUtils
						.mapToEducationRecords((List<Map<String, Object>>) resume.get("educations"));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			ProfileImportRecord importRecords = null;
			try {
				importRecords = profileUtils.mapToImportRecord((Map<String, Object>) resume.get("import"));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			List<IntentionRecord> intentionRecords = null;
			try {
				intentionRecords = profileUtils
						.mapToIntentionRecord((List<Map<String, Object>>) resume.get("intentions"));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			List<ProfileLanguageRecord> languages = null;
			try {
				languages = profileUtils.mapToLanguageRecord((List<Map<String, Object>>) resume.get("languages"));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			ProfileOtherRecord otherRecord = null;
			try {
				otherRecord = profileUtils.mapToOtherRecord((Map<String, Object>) resume.get("other"));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			List<ProfileProjectexpRecord> projectExps = null;
			try {
				projectExps = profileUtils
						.mapToProjectExpsRecords((List<Map<String, Object>>) resume.get("projectexps"));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			List<ProfileSkillRecord> skillRecords = null;
			try {
				skillRecords = profileUtils.mapToSkillRecords((List<Map<String, Object>>) resume.get("skills"));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			List<ProfileWorkexpEntity> workexpRecords = null;
			try {
				workexpRecords = profileUtils.mapToWorkexpRecords((List<Map<String, Object>>) resume.get("workexps"),
						profileRecord.getSource().intValue());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			List<ProfileWorksRecord> worksRecords = null;
			try {
				worksRecords = profileUtils.mapToWorksRecords((List<Map<String, Object>>) resume.get("works"));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			int id = profileDao.saveProfile(profileRecord, basicRecord, attachmentRecords, awardsRecords,
					credentialsRecords, educationRecords, importRecords, intentionRecords, languages, otherRecord,
					projectExps, skillRecords, workexpRecords, worksRecords, userRecord);
			if (id > 0) {
				return ResponseUtils.success(String.valueOf(id));
			}
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
	}

	@SuppressWarnings("unchecked")
	public Response importCV(String profile, int userId) throws TException {

		logger.info("importCV profile:"+profile);
		Map<String, Object> resume = JSON.parseObject(profile);
		logger.info("resume:"+resume);
		ProfileProfileRecord profileRecord = profileUtils
				.mapToProfileRecord((Map<String, Object>) resume.get("profile"));
		if (profileRecord == null) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_ILLEGAL);
		}
		UserUserRecord userRecord = userDao.getUserById(profileRecord.getUserId().intValue());
		if (userRecord == null) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
		}
		logger.info("importCV user_id:"+userRecord.getId().intValue());
		// ProfileProfileRecord profileRecord =
		// profileUtils.mapToProfileRecord((Map<String, Object>)
		// resume.get("user_user"));
		List<ProfileProfileRecord> oldProfile = profileDao.getProfilesByIdOrUserIdOrUUID(userId, 0, null);

		if (oldProfile != null && oldProfile.size() > 0 && StringUtils.isNotNullOrEmpty(oldProfile.get(0).getUuid())) {
			logger.info("importCV oldProfile:"+oldProfile.get(0).getId().intValue());
			profileRecord.setUuid(oldProfile.get(0).getUuid());
		} else {
			profileRecord.setUuid(UUID.randomUUID().toString());
		}
		ProfilePojo profilePojo = ProfilePojo.parseProfile(resume, userRecord);
		
		int id = profileDao.saveProfile(profilePojo.getProfileRecord(), profilePojo.getBasicRecord(),
				profilePojo.getAttachmentRecords(), profilePojo.getAwardsRecords(), profilePojo.getCredentialsRecords(),
				profilePojo.getEducationRecords(), profilePojo.getImportRecords(), profilePojo.getIntentionRecords(),
				profilePojo.getLanguageRecords(), profilePojo.getOtherRecord(), profilePojo.getProjectExps(),
				profilePojo.getSkillRecords(), profilePojo.getWorkexpRecords(), profilePojo.getWorksRecords(),
				userRecord, oldProfile);
		if (id > 0) {
			logger.info("importCV 添加成功");
			return ResponseUtils.success(id);
		} else {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
		}
	}

	/**
	 * 创建简历
	 * 
	 * @param profile
	 *            简历json格式的数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Response createProfile(String profile) {
		Map<String, Object> resume = JSON.parseObject(profile);
		ProfileProfileRecord profileRecord = profileUtils
				.mapToProfileRecord((Map<String, Object>) resume.get("profile"));
		if (profileRecord == null) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_ILLEGAL);
		}
		UserUserRecord userRecord = userDao.getUserById(profileRecord.getUserId().intValue());
		if (userRecord == null) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
		}
		
		ProfilePojo profilePojo = ProfilePojo.parseProfile(resume, userRecord);
		
		int id = profileDao.saveProfile(profilePojo.getProfileRecord(), profilePojo.getBasicRecord(),
				profilePojo.getAttachmentRecords(), profilePojo.getAwardsRecords(), profilePojo.getCredentialsRecords(),
				profilePojo.getEducationRecords(), profilePojo.getImportRecords(), profilePojo.getIntentionRecords(),
				profilePojo.getLanguageRecords(), profilePojo.getOtherRecord(), profilePojo.getProjectExps(),
				profilePojo.getSkillRecords(), profilePojo.getWorkexpRecords(), profilePojo.getWorksRecords(),
				userRecord, null);
		if (id > 0) {
			return ResponseUtils.success(id);
		} else {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
		}
	}

	// 完善简历
	@SuppressWarnings("unchecked")
	public Response improveProfile(String profile) {
		Map<String, Object> resume = JSON.parseObject(profile);
		ProfileProfileRecord profileRecord = profileUtils
				.mapToProfileRecord((Map<String, Object>) resume.get("profile"));
		if (profileRecord == null) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_ILLEGAL);
		}
		UserUserRecord userRecord = userDao.getUserById(profileRecord.getUserId().intValue());
		if (userRecord == null) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
		}
		ProfileProfileRecord profileDB = profileDao.getProfileByIdOrUserIdOrUUID(userRecord.getId().intValue(), 0, null);
		
		if(profileDB != null) {
			((Map<String, Object>) resume.get("profile")).put("origin", profileDB.getOrigin());
			ProfilePojo profilePojo = ProfilePojo.parseProfile(resume, userRecord);
			int profileId = profileDB.getId().intValue();
			improveUser(profilePojo.getUserRecord());
			improveProfile(profilePojo.getProfileRecord(), profileDB);
			improveBasic(profilePojo.getBasicRecord(), profileId);
			improveAttachment(profilePojo.getAttachmentRecords(), profileId);
			improveAwards(profilePojo.getAwardsRecords(), profileId);
			improveCredentials(profilePojo.getCredentialsRecords(), profileId);
			improveEducation(profilePojo.getEducationRecords(), profileId);
			improveIntention(profilePojo.getIntentionRecords(), profileId);
			improveLanguage(profilePojo.getLanguageRecords(), profileId);
			improveOther(profilePojo.getOtherRecord(), profileId);
			improveProjectexp(profilePojo.getProjectExps(), profileId);
			improveSkill(profilePojo.getSkillRecords(), profileId);
			improveWorkexp(profilePojo.getWorkexpRecords(), profileId);
			improveWorks(profilePojo.getWorksRecords(), profileId);
			completenessImpl.getCompleteness1(0, null, profileId);
			return ResponseUtils.success(null);
		} else {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_ALLREADY_NOT_EXIST);
		}
	}
	
	public Response improveProfile(int destUserId, int originUserId) {
		ProfileProfileRecord destProfile = profileDao.getProfileByIdOrUserIdOrUUID(destUserId, 0, null);
		ProfileProfileRecord originProfile = profileDao.getProfileByIdOrUserIdOrUUID(originUserId, 0, null);
		try {
			if (originProfile == null && destProfile != null && userDao.getUserById(originUserId) != null) {
				destProfile.setUserId(UInteger.valueOf(originUserId));
				profileDao.putResource(destProfile);
			}
			if(originProfile != null && destProfile != null) {
				QueryUtil queryUtil = new QueryUtil();
				HashMap<String, String> eqs = new HashMap<String, String>();
				queryUtil.setEqualFilter(eqs);
				// dest 简历信息查询
				eqs.put("profile_id", String.valueOf(destProfile.getId()));
				ProfileBasicRecord destRecord = profileBasicDao.getResource(queryUtil);
				List<ProfileAttachmentRecord> destAttachments = attachmentDao.getResources(queryUtil);
				List<ProfileAwardsRecord> destAwards = awardsDao.getResources(queryUtil);
				List<ProfileCredentialsRecord> destCredentials = credentialsDao.getResources(queryUtil);
				List<ProfileEducationRecord> destEducations = educationDao.getResources(queryUtil);
				List<IntentionRecord> destIntentions = new ArrayList<IntentionRecord>();
				QueryUtil query = new QueryUtil();
				Map<String, String> param = new HashMap<>();
				query.setEqualFilter(param);
				intentionDao.getResources(queryUtil).forEach(intention -> {
					IntentionRecord irecodr = new IntentionRecord(intention);
					param.put("profile_intention_id", String.valueOf(intention.getId().intValue()));
					try {
						irecodr.setCities(intentionCityDao.getResources(query));
						irecodr.setPositions(intentionPositionDao.getResources(query));
						irecodr.setIndustries(intentionIndustryDao.getResources(query));
						destIntentions.add(irecodr);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				});
				List<ProfileLanguageRecord> destLanguages = languageDao.getResources(queryUtil);
				ProfileOtherRecord destOther = otherDao.getResource(queryUtil);
				List<ProfileProjectexpRecord> destProjectexps = projectExpDao.getResources(queryUtil);
				List<ProfileSkillRecord> destSkills = skillDao.getResources(queryUtil);
				List<ProfileWorkexpEntity> destWorkxps = new ArrayList<ProfileWorkexpEntity>();
				workExpDao.getResources(queryUtil).forEach(workexp -> {
					ProfileWorkexpEntity workexpEntity = new ProfileWorkexpEntity(workexp);
					destWorkxps.add(workexpEntity);
				});
				List<ProfileWorksRecord> destWorks = worksDao.getResources(queryUtil);
				int originProfileId = originProfile.getId().intValue();
				improveBasic(destRecord, originProfileId);
				improveAttachment(destAttachments, originProfileId);
				improveAwards(destAwards, originProfileId);
				improveCredentials(destCredentials, originProfileId);
				improveEducation(destEducations, originProfileId);
				improveIntention(destIntentions, originProfileId);
				improveLanguage(destLanguages, originProfileId);
				improveOther(destOther, originProfileId);
				improveProjectexp(destProjectexps, originProfileId);
				improveSkill(destSkills, originProfileId);
				improveWorks(destWorks, originProfileId);
				improveWorkexp(destWorkxps, originProfileId);
				completenessImpl.getCompleteness(0, null, originProfile.getId().intValue());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
		return ResponseUtils.success(null);
	}

	private void improveUser(UserUserRecord userRecord) {
		try {
			userDao.putResource(userRecord);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
	}

	private void improveWorks(List<ProfileWorksRecord> worksRecords, int profileId) {
		if(worksRecords != null && worksRecords.size() > 0) {
			worksDao.delWorksByProfileId(profileId);
			try {
				worksRecords.forEach(skill -> {
					skill.setProfileId(UInteger.valueOf(profileId));
				});
				worksDao.postResources(worksRecords);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
	}

	private void improveWorkexp(List<ProfileWorkexpEntity> workexpRecords, int profileId) {
		if(workexpRecords != null && workexpRecords.size() > 0) {
			workExpDao.delWorkExpsByProfileId(profileId);
			try {
				List<ProfileWorkexpEntity> records = new ArrayList<>();
				
				workexpRecords.forEach(skill -> {
					skill.setProfileId(UInteger.valueOf(profileId));
					records.add(skill);
				});
				workExpDao.postWordExps(records);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
	}

	private void improveSkill(List<ProfileSkillRecord> skillRecords, int profileId) {
		if(skillRecords != null && skillRecords.size() > 0) {
			skillDao.delSkillByProfileId(profileId);
			try {
				skillRecords.forEach(skill -> {
					skill.setProfileId(UInteger.valueOf(profileId));
				});
				skillDao.postResources(skillRecords);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
	}

	private void improveProjectexp(List<ProfileProjectexpRecord> projectExps, int profileId) {
		if(projectExps != null && projectExps.size() > 0) {
			projectExpDao.delProjectExpByProfileId(profileId);
			try {
				projectExps.forEach(language -> {
					language.setProfileId(UInteger.valueOf(profileId));
				});
				projectExpDao.postResources(projectExps);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
	}

	private void improveOther(ProfileOtherRecord otherRecord, int profileId) {
		if(otherRecord != null && StringUtils.isNotNullOrEmpty(otherRecord.getOther())) {
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("profile_id", String.valueOf(profileId));
			try {
				ProfileOtherRecord record = otherDao.getResource(qu);
				if(record == null && otherRecord != null) {
					otherRecord.setProfileId(UInteger.valueOf(profileId));
					otherDao.postResource(otherRecord);
				} 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void improveLanguage(List<ProfileLanguageRecord> languageRecords, int profileId) {
		if(languageRecords != null && languageRecords.size() > 0) {
			languageDao.delLanguageByProfileId(profileId);
			try {
				languageRecords.forEach(language -> {
					language.setProfileId(UInteger.valueOf(profileId));
				});
				languageDao.postResources(languageRecords);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
	}

	private void improveIntention(List<IntentionRecord> intentionRecords, int profileId) {
		if(intentionRecords != null && intentionRecords.size() > 0) {
			intentionDao.delIntentionsByProfileId(profileId);
			try {
				intentionRecords.forEach(intention -> {
					intention.setProfileId(UInteger.valueOf(profileId));
				});
				intentionDao.postIntentions(intentionRecords);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
	}

	private void improveEducation(List<ProfileEducationRecord> educationRecords, int profileId) {
		if(educationRecords != null && educationRecords.size() > 0) {
			educationDao.delEducationsByProfileId(profileId);
			try {
				educationRecords.forEach(education ->{
					education.setProfileId(UInteger.valueOf(profileId));
				});
				
				educationDao.saveEducations(educationRecords);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
	}

	private void improveCredentials(List<ProfileCredentialsRecord> credentialsRecords, int profileId) {
		if(credentialsRecords != null && credentialsRecords.size() > 0) {
			credentialsDao.delCredentialsByProfileId(profileId);
			try {
				credentialsRecords.forEach(credential -> {
					credential.setProfileId(UInteger.valueOf(profileId));
				});
				credentialsDao.postResources(credentialsRecords);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
	}

	private void improveAwards(List<ProfileAwardsRecord> awardsRecords, int profileId) {
		if(awardsRecords != null && awardsRecords.size() > 0) {
			awardsDao.delAwardsByProfileId(profileId);
			try {
				awardsRecords.forEach(award -> {
					award.setProfileId(UInteger.valueOf(profileId));
				});
				awardsDao.postResources(awardsRecords);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
	}

	private void improveAttachment(List<ProfileAttachmentRecord> attachmentRecords, int profileId) {
		if(attachmentRecords != null && attachmentRecords.size() > 0) {
			attachmentDao.delAttachmentsByProfileId(profileId);
			try {
				attachmentRecords.forEach(attachment -> {
					attachment.setProfileId(UInteger.valueOf(profileId));
				});
				attachmentDao.postResources(attachmentRecords);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
	}

	/**
	 * 完善基本信息
	 * @param basicRecord
	 * @param profileId
	 */
	private void improveBasic(ProfileBasicRecord basicRecord, int profileId) {
		if(basicRecord != null) {
			QueryUtil qu  = new QueryUtil();
			qu.addEqualFilter("profile_id", String.valueOf(profileId));
			try {
				boolean flag = false;
				ProfileBasicRecord basic = profileBasicDao.getResource(qu);
				if(basic != null) {
					if(StringUtils.isNotNullOrEmpty(basicRecord.getName()) && StringUtils.isNullOrEmpty(basic.getName())) {
						basic.setName(basicRecord.getName());
						flag = true;
					}
					if(basicRecord.getGender() != null && basic.getGender() == null) {
						basic.setGender(basicRecord.getGender());
						flag = true;
					}
					if(basicRecord.getNationalityCode() != null && basic.getNationalityCode() == null) {
						basic.setNationalityCode(basicRecord.getNationalityCode());
						flag = true;
					}
					if(StringUtils.isNotNullOrEmpty(basicRecord.getNationalityName()) && StringUtils.isNullOrEmpty(basic.getNationalityName())) {
						basic.setNationalityName(basicRecord.getNationalityName());
						flag = true;
					}
					if(basicRecord.getCityCode() != null && basic.getCityCode() == null) {
						basic.setCityCode(basicRecord.getCityCode());
						flag = true;
					}
					if(StringUtils.isNotNullOrEmpty(basicRecord.getCityName()) && StringUtils.isNullOrEmpty(basic.getCityName())) {
						basic.setCityName(basicRecord.getCityName());
						flag = true;
					}
					if(basicRecord.getBirth() != null && basic.getBirth() == null) {
						basic.setBirth(basicRecord.getBirth());
						flag = true;
					}
					if(StringUtils.isNotNullOrEmpty(basicRecord.getWeixin()) && StringUtils.isNullOrEmpty(basic.getWeixin())) {
						basic.setWeixin(basicRecord.getWeixin());
						flag = true;
					}
					if(StringUtils.isNotNullOrEmpty(basicRecord.getQq()) && StringUtils.isNullOrEmpty(basic.getQq())) {
						basic.setQq(basicRecord.getQq());
						flag = true;
					}
					if(StringUtils.isNotNullOrEmpty(basicRecord.getMotto()) && StringUtils.isNullOrEmpty(basic.getMotto())) {
						basic.setMotto(basicRecord.getMotto());
						flag = true;
					}
					if(StringUtils.isNotNullOrEmpty(basicRecord.getSelfIntroduction()) && StringUtils.isNullOrEmpty(basic.getSelfIntroduction())) {
						basic.setSelfIntroduction(basicRecord.getSelfIntroduction());
						flag = true;
					}
					if(flag) {
						profileBasicDao.putResource(basic);
					}
				} else {
					basicRecord.setProfileId(UInteger.valueOf(profileId));
					profileBasicDao.postResource(basicRecord);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
	}

	/**
	 * 更新profile_profile数据
	 * @param profileRecord
	 * @param record 
	 */
	private void improveProfile(ProfileProfileRecord profileRecord, ProfileProfileRecord record) {
		if(profileRecord != null) {
			if(record != null) {
				boolean flag = false;
				try {
					if(profileRecord.getOrigin() != null && record.getOrigin() != null && !profileRecord.getOrigin().equals(record.getOrigin())) {
						record.setOrigin(profileRecord.getOrigin());
						flag = true;
					}
					if(flag) {
						profileDao.putResource(record);
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage(), e);
				} finally {
					//do nothing
				}
			}
		}
	}

	public Response verifyRequires(int userId, int positionId) throws TException {
		UserUserRecord userRecord = userDao.getUserById(userId);
		JobPositionRecord positionRecord = jobPositionDao.getPositionById(positionId);
		if (userRecord == null) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
		}
		if (positionRecord == null) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_POSITION_NOTEXIST);
		}
		if (positionRecord.getAppCvConfigId() != null && positionRecord.getAppCvConfigId().intValue() > 0) {
			return ResponseUtils.success(true);
		} else {
			return ResponseUtils.success(false);
		}
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
			// 按照结束时间倒序
			query.addToOrders(new OrderBy("end_until_now", Order.DESC));
			query.addToOrders(new OrderBy("start", Order.DESC));
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
			// 按照结束时间倒序
			query.addToOrders(new OrderBy("end_until_now", Order.DESC));
			query.addToOrders(new OrderBy("start", Order.DESC));
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
			// 按照结束时间倒序
			query.addToOrders(new OrderBy("end_until_now", Order.DESC));
			query.addToOrders(new OrderBy("start", Order.DESC));
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

	private Map<String, Object> buildBasic(ProfileProfileRecord profileRecord, CommonQuery query,
			List<DictConstantRecord> constantRecords) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			/*ProfileBasicRecord basicRecord = profileBasicDao.getResource(query);
			UserUserRecord userRecord = userDao.getUserById(profileRecord.getUserId().intValue());
			ProfileWorkexpRecord lastWorkExp = workExpDao.getLastWorkExp(profileRecord.getId().intValue());
			UserSettingsRecord userSettingsRecord = userSettingsDao
					.getUserSettingsById(profileRecord.getUserId().intValue());*/

			Future<ProfileBasicRecord> basicRecordFuture = pool.startTast(() -> profileBasicDao.getResource(query));
			Future<UserUserRecord> userRecordFuture = pool.startTast(() -> userDao.getUserById(profileRecord.getUserId().intValue()));
			Future<ProfileWorkexpRecord> lastWorkExpFuture = pool.startTast(() -> workExpDao.getLastWorkExp(profileRecord.getId().intValue()));
			Future<UserSettingsRecord> userSettingsRecordFuture = pool.startTast(() -> userSettingsDao.getUserSettingsById(profileRecord.getUserId().intValue()));

			ProfileBasicRecord basicRecord = basicRecordFuture.get();
			UserUserRecord userRecord = userRecordFuture.get();
			ProfileWorkexpRecord lastWorkExp = lastWorkExpFuture.get();
			UserSettingsRecord userSettingsRecord = userSettingsRecordFuture.get();

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
			UserWxUserRecord wxuserRecord = null;
			if (userRecord != null) {
				wxuserRecord = wxuserDao.getWXUserByUserId(userRecord.getId().intValue());
				if (!StringUtils.isNullOrEmpty(userRecord.getHeadimg())) {
					map.put("headimg", userRecord.getHeadimg());
				} else {
					if (wxuserRecord != null) {
						map.put("headimg", wxuserRecord.getHeadimgurl());
					}
				}
				map.put("mobile", userRecord.getMobile());
				map.put("email", userRecord.getEmail());
				map.put("name", userRecord.getName());
			}
			if (lastWorkExp != null) {
				if (company != null) {
					map.put("company_id", company.getId().intValue());
					map.put("company_name", company.getName());
					map.put("company_logo", company.getLogo());
					map.put("company_scale", company.getScale().intValue());
				}
				map.put("industry_name", lastWorkExp.getIndustryName());
				map.put("industry_code", lastWorkExp.getIndustryCode().intValue());
				map.put("position_name", lastWorkExp.getPositionName());
				map.put("current_job", lastWorkExp.getJob());
			}
			if (basicRecord != null) {
				map.put("update_time", DateUtils.dateToShortTime(profileRecord.getUpdateTime()));
				map.put("completeness", profileRecord.getCompleteness().intValue());
				map.put("uuid", profileRecord.getUuid());
				map.put("city_name", basicRecord.getCityName());
				map.put("city_code", basicRecord.getCityCode().intValue());
				if (basicRecord.getGender() != null) {
					map.put("gender", basicRecord.getGender().intValue());
					for (DictConstantRecord constantRecord : constantRecords) {
						if (constantRecord.getParentCode().intValue() == 3109
								&& constantRecord.getCode().intValue() == basicRecord.getGender().intValue()) {
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

	private Map<String, Object> buildProfile(ProfileProfileRecord profileRecord, CommonQuery query,
			List<DictConstantRecord> constantRecords) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", profileRecord.getId().intValue());
		if (profileRecord.getUuid() != null) {
			map.put("uuid", profileRecord.getUuid());
		}
		if (profileRecord.getLang() != null) {
			map.put("lang", profileRecord.getLang().intValue());
		}
		if (profileRecord.getSource() != null) {
			map.put("source", profileRecord.getSource().intValue());
			if (constantRecords != null && constantRecords.size() > 0) {
				for (DictConstantRecord constantRecord : constantRecords) {
					if (constantRecord.getParentCode().intValue() == 3119) {
						if (profileRecord.getSource().intValue() == constantRecord.getCode().intValue()) {
							map.put("source_name", constantRecord.getName());
							break;
						}
					}
				}
			}
		}
		if (profileRecord.getCompleteness() != null) {
			map.put("completeness", profileRecord.getCompleteness().intValue());
		}
		if (profileRecord.getUserId() != null) {
			map.put("user_id", profileRecord.getUserId().intValue());
		}
		if (profileRecord.getCreateTime() != null) {
			map.put("create_time", DateUtils.dateToShortTime(profileRecord.getCreateTime()));
		}
		if (profileRecord.getUpdateTime() != null) {
			map.put("update_time", DateUtils.dateToShortTime(profileRecord.getUpdateTime()));
		}
		//在profile中添加origin字段的获取，用于获取第三方简历来源
		map.put("origin", profileRecord.getOrigin());
		return map;
	}

	@Autowired
	private IndustryDao dictIndustryDao;

	@Autowired
	private PositionDao dictPositionDao;

	@Autowired
	private CityDao dictCityDao;

	@Autowired
	private WXUserDao wxuserDao;

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
	private OtherDao otherDao;

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

	@Autowired
	private ProfileCompletenessImpl completenessImpl;

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

	public WXUserDao getWxuserDao() {
		return wxuserDao;
	}

	public void setWxuserDao(WXUserDao wxuserDao) {
		this.wxuserDao = wxuserDao;
	}

	public ProfileCompletenessImpl getCompletenessImpl() {
		return completenessImpl;
	}

	public void setCompletenessImpl(ProfileCompletenessImpl completenessImpl) {
		this.completenessImpl = completenessImpl;
	}

	public CityDao getDictCityDao() {
		return dictCityDao;
	}

	public void setDictCityDao(CityDao dictCityDao) {
		this.dictCityDao = dictCityDao;
	}

	public IndustryDao getDictIndustryDao() {
		return dictIndustryDao;
	}

	public void setDictIndustryDao(IndustryDao dictIndustryDao) {
		this.dictIndustryDao = dictIndustryDao;
	}

	public PositionDao getDictPositionDao() {
		return dictPositionDao;
	}

	public void setDictPositionDao(PositionDao dictPositionDao) {
		this.dictPositionDao = dictPositionDao;
	}

	public OtherDao getOtherDao() {
		return otherDao;
	}

	public void setOtherDao(OtherDao otherDao) {
		this.otherDao = otherDao;
	}
}