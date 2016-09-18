package com.moseeker.profile.dao;

import java.util.Set;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.profiledb.tables.records.ProfileSkillRecord;



public interface SkillDao extends BaseDao<ProfileSkillRecord> {

	int updateProfileUpdateTime(Set<Integer> skillIds);

}
