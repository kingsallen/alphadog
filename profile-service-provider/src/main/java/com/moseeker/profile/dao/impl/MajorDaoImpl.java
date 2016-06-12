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
import com.moseeker.common.util.StringUtils;
import com.moseeker.db.dictdb.tables.DictMajor;
import com.moseeker.db.dictdb.tables.records.DictMajorRecord;
import com.moseeker.profile.dao.MajorDao;

@Repository
public class MajorDaoImpl extends
		BaseDaoImpl<DictMajorRecord, DictMajor> implements
		MajorDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = DictMajor.DICT_MAJOR;
	}

	@Override
	public List<DictMajorRecord> getMajorsByIDs(List<String> ids) {
		
		List<DictMajorRecord> records = new ArrayList<>();
		Connection conn = null;
		try {
			if(ids != null && ids.size() > 0) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				SelectWhereStep<DictMajorRecord> select = create.selectFrom(DictMajor.DICT_MAJOR);
				SelectConditionStep<DictMajorRecord> selectCondition = null;
				for(int i=0; i<ids.size(); i++) {
					if(i == 0) {
						selectCondition = select.where(DictMajor.DICT_MAJOR.CODE.equal(ids.get(i)));
					} else {
						selectCondition.or(DictMajor.DICT_MAJOR.CODE.equal(ids.get(i)));
					}
				}
				records = selectCondition.fetch();
			}
		} catch (Exception e) {
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
	public DictMajorRecord getMajorByID(String code) {
		DictMajorRecord record = null;
		Connection conn = null;
		try {
			if(!StringUtils.isNullOrEmpty(code)) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				Result<DictMajorRecord> result = create.selectFrom(DictMajor.DICT_MAJOR)
						.where(DictMajor.DICT_MAJOR.CODE.equal(code)).limit(1).fetch();
				if(result != null && result.size() > 0) {
					record = result.get(0);
				}
			}
		} catch (Exception e) {
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
