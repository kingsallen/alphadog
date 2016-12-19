package com.moseeker.profile.dao.impl;

import java.sql.Connection;

import org.jooq.DSLContext;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileOther;
import com.moseeker.db.profiledb.tables.records.ProfileOtherRecord;
import com.moseeker.profile.dao.OtherDao;

@Repository
public class OtherDaoImpl extends BaseDaoImpl<ProfileOtherRecord, ProfileOther> implements OtherDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileOther.PROFILE_OTHER;
	}

	@Override
	public int delOtherByProfileId(int profileId) {
		int count = 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
			
			count = create.deleteFrom(ProfileOther.PROFILE_OTHER).where(
					ProfileOther.PROFILE_OTHER.PROFILE_ID.equal(UInteger.valueOf(profileId))).execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return count;
	}
}
