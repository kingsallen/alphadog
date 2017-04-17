package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Set;

import org.jooq.DSLContext;

import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileLanguage;
import com.moseeker.db.profiledb.tables.ProfileProfile;
import com.moseeker.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.profile.dao.LanguageDao;

@Repository
public class LanguageDaoImpl extends
		BaseDaoImpl<ProfileLanguageRecord, ProfileLanguage> implements
		LanguageDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileLanguage.PROFILE_LANGUAGE;
	}

	@Override
	public int updateProfileUpdateTime(Set<Integer> languageIds) {
		int status = 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {

			Timestamp updateTime = new Timestamp(System.currentTimeMillis());
			status = create.update(ProfileProfile.PROFILE_PROFILE)
					.set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
					.where(ProfileProfile.PROFILE_PROFILE.ID
							.in(create.select(ProfileLanguage.PROFILE_LANGUAGE.PROFILE_ID)
									.from(ProfileLanguage.PROFILE_LANGUAGE)
									.where(ProfileLanguage.PROFILE_LANGUAGE.ID.in(languageIds))))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return status;
	}

	@Override
	public int delLanguageByProfileId(int profileId) {
		int count = 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
			count = create.delete(ProfileLanguage.PROFILE_LANGUAGE)
					.where(ProfileLanguage.PROFILE_LANGUAGE.PROFILE_ID.equal((int)(profileId)))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return count;
	}
}
