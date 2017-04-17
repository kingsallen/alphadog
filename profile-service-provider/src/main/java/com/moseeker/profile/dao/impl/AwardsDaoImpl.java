package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashSet;

import org.jooq.DSLContext;

import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.ProfileAwards;
import com.moseeker.db.profiledb.tables.ProfileProfile;
import com.moseeker.db.profiledb.tables.records.ProfileAwardsRecord;
import com.moseeker.profile.dao.AwardsDao;
import com.moseeker.thrift.gen.profile.struct.Awards;

@Repository
public class AwardsDaoImpl extends
		BaseDaoImpl<ProfileAwardsRecord, ProfileAwards> implements
		AwardsDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileAwards.PROFILE_AWARDS;
	}

	protected Awards DBToStruct(ProfileAwardsRecord r) {
		return (Awards)BeanUtils.DBToStruct(Awards.class, r);
	}

	protected ProfileAwardsRecord structToDB(Awards attachment)
			throws ParseException {
		return (ProfileAwardsRecord)BeanUtils.structToDB(attachment, ProfileAwardsRecord.class);
	}

	@Override
	public int updateProfileUpdateTime(HashSet<Integer> awardIds) {
		int status = 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {

			Timestamp updateTime = new Timestamp(System.currentTimeMillis());
			status = create.update(ProfileProfile.PROFILE_PROFILE)
					.set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
					.where(ProfileProfile.PROFILE_PROFILE.ID
							.in(create.select(ProfileAwards.PROFILE_AWARDS.PROFILE_ID)
									.from(ProfileAwards.PROFILE_AWARDS)
									.where(ProfileAwards.PROFILE_AWARDS.ID.in(awardIds))))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return status;
	}

	@Override
	public int delAwardsByProfileId(int profileId) {
		int count= 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
			count = create.delete(ProfileAwards.PROFILE_AWARDS)
					 .where(ProfileAwards.PROFILE_AWARDS.PROFILE_ID.equal((int)(profileId)))
					 .execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		return count;
	}
}
