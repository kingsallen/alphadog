package com.moseeker.dict.dao;

import java.util.List;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.dictdb.tables.records.DictPositionRecord;

public interface PositionDao extends BaseDao<DictPositionRecord> {

	List<DictPositionRecord> getIndustriesByParentCode(int parentCode);
}