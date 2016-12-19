package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Set;

import org.jooq.DSLContext;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileProfile;
import com.moseeker.db.profiledb.tables.ProfileSkill;
import com.moseeker.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.profile.dao.SkillDao;

@Repository
public class SkillDaoImpl extends
		BaseDaoImpl<ProfileSkillRecord, ProfileSkill> implements
		SkillDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileSkill.PROFILE_SKILL;
	}

	@Override
	public int updateProfileUpdateTime(Set<Integer> skillIds) {
		int status = 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {

			Timestamp updateTime = new Timestamp(System.currentTimeMillis());
			status = create.update(ProfileProfile.PROFILE_PROFILE)
					.set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
					.where(ProfileProfile.PROFILE_PROFILE.ID
							.in(create.select(ProfileSkill.PROFILE_SKILL.PROFILE_ID)
									.from(ProfileSkill.PROFILE_SKILL)
									.where(ProfileSkill.PROFILE_SKILL.ID.in(skillIds))))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return status;
	}

	@Override
	public int delSkillByProfileId(int profileId) {
		int count = 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
			count = create.delete(ProfileSkill.PROFILE_SKILL)
					.where(ProfileSkill.PROFILE_SKILL.PROFILE_ID.equal(UInteger.valueOf(profileId)))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return count;
	}
}
