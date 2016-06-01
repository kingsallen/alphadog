package com.moseeker.profile.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileImport;
import com.moseeker.db.profiledb.tables.records.ProfileImportRecord;
import com.moseeker.profile.dao.ProfileImportDao;

@Repository
public class ImportDaoImpl extends
		BaseDaoImpl<ProfileImportRecord, ProfileImport> implements
		ProfileImportDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileImport.PROFILE_IMPORT;
	}
}
