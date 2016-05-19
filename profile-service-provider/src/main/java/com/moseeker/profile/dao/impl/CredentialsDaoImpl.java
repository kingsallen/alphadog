package com.moseeker.profile.dao.impl;

import java.text.ParseException;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.ProfileCredentials;
import com.moseeker.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.profile.dao.CredentialsDao;
import com.moseeker.thrift.gen.profile.struct.Credentials;

@Repository
public class CredentialsDaoImpl extends
		BaseDaoImpl<ProfileCredentialsRecord, ProfileCredentials> implements
		CredentialsDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileCredentials.PROFILE_CREDENTIALS;
	}

	protected Credentials DBToStruct(ProfileCredentialsRecord r) {
		return (Credentials)BeanUtils.DBToStruct(Credentials.class, r);
	}

	protected ProfileCredentialsRecord structToDB(Credentials credentials)
			throws ParseException {
		return (ProfileCredentialsRecord)BeanUtils.structToDB(credentials, ProfileCredentialsRecord.class);
	}
}
