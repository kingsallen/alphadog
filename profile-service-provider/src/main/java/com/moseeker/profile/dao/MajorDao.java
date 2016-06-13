package com.moseeker.profile.dao;

import java.util.List;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.dictdb.tables.records.DictMajorRecord;

public interface MajorDao extends BaseDao<DictMajorRecord> {

	List<DictMajorRecord> getMajorsByIDs(List<String> codes);

	DictMajorRecord getMajorByID(String code);

}
