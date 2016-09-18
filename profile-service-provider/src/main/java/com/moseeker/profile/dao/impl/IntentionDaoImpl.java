package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.HashSet;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileIntention;
import com.moseeker.db.profiledb.tables.ProfileProfile;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionRecord;
import com.moseeker.profile.dao.IntentionDao;

@Repository
public class IntentionDaoImpl extends
		BaseDaoImpl<ProfileIntentionRecord, ProfileIntention> implements
		IntentionDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileIntention.PROFILE_INTENTION;
	}

	@Override
	public int updateProfileUpdateTime(HashSet<Integer> intentionIds) {
		int status = 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {

			Timestamp updateTime = new Timestamp(System.currentTimeMillis());
			status = create.update(ProfileProfile.PROFILE_PROFILE)
					.set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
					.where(ProfileProfile.PROFILE_PROFILE.ID
							.in(create.select(ProfileIntention.PROFILE_INTENTION.PROFILE_ID)
									.from(ProfileIntention.PROFILE_INTENTION)
									.where(ProfileIntention.PROFILE_INTENTION.ID.in(intentionIds))))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return status;
	}
}
