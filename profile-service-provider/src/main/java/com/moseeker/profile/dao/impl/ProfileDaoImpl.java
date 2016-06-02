package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Result;
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
			conn.setAutoCommit(false);
			if(profileRecord != null) {
				Timestamp now = new Timestamp(System.currentTimeMillis());
				profileRecord.setCreateTime(now);
				create.attach(profileRecord);
				profileRecord.insert();
				
				if(basicRecord != null) {
					basicRecord.setProfileId(profileRecord.getId());
					basicRecord.setCreateTime(now);
					create.attach(basicRecord);
					basicRecord.insert();
				}
				if(attachmentRecords != null && attachmentRecords.size() > 0) {
					attachmentRecords.forEach(attachmentRecord -> {
						attachmentRecord.setProfileId(profileRecord.getId());
						attachmentRecord.setCreateTime(now);
					});
					create.batchInsert(attachmentRecords);
				}
				if(awardsRecords != null && awardsRecords.size() > 0) {
					awardsRecords.forEach(awardsRecord -> {
						awardsRecord.setProfileId(profileRecord.getId());
						awardsRecord.setCreateTime(now);
					});
					create.batchInsert(awardsRecords);
				}
				if(credentialsRecords != null && credentialsRecords.size() > 0) {
					credentialsRecords.forEach(credentialsRecord -> {
						credentialsRecord.setProfileId(profileRecord.getId());
						credentialsRecord.setCreateTime(now);
					});
					create.batchInsert(credentialsRecords);
				}
				if(educationRecords != null && educationRecords.size() > 0) {
					educationRecords.forEach(educationRecord -> {
						educationRecord.setProfileId(profileRecord.getId());
						educationRecord.setCreateTime(now);
					});
					create.batchInsert(educationRecords);
				}
				if(importRecord != null && importRecord.size() > 0) {
					create.attach(importRecord);
					importRecord.setCreateTime(now);
					importRecord.setProfileId(profileRecord.getId());
					importRecord.insert();
				}
				if(intentionRecords != null && intentionRecords.size() > 0) {
					intentionRecords.forEach(intentionRecord -> {
						intentionRecord.setProfileId(profileRecord.getId());
						intentionRecord.setCreateTime(now);
						create.attach(intentionRecord);
						intentionRecord.insert();
						if(intentionRecord.getCities().size() > 0) {
							intentionRecord.getCities().forEach(city -> {
								city.setProfileIntentionId(intentionRecord.getId());
								create.attach(city);
								city.insert();
							});
						}
						if(intentionRecord.getPositions().size() > 0) {
							intentionRecord.getPositions().forEach(position -> {
								position.setProfileIntentionId(intentionRecord.getId());
								create.attach(position);
								position.insert();
							});
						}
						if(intentionRecord.getIndustries().size() > 0) {
							intentionRecord.getIndustries().forEach(industry -> {
								industry.setProfileIntentionId(intentionRecord.getId());
								create.attach(industry);
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
					create.batchInsert(languages);
				}
				if(otherRecord != null) {
					create.attach(otherRecord);
					otherRecord.setCreateTime(now);
					otherRecord.insert();
				}
				if(projectExps != null && projectExps.size() > 0) {
					projectExps.forEach(projectExp -> {
						projectExp.setProfileId(profileRecord.getId());
						projectExp.setCreateTime(now);
					});
					create.batchInsert(projectExps);
				}
				if(skillRecords != null && skillRecords.size() > 0) {
					skillRecords.forEach(skill -> {
						skill.setProfileId(profileRecord.getId());
						skill.setCreateTime(now);
					});
					create.batchInsert(skillRecords);
				}
				if(workexpRecords != null && workexpRecords.size() > 0) {
					workexpRecords.forEach(workexp -> {
						workexp.setProfileId(profileRecord.getId());
						workexp.setCreateTime(now);
					});
					create.batchInsert(workexpRecords);
				}
				
				if(worksRecords != null && worksRecords.size() > 0) {
					worksRecords.forEach(worksRecord -> {
						worksRecord.setProfileId(profileRecord.getId());
						worksRecord.setCreateTime(now);
					});
					create.batchInsert(worksRecords);
				}
				profileId = profileRecord.getId().intValue();
			}
			conn.commit();
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			try {
				if(conn != null && !conn.isClosed()) {
					conn.rollback();
				}
			} catch (SQLException e1) {
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
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

	@Override
	public int deleteProfile(int profileId) {
		int result = 0;
		if(profileId > 0) {
			Connection conn = null;
			try {
				conn = DBConnHelper.DBConn.getConn();
				conn.setAutoCommit(false);
				Statement stmt = conn.createStatement();
				StringBuffer sb = new StringBuffer("(");
				ResultSet resultSet = stmt.executeQuery("select id from profileDB.profile_intention where profile_id = "+profileId);
				while(resultSet.next()) {
					sb.append(resultSet.getLong("id")+",");
				}
				if(sb.length() > 1) {
					sb.deleteCharAt(sb.length()-1);
					sb.append(")");
					stmt.executeUpdate("delete from profileDB.profile_intention_city where profile_intention_id in "+sb.toString());
					stmt.executeUpdate("delete from profileDB.profile_intention_position where profile_intention_id in "+sb.toString());
					stmt.executeUpdate("delete from profileDB.profile_intention_industry where profile_intention_id in "+sb.toString());
				}
				stmt.executeUpdate("delete from profileDB.profile_attachment where profile_id = "+profileId);
				stmt.executeUpdate("delete from profileDB.profile_awards where profile_id = "+profileId);
				stmt.executeUpdate("delete from profileDB.profile_basic where profile_id = "+profileId);
				stmt.executeUpdate("delete from profileDB.profile_credentials where profile_id = "+profileId);
				stmt.executeUpdate("delete from profileDB.profile_education where profile_id = "+profileId);
				stmt.executeUpdate("delete from profileDB.profile_import where profile_id = "+profileId);
				stmt.executeUpdate("delete from profileDB.profile_import where profile_id = "+profileId);
				
				stmt.executeUpdate("delete from profileDB.profile_intention where profile_id = "+profileId);
				stmt.executeUpdate("delete from profileDB.profile_language where profile_id = "+profileId);
				stmt.executeUpdate("delete from profileDB.profile_other where profile_id = "+profileId);
				stmt.executeUpdate("delete from profileDB.profile_projectexp where profile_id = "+profileId);
				stmt.executeUpdate("delete from profileDB.profile_skill where profile_id = "+profileId);
				stmt.executeUpdate("delete from profileDB.profile_workexp where profile_id = "+profileId);
				stmt.executeUpdate("delete from profileDB.profile_works where profile_id = "+profileId);
				result = stmt.executeUpdate("delete from profileDB.profile_profile where id = "+profileId);
				conn.commit();
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
				try {
					if(conn != null && !conn.isClosed()) {
						conn.rollback();
					}
				} catch (SQLException e1) {
					logger.error(e.getMessage(), e);
				}
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
		}
		return result;
	}
}
