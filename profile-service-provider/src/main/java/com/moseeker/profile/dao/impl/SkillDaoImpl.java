package com.moseeker.profile.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileSkill;
import com.moseeker.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.profile.dao.SkillDao;

@Repository
public class SkillDaoImpl extends
		BaseDaoImpl<ProfileSkillRecord, ProfileSkill> implements
		SkillDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileSkill.PROFILE_SKILL;
	}
}
