package com.moseeker.profile.dao;

import java.util.List;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.dictdb.tables.records.DictCountryRecord;

public interface CountryDao extends BaseDao<DictCountryRecord> {

	List<DictCountryRecord> getCountresByIDs(List<Integer> ids);

	DictCountryRecord getCountryByID(int nationality_code);

}
