package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Set;

import org.jooq.DSLContext;

import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.ProfileCredentials;
import com.moseeker.db.profiledb.tables.ProfileProfile;
import com.moseeker.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.profile.dao.CredentialsDao;
import com.moseeker.thrift.gen.profile.struct.Credentials;

@Repository
public class CredentialsDaoImpl extends BaseDaoImpl<ProfileCredentialsRecord, ProfileCredentials>
		implements CredentialsDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileCredentials.PROFILE_CREDENTIALS;
	}

	protected Credentials DBToStruct(ProfileCredentialsRecord r) {
		return (Credentials) BeanUtils.DBToStruct(Credentials.class, r);
	}

	protected ProfileCredentialsRecord structToDB(Credentials credentials) throws ParseException {
		return (ProfileCredentialsRecord) BeanUtils.structToDB(credentials, ProfileCredentialsRecord.class);
	}

	@Override
	public int updateProfileUpdateTime(Set<Integer> credentialIds) {
		int status = 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {

			Timestamp updateTime = new Timestamp(System.currentTimeMillis());
			status = create.update(ProfileProfile.PROFILE_PROFILE)
					.set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
					.where(ProfileProfile.PROFILE_PROFILE.ID
							.in(create.select(ProfileCredentials.PROFILE_CREDENTIALS.PROFILE_ID)
									.from(ProfileCredentials.PROFILE_CREDENTIALS)
									.where(ProfileCredentials.PROFILE_CREDENTIALS.ID.in(credentialIds))))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return status;
	}

	@Override
	public int delCredentialsByProfileId(int profileId) {
		int count = 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
			count = create.delete(ProfileCredentials.PROFILE_CREDENTIALS)
					.where(ProfileCredentials.PROFILE_CREDENTIALS.PROFILE_ID.equal((int)(profileId)))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return count;
	}
}
