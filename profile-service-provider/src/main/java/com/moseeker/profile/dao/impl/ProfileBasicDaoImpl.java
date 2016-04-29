package com.moseeker.profile.dao.impl;

import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.moseeker.db.profileDB.tables.ProfileBasic;
import com.moseeker.db.profileDB.tables.records.ProfileBasicRecord;
import com.moseeker.profile.dao.BasicDaoImpl;
import com.moseeker.profile.dao.ProfileBasicDao;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;

@Repository
public class ProfileBasicDaoImpl extends BasicDaoImpl<ProfileBasicRecord, ProfileBasic> implements
	ProfileBasicDao<ProfileBasicRecord> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileBasic.PROFILE_BASIC;
	}
}
