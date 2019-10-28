package com.moseeker.entity.biz;

import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.profiledb.*;
import com.moseeker.baseorm.dao.userdb.UserSettingsDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.*;
import com.moseeker.baseorm.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.providerutils.QueryUtil;

import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.SensorSend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@CounterIface
public class ProfileCompletenessImpl {

    Logger logger = LoggerFactory.getLogger(ProfileCompletenessImpl.class);

    @Autowired
    private UserUserDao userDao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private UserWxUserDao wxuserDao;

    @Autowired
    private UserSettingsDao settingDao;

    @Autowired
    private ProfileBasicDao basicDao;

    @Autowired
    private ProfileWorkexpDao workExpDao;

    @Autowired
    private HrCompanyDao companyDao;

    @Autowired
    private ProfileProjectexpDao projectExpDao;

    @Autowired
    private ProfileLanguageDao languageDao;

    @Autowired
    private ProfileSkillDao skillDao;

    @Autowired
    private ProfileCredentialsDao credentialsDao;

    @Autowired
    private ProfileWorksDao worksDao;

    @Autowired
    private ProfileAwardsDao awardsDao;

    @Autowired
    private ProfileIntentionDao intentionDao;

    @Autowired
    private ProfileIntentionCityDao intentionCityDao;

    @Autowired
    private ProfileEducationDao educationDao;

    @Autowired
    private ProfileIntentionPositionDao intentionPositionDao;

    @Autowired
    private ProfileCompletenessDao completenessDao;

