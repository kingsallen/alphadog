package com.moseeker.profile.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jooq.types.UInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.profiledb.tables.records.ProfileAwardsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.db.profiledb.tables.records.ProfileCompletenessRecord;
import com.moseeker.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionCityRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionPositionRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionRecord;
import com.moseeker.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorkexpRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorksRecord;
import com.moseeker.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.profile.dao.AwardsDao;
import com.moseeker.profile.dao.CompanyDao;
import com.moseeker.profile.dao.CompletenessDao;
import com.moseeker.profile.dao.CredentialsDao;
import com.moseeker.profile.dao.IntentionCityDao;
import com.moseeker.profile.dao.IntentionDao;
import com.moseeker.profile.dao.IntentionPositionDao;
import com.moseeker.profile.dao.LanguageDao;
import com.moseeker.profile.dao.ProfileBasicDao;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.profile.dao.ProjectExpDao;
import com.moseeker.profile.dao.SkillDao;
import com.moseeker.profile.dao.UserDao;
import com.moseeker.profile.dao.UserSettingsDao;
import com.moseeker.profile.dao.WXUserDao;
import com.moseeker.profile.dao.WorkExpDao;
import com.moseeker.profile.dao.WorksDao;
import com.moseeker.profile.service.impl.serviceutils.CompletenessCalculator;

@Service
public class ProfileCompletenessImpl {

	Logger logger = LoggerFactory.getLogger(ProfileCompletenessImpl.class);

	private CompletenessCalculator completenessCalculator = new CompletenessCalculator();

	@Autowired
	private UserDao userDao;

	@Autowired
	private ProfileDao profileDao;

	@Autowired
	private WXUserDao wxuserDao;

	@Autowired
	private UserSettingsDao settingDao;

	@Autowired
	private ProfileBasicDao basicDao;

	@Autowired
	private WorkExpDao workExpDao;

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private ProjectExpDao projectExpDao;

	@Autowired
	private LanguageDao languageDao;

	@Autowired
	private SkillDao skillDao;

	@Autowired
	private CredentialsDao credentialsDao;

	@Autowired
	private WorksDao worksDao;

	@Autowired
	private AwardsDao awardsDao;

	@Autowired
	private IntentionDao intentionDao;

	@Autowired
	private IntentionCityDao intentionCityDao;

	@Autowired
	private IntentionPositionDao intentionPositionDao;

	@Autowired
	private CompletenessDao completenessDao;

	public void reCalculateUserUser(int profileId) {
		ProfileCompletenessRecord completenessRecord = completenessDao.getCompletenessByProfileId(profileId);
		if (completenessRecord == null) {
			reCalculateProfileCompleteness(profileId);
		} else {
			ProfileProfileRecord profileRecord = profileDao.getProfileByIdOrUserIdOrUUID(0, profileId, null);
			if (profileRecord != null) {
				UserUserRecord userRecord = userDao.getUserById(profileRecord.getUserId().intValue());
				UserWxUserRecord wxuserRecord = null;
				UserSettingsRecord settingRecord = null;
				if (userRecord != null) {
					settingRecord = settingDao.getUserSettingsById(profileRecord.getUserId().intValue());
					try {
						wxuserRecord = wxuserDao.getWXUserByUserId(userRecord.getId().intValue());
					} catch (SQLException e) {
						logger.error(e.getMessage(), e);
					}
					int useruserCompleteness = completenessCalculator.calculateUserUser(userRecord, settingRecord,
							wxuserRecord);

					if (completenessRecord.getUserUser().intValue() != useruserCompleteness) {
						completenessRecord.setUserUser(useruserCompleteness);
						completenessDao.updateCompleteness(completenessRecord);
					}
				}
			}
		}
	}

