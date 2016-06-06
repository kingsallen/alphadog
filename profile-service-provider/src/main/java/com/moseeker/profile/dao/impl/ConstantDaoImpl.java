package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.dictdb.tables.DictConstant;
import com.moseeker.db.dictdb.tables.records.DictConstantRecord;
import com.moseeker.profile.dao.ConstantDao;

@Repository
public class ConstantDaoImpl extends
		BaseDaoImpl<DictConstantRecord, DictConstant> implements
		ConstantDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = DictConstant.DICT_CONSTANT;
	}

	@Override
	public List<DictConstantRecord> getCitiesByParentCodes(List<Integer> parentCodes) {
		List<DictConstantRecord> records = new ArrayList<>();
		Connection conn = null;
		try {
			if(parentCodes != null && parentCodes.size() > 0) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				SelectWhereStep<DictConstantRecord> select = create.selectFrom(DictConstant.DICT_CONSTANT);
				SelectConditionStep<DictConstantRecord> selectCondition = null;
				for(int i=0; i<parentCodes.size(); i++) {
					if(i == 0) {
						selectCondition = select.where(DictConstant.DICT_CONSTANT.PARENT_CODE.equal(parentCodes.get(i)));
					} else {
						selectCondition.or(DictConstant.DICT_CONSTANT.PARENT_CODE.equal(parentCodes.get(i)));
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
	public DictConstantRecord getCityByCode(int city_code) {
		DictConstantRecord record = null;
		Connection conn = null;
		try {
			if(city_code > 0) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				Result<DictConstantRecord> result = create.selectFrom(DictConstant.DICT_CONSTANT)
						.where(DictConstant.DICT_CONSTANT.CODE.equal(city_code))
						.limit(1).fetch();
				if(result != null && result.size() > 0) {
					record = result.get(0);
				}
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