    @Autowired
    private SensorSend sensorSend;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    public int getCompleteness(int userId, String uuid, int profileId) {
        int totalComplementness = 0;
        ProfileProfileRecord profileRecord = profileDao.getProfileByIdOrUserIdOrUUID(userId, profileId, uuid);
        if (profileRecord == null) {
            return calculateUserUserByUserId(userId);
        }
        if (profileRecord.getCompleteness().intValue() != 0 && profileRecord.getCompleteness().intValue() != 10) {
            totalComplementness = profileRecord.getCompleteness().intValue();
        } else {
            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("profile_id", String.valueOf(profileRecord.getId().intValue()));
            ProfileCompletenessRecord completenessRecord;
            try {
                completenessRecord = completenessDao.getRecord(qu);
                if (completenessRecord != null) {
                    totalComplementness = completenessRecord.getUserUser()
                            + completenessRecord.getProfileBasic() + completenessRecord.getProfileWorkexp()
                            + completenessRecord.getProfileEducation() + completenessRecord.getProfileProjectexp()
                            + completenessRecord.getProfileLanguage() + completenessRecord.getProfileSkill()
                            + completenessRecord.getProfileCredentials() + completenessRecord.getProfileAwards()
                            + completenessRecord.getProfileWorks() + completenessRecord.getProfileIntention();
                    if (totalComplementness != profileRecord.getCompleteness().intValue()) {
                        profileRecord.setCompleteness((byte) (totalComplementness));
                        profileDao.updateRecord(profileRecord);
                    }
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
                ProfileBasicRecord record = basicDao.getRecord(qu);
                ProfileProfileRecord profileRecord = profileDao.getProfileByIdOrUserIdOrUUID(0, profileId, null);
                if (profileRecord != null) {
                    UserUserRecord userRecord = userDao.getUserById(profileRecord.getUserId().intValue());
                    if (userRecord != null) {
                        int basicCompleteness = CompletenessCalculator.calculateProfileBasic(record, userRecord.getMobile());
                        if (basicCompleteness != completenessRecord.getProfileBasic()) {
                            completenessRecord.setProfileBasic(basicCompleteness);
                            result = completenessDao.updateCompleteness(completenessRecord);
                            reCalculateProfileCompleteness(completenessRecord);
                        } else {
                            result = 1;
                        }
                    }

                }

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

    public int calculateUserUserByUserId(int userId) {
        int useruserCompleteness = 0;
        UserUserRecord userRecord = userDao.getUserById(userId);
        UserWxUserRecord wxuserRecord = null;
        UserSettingsRecord settingRecord = null;
        if (userRecord != null) {
            settingRecord = settingDao.getUserSettingsById(userId);
            wxuserRecord = wxuserDao.getWXUserByUserId(userId);
            useruserCompleteness = CompletenessCalculator.calculateUserUser(userRecord, settingRecord,
                    wxuserRecord);
        }
        return useruserCompleteness;
    }

    public int reCalculateUserUserByUserIdOrMobile(int userId, String mobile) {
        int result = 0;
        ProfileProfileRecord profileRecord = null;
        if (userId == 0 && StringUtils.isNotNullOrEmpty(mobile)) {
            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("username", mobile);
            try {
                UserUserRecord userRecord = userDao.getRecord(qu);
                if (userRecord != null) {
                    profileRecord = profileDao.getProfileByIdOrUserIdOrUUID(userRecord.getId().intValue(), 0, null);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            profileRecord = profileDao.getProfileByIdOrUserIdOrUUID(userId, 0, null);
        }
        if (profileRecord != null) {
            ProfileCompletenessRecord completenessRecord = completenessDao.getCompletenessByProfileId(profileRecord.getId().intValue());
            if (completenessRecord == null) {
                reCalculateProfileCompleteness(profileRecord.getId().intValue());
            } else {
                UserUserRecord userRecord = userDao.getUserById(profileRecord.getUserId().intValue());
                UserWxUserRecord wxuserRecord = null;
                UserSettingsRecord settingRecord = null;
                if (userRecord != null) {
                    settingRecord = settingDao.getUserSettingsById(profileRecord.getUserId().intValue());
                    wxuserRecord = wxuserDao.getWXUserByUserId(userRecord.getId().intValue());
                    int useruserCompleteness = CompletenessCalculator.calculateUserUser(userRecord, settingRecord,
                            wxuserRecord);

                    if (completenessRecord.getUserUser().intValue() != useruserCompleteness) {
                        completenessRecord.setUserUser(useruserCompleteness);
                        result = completenessDao.updateCompleteness(completenessRecord);
                        reCalculateProfileCompleteness(completenessRecord);
                    } else {
                        result = 1;
                    }
                    String distinctId = profileRecord.getUserId().toString();
                    String property=String.valueOf(useruserCompleteness);
                    logger.info("ProfileCompletenessImpl.reCalculateUserUserByUserIdOrMobile213  distinctId{}"+distinctId+ "eventName{}"+"ProfileCompleteness"+property);
                    sensorSend.profileSet(distinctId,"ProfileCompleteness",property);
                    redisClient.set(Constant.APPID_ALPHADOG, KeyIdentifier.USER_PROFILE_COMPLETENESS.toString(), distinctId, String.valueOf(property));
                }
            }
        }
        return result;
    }

    public int reCalculateUserUser(int profileId) {
        int result = 0;
//			ProfileCompletenessRecord completenessRecord = completenessDao.getCompletenessByProfileId(profileId);
//			if (completenessRecord == null) {
        reCalculateProfileCompleteness(profileId);
//			} else {
//				ProfileProfileRecord profileRecord = profileDao.getProfileByIdOrUserIdOrUUID(0, profileId, null);
//				if (profileRecord != null) {
//					UserUserRecord userRecord = userDao.getUserById(profileRecord.getUserId().intValue());
//					UserWxUserRecord wxuserRecord = null;
//					UserSettingsRecord settingRecord = null;
//					if (userRecord != null) {
//						settingRecord = settingDao.getUserSettingsById(profileRecord.getUserId().intValue());
//						try {
//							wxuserRecord = wxuserDao.getWXUserByUserId(userRecord.getId().intValue());
//						} catch (SQLException e) {
//							logger.error(e.getMessage(), e);
//						}
//						int useruserCompleteness = completenessCalculator.calculateUserUser(userRecord, settingRecord,
//								wxuserRecord);
//
//						if (completenessRecord.getUserUser().intValue() != useruserCompleteness) {
//							completenessRecord.setUserUser(useruserCompleteness);
//							result = completenessDao.updateCompleteness(completenessRecord);
//							reCalculateProfileCompleteness(completenessRecord);
//						} else {
//							result = 1;
//						}
//					}
//				}
//			}
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
        if (profileId == 0) {
            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("id", String.valueOf(workExpId));
            try {
                ProfileWorkexpRecord workExpRecord = workExpDao.getRecord(qu);
                if (workExpRecord != null && workExpRecord.getProfileId() != null) {
                    profileId = workExpRecord.getProfileId().intValue();
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
//			ProfileCompletenessRecord completenessRecord = completenessDao.getCompletenessByProfileId(profileId);
//			if (completenessRecord == null) {
        reCalculateProfileCompleteness(profileId);
//			} else {
//				
//				QueryUtil qu = new QueryUtil();
//				qu.addEqualFilter("profile_id", String.valueOf(profileId));
//				
//				List<ProfileWorkexpRecord> workExps = null;
//				List<HrCompanyRecord> companies = null;
//				
//				try {
//					List<ProfileEducationRecord> educations =educationDao.getResources(qu);
//					ProfileBasicRecord basicRecord = basicDao.getResource(qu);
//					Date birth=null;
//					if(basicRecord!=null){
//						birth=basicRecord.getBirth();
//					}
//					workExps = workExpDao.getResources(qu);
//					List<Integer> companyIds = new ArrayList<>();
//					if (workExps != null && workExps.size() > 0) {
//						workExps.forEach(workExp -> {
//							if (workExp.getCompanyId() != null && workExp.getCompanyId().intValue() > 0) {
//								companyIds.add(workExp.getCompanyId().intValue());
//							}
//						});
//					}
//					companies = companyDao.getCompaniesByIds(companyIds);
////					int workExpCompleteness = completenessCalculator.calculateProfileWorkexps(workExps, companies);
//					int workExpCompleteness = completenessCalculator.calculateProfileWorkexps(workExps, educations,birth);
//					if(workExpCompleteness != completenessRecord.getProfileWorkexp()) {
//						completenessRecord.setProfileWorkexp(workExpCompleteness);
//						result = completenessDao.updateCompleteness(completenessRecord);
//						reCalculateProfileCompleteness(completenessRecord);
//					} else {
//						result = 1;
//					}
//				} catch (Exception e) {
//					logger.error(e.getMessage(), e);
//				}
//			}
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
        if (profileId == 0) {
            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("id", String.valueOf(educationId));
            try {
                ProfileEducationRecord educationRecord = educationDao.getRecord(qu);
                if (educationRecord != null && educationRecord.getProfileId() != null) {
                    profileId = educationRecord.getProfileId().intValue();
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

//			ProfileCompletenessRecord completenessRecord = completenessDao.getCompletenessByProfileId(profileId);
//			if (completenessRecord == null) {
        reCalculateProfileCompleteness(profileId);
        result = 1;
//			} else {
//				
//				QueryUtil qu = new QueryUtil();
//				qu.addEqualFilter("profile_id", String.valueOf(profileId));
//				try {
//					List<ProfileEducationRecord> educationRecords = educationDao.getResources(qu);
//					int educationCompleteness = completenessCalculator.calculateProfileEducations(educationRecords);
//					if(educationCompleteness != completenessRecord.getProfileEducation().intValue()) {
//						completenessRecord.setProfileEducation(educationCompleteness);
//						result = completenessDao.updateCompleteness(completenessRecord);
//						reCalculateProfileCompleteness(completenessRecord);
//					} else {
//						result = 1;
//					}
//				} catch (Exception e) {
//					logger.error(e.getMessage(), e);
//				}
//			}
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
        if (profileId == 0) {
            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("id", String.valueOf(projectExpId));
            try {
                ProfileProjectexpRecord projectExpRecord = projectExpDao.getRecord(qu);
                if (projectExpRecord != null && projectExpRecord.getProfileId() != null) {
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
                //传参加入工作经历
                List<ProfileWorkexpRecord> workExps = workExpDao.getRecords(qu);
                List<ProfileProjectexpRecord> ProjectExpRecords = projectExpDao.getRecords(qu);
                int projectExpCompleteness = CompletenessCalculator.calculateProjectexps(ProjectExpRecords, workExps);
                if (projectExpCompleteness != completenessRecord.getProfileProjectexp().intValue()) {
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
        if (profileId == null || profileId == 0) {
            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("id", String.valueOf(languageId));
            try {
                ProfileLanguageRecord languageRecord = languageDao.getRecord(qu);
                if (languageRecord != null && languageRecord.getProfileId() != null) {
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
                List<ProfileLanguageRecord> languageRecords = languageDao.getRecords(qu);
                int languageCompleteness = CompletenessCalculator.calculateLanguages(languageRecords);
                if (languageCompleteness != completenessRecord.getProfileLanguage().intValue()) {
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
        if (profileId == 0) {
            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("id", String.valueOf(skillId));
            try {
                ProfileSkillRecord skillRecord = skillDao.getRecord(qu);
                if (skillRecord != null && skillRecord.getProfileId() != null) {
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
                List<ProfileSkillRecord> skillRecords = skillDao.getRecords(qu);
                int skillCompleteness = CompletenessCalculator.calculateSkills(skillRecords);
                if (skillCompleteness != completenessRecord.getProfileSkill().intValue()) {
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
        if (profileId == 0) {
            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("id", String.valueOf(credentialId));
            try {
                ProfileCredentialsRecord credentialRecord = credentialsDao.getRecord(qu);
                if (credentialRecord != null && credentialRecord.getProfileId() != null) {
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
                List<ProfileCredentialsRecord> credentialRecords = credentialsDao.getRecords(qu);
                int credentialCompleteness = CompletenessCalculator.calculateCredentials(credentialRecords);
                if (credentialCompleteness != completenessRecord.getProfileCredentials().intValue()) {
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
        if (profileId == 0) {
            Query.QueryBuilder qu = new Query.QueryBuilder();
            qu.where("id", awardId);
            try {
                ProfileAwardsRecord awardRecord = awardsDao.getRecord(qu.buildQuery());
                if (awardRecord != null && awardRecord.getProfileId() != null) {
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
                List<ProfileAwardsRecord> awardRecords = awardsDao.getRecords(qu);
                int awardCompleteness = CompletenessCalculator.calculateAwards(awardRecords);
                if (awardCompleteness != completenessRecord.getProfileAwards().intValue()) {
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
        if (profileId == 0) {
            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("id", String.valueOf(worksId));
            try {
                ProfileWorksRecord worksRecord = worksDao.getRecord(qu);
                if (worksRecord != null && worksRecord.getProfileId() != null) {
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
                List<ProfileWorksRecord> worksRecords = worksDao.getRecords(qu);
                int worksCompleteness = CompletenessCalculator.calculateWorks(worksRecords);
                if (worksCompleteness != completenessRecord.getProfileWorks().intValue()) {
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
        if (profileId == 0) {
            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("id", String.valueOf(intentionId));
            try {
                ProfileIntentionRecord intentionRecord = intentionDao.getRecord(qu);
                if (intentionRecord != null && intentionRecord.getProfileId() != null) {
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
                List<ProfileIntentionRecord> intentionRecords = intentionDao.getRecords(qu);
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
                int intentionCompleteness = CompletenessCalculator.calculateIntentions(intentionRecords, cityRecords, positionRecords);
                if (intentionCompleteness != completenessRecord.getProfileIntention().intValue()) {
                    completenessRecord.setProfileIntention(intentionCompleteness);
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

    public int reCalculateProfileCompleteness(int profileId) {
        int completeness = 0;
        ProfileProfileRecord profileRecord = profileDao.getProfileByIdOrUserIdOrUUID(0, profileId, null);
        if (profileRecord != null) {

            ProfileCompletenessRecord completenessRecord = completenessDao.getCompletenessByProfileId(profileId);
            if (completenessRecord == null) {
                completenessRecord = new ProfileCompletenessRecord();
                completenessRecord.setProfileId((int) (profileId));
            }

            UserUserRecord userRecord = userDao.getUserById(profileRecord.getUserId().intValue());
            UserWxUserRecord wxuserRecord = null;
            UserSettingsRecord settingRecord = null;
            if (userRecord != null) {
                settingRecord = settingDao.getUserSettingsById(profileRecord.getUserId().intValue());
                wxuserRecord = wxuserDao.getWXUserByUserId(userRecord.getId().intValue());
                int useruserCompleteness = CompletenessCalculator.calculateUserUser(userRecord, settingRecord,
                        wxuserRecord);
                completenessRecord.setUserUser(useruserCompleteness);
                completeness += useruserCompleteness;
            }

            QueryUtil qu = new QueryUtil();
            qu.addEqualFilter("profile_id", String.valueOf(profileId));
            Date birth = null;
            ProfileBasicRecord basicRecord = null;
            if (userRecord != null) {
                basicRecord = basicDao.getRecord(qu);
                int basicCompleteness = CompletenessCalculator.calculateProfileBasic(basicRecord, userRecord.getMobile());
                completenessRecord.setProfileBasic(basicCompleteness);
                completeness += basicCompleteness;
                if (basicRecord != null) {
                    birth = basicRecord.getBirth();
                }
            }
            List<ProfileEducationRecord> educations = null;
            try {
                educations = educationDao.getRecords(qu);
                int educationCompleteness = CompletenessCalculator.calculateProfileEducations(educations);
                completenessRecord.setProfileEducation(educationCompleteness);
                completeness += educationCompleteness;
            } catch (Exception e1) {
                logger.error(e1.getMessage(), e1);
            }
            List<ProfileWorkexpRecord> workExps = null;
            List<HrCompanyRecord> companies = null;
            try {
                workExps = workExpDao.getRecords(qu);
                List<Integer> companyIds = new ArrayList<>();
                if (workExps != null && workExps.size() > 0) {
                    workExps.forEach(workExp -> {
                        if (workExp.getCompanyId() != null && workExp.getCompanyId().intValue() > 0) {
                            companyIds.add(workExp.getCompanyId().intValue());
                        }
                    });
                }
                companies = companyDao.getCompaniesByIds(companyIds);
//				int workExpCompleteness = completenessCalculator.calculateProfileWorkexps(workExps, companies);
                int workExpCompleteness = CompletenessCalculator.calculateProfileWorkexps(workExps, educations, birth);
                completenessRecord.setProfileWorkexp(workExpCompleteness);
                completeness += workExpCompleteness;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }


            List<ProfileProjectexpRecord> projectExps = null;
            try {
                projectExps = projectExpDao.getRecords(qu);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            int projectExpCompleteness = CompletenessCalculator.calculateProjectexps(projectExps, workExps);
            completenessRecord.setProfileProjectexp(projectExpCompleteness);
            completeness += projectExpCompleteness;

            List<ProfileLanguageRecord> languageRecords = null;
            try {
                languageRecords = languageDao.getRecords(qu);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            int languageCompleteness = CompletenessCalculator.calculateLanguages(languageRecords);
            completenessRecord.setProfileLanguage(languageCompleteness);
            completeness += languageCompleteness;

            List<ProfileSkillRecord> skillRecords = null;
            try {
                skillRecords = skillDao.getRecords(qu);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            int skillCompleteness = CompletenessCalculator.calculateSkills(skillRecords);
            completenessRecord.setProfileSkill(skillCompleteness);
            completeness += skillCompleteness;

            List<ProfileCredentialsRecord> credentialsRecords = null;
            try {
                credentialsRecords = credentialsDao.getRecords(qu);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            int credentialCompleteness = CompletenessCalculator.calculateCredentials(credentialsRecords);
            completenessRecord.setProfileCredentials(credentialCompleteness);
            completeness += credentialCompleteness;

            List<ProfileAwardsRecord> awardRecords = null;
            try {
                awardRecords = awardsDao.getRecords(qu);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            int awardCompleteness = CompletenessCalculator.calculateAwards(awardRecords);
            completenessRecord.setProfileAwards(awardCompleteness);
            completeness += awardCompleteness;

            List<ProfileWorksRecord> workRecords = null;
            try {
                workRecords = worksDao.getRecords(qu);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            int worksCompleteness = CompletenessCalculator.calculateWorks(workRecords);
            completenessRecord.setProfileWorks(worksCompleteness);
            completeness += worksCompleteness;

            List<ProfileIntentionRecord> intentionRecords = null;
            try {
                intentionRecords = intentionDao.getRecords(qu);
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
            int intentionCompleteness = CompletenessCalculator.calculateIntentions(intentionRecords, cityRecords,
                    positionRecords);
            completenessRecord.setProfileIntention(intentionCompleteness);
            completenessDao.saveOrUpdate(completenessRecord);
            completeness += intentionCompleteness;
            profileRecord.setCompleteness((byte) (completeness));
            try {
                profileDao.updateRecord(profileRecord);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            String distinctId = profileRecord.getUserId().toString();
            String property=String.valueOf(completeness);
            logger.info("ProfileCompletenessImpl.reCalculateProfileCompleteness807  distinctId{}"+distinctId+ "eventName{}"+"ProfileCompleteness"+property);
            sensorSend.profileSet(distinctId,"ProfileCompleteness",property);
            redisClient.set(Constant.APPID_ALPHADOG, KeyIdentifier.USER_PROFILE_COMPLETENESS.toString(), distinctId, String.valueOf(property));
        }
        return completeness;
    }

    private void reCalculateProfileCompleteness(ProfileCompletenessRecord completenessRecord) {
        int totalComplementness = completenessRecord.getUserUser()
                + completenessRecord.getProfileBasic() + completenessRecord.getProfileWorkexp()
                + completenessRecord.getProfileEducation() + completenessRecord.getProfileProjectexp()
                + completenessRecord.getProfileLanguage() + completenessRecord.getProfileSkill()
                + completenessRecord.getProfileCredentials() + completenessRecord.getProfileAwards()
                + completenessRecord.getProfileWorks() + completenessRecord.getProfileIntention();
        ProfileProfileRecord profileRecord = profileDao.getProfileByIdOrUserIdOrUUID(0,
                completenessRecord.getProfileId().intValue(), null);
        if (profileRecord != null) {
            profileRecord.setCompleteness((byte) (totalComplementness));
            profileDao.updateRecord(profileRecord);
            String distinctId = profileRecord.getUserId().toString();
            String property=String.valueOf(totalComplementness);
            logger.info("ProfileCompletenessImpl.reCalculateProfileCompleteness835  distinctId{}"+distinctId+ "eventName{}"+"ProfileCompleteness"+property);
            sensorSend.profileSet(distinctId,"ProfileCompleteness",property);
            redisClient.set(Constant.APPID_ALPHADOG, KeyIdentifier.USER_PROFILE_COMPLETENESS.toString(), distinctId, String.valueOf(property));
        }
    }
}