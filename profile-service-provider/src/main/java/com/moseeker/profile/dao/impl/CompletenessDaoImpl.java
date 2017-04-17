package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.jooq.DSLContext;


import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileCompleteness;
import com.moseeker.db.profiledb.tables.records.ProfileCompletenessRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.profile.dao.CompletenessDao;

@Repository
public class CompletenessDaoImpl extends BaseDaoImpl<ProfileCompletenessRecord, ProfileCompleteness>
		implements CompletenessDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileCompleteness.PROFILE_COMPLETENESS;
	}

	@Override
	public ProfileCompletenessRecord getCompletenessByProfileId(int profileId) {
		Connection conn = null;
		ProfileCompletenessRecord record = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			record = create.selectFrom(ProfileCompleteness.PROFILE_COMPLETENESS)
					.where(ProfileCompleteness.PROFILE_COMPLETENESS.PROFILE_ID.equal((int)(profileId)))
					.fetchOne();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return record;
	}

	@Override
	public void reCalculateProfileCompleteness(int profileId) {

	}

	@Override
	public int updateCompleteness(ProfileCompletenessRecord completenessRecord) {
		int result = 0;
		Connection conn = null;
		try {
			if (completenessRecord != null) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

				create.attach(completenessRecord);
				result = completenessRecord.update();

				ProfileProfileRecord profileRecord = new ProfileProfileRecord();
				profileRecord.setId(completenessRecord.getProfileId());
				create.attach(profileRecord);

				int completeness = calculatorTotalCompleteness(completenessRecord);

				if (profileRecord.getCompleteness() == null
						|| completeness != profileRecord.getCompleteness().intValue()) {
					profileRecord.setCompleteness((byte)(completeness));
					profileRecord.update();
				}
			}

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error(e1.getMessage(), e1);
			}
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}

	private int calculatorTotalCompleteness(ProfileCompletenessRecord completenessRecord) {
		int completeness = 0;
		completeness += completenessRecord.getUserUser() == null ? 0 : completenessRecord.getUserUser();
		completeness += completenessRecord.getProfileBasic() == null ? 0 : completenessRecord.getProfileBasic();
		completeness += completenessRecord.getProfileWorkexp() == null ? 0 : completenessRecord.getProfileWorkexp();
		completeness += completenessRecord.getProfileEducation() == null ? 0 : completenessRecord.getProfileEducation();
		completeness += completenessRecord.getProfileProjectexp() == null ? 0
				: completenessRecord.getProfileProjectexp();
		completeness += completenessRecord.getProfileLanguage() == null ? 0 : completenessRecord.getProfileLanguage();
		completeness += completenessRecord.getProfileSkill() == null ? 0 : completenessRecord.getProfileSkill();
		completeness += completenessRecord.getProfileCredentials() == null ? 0
				: completenessRecord.getProfileCredentials();
		completeness += completenessRecord.getProfileAwards() == null ? 0 : completenessRecord.getProfileAwards();
		completeness += completenessRecord.getProfileWorks() == null ? 0 : completenessRecord.getProfileWorks();
		completeness += completenessRecord.getProfileIntention() == null ? 0 : completenessRecord.getProfileIntention();
		return completeness;
	}

	@Override
	public int saveOrUpdate(ProfileCompletenessRecord completenessRecord) {
		int result = 0;
		if (completenessRecord != null && completenessRecord.getProfileId() != null
				&& completenessRecord.getProfileId().intValue() > 0) {
			Connection conn = null;
			try {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				ProfileCompletenessRecord toBeUpdate = create.selectFrom(ProfileCompleteness.PROFILE_COMPLETENESS)
						.where(ProfileCompleteness.PROFILE_COMPLETENESS.PROFILE_ID
								.equal(completenessRecord.getProfileId()))
						.fetchOne();
				if (toBeUpdate != null) {
					toBeUpdate.setUserUser(completenessRecord.getUserUser());
					toBeUpdate.setProfileBasic(completenessRecord.getProfileBasic());
					toBeUpdate.setProfileWorkexp(completenessRecord.getProfileWorkexp());
					toBeUpdate.setProfileEducation(completenessRecord.getProfileEducation());
					toBeUpdate.setProfileProjectexp(completenessRecord.getProfileProjectexp());
					toBeUpdate.setProfileLanguage(completenessRecord.getProfileLanguage());
					toBeUpdate.setProfileSkill(completenessRecord.getProfileSkill());
					toBeUpdate.setProfileCredentials(completenessRecord.getProfileCredentials());
					toBeUpdate.setProfileAwards(completenessRecord.getProfileAwards());
					toBeUpdate.setProfileWorks(completenessRecord.getProfileWorks());
					toBeUpdate.setProfileIntention(completenessRecord.getProfileIntention());
					create.attach(toBeUpdate);
					result = toBeUpdate.update();
				} else {
					create.attach(completenessRecord);
					result = completenessRecord.insert();
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
				}
			}
		}
		return result;
	}

}
