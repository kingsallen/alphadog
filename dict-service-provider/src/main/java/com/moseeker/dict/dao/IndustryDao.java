package com.moseeker.dict.dao;

import java.util.List;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.dictdb.tables.records.DictIndustryRecord;

public interface IndustryDao extends BaseDao<DictIndustryRecord> {

	List<DictIndustryRecord> getIndustriesByType(int type);
}