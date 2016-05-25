package com.moseeker.profile.dao;

import java.util.List;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.dictdb.tables.records.DictCollegeRecord;

public interface CollegeDao extends BaseDao<DictCollegeRecord> {

	List<DictCollegeRecord> getCollegesByIDs(List<Integer> ids);

	DictCollegeRecord getCollegeByID(int code);
}
