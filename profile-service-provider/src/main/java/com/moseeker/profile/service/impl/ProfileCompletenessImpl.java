package com.moseeker.profile.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.profiledb.tables.records.ProfileAwardsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.db.profiledb.tables.records.ProfileCompletenessRecord;
import com.moseeker.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileEducationRecord;
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
import com.moseeker.profile.dao.EducationDao;
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
	private EducationDao educationDao;

	@Autowired
	private IntentionPositionDao intentionPositionDao;
	
	@Autowired
	private CompletenessDao completenessDao;
	
	public int getCompleteness(int userId, String uuid, int profileId) {
		int totalComplementness = 0;
		ProfileProfileRecord profileRecord = profileDao.getProfileByIdOrUserIdOrUUID(userId, profileId, uuid);
		if (profileRecord == null) {
			return 0;
		}
		if(profileRecord.getCompleteness().intValue() != 0 && profileRecord.getCompleteness().intValue() != 10) {
			totalComplementness = profileRecord.getCompleteness().intValue();
		} else {
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("profile_id", String.valueOf(profileRecord.getId().intValue()));
			ProfileCompletenessRecord completenessRecord;
			try {
				completenessRecord = completenessDao.getResource(qu);
				if (completenessRecord != null) {
					totalComplementness = completenessRecord.getUserUser()
							+ completenessRecord.getProfileBasic() + completenessRecord.getProfileWorkexp()
							+ completenessRecord.getProfileEducation() + completenessRecord.getProfileProjectexp()
							+ completenessRecord.getProfileLanguage() + completenessRecord.getProfileSkill()
							+ completenessRecord.getProfileCredentials() + completenessRecord.getProfileAwards()
							+ completenessRecord.getProfileWorks() + completenessRecord.getProfileIntention();
				} else {
					totalComplementness = reCalculateProfileCompleteness(profileRecord.getId().intValue());
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return totalComplementness;
	}
	
	public int reCalculateProfileBasic(int profileId) {
		int result = 0;
		ProfileCompletenessRecord completenessRecord = completenessDao.getCompletenessByProfileId(profileId);
		if (completenessRecord == null) {
			reCalculateProfileCompleteness(profileId);
		} else {
			
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("profile_id", String.valueOf(profileId));
			try {
				ProfileBasicRecord record = basicDao.getResource(qu);
				
				int basicCompleteness = completenessCalculator.calculateProfileBasic(record);
				if(basicCompleteness != completenessRecord.getProfileBasic()) {
					completenessRecord.setProfileBasic(basicCompleteness);
					result = completenessDao.updateCompleteness(completenessRecord);
					reCalculateProfileCompleteness(completenessRecord);
				} else {
					result = 1;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}
	
	public int calculateUserUserByUserId(int userId, String mobile) {
		int useruserCompleteness = 0;
		UserUserRecord userRecord = userDao.getUserById(userId);
		UserWxUserRecord wxuserRecord = null;
		UserSettingsRecord settingRecord = null;
		if (userRecord != null) {
			settingRecord = settingDao.getUserSettingsById(userId);
			try {
				wxuserRecord = wxuserDao.getWXUserByUserId(userId);
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
			useruserCompleteness = completenessCalculator.calculateUserUser(userRecord, settingRecord,
					wxuserRecord);
		}
		return useruserCompleteness;
	}
	
	public int reCalculateUserUserByUserIdOrMobile(int userId, String mobile) {
		int result = 0;
		ProfileProfileRecord profileRecord = null;
		try {
			if(userId == 0 && StringUtils.isNotNullOrEmpty(mobile)) {
				QueryUtil qu = new QueryUtil();
				qu.addEqualFilter("username", mobile);
				try {
					UserUserRecord userRecord = userDao.getResource(qu);
					if(userRecord != null) {
						profileRecord = profileDao.getProfileByIdOrUserIdOrUUID(userRecord.getId().intValue(), 0, null);
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}		
			} else {
				profileRecord = profileDao.getProfileByIdOrUserIdOrUUID(userId, 0, null);
			}
			if(profileRecord != null) {
				ProfileCompletenessRecord completenessRecord = completenessDao.getCompletenessByProfileId(profileRecord.getId().intValue());
				if (completenessRecord == null) {
					reCalculateProfileCompleteness(profileRecord.getId().intValue());
				} else {
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
							result = completenessDao.updateCompleteness(completenessRecord);
							reCalculateProfileCompleteness(completenessRecord);
						} else {
							result = 1;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	public int reCalculateUserUser(int profileId) {
		int result = 0;
		try {
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
							result = completenessDao.updateCompleteness(completenessRecord);
							reCalculateProfileCompleteness(completenessRecord);
						} else {
							result = 1;
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	public int reCalculateProfileWorkExp(int profileId) {
		return reCalculateProfileWorkExp(profileId, 0);
	}
	
	public int reCalculateProfileWorkExpUseWorkExpId(int workExpId) {
		return reCalculateProfileWorkExp(0, workExpId);
	}
	
	public int reCalculateProfileWorkExp(int profileId, int workExpId) {
		int result = 0;
		try {
			if(profileId == 0) {
				QueryUtil qu = new QueryUtil();
				qu.addEqualFilter("id", String.valueOf(workExpId));
				try {
					ProfileWorkexpRecord workExpRecord = workExpDao.getResource(qu);
					if(workExpRecord.getProfileId() != null) {
						profileId = workExpRecord.getProfileId().intValue();
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			ProfileCompletenessRecord completenessRecord = completenessDao.getCompletenessByProfileId(profileId);
			if (completenessRecord == null) {
				reCalculateProfileCompleteness(profileId);
			} else {
				
				QueryUtil qu = new QueryUtil();
				qu.addEqualFilter("profile_id", String.valueOf(profileId));
				
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
					if(workExpCompleteness != completenessRecord.getProfileWorkexp()) {
						completenessRecord.setProfileWorkexp(workExpCompleteness);
						result = completenessDao.updateCompleteness(completenessRecord);
						reCalculateProfileCompleteness(completenessRecord);
					} else {
						result = 1;
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	public void reCalculateProfileEducationByEducationId(int educationId) {
		reCalculateProfileEducation(0, educationId);
	}
	
	public void reCalculateProfileEducationByProfileId(int profileId) {
		reCalculateProfileEducation(profileId, 0);
	}
	
	public int reCalculateProfileEducation(int profileId, int educationId) {
		int result = 0;
		try {
			if(profileId == 0) {
				QueryUtil qu = new QueryUtil();
				qu.addEqualFilter("id", String.valueOf(educationId));
				try {
					ProfileEducationRecord educationRecord = educationDao.getResource(qu);
					if(educationRecord.getProfileId() != null) {
						profileId = educationRecord.getProfileId().intValue();
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			
			ProfileCompletenessRecord completenessRecord = completenessDao.getCompletenessByProfileId(profileId);
			if (completenessRecord == null) {
				reCalculateProfileCompleteness(profileId);
			} else {
				
				QueryUtil qu = new QueryUtil();
				qu.addEqualFilter("profile_id", String.valueOf(profileId));
				try {
					List<ProfileEducationRecord> educationRecords = educationDao.getResources(qu);
					int educationCompleteness = completenessCalculator.calculateProfileEducations(educationRecords);
					if(educationCompleteness != completenessRecord.getProfileEducation().intValue()) {
						completenessRecord.setProfileEducation(educationCompleteness);
						result = completenessDao.updateCompleteness(completenessRecord);
						reCalculateProfileCompleteness(completenessRecord);
					} else {
						result = 1;
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}
	
	public int reCalculateProfileProjectExpByProjectExpId(Integer projectExpId) {
		return reCalculateProfileProjectExp(0, projectExpId);
	}
	
	public int reCalculateProfileProjectExpByProfileId(Integer profileId) {
		return reCalculateProfileProjectExp(profileId, 0);
	}
	
	public int reCalculateProfileProjectExp(int profileId, int projectExpId) {
		int result = 0;
		if(profileId == 0) {
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("id", String.valueOf(projectExpId));
			try {
				ProfileProjectexpRecord projectExpRecord = projectExpDao.getResource(qu);
				if(projectExpRecord.getProfileId() != null) {
					profileId = projectExpRecord.getProfileId().intValue();
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		ProfileCompletenessRecord completenessRecord = completenessDao.getCompletenessByProfileId(profileId);
		if (completenessRecord == null) {
			reCalculateProfileCompleteness(profileId);
		} else {
			
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("profile_id", String.valueOf(profileId));
			try {
				List<ProfileProjectexpRecord> ProjectExpRecords = projectExpDao.getResources(qu);
				int projectExpCompleteness = completenessCalculator.calculateProjectexps(ProjectExpRecords);
				if(projectExpCompleteness != completenessRecord.getProfileProjectexp().intValue()) {
					completenessRecord.setProfileProjectexp(projectExpCompleteness);
					result = completenessDao.updateCompleteness(completenessRecord);
					reCalculateProfileCompleteness(completenessRecord);
				} else {
					result = 1;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}
	
	public int recalculateprofileLanguage(Integer profileId, int languageId) {
		int result = 0;
		if(profileId == 0) {
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("id", String.valueOf(languageId));
			try {
				ProfileLanguageRecord languageRecord = languageDao.getResource(qu);
				if(languageRecord.getProfileId() != null) {
					profileId = languageRecord.getProfileId().intValue();
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		ProfileCompletenessRecord completenessRecord = completenessDao.getCompletenessByProfileId(profileId);
		if (completenessRecord == null) {
			reCalculateProfileCompleteness(profileId);
		} else {
			
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("profile_id", String.valueOf(profileId));
			try {
				List<ProfileLanguageRecord> languageRecords = languageDao.getResources(qu);
				int languageCompleteness = completenessCalculator.calculateLanguages(languageRecords);
				if(languageCompleteness != completenessRecord.getProfileLanguage().intValue()) {
					completenessRecord.setProfileLanguage(languageCompleteness);
					result = completenessDao.updateCompleteness(completenessRecord);
					reCalculateProfileCompleteness(completenessRecord);
				} else {
					result = 1;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}
	
	public int reCalculateProfileSkill(Integer profileId, int skillId) {
		int result = 0;
		if(profileId == 0) {
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("id", String.valueOf(skillId));
			try {
				ProfileSkillRecord skillRecord = skillDao.getResource(qu);
				if(skillRecord.getProfileId() != null) {
					profileId = skillRecord.getProfileId().intValue();
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		ProfileCompletenessRecord completenessRecord = completenessDao.getCompletenessByProfileId(profileId);
		if (completenessRecord == null) {
			reCalculateProfileCompleteness(profileId);
		} else {
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("profile_id", String.valueOf(profileId));
			try {
				List<ProfileSkillRecord> skillRecords = skillDao.getResources(qu);
				int skillCompleteness = completenessCalculator.calculateSkills(skillRecords);
				if(skillCompleteness != completenessRecord.getProfileSkill().intValue()) {
					completenessRecord.setProfileSkill(skillCompleteness);
					result = completenessDao.updateCompleteness(completenessRecord);
					reCalculateProfileCompleteness(completenessRecord);
				} else {
					result = 1;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}
	
	public int recalculateProfileCredential(int profileId, int credentialId) {
		int result = 0;
		if(profileId == 0) {
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("id", String.valueOf(credentialId));
			try {
				ProfileCredentialsRecord credentialRecord = credentialsDao.getResource(qu);
				if(credentialRecord.getProfileId() != null) {
					profileId = credentialRecord.getProfileId().intValue();
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		ProfileCompletenessRecord completenessRecord = completenessDao.getCompletenessByProfileId(profileId);
		if (completenessRecord == null) {
			reCalculateProfileCompleteness(profileId);
		} else {
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("profile_id", String.valueOf(profileId));
			try {
				List<ProfileCredentialsRecord> credentialRecords = credentialsDao.getResources(qu);
				int credentialCompleteness = completenessCalculator.calculateCredentials(credentialRecords);
				if(credentialCompleteness != completenessRecord.getProfileCredentials().intValue()) {
					completenessRecord.setProfileCredentials(credentialCompleteness);
					result = completenessDao.updateCompleteness(completenessRecord);
					reCalculateProfileCompleteness(completenessRecord);
				} else {
					result = 1;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}
	
	public int reCalculateProfileAward(Integer profileId, int awardId) {
		int result = 0;
		if(profileId == 0) {
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("id", String.valueOf(awardId));
			try {
				ProfileAwardsRecord awardRecord = awardsDao.getResource(qu);
				if(awardRecord.getProfileId() != null) {
					profileId = awardRecord.getProfileId().intValue();
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		ProfileCompletenessRecord completenessRecord = completenessDao.getCompletenessByProfileId(profileId);
		if (completenessRecord == null) {
			reCalculateProfileCompleteness(profileId);
		} else {
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("profile_id", String.valueOf(profileId));
			try {
				List<ProfileAwardsRecord> awardRecords = awardsDao.getResources(qu);
				int awardCompleteness = completenessCalculator.calculateAwards(awardRecords);
				if(awardCompleteness != completenessRecord.getProfileAwards().intValue()) {
					completenessRecord.setProfileAwards(awardCompleteness);
					result = completenessDao.updateCompleteness(completenessRecord);
					reCalculateProfileCompleteness(completenessRecord);
				} else {
					result = 1;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}
	
	public int reCalculateProfileWorks(Integer profileId, int worksId) {
		int result = 0;
		if(profileId == 0) {
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("id", String.valueOf(worksId));
			try {
				ProfileWorksRecord worksRecord = worksDao.getResource(qu);
				if(worksRecord.getProfileId() != null) {
					profileId = worksRecord.getProfileId().intValue();
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		ProfileCompletenessRecord completenessRecord = completenessDao.getCompletenessByProfileId(profileId);
		if (completenessRecord == null) {
			reCalculateProfileCompleteness(profileId);
		} else {
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("profile_id", String.valueOf(profileId));
			try {
				List<ProfileWorksRecord> worksRecords = worksDao.getResources(qu);
				int worksCompleteness = completenessCalculator.calculateWorks(worksRecords);
				if(worksCompleteness != completenessRecord.getProfileWorks().intValue()) {
					completenessRecord.setProfileWorks(worksCompleteness);
					result = completenessDao.updateCompleteness(completenessRecord);
					reCalculateProfileCompleteness(completenessRecord);
				} else {
					result = 1;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}
	
	public int reCalculateProfileIntention(int profileId, int intentionId) {
		int result = 0;
		if(profileId == 0) {
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("id", String.valueOf(intentionId));
			try {
				ProfileIntentionRecord intentionRecord = intentionDao.getResource(qu);
				if(intentionRecord.getProfileId() != null) {
					profileId = intentionRecord.getProfileId().intValue();
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		ProfileCompletenessRecord completenessRecord = completenessDao.getCompletenessByProfileId(profileId);
		if (completenessRecord == null) {
			reCalculateProfileCompleteness(profileId);
		} else {
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("profile_id", String.valueOf(profileId));
			try {
				List<ProfileIntentionRecord> intentionRecords = intentionDao.getResources(qu);
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
				int intentionCompleteness = completenessCalculator.calculateIntentions(intentionRecords, cityRecords, positionRecords);
				if(intentionCompleteness != completenessRecord.getProfileIntention().intValue()) {
					completenessRecord.setProfileWorks(intentionCompleteness);
					result = completenessDao.updateCompleteness(completenessRecord);
					reCalculateProfileCompleteness(completenessRecord);
				} else {
					result = 1;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}

	private int reCalculateProfileCompleteness(int profileId) {
		int completeness = 0;
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
				completeness += useruserCompleteness;
			}

			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("profile_id", String.valueOf(profileId));

			ProfileBasicRecord basicRecord = null;
			try {
				basicRecord = basicDao.getResource(qu);
				int basicCompleteness = completenessCalculator.calculateProfileBasic(basicRecord);
				completenessRecord.setProfileBasic(basicCompleteness);
				completeness += basicCompleteness;
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
				completeness += workExpCompleteness;
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
			completeness += projectExpCompleteness;

			List<ProfileLanguageRecord> languageRecords = null;
			try {
				languageRecords = languageDao.getResources(qu);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			int languageCompleteness = completenessCalculator.calculateLanguages(languageRecords);
			completenessRecord.setProfileLanguage(languageCompleteness);
			completeness += languageCompleteness;

			List<ProfileSkillRecord> skillRecords = null;
			try {
				skillRecords = skillDao.getResources(qu);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			int skillCompleteness = completenessCalculator.calculateSkills(skillRecords);
			completenessRecord.setProfileSkill(skillCompleteness);
			completeness += skillCompleteness;

			List<ProfileCredentialsRecord> credentialsRecords = null;
			try {
				credentialsRecords = credentialsDao.getResources(qu);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			int credentialCompleteness = completenessCalculator.calculateCredentials(credentialsRecords);
			completenessRecord.setProfileCredentials(credentialCompleteness);
			completeness += credentialCompleteness;

			List<ProfileAwardsRecord> awardRecords = null;
			try {
				awardRecords = awardsDao.getResources(qu);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			int awardCompleteness = completenessCalculator.calculateAwards(awardRecords);
			completenessRecord.setProfileAwards(awardCompleteness);
			completeness += awardCompleteness;

			List<ProfileWorksRecord> workRecords = null;
			try {
				workRecords = worksDao.getResources(qu);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			int worksCompleteness = completenessCalculator.calculateWorks(workRecords);
			completenessRecord.setProfileWorks(worksCompleteness);
			completeness += worksCompleteness;

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
			completenessDao.saveOrUpdate(completenessRecord);
			completeness += intentionCompleteness;
			profileRecord.setCompleteness(UByte.valueOf(completeness));
			try {
				profileDao.putResource(profileRecord);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return completeness;
	}
	
	private void reCalculateProfileCompleteness(ProfileCompletenessRecord completenessRecord) throws Exception {
		int totalComplementness = completenessRecord.getUserUser()
				+ completenessRecord.getProfileBasic() + completenessRecord.getProfileWorkexp()
				+ completenessRecord.getProfileEducation() + completenessRecord.getProfileProjectexp()
				+ completenessRecord.getProfileLanguage() + completenessRecord.getProfileSkill()
				+ completenessRecord.getProfileCredentials() + completenessRecord.getProfileAwards()
				+ completenessRecord.getProfileWorks() + completenessRecord.getProfileIntention();
		ProfileProfileRecord profileRecord = profileDao.getProfileByIdOrUserIdOrUUID(0, completenessRecord.getProfileId().intValue(), null);
		if(profileRecord != null) {
			profileRecord.setCompleteness(UByte.valueOf(totalComplementness));
			profileDao.putResource(profileRecord);
		}
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