	private int reCalculateProfileCompleteness(int profileId) {
		int result = 0;
		ProfileProfileRecord profileRecord = profileDao.getProfileByIdOrUserIdOrUUID(0, profileId, null);
		if (profileRecord != null) {

			ProfileCompletenessRecord completenessRecord = completenessDao.getCompletenessByProfileId(profileId);
			if (completenessRecord == null) {
				completenessRecord = new ProfileCompletenessRecord();
				completenessRecord.setProfileId(UInteger.valueOf(profileId));
			}

			UserUserRecord userRecord = userDao.getUserById(profileRecord.getUserId().intValue());
			UserWxUserRecord wxuserRecord = null;
			UserSettingsRecord settingRecord = null;
			if (userRecord != null) {
				settingRecord = settingDao.getUserSettingsById(profileRecord.getUserId().intValue());
				try {
					wxuserRecord = wxuserDao.getWXUserByUserId(userRecord.getId().intValue());
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				}
				int useruserCompleteness = completenessCalculator.calculateUserUser(userRecord, settingRecord,
						wxuserRecord);
				completenessRecord.setUserUser(useruserCompleteness);
			}

			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("profile_id", String.valueOf(profileId));

			ProfileBasicRecord basicRecord = null;
			try {
				basicRecord = basicDao.getResource(qu);
				int basicCompleteness = completenessCalculator.calculateProfileBasic(basicRecord);
				completenessRecord.setProfileBasic(basicCompleteness);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			List<ProfileWorkexpRecord> workExps = null;
			List<HrCompanyRecord> companies = null;
			try {
				workExps = workExpDao.getResources(qu);
				List<Integer> companyIds = new ArrayList<>();
				if (workExps != null && workExps.size() > 0) {
					workExps.forEach(workExp -> {
						if (workExp.getCompanyId() != null && workExp.getCompanyId().intValue() > 0) {
							companyIds.add(workExp.getCompanyId().intValue());
						}
					});
				}
				companies = companyDao.getCompaniesByIds(companyIds);
				int workExpCompleteness = completenessCalculator.calculateProfileWorkexps(workExps, companies);
				completenessRecord.setProfileWorkexp(workExpCompleteness);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			List<ProfileProjectexpRecord> projectExps = null;
			try {
				projectExps = projectExpDao.getResources(qu);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			int projectExpCompleteness = completenessCalculator.calculateProjectexps(projectExps);
			completenessRecord.setProfileProjectexp(projectExpCompleteness);

			List<ProfileLanguageRecord> languageRecords = null;
			try {
				languageRecords = languageDao.getResources(qu);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			int languageCompleteness = completenessCalculator.calculateLanguages(languageRecords);
			completenessRecord.setProfileLanguage(languageCompleteness);

			List<ProfileSkillRecord> skillRecords = null;
			try {
				skillRecords = skillDao.getResources(qu);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			int skillCompleteness = completenessCalculator.calculateSkills(skillRecords);
			completenessRecord.setProfileSkill(skillCompleteness);

			List<ProfileCredentialsRecord> credentialsRecords = null;
			try {
				credentialsRecords = credentialsDao.getResources(qu);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			int credentialCompleteness = completenessCalculator.calculateCredentials(credentialsRecords);
			completenessRecord.setProfileCredentials(credentialCompleteness);

			List<ProfileAwardsRecord> awardRecords = null;
			try {
				awardRecords = awardsDao.getResources(qu);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			int awardCompleteness = completenessCalculator.calculateAwards(awardRecords);
			completenessRecord.setProfileAwards(awardCompleteness);

			List<ProfileWorksRecord> workRecords = null;
			try {
				workRecords = worksDao.getResources(qu);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			int worksCompleteness = completenessCalculator.calculateWorks(workRecords);
			completenessRecord.setProfileWorks(worksCompleteness);

			List<ProfileIntentionRecord> intentionRecords = null;
			try {
				intentionRecords = intentionDao.getResources(qu);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			List<ProfileIntentionCityRecord> cityRecords = null;
			List<ProfileIntentionPositionRecord> positionRecords = null;
			if (intentionRecords != null && intentionRecords.size() > 0) {
				List<Integer> intentionIds = new ArrayList<>();
				intentionRecords.forEach(intentionRecord -> {
					intentionIds.add(intentionRecord.getId().intValue());
				});
				cityRecords = intentionCityDao.getIntentionCities(intentionIds);
				positionRecords = intentionPositionDao.getIntentionPositions(intentionIds);
			}
			int intentionCompleteness = completenessCalculator.calculateIntentions(intentionRecords, cityRecords,
					positionRecords);
			completenessRecord.setProfileIntention(intentionCompleteness);
			result = completenessDao.saveOrUpdate(completenessRecord);
		}
		return result;
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

	public WXUserDao getWxuserDao() {
		return wxuserDao;
	}

	public void setWxuserDao(WXUserDao wxuserDao) {
		this.wxuserDao = wxuserDao;
	}

	public UserSettingsDao getSettingDao() {
		return settingDao;
	}

	public void setSettingDao(UserSettingsDao settingDao) {
		this.settingDao = settingDao;
	}

	public CompletenessDao getCompletenessDao() {
		return completenessDao;
	}

	public void setCompletenessDao(CompletenessDao completenessDao) {
		this.completenessDao = completenessDao;
	}
}
