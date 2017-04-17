package com.moseeker.dict.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.jooq.DSLContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.dictdb.tables.DictPosition;
import com.moseeker.db.dictdb.tables.records.DictPositionRecord;
import com.moseeker.dict.dao.PositionDao;

@Repository
public class PositionDaoImpl extends BaseDaoImpl<DictPositionRecord, DictPosition> implements PositionDao {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void initJOOQEntity() {
		this.tableLike = DictPosition.DICT_POSITION;
	}

	@Override
	public List<DictPositionRecord> getIndustriesByParentCode(int parentCode) {
		List<DictPositionRecord> records = null;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);) {
			records = create.selectFrom(DictPosition.DICT_POSITION)
					.where(DictPosition.DICT_POSITION.PARENT.equal((int)(parentCode))).fetch();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return records;
	}
}