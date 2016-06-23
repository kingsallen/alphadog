package com.moseeker.profile.dao.impl;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileCompleteness;
import com.moseeker.db.profiledb.tables.records.ProfileCompletenessRecord;
import com.moseeker.profile.dao.CompletenessDao;

public class CompletenessDaoImpl extends BaseDaoImpl<ProfileCompletenessRecord, ProfileCompleteness>
		implements CompletenessDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileCompleteness.PROFILE_COMPLETENESS;
	}

}
