package com.moseeker.profile.dao.impl;

import java.text.ParseException;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.ProfileOther;
import com.moseeker.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileOtherRecord;
import com.moseeker.profile.dao.CustomizeResumeDao;
import com.moseeker.thrift.gen.profile.struct.CustomizeResume;

@Repository
public class CustomizeResumeDaoImpl extends
		BaseDaoImpl<ProfileOtherRecord, ProfileOther> implements
		CustomizeResumeDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileOther.PROFILE_OTHER;
	}

	protected CustomizeResume DBToStruct(ProfileOtherRecord r) {
		return (CustomizeResume)BeanUtils.DBToStruct(CustomizeResume.class, r);
	}

	protected ProfileOtherRecord structToDB(CustomizeResume customizeResume)
			throws ParseException {
		return BeanUtils.structToDB(customizeResume, ProfileOtherRecord.class);
	}
}
