package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Set;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileProfile;
import com.moseeker.db.profiledb.tables.ProfileWorks;
import com.moseeker.db.profiledb.tables.records.ProfileWorksRecord;
import com.moseeker.profile.dao.WorksDao;

@Repository
public class WorksDaoImpl extends
		BaseDaoImpl<ProfileWorksRecord, ProfileWorks> implements
		WorksDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileWorks.PROFILE_WORKS;
	}

	@Override
	public int updateProfileUpdateTime(Set<Integer> workIds) {
		int status = 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {

			Timestamp updateTime = new Timestamp(System.currentTimeMillis());
			status = create.update(ProfileProfile.PROFILE_PROFILE)
					.set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
					.where(ProfileProfile.PROFILE_PROFILE.ID
							.in(create.select(ProfileWorks.PROFILE_WORKS.PROFILE_ID)
									.from(ProfileWorks.PROFILE_WORKS)
									.where(ProfileWorks.PROFILE_WORKS.ID.in(workIds))))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return status;
	}
}
