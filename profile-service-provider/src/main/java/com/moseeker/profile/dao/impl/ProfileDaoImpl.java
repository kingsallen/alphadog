package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.db.dictdb.tables.DictCity;
import com.moseeker.db.dictdb.tables.DictCollege;
import com.moseeker.db.dictdb.tables.DictCountry;
import com.moseeker.db.dictdb.tables.DictIndustry;
import com.moseeker.db.dictdb.tables.DictPosition;
import com.moseeker.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.db.dictdb.tables.records.DictCollegeRecord;
import com.moseeker.db.dictdb.tables.records.DictCountryRecord;
import com.moseeker.db.dictdb.tables.records.DictIndustryRecord;
import com.moseeker.db.dictdb.tables.records.DictPositionRecord;
import com.moseeker.db.hrdb.tables.HrCompany;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.profiledb.tables.ProfileProfile;
import com.moseeker.db.profiledb.tables.records.ProfileAttachmentRecord;
import com.moseeker.db.profiledb.tables.records.ProfileAwardsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.db.profiledb.tables.records.ProfileCompletenessRecord;
import com.moseeker.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.db.profiledb.tables.records.ProfileImportRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionCityRecord;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionPositionRecord;
import com.moseeker.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.db.profiledb.tables.records.ProfileOtherRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorksRecord;
import com.moseeker.db.userdb.tables.UserSettings;
import com.moseeker.db.userdb.tables.UserUser;
import com.moseeker.db.userdb.tables.UserWxUser;
import com.moseeker.db.userdb.tables.records.UserSettingsRecord;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.profile.dao.ProfileDao;
import com.moseeker.profile.dao.entity.ProfileWorkexpEntity;
import com.moseeker.profile.service.impl.serviceutils.CompletenessCalculator;

@Repository
public class ProfileDaoImpl extends BaseDaoImpl<ProfileProfileRecord, ProfileProfile> implements ProfileDao {
	
