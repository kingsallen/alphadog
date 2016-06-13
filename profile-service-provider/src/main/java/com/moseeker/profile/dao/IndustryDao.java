package com.moseeker.profile.dao;

import java.util.List;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.dictdb.tables.records.DictIndustryRecord;

public interface IndustryDao extends BaseDao<DictIndustryRecord> {

	List<DictIndustryRecord> getIndustriesByCodes(List<Integer> industryCodes);

	DictIndustryRecord getIndustryByCode(int intValue);

}
