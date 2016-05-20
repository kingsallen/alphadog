package com.moseeker.profile.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.dictdb.tables.DictPosition;
import com.moseeker.db.dictdb.tables.records.DictPositionRecord;
import com.moseeker.profile.dao.PositionDao;

@Repository
public class PositionDaoImpl extends
		BaseDaoImpl<DictPositionRecord, DictPosition> implements
		PositionDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = DictPosition.DICT_POSITION;
	}
}