	private CompletenessCalculator completenessCalculator = new CompletenessCalculator();

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileProfile.PROFILE_PROFILE;
	}

	@Override
	public ProfileProfileRecord getProfileByIdOrUserIdOrUUID(int userId, int profileId, String uuid) {
		ProfileProfileRecord record = null;
		Connection conn = null;
		try {
			if (userId > 0 || profileId > 0 || !StringUtils.isNullOrEmpty(uuid)) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				Condition condition = null;
				if (userId > 0) {
					if (condition == null) {
						condition = ProfileProfile.PROFILE_PROFILE.USER_ID.equal(UInteger.valueOf(userId));
					}
				}
				if (profileId > 0) {
					if (condition == null) {
						condition = ProfileProfile.PROFILE_PROFILE.ID.equal(UInteger.valueOf(profileId));
					} else {
						condition = condition.or(ProfileProfile.PROFILE_PROFILE.ID.equal(UInteger.valueOf(profileId)));
					}
				}
				if (!StringUtils.isNullOrEmpty(uuid)) {
					if (condition == null) {
						condition = ProfileProfile.PROFILE_PROFILE.UUID.equal(uuid);
					} else {
						condition = condition.or(ProfileProfile.PROFILE_PROFILE.UUID.equal(uuid));
					}
				}

				if (condition != null) {
					Result<ProfileProfileRecord> result = create.selectFrom(ProfileProfile.PROFILE_PROFILE)
							.where(condition).and(ProfileProfile.PROFILE_PROFILE.DISABLE.equal(UByte.valueOf(1)))
							.limit(1).fetch();
					if (result != null && result.size() > 0) {
						record = result.get(0);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				// do nothing
			}
		}
		return record;
	}

	@Override
	public List<ProfileProfileRecord> getProfilesByIdOrUserIdOrUUID(int userId, int profileId, String uuid) {
		List<ProfileProfileRecord> records = null;
		Connection conn = null;
		try {
			if (userId > 0 || profileId > 0 || !StringUtils.isNullOrEmpty(uuid)) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				Condition condition = null;
				if (userId > 0) {
					if (condition == null) {
						condition = ProfileProfile.PROFILE_PROFILE.USER_ID.equal(UInteger.valueOf(userId));
					}
				}
				if (profileId > 0) {
					if (condition == null) {
						condition = ProfileProfile.PROFILE_PROFILE.ID.equal(UInteger.valueOf(profileId));
					} else {
						condition = condition.or(ProfileProfile.PROFILE_PROFILE.ID.equal(UInteger.valueOf(profileId)));
					}
				}
				if (!StringUtils.isNullOrEmpty(uuid)) {
					if (condition == null) {
						condition = ProfileProfile.PROFILE_PROFILE.UUID.equal(uuid);
					} else {
						condition = condition.or(ProfileProfile.PROFILE_PROFILE.UUID.equal(uuid));
					}
				}

				if (condition != null) {
					Result<ProfileProfileRecord> result = create.selectFrom(ProfileProfile.PROFILE_PROFILE)
							.where(condition).and(ProfileProfile.PROFILE_PROFILE.DISABLE.equal(UByte.valueOf(1)))
							.fetch();
					if (result != null && result.size() > 0) {
						records = result;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				// do nothing
			}
		}
		return records;
	}

	@Override
	public int deleteProfile(int profileId) {
		int result = 0;
		if (profileId > 0) {
			Connection conn = null;
			try {
				conn = DBConnHelper.DBConn.getConn();
				conn.setAutoCommit(false);
				Statement stmt = conn.createStatement();
				StringBuffer sb = new StringBuffer("(");
				ResultSet resultSet = stmt
						.executeQuery("select id from profiledb.profile_intention where profile_id = " + profileId);
				while (resultSet.next()) {
					sb.append(resultSet.getLong("id") + ",");
				}
				if (sb.length() > 1) {
					sb.deleteCharAt(sb.length() - 1);
					sb.append(")");
					stmt.executeUpdate("delete from profiledb.profile_intention_city where profile_intention_id in "
							+ sb.toString());
					stmt.executeUpdate("delete from profiledb.profile_intention_position where profile_intention_id in "
							+ sb.toString());
					stmt.executeUpdate("delete from profiledb.profile_intention_industry where profile_intention_id in "
							+ sb.toString());
				}
				stmt.executeUpdate("delete from profiledb.profile_attachment where profile_id = " + profileId);
				stmt.executeUpdate("delete from profiledb.profile_awards where profile_id = " + profileId);
				stmt.executeUpdate("delete from profiledb.profile_basic where profile_id = " + profileId);
				stmt.executeUpdate("delete from profiledb.profile_credentials where profile_id = " + profileId);
				stmt.executeUpdate("delete from profiledb.profile_education where profile_id = " + profileId);
				stmt.executeUpdate("delete from profiledb.profile_import where profile_id = " + profileId);
				stmt.executeUpdate("delete from profiledb.profile_import where profile_id = " + profileId);

				stmt.executeUpdate("delete from profiledb.profile_intention where profile_id = " + profileId);
				stmt.executeUpdate("delete from profiledb.profile_language where profile_id = " + profileId);
				stmt.executeUpdate("delete from profiledb.profile_other where profile_id = " + profileId);
				stmt.executeUpdate("delete from profiledb.profile_projectexp where profile_id = " + profileId);
				stmt.executeUpdate("delete from profiledb.profile_skill where profile_id = " + profileId);
				stmt.executeUpdate("delete from profiledb.profile_workexp where profile_id = " + profileId);
				stmt.executeUpdate("delete from profiledb.profile_works where profile_id = " + profileId);
				result = stmt.executeUpdate("delete from profiledb.profile_profile where id = " + profileId);
				conn.commit();
				conn.setAutoCommit(true);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				try {
					if (conn != null && !conn.isClosed()) {
						conn.rollback();
					}
				} catch (SQLException e1) {
					logger.error(e.getMessage(), e);
				}
			} finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					logger.error(e.getMessage(), e);
				} finally {
					// do nothing
				}
			}
		}
		return result;
	}

	@Override
	public int saveProfile(ProfileProfileRecord profileRecord, ProfileBasicRecord basicRecord,
			List<ProfileAttachmentRecord> attachmentRecords, List<ProfileAwardsRecord> awardsRecords,
			List<ProfileCredentialsRecord> credentialsRecords, List<ProfileEducationRecord> educationRecords,
			ProfileImportRecord importRecord, List<IntentionRecord> intentionRecords,
			List<ProfileLanguageRecord> languages, ProfileOtherRecord otherRecord,
			List<ProfileProjectexpRecord> projectExps, List<ProfileSkillRecord> skillRecords,
			List<ProfileWorkexpEntity> workexpRecords, List<ProfileWorksRecord> worksRecords,
			UserUserRecord userRecord) {
		int profileId = 0;
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			conn.setAutoCommit(false);
			Result<DictCollegeRecord> colleges = create.selectFrom(DictCollege.DICT_COLLEGE).fetch();
			Result<DictCityRecord> cities = create.selectFrom(DictCity.DICT_CITY).fetch();
			Result<DictPositionRecord> positions = create.selectFrom(DictPosition.DICT_POSITION).fetch();
			Result<DictIndustryRecord> industries = create.selectFrom(DictIndustry.DICT_INDUSTRY).fetch();
			if (profileRecord != null) {
				Timestamp now = new Timestamp(System.currentTimeMillis());
				profileRecord.setCreateTime(now);
				create.attach(profileRecord);
				profileRecord.insert();
				
				/* 计算profile完整度 */
				ProfileCompletenessRecord completenessRecord = new ProfileCompletenessRecord();

				if (basicRecord != null) {
					basicRecord.setProfileId(profileRecord.getId());
					basicRecord.setCreateTime(now);
					create.attach(basicRecord);
					if (!StringUtils.isNullOrEmpty(basicRecord.getNationalityName())) {
						DictCountryRecord countryRecord = create.selectFrom(DictCountry.DICT_COUNTRY)
								.where(DictCountry.DICT_COUNTRY.NAME.equal(basicRecord.getNationalityName())).limit(1)
								.fetchOne();
						if (countryRecord != null) {
							basicRecord.setNationalityCode(countryRecord.getId().intValue());
						}
					}
					if (!StringUtils.isNullOrEmpty(basicRecord.getCityName())) {
						DictCityRecord cityRecord = create.selectFrom(DictCity.DICT_CITY)
								.where(DictCity.DICT_CITY.NAME.equal(basicRecord.getCityName())).limit(1).fetchOne();
						if (cityRecord != null) {
							basicRecord.setCityCode(cityRecord.getCode().intValue());
						}
					}
					basicRecord.insert();
					int basicCompleteness = completenessCalculator.calculateProfileBasic(basicRecord);
					completenessRecord.setProfileBasic(basicCompleteness);
				}
				if (attachmentRecords != null && attachmentRecords.size() > 0) {
					attachmentRecords.forEach(attachmentRecord -> {
						attachmentRecord.setProfileId(profileRecord.getId());
						attachmentRecord.setCreateTime(now);
						create.attach(attachmentRecord);
						attachmentRecord.insert();
					});
				}
				if (awardsRecords != null && awardsRecords.size() > 0) {
					awardsRecords.forEach(awardsRecord -> {
						awardsRecord.setProfileId(profileRecord.getId());
						awardsRecord.setCreateTime(now);
						create.attach(awardsRecord);
						awardsRecord.insert();
					});
					int awardCompleteness = completenessCalculator.calculateAwards(awardsRecords);
					completenessRecord.setProfileAwards(awardCompleteness);
				}
				if (credentialsRecords != null && credentialsRecords.size() > 0) {
					credentialsRecords.forEach(credentialsRecord -> {
						credentialsRecord.setProfileId(profileRecord.getId());
						credentialsRecord.setCreateTime(now);
						create.attach(credentialsRecord);
						credentialsRecord.insert();
					});
					int credentialsCompleteness = completenessCalculator.calculateCredentials(credentialsRecords);
					completenessRecord.setProfileCredentials(credentialsCompleteness);
				}
				if (educationRecords != null && educationRecords.size() > 0) {
					educationRecords.forEach(educationRecord -> {
						educationRecord.setProfileId(profileRecord.getId());
						educationRecord.setCreateTime(now);
						if (!StringUtils.isNullOrEmpty(educationRecord.getCollegeName())) {
							for (DictCollegeRecord collegeRecord : colleges) {
								if (educationRecord.getCollegeName().equals(collegeRecord.getName())) {
									educationRecord.setCollegeCode(collegeRecord.getCode().intValue());
									educationRecord.setCollegeLogo(collegeRecord.getLogo());
									break;
								}
							}
						}
						create.attach(educationRecord);
						educationRecord.insert();
					});
					int educationCompleteness = completenessCalculator.calculateProfileEducations(educationRecords);
					completenessRecord.setProfileEducation(educationCompleteness);
				}
				if (importRecord != null && importRecord.size() > 0) {
					create.attach(importRecord);
					importRecord.setCreateTime(now);
					importRecord.setProfileId(profileRecord.getId());
					importRecord.insert();
				}
				if (intentionRecords != null && intentionRecords.size() > 0) {
					List<ProfileIntentionCityRecord> intentionCityRecords = new ArrayList<>();
					List<ProfileIntentionPositionRecord> intentionPositionRecords = new ArrayList<>();
					intentionRecords.forEach(intentionRecord -> {
						intentionRecord.setProfileId(profileRecord.getId());
						intentionRecord.setCreateTime(now);
						create.attach(intentionRecord);
						intentionRecord.insert();
						if (intentionRecord.getCities().size() > 0) {
							intentionRecord.getCities().forEach(city -> {
								city.setProfileIntentionId(intentionRecord.getId());
								if (!StringUtils.isNullOrEmpty(city.getCityName())) {
									for (DictCityRecord cityRecord : cities) {
										if (city.getCityName().equals(cityRecord.getName())) {
											city.setCityCode(cityRecord.getCode());
											intentionCityRecords.add(city);
											break;
										}
									}
								}
								create.attach(city);
								city.insert();
							});
						}
						if (intentionRecord.getPositions().size() > 0) {
							intentionRecord.getPositions().forEach(position -> {
								position.setProfileIntentionId(intentionRecord.getId());
								if (!StringUtils.isNullOrEmpty(position.getPositionName())) {
									for (DictPositionRecord positionRecord : positions) {
										if (positionRecord.getName().equals(position.getPositionName())) {
											position.setPositionCode(positionRecord.getCode());
											intentionPositionRecords.add(position);
											break;
										}
									}
								}
								create.attach(position);
								position.insert();
							});
						}
						if (intentionRecord.getIndustries().size() > 0) {
							intentionRecord.getIndustries().forEach(industry -> {
								industry.setProfileIntentionId(intentionRecord.getId());
								if (!StringUtils.isNullOrEmpty(industry.getIndustryName())) {
									for (DictIndustryRecord industryRecord : industries) {
										if (industry.getIndustryName().equals(industryRecord.getName())) {
											industry.setIndustryCode(industryRecord.getCode());
											break;
										}
									}
								}
								create.attach(industry);
								industry.insert();
							});
						}
					});
					int intentionCompleteness = completenessCalculator.calculateIntentions(intentionRecords, intentionCityRecords, intentionPositionRecords);
					completenessRecord.setProfileIntention(intentionCompleteness);
				}
				if (languages != null && languages.size() > 0) {
					languages.forEach(language -> {
						language.setProfileId(profileRecord.getId());
						language.setCreateTime(now);
						create.attach(language);
						language.insert();
					});
					int languageCompleteness = completenessCalculator.calculateLanguages(languages);
					completenessRecord.setProfileLanguage(languageCompleteness);
				}
				if (otherRecord != null) {
					create.attach(otherRecord);
					otherRecord.setCreateTime(now);
					otherRecord.insert();
				}
				if (projectExps != null && projectExps.size() > 0) {
					projectExps.forEach(projectExp -> {
						projectExp.setProfileId(profileRecord.getId());
						projectExp.setCreateTime(now);
						create.attach(projectExp);
						projectExp.insert();
					});
					int projectExpCompleteness = completenessCalculator.calculateProjectexps(projectExps);
					completenessRecord.setProfileProjectexp(projectExpCompleteness);
				}
				if (skillRecords != null && skillRecords.size() > 0) {
					skillRecords.forEach(skill -> {
						skill.setProfileId(profileRecord.getId());
						skill.setCreateTime(now);
						create.attach(skill);
						skill.insert();
					});
					int skillCompleteness = completenessCalculator.calculateSkills(skillRecords);
					completenessRecord.setProfileSkill(skillCompleteness);
				}
				if (workexpRecords != null && workexpRecords.size() > 0) {
					List<HrCompanyRecord> companies = new ArrayList<>();
					workexpRecords.forEach(workexp -> {
						workexp.setProfileId(profileRecord.getId());
						workexp.setCreateTime(now);
						if (workexp.getCompany() != null && !StringUtils.isNullOrEmpty(workexp.getCompany().getName())) {
							HrCompanyRecord hc = create.selectFrom(HrCompany.HR_COMPANY)
									.where(HrCompany.HR_COMPANY.NAME.equal(workexp.getCompany().getName()))
									.limit(1)
									.fetchOne();
							if (hc != null) {
								companies.add(hc);
								workexp.setCompanyId(hc.getId());
							} else {
								HrCompanyRecord newCompany = workexp.getCompany();
								newCompany.setType(UByte.valueOf(Constant.COMPANY_TYPE_FREE));
								newCompany.setSource(UByte.valueOf(Constant.COMPANY_SOURCE_PROFILE));
								create.attach(newCompany);
								newCompany.insert();
								workexp.setCompanyId(newCompany.getId());
								companies.add(newCompany);
							}
						}
						if (!StringUtils.isNullOrEmpty(workexp.getIndustryName())) {
							for (DictIndustryRecord industryRecord : industries) {
								if (workexp.getIndustryName().equals(industryRecord.getName())) {
									workexp.setIndustryCode(industryRecord.getCode());
									break;
								}
							}
						}
						if (!StringUtils.isNullOrEmpty(workexp.getCityName())) {
							for (DictCityRecord cityRecord : cities) {
								if (workexp.getCityName().equals(cityRecord.getName())) {
									workexp.setCityCode(cityRecord.getCode());
									break;
								}
							}
						}
						if (!StringUtils.isNullOrEmpty(workexp.getPositionName())) {
							for (DictPositionRecord positionRecord : positions) {
								if (positionRecord.getName().equals(workexp.getPositionName())) {
									workexp.setPositionCode(positionRecord.getCode());
									break;
								}
							}
						}

						create.attach(workexp);
						workexp.insert();
					});
					int workExpCompleteness = completenessCalculator.calculateProfileWorkexps(workexpRecords, companies);
					completenessRecord.setProfileWorkexp(workExpCompleteness);
				}

				if (worksRecords != null && worksRecords.size() > 0) {
					worksRecords.forEach(worksRecord -> {
						worksRecord.setProfileId(profileRecord.getId());
						worksRecord.setCreateTime(now);
						create.attach(worksRecord);
						worksRecord.insert();
					});
					int worksCompleteness = completenessCalculator.calculateWorks(worksRecords);
					completenessRecord.setProfileWorks(worksCompleteness);
				}
				if (userRecord != null) {
					create.attach(userRecord);
					userRecord.update();
					
					/* 计算简历完整度 */
					completenessRecord.setProfileId(profileRecord.getId());
					UserWxUserRecord wxuserRecord = create.selectFrom(UserWxUser.USER_WX_USER).where(UserWxUser.USER_WX_USER.SYSUSER_ID.equal(userRecord.getId().intValue())).limit(1).fetchOne();
					UserSettingsRecord settingRecord = create.selectFrom(UserSettings.USER_SETTINGS).where(UserSettings.USER_SETTINGS.USER_ID.equal(userRecord.getId())).limit(1).fetchOne();
					int userCompleteness = completenessCalculator.calculateUserUser(userRecord, settingRecord, wxuserRecord);
					completenessRecord.setUserUser(userCompleteness);
				}
				
				int totalComplementness = (completenessRecord.getUserUser() == null ? 0: completenessRecord.getUserUser())
						+ (completenessRecord.getProfileBasic() == null ? 0:completenessRecord.getProfileBasic())
						+ (completenessRecord.getProfileWorkexp() == null ? 0 : completenessRecord.getProfileWorkexp())
						+ (completenessRecord.getProfileEducation() == null ? 0 : completenessRecord.getProfileEducation())
						+ (completenessRecord.getProfileProjectexp() == null ? 0 : completenessRecord.getProfileProjectexp())
						+ (completenessRecord.getProfileLanguage() == null ? 0 : completenessRecord.getProfileLanguage()) 
						+ (completenessRecord.getProfileSkill() == null ? 0 : completenessRecord.getProfileSkill())
						+ (completenessRecord.getProfileCredentials() == null ? 0 : completenessRecord.getProfileCredentials())
						+ (completenessRecord.getProfileAwards() == null ? 0 : completenessRecord.getProfileAwards())
						+ (completenessRecord.getProfileWorks() == null ? 0 : completenessRecord.getProfileWorks())
						+ (completenessRecord.getProfileIntention() == null ? 0 : completenessRecord.getProfileIntention());
				
				create.attach(completenessRecord);
				completenessRecord.insert();
				profileRecord.setCompleteness(UByte.valueOf(totalComplementness));
				profileRecord.update();
				profileId = profileRecord.getId().intValue();
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			try {
				if (conn != null && !conn.isClosed()) {
					conn.rollback();
				}
			} catch (SQLException e1) {
				logger.error(e.getMessage(), e);
			} finally {
				// do nothing
			}
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				// do nothing
			}
		}
		return profileId;
	}

	@Override
	public int saveProfile(ProfileProfileRecord profileRecord, ProfileBasicRecord basicRecord,
			List<ProfileAttachmentRecord> attachmentRecords, List<ProfileAwardsRecord> awardsRecords,
			List<ProfileCredentialsRecord> credentialsRecords, List<ProfileEducationRecord> educationRecords,
			ProfileImportRecord importRecord, List<IntentionRecord> intentionRecords,
			List<ProfileLanguageRecord> languages, ProfileOtherRecord otherRecord,
			List<ProfileProjectexpRecord> projectExps, List<ProfileSkillRecord> skillRecords,
			List<ProfileWorkexpEntity> workexpRecords, List<ProfileWorksRecord> worksRecords, UserUserRecord userRecord,
			List<ProfileProfileRecord> oldProfile) {
		int profileId = 0;
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			conn.setAutoCommit(false);

			if (oldProfile != null && oldProfile.size() > 0) {
				for (ProfileProfileRecord record : oldProfile) {
					clearProfile(record.getId().intValue(), conn);
				}
			}

			Result<DictCollegeRecord> colleges = create.selectFrom(DictCollege.DICT_COLLEGE).fetch();
			Result<DictCityRecord> cities = create.selectFrom(DictCity.DICT_CITY).fetch();
			Result<DictPositionRecord> positions = create.selectFrom(DictPosition.DICT_POSITION).fetch();
			Result<DictIndustryRecord> industries = create.selectFrom(DictIndustry.DICT_INDUSTRY).fetch();
			if (profileRecord != null) {
				Timestamp now = new Timestamp(System.currentTimeMillis());
				profileRecord.setCreateTime(now);
				create.attach(profileRecord);
				profileRecord.insert();
				
				/* 计算profile完整度 */
				ProfileCompletenessRecord completenessRecord = new ProfileCompletenessRecord();

				if (basicRecord != null) {
					basicRecord.setProfileId(profileRecord.getId());
					basicRecord.setCreateTime(now);
					create.attach(basicRecord);
					if (!StringUtils.isNullOrEmpty(basicRecord.getNationalityName())) {
						DictCountryRecord countryRecord = create.selectFrom(DictCountry.DICT_COUNTRY)
								.where(DictCountry.DICT_COUNTRY.NAME.equal(basicRecord.getNationalityName())).limit(1)
								.fetchOne();
						if (countryRecord != null) {
							basicRecord.setNationalityCode(countryRecord.getId().intValue());
						}
					}
					if (!StringUtils.isNullOrEmpty(basicRecord.getCityName())) {
						DictCityRecord cityRecord = create.selectFrom(DictCity.DICT_CITY)
								.where(DictCity.DICT_CITY.NAME.equal(basicRecord.getCityName())).limit(1).fetchOne();
						if (cityRecord != null) {
							basicRecord.setCityCode(cityRecord.getCode().intValue());
						}
					}
					basicRecord.insert();
					int basicCompleteness = completenessCalculator.calculateProfileBasic(basicRecord);
					completenessRecord.setProfileBasic(basicCompleteness);
				}
				if (attachmentRecords != null && attachmentRecords.size() > 0) {
					attachmentRecords.forEach(attachmentRecord -> {
						attachmentRecord.setProfileId(profileRecord.getId());
						attachmentRecord.setCreateTime(now);
						create.attach(attachmentRecord);
						attachmentRecord.insert();
					});
				}
				if (awardsRecords != null && awardsRecords.size() > 0) {
					awardsRecords.forEach(awardsRecord -> {
						awardsRecord.setProfileId(profileRecord.getId());
						awardsRecord.setCreateTime(now);
						create.attach(awardsRecord);
						awardsRecord.insert();
					});
					int awardCompleteness = completenessCalculator.calculateAwards(awardsRecords);
					completenessRecord.setProfileAwards(awardCompleteness);
				}
				if (credentialsRecords != null && credentialsRecords.size() > 0) {
					credentialsRecords.forEach(credentialsRecord -> {
						credentialsRecord.setProfileId(profileRecord.getId());
						credentialsRecord.setCreateTime(now);
						create.attach(credentialsRecord);
						credentialsRecord.insert();
					});
					int credentialsCompleteness = completenessCalculator.calculateCredentials(credentialsRecords);
					completenessRecord.setProfileCredentials(credentialsCompleteness);
				}
				if (educationRecords != null && educationRecords.size() > 0) {
					educationRecords.forEach(educationRecord -> {
						educationRecord.setProfileId(profileRecord.getId());
						educationRecord.setCreateTime(now);
						if (!StringUtils.isNullOrEmpty(educationRecord.getCollegeName())) {
							for (DictCollegeRecord collegeRecord : colleges) {
								if (educationRecord.getCollegeName().equals(collegeRecord.getName())) {
									educationRecord.setCollegeCode(collegeRecord.getCode().intValue());
									educationRecord.setCollegeLogo(collegeRecord.getLogo());
									break;
								}
							}
						}
						create.attach(educationRecord);
						educationRecord.insert();
					});
					int educationCompleteness = completenessCalculator.calculateProfileEducations(educationRecords);
					completenessRecord.setProfileEducation(educationCompleteness);
				}
				if (importRecord != null) {
					create.attach(importRecord);
					importRecord.setCreateTime(now);
					importRecord.setProfileId(profileRecord.getId());
					importRecord.insert();
				}
				if (intentionRecords != null && intentionRecords.size() > 0) {
					List<ProfileIntentionCityRecord> intentionCityRecords = new ArrayList<>();
					List<ProfileIntentionPositionRecord> intentionPositionRecords = new ArrayList<>();
					intentionRecords.forEach(intentionRecord -> {
						intentionRecord.setProfileId(profileRecord.getId());
						intentionRecord.setCreateTime(now);
						create.attach(intentionRecord);
						intentionRecord.insert();
						if (intentionRecord.getCities().size() > 0) {
							intentionRecord.getCities().forEach(city -> {
								city.setProfileIntentionId(intentionRecord.getId());
								if (!StringUtils.isNullOrEmpty(city.getCityName())) {
									for (DictCityRecord cityRecord : cities) {
										if (city.getCityName().equals(cityRecord.getName())) {
											city.setCityCode(cityRecord.getCode());
											intentionCityRecords.add(city);
											break;
										}
									}
								}
								create.attach(city);
								city.insert();
							});
						}
						if (intentionRecord.getPositions().size() > 0) {
							intentionRecord.getPositions().forEach(position -> {
								position.setProfileIntentionId(intentionRecord.getId());
								if (!StringUtils.isNullOrEmpty(position.getPositionName())) {
									for (DictPositionRecord positionRecord : positions) {
										if (positionRecord.getName().equals(position.getPositionName())) {
											position.setPositionCode(positionRecord.getCode());
											intentionPositionRecords.add(position);
											break;
										}
									}
								}
								create.attach(position);
								position.insert();
							});
						}
						if (intentionRecord.getIndustries().size() > 0) {
							intentionRecord.getIndustries().forEach(industry -> {
								industry.setProfileIntentionId(intentionRecord.getId());
								if (!StringUtils.isNullOrEmpty(industry.getIndustryName())) {
									for (DictIndustryRecord industryRecord : industries) {
										if (industry.getIndustryName().equals(industryRecord.getName())) {
											industry.setIndustryCode(industryRecord.getCode());
											break;
										}
									}
								}
								create.attach(industry);
								industry.insert();
							});
						}
					});
					int intentionCompleteness = completenessCalculator.calculateIntentions(intentionRecords, intentionCityRecords, intentionPositionRecords);
					completenessRecord.setProfileIntention(intentionCompleteness);
				}
				if (languages != null && languages.size() > 0) {
					languages.forEach(language -> {
						language.setProfileId(profileRecord.getId());
						language.setCreateTime(now);
						create.attach(language);
						language.insert();
					});
					int languageCompleteness = completenessCalculator.calculateLanguages(languages);
					completenessRecord.setProfileLanguage(languageCompleteness);
				}
				if (otherRecord != null) {
					create.attach(otherRecord);
					otherRecord.setCreateTime(now);
					otherRecord.setProfileId(profileRecord.getId());
					otherRecord.insert();
				}
				if (projectExps != null && projectExps.size() > 0) {
					projectExps.forEach(projectExp -> {
						projectExp.setProfileId(profileRecord.getId());
						projectExp.setCreateTime(now);
						create.attach(projectExp);
						projectExp.insert();
					});
					int projectExpCompleteness = completenessCalculator.calculateProjectexps(projectExps);
					completenessRecord.setProfileProjectexp(projectExpCompleteness);
				}
				if (skillRecords != null && skillRecords.size() > 0) {
					skillRecords.forEach(skill -> {
						skill.setProfileId(profileRecord.getId());
						skill.setCreateTime(now);
						create.attach(skill);
						skill.insert();
					});
					int skillCompleteness = completenessCalculator.calculateSkills(skillRecords);
					completenessRecord.setProfileSkill(skillCompleteness);
				}
				if (workexpRecords != null && workexpRecords.size() > 0) {
					workexpRecords.forEach(workexp -> {
						workexp.setProfileId(profileRecord.getId());
						workexp.setCreateTime(now);
						if (workexp.getCompany() != null && !StringUtils.isNullOrEmpty(workexp.getCompany().getName())) {
							HrCompanyRecord hc = create.selectFrom(HrCompany.HR_COMPANY)
									.where(HrCompany.HR_COMPANY.NAME.equal(workexp.getCompany().getName()))
									.limit(1)
									.fetchOne();
							if (hc != null) {
								workexp.setCompanyId(hc.getId());
							} else {
								HrCompanyRecord newCompany = workexp.getCompany();
								create.attach(newCompany);
								newCompany.insert();
								workexp.setCompanyId(newCompany.getId());
							}
						}
						if (!StringUtils.isNullOrEmpty(workexp.getIndustryName())) {
							for (DictIndustryRecord industryRecord : industries) {
								if (workexp.getIndustryName().equals(industryRecord.getName())) {
									workexp.setIndustryCode(industryRecord.getCode());
									break;
								}
							}
						}
						if (!StringUtils.isNullOrEmpty(workexp.getCityName())) {
							for (DictCityRecord cityRecord : cities) {
								if (workexp.getCityName().equals(cityRecord.getName())) {
									workexp.setCityCode(cityRecord.getCode());
									break;
								}
							}
						}
						if (!StringUtils.isNullOrEmpty(workexp.getPositionName())) {
							for (DictPositionRecord positionRecord : positions) {
								if (positionRecord.getName().equals(workexp.getPositionName())) {
									workexp.setPositionCode(positionRecord.getCode());
									break;
								}
							}
						}

						create.attach(workexp);
						workexp.insert();
					});
				}

				if (worksRecords != null && worksRecords.size() > 0) {
					worksRecords.forEach(worksRecord -> {
						worksRecord.setProfileId(profileRecord.getId());
						worksRecord.setCreateTime(now);
						create.attach(worksRecord);
						worksRecord.insert();
					});
				}
				if (userRecord != null) {
					create.attach(userRecord);
					userRecord.update();
				}
				profileId = profileRecord.getId().intValue();
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			try {
				if (conn != null && !conn.isClosed()) {
					conn.rollback();
				}
			} catch (SQLException e1) {
				logger.error(e.getMessage(), e);
			} finally {
				// do nothing
			}
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				// do nothing
			}
		}
		return profileId;
	}

	private int clearProfile(int profileId, Connection conn) throws Exception {
		int result = 0;
		Statement stmt = conn.createStatement();
		StringBuffer sb = new StringBuffer("(");
		ResultSet resultSet = stmt
				.executeQuery("select id from profiledb.profile_intention where profile_id = " + profileId);
		while (resultSet.next()) {
			sb.append(resultSet.getLong("id") + ",");
		}
		if (sb.length() > 1) {
			sb.deleteCharAt(sb.length() - 1);
			sb.append(")");
			stmt.executeUpdate(
					"delete from profiledb.profile_intention_city where profile_intention_id in " + sb.toString());
			stmt.executeUpdate(
					"delete from profiledb.profile_intention_position where profile_intention_id in " + sb.toString());
			stmt.executeUpdate(
					"delete from profiledb.profile_intention_industry where profile_intention_id in " + sb.toString());
		}
		stmt.executeUpdate("delete from profiledb.profile_attachment where profile_id = " + profileId);
		stmt.executeUpdate("delete from profiledb.profile_awards where profile_id = " + profileId);
		stmt.executeUpdate("delete from profiledb.profile_basic where profile_id = " + profileId);
		stmt.executeUpdate("delete from profiledb.profile_credentials where profile_id = " + profileId);
		stmt.executeUpdate("delete from profiledb.profile_education where profile_id = " + profileId);
		stmt.executeUpdate("delete from profiledb.profile_import where profile_id = " + profileId);
		stmt.executeUpdate("delete from profiledb.profile_import where profile_id = " + profileId);

		stmt.executeUpdate("delete from profiledb.profile_intention where profile_id = " + profileId);
		stmt.executeUpdate("delete from profiledb.profile_language where profile_id = " + profileId);
		stmt.executeUpdate("delete from profiledb.profile_other where profile_id = " + profileId);
		stmt.executeUpdate("delete from profiledb.profile_projectexp where profile_id = " + profileId);
		stmt.executeUpdate("delete from profiledb.profile_skill where profile_id = " + profileId);
		stmt.executeUpdate("delete from profiledb.profile_workexp where profile_id = " + profileId);
		stmt.executeUpdate("delete from profiledb.profile_works where profile_id = " + profileId);
		result = stmt.executeUpdate("delete from profiledb.profile_profile where id = " + profileId);
		return result;
	}

	@Override
	public Result<Record2<UInteger, String>> findRealName(List<Integer> profileIds) {
		Result<Record2<UInteger, String>> result = null;
		if (profileIds != null && profileIds.size() > 0) {
			try (Connection conn = DBConnHelper.DBConn.getConn();
					DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
				conn.setAutoCommit(false);
				Condition condition = null;
				for (Integer profileId : profileIds) {
					if (condition != null) {
						condition = condition.or(ProfileProfile.PROFILE_PROFILE.ID.equal(UInteger.valueOf(profileId)));
					} else {
						condition = ProfileProfile.PROFILE_PROFILE.ID.equal(UInteger.valueOf(profileId));
					}
				}
				result = create.select(ProfileProfile.PROFILE_PROFILE.ID, UserUser.USER_USER.NAME)
						.from(ProfileProfile.PROFILE_PROFILE).join(UserUser.USER_USER)
						.on(ProfileProfile.PROFILE_PROFILE.USER_ID.equal(UserUser.USER_USER.ID)).where(condition)
						.fetch();
				conn.commit();
				conn.setAutoCommit(true);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}

	@Override
	public String findRealName(int profileId) {
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
			Condition condition = ProfileProfile.PROFILE_PROFILE.ID.equal(UInteger.valueOf(profileId));
			Record1<String> username = create.select(UserUser.USER_USER.NAME).from(ProfileProfile.PROFILE_PROFILE)
					.join(UserUser.USER_USER).on(ProfileProfile.PROFILE_PROFILE.USER_ID.equal(UserUser.USER_USER.ID))
					.where(condition).limit(1).fetchOne();
			if (username != null) {
				return (String) username.get(0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public void updateRealName(int profileId, String name) {
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
			ProfileProfileRecord record = create.selectFrom(ProfileProfile.PROFILE_PROFILE)
					.where(ProfileProfile.PROFILE_PROFILE.ID.equal(UInteger.valueOf(profileId))).fetchOne();
			if (record != null) {
				UserUserRecord userRecord = new UserUserRecord();
				userRecord.setId(record.getUserId());
				create.attach(userRecord);
				userRecord.setName(name);
				userRecord.update();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
