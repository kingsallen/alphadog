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
import com.moseeker.db.dictdb.tables.DictCollege;
import com.moseeker.db.dictdb.tables.records.DictCollegeRecord;
import com.moseeker.profile.dao.CollegeDao;

@Repository
public class CollegeDaoImpl extends
		BaseDaoImpl<DictCollegeRecord, DictCollege> implements
		CollegeDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = DictCollege.DICT_COLLEGE;
	}

	@Override
	public List<DictCollegeRecord> getCollegesByIDs(List<Integer> ids) {
		
		List<DictCollegeRecord> records = new ArrayList<>();
		Connection conn = null;
		try {
			if(ids != null && ids.size() > 0) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				SelectWhereStep<DictCollegeRecord> select = create.selectFrom(DictCollege.DICT_COLLEGE);
				SelectConditionStep<DictCollegeRecord> selectCondition = null;
				for(int i=0; i<ids.size(); i++) {
					if(i == 0) {
						selectCondition = select.where(DictCollege.DICT_COLLEGE.CODE.equal(UInteger.valueOf(ids.get(i))));
					} else {
						selectCondition.or(DictCollege.DICT_COLLEGE.CODE.equal(UInteger.valueOf(ids.get(i))));
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
	public DictCollegeRecord getCollegeByID(int code) {
		DictCollegeRecord record = null;
		Connection conn = null;
		try {
			if(code > 0) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				Result<DictCollegeRecord> result = create.selectFrom(DictCollege.DICT_COLLEGE)
						.where(DictCollege.DICT_COLLEGE.CODE.equal(UInteger.valueOf(code))).limit(1).fetch();
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