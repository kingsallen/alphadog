package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Set;

import org.jooq.DSLContext;

import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileProfile;
import com.moseeker.db.profiledb.tables.ProfileProjectexp;
import com.moseeker.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.profile.dao.ProjectExpDao;

@Repository
public class ProjectExpDaoImpl extends
		BaseDaoImpl<ProfileProjectexpRecord, ProfileProjectexp> implements
		ProjectExpDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileProjectexp.PROFILE_PROJECTEXP;
	}

	@Override
	public int updateProfileUpdateTime(Set<Integer> projectExpIds) {
		int status = 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {

			Timestamp updateTime = new Timestamp(System.currentTimeMillis());
			status = create.update(ProfileProfile.PROFILE_PROFILE)
					.set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
					.where(ProfileProfile.PROFILE_PROFILE.ID
							.in(create.select(ProfileProjectexp.PROFILE_PROJECTEXP.PROFILE_ID)
									.from(ProfileProjectexp.PROFILE_PROJECTEXP)
									.where(ProfileProjectexp.PROFILE_PROJECTEXP.ID.in(projectExpIds))))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return status;
	}

	@Override
	public int delProjectExpByProfileId(int profileId) {
		int count = 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
			count = create.delete(ProfileProjectexp.PROFILE_PROJECTEXP)
					.where(ProfileProjectexp.PROFILE_PROJECTEXP.PROFILE_ID.equal((int)(profileId)))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return count;
	}
}
