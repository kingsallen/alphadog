package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.HashSet;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileEducation;
import com.moseeker.db.profiledb.tables.ProfileProfile;
import com.moseeker.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.profile.dao.EducationDao;

@Repository
public class EducationDaoImpl extends
		BaseDaoImpl<ProfileEducationRecord, ProfileEducation> implements
		 EducationDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileEducation.PROFILE_EDUCATION;
	}

	@Override
	public int updateProfileUpdateTime(HashSet<Integer> educationIds) {
		int status = 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {

			Timestamp updateTime = new Timestamp(System.currentTimeMillis());
			status = create.update(ProfileProfile.PROFILE_PROFILE)
					.set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
					.where(ProfileProfile.PROFILE_PROFILE.ID
							.in(create.select(ProfileEducation.PROFILE_EDUCATION.PROFILE_ID)
									.from(ProfileEducation.PROFILE_EDUCATION)
									.where(ProfileEducation.PROFILE_EDUCATION.ID.in(educationIds))))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return status;
	}
}
