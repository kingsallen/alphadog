package com.moseeker.profile.dao;

import java.util.List;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.dictdb.tables.records.DictPositionRecord;

public interface PositionDao extends BaseDao<DictPositionRecord> {

	List<DictPositionRecord> getPositionsByCodes(List<Integer> positionCodes);

	DictPositionRecord getPositionByCode(int positionCode);
}
