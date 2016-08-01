package com.moseeker.dict.dao;

import java.util.List;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.dictdb.tables.records.DictIndustryTypeRecord;

public interface IndustryTypeDao extends BaseDao<DictIndustryTypeRecord> {

	List<DictIndustryTypeRecord> getAll();
}