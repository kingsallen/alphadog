package com.moseeker.profile.dao.impl;

import java.text.ParseException;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.profiledb.tables.ProfileAwards;
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
}
