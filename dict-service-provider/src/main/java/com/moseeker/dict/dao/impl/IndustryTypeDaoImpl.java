package com.moseeker.dict.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.dictdb.tables.DictIndustryType;
import com.moseeker.db.dictdb.tables.records.DictIndustryTypeRecord;
import com.moseeker.dict.dao.IndustryTypeDao;

@Repository
public class IndustryTypeDaoImpl extends BaseDaoImpl<DictIndustryTypeRecord, DictIndustryType> implements IndustryTypeDao {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void initJOOQEntity() {
        this.tableLike = DictIndustryType.DICT_INDUSTRY_TYPE;
    }

	@Override
	public List<DictIndustryTypeRecord> getAll() {
		List<DictIndustryTypeRecord> records = null;
		try (
				Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				) {
			records = create.selectFrom(DictIndustryType.DICT_INDUSTRY_TYPE).fetch();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		return records;
	}
}