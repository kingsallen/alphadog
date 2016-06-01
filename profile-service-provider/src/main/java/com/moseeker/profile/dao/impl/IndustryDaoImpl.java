package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.dictdb.tables.DictIndustry;
import com.moseeker.db.dictdb.tables.records.DictIndustryRecord;
import com.moseeker.profile.dao.IndustryDao;

@Repository
public class IndustryDaoImpl extends
		BaseDaoImpl<DictIndustryRecord, DictIndustry> implements
		IndustryDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = DictIndustry.DICT_INDUSTRY;
	}

	@Override
	public List<DictIndustryRecord> getIndustriesByCodes(List<Integer> industryCodes) {
		List<DictIndustryRecord> records = new ArrayList<>();
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			SelectWhereStep<DictIndustryRecord> select = create.selectFrom(DictIndustry.DICT_INDUSTRY);
			SelectConditionStep<DictIndustryRecord> selectCondition = null;
			if(industryCodes != null && industryCodes.size() > 0) {
				for(int i=0; i<industryCodes.size(); i++) {
					if(i == 0) {
						selectCondition = select.where(DictIndustry.DICT_INDUSTRY.CODE.equal(UInteger.valueOf(industryCodes.get(i))));
					} else {
						selectCondition.or(DictIndustry.DICT_INDUSTRY.CODE.equal(UInteger.valueOf(industryCodes.get(i))));
					}
				}
				records = selectCondition.fetch();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
		
		return records;
	}

	@Override
	public DictIndustryRecord getIndustryByCode(int intValue) {
		DictIndustryRecord record = null;
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			Result<DictIndustryRecord> result= create.selectFrom(DictIndustry.DICT_INDUSTRY).where(DictIndustry.DICT_INDUSTRY.CODE.equal(UInteger.valueOf(intValue))).limit(1).fetch();
			if(result != null && result.size() > 0) {
				record = result.get(0);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
		return record;
	}
}
