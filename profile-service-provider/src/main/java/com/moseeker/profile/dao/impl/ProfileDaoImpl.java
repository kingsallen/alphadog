package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileProfile;
import com.moseeker.db.profiledb.tables.records.ProfileAttachmentRecord;
import com.moseeker.db.profiledb.tables.records.ProfileAwardsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.db.profiledb.tables.records.ProfileImportRecord;
import com.moseeker.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.db.profiledb.tables.records.ProfileOtherRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorkexpRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorksRecord;
import com.moseeker.profile.dao.ProfileDao;

@Repository
public class ProfileDaoImpl extends
		BaseDaoImpl<ProfileProfileRecord, ProfileProfile> implements
		ProfileDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileProfile.PROFILE_PROFILE;
	}

	@Override
	public ProfileProfileRecord getProfileByIdOrUserId(int userId, int profileId) {
		ProfileProfileRecord record = null;
		Connection conn = null;
		try {
			if(userId > 0 || profileId > 0) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				Result<ProfileProfileRecord> result = create.selectFrom(ProfileProfile.PROFILE_PROFILE)
						.where((ProfileProfile.PROFILE_PROFILE.ID.equal(UInteger.valueOf(profileId)))
						.or(ProfileProfile.PROFILE_PROFILE.USER_ID.equal(UInteger.valueOf(userId))))
						.and(ProfileProfile.PROFILE_PROFILE.DISABLE.equal(UByte.valueOf(1)))
						.limit(1).fetch();
				if(result != null && result.size() > 0) {
					record = result.get(0);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
		return record;
	}

	@Override
	public int saveProfile(ProfileProfileRecord profileRecord, ProfileBasicRecord basicRecord,
			List<ProfileAttachmentRecord> attachmentRecords, List<ProfileAwardsRecord> awardsRecords,
			List<ProfileCredentialsRecord> credentialsRecords, List<ProfileEducationRecord> educationRecords,
			ProfileImportRecord importRecord, List<IntentionRecord> intentionRecords,
			List<ProfileLanguageRecord> languages, ProfileOtherRecord otherRecord,
			List<ProfileProjectexpRecord> projectExps, List<ProfileSkillRecord> skillRecords,
			List<ProfileWorkexpRecord> workexpRecords, List<ProfileWorksRecord> worksRecords) {
		int profileId = 0;
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			if(profileRecord != null) {
				create.transaction(configuration -> {
					Timestamp now = new Timestamp(System.currentTimeMillis());
					profileRecord.setCreateTime(now);
					DSL.using(configuration).attach(profileRecord);
					profileRecord.insert();
					if(basicRecord != null) {
						basicRecord.setProfileId(profileRecord.getId());
						basicRecord.setCreateTime(now);
						DSL.using(configuration).attach(basicRecord);
						basicRecord.insert();
					}
					if(attachmentRecords != null && attachmentRecords.size() > 0) {
						attachmentRecords.forEach(attachmentRecord -> {
							attachmentRecord.setProfileId(profileRecord.getId());
							attachmentRecord.setCreateTime(now);
						});
						DSL.using(configuration).batchInsert(attachmentRecords);
					}
					if(awardsRecords != null && awardsRecords.size() > 0) {
						awardsRecords.forEach(awardsRecord -> {
							awardsRecord.setProfileId(profileRecord.getId());
							awardsRecord.setCreateTime(now);
						});
						DSL.using(configuration).batchInsert(awardsRecords);
					}
					if(credentialsRecords != null && credentialsRecords.size() > 0) {
						credentialsRecords.forEach(credentialsRecord -> {
							credentialsRecord.setProfileId(profileRecord.getId());
							credentialsRecord.setCreateTime(now);
						});
						DSL.using(configuration).batchInsert(credentialsRecords);
					}
					if(educationRecords != null && educationRecords.size() > 0) {
						educationRecords.forEach(educationRecord -> {
							educationRecord.setProfileId(profileRecord.getId());
							educationRecord.setCreateTime(now);
						});
						DSL.using(configuration).batchInsert(educationRecords);
					}
					if(importRecord != null && importRecord.size() > 0) {
						DSL.using(configuration).attach(importRecord);
						importRecord.setCreateTime(now);
						importRecord.insert();
					}
					if(intentionRecords != null && intentionRecords.size() > 0) {
						intentionRecords.forEach(intentionRecord -> {
							intentionRecord.setProfileId(profileRecord.getId());
							intentionRecord.setCreateTime(now);
							DSL.using(configuration).attach(intentionRecord);
							intentionRecord.insert();
							if(intentionRecord.getCities().size() > 0) {
								intentionRecord.getCities().forEach(city -> {
									city.setProfileIntentionId(intentionRecord.getId());
									DSL.using(configuration).attach(city);
									city.insert();
								});
							}
							if(intentionRecord.getPositions().size() > 0) {
								intentionRecord.getPositions().forEach(position -> {
									position.setProfileIntentionId(intentionRecord.getId());
									DSL.using(configuration).attach(position);
									position.insert();
								});
							}
							if(intentionRecord.getIndustries().size() > 0) {
								intentionRecord.getIndustries().forEach(industry -> {
									industry.setProfileIntentionId(intentionRecord.getId());
									DSL.using(configuration).attach(industry);
									industry.insert();
								});
							}
						});
					}
					if(languages != null && languages.size() > 0) {
						languages.forEach(language -> {
							language.setProfileId(profileRecord.getId());
							language.setCreateTime(now);
						});
						DSL.using(configuration).batchInsert(languages);
					}
					if(otherRecord != null) {
						DSL.using(configuration).attach(otherRecord);
						otherRecord.setCreateTime(now);
						otherRecord.insert();
					}
					if(projectExps != null && projectExps.size() > 0) {
						projectExps.forEach(projectExp -> {
							projectExp.setProfileId(profileRecord.getId());
							projectExp.setCreateTime(now);
						});
						DSL.using(configuration).batchInsert(projectExps);
					}
					if(skillRecords != null && skillRecords.size() > 0) {
						skillRecords.forEach(skill -> {
							skill.setProfileId(profileRecord.getId());
							skill.setCreateTime(now);
						});
						DSL.using(configuration).batchInsert(skillRecords);
					}
					if(workexpRecords != null && workexpRecords.size() > 0) {
						workexpRecords.forEach(workexp -> {
							workexp.setProfileId(profileRecord.getId());
							workexp.setCreateTime(now);
						});
						DSL.using(configuration).batchInsert(workexpRecords);
					}
					
					if(worksRecords != null && worksRecords.size() > 0) {
						worksRecords.forEach(worksRecord -> {
							worksRecord.setProfileId(profileRecord.getId());
							worksRecord.setCreateTime(now);
						});
						DSL.using(configuration).batchInsert(worksRecords);
					}
				});
				profileId = profileRecord.getId().intValue();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
		return profileId;
	}
}
