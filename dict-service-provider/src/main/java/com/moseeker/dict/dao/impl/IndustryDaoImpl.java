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
import com.moseeker.db.dictdb.tables.DictIndustry;
import com.moseeker.db.dictdb.tables.records.DictIndustryRecord;
import com.moseeker.dict.dao.IndustryDao;

@Repository
public class IndustryDaoImpl extends BaseDaoImpl<DictIndustryRecord, DictIndustry> implements IndustryDao {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void initJOOQEntity() {
		this.tableLike = DictIndustry.DICT_INDUSTRY;
	}

	@Override
	public List<DictIndustryRecord> getIndustriesByType(int type) {
		List<DictIndustryRecord> records = null;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);) {
			records = create.selectFrom(DictIndustry.DICT_INDUSTRY)
					.where(DictIndustry.DICT_INDUSTRY.TYPE.equal((int)(type))).fetch();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return records;
	}
}