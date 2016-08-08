package com.moseeker.profile.dao;

import java.util.List;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.dictdb.tables.records.DictConstantRecord;

public interface ConstantDao extends BaseDao<DictConstantRecord> {

	List<DictConstantRecord> getCitiesByParentCodes(List<Integer> parentCodes);

	DictConstantRecord getCityByCode(int city_code);

}
