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

	@Override
	public List<DictPositionRecord> getPositionsByCodes(List<Integer> positionCodes) {
		List<DictPositionRecord> records = new ArrayList<>();
		Connection conn = null;
		try {
			if(positionCodes != null && positionCodes.size() > 0) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				SelectWhereStep<DictPositionRecord> select = create.selectFrom(DictPosition.DICT_POSITION);
				SelectConditionStep<DictPositionRecord> selectCondition = null;
				for(int i=0; i<positionCodes.size(); i++) {
					if(i == 0) {
						selectCondition = select.where(DictPosition.DICT_POSITION.CODE.equal((int)(positionCodes.get(i))));
					} else {
						selectCondition.or(DictPosition.DICT_POSITION.CODE.equal((int)(positionCodes.get(i))));
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
	public DictPositionRecord getPositionByCode(int positionCode) {
		DictPositionRecord record = null;
		Connection conn = null;
		try {
			if(positionCode > 0) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				Result<DictPositionRecord> result = create.selectFrom(DictPosition.DICT_POSITION)
						.where(DictPosition.DICT_POSITION.CODE.equal((int)(positionCode)))
						.limit(1).fetch();
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
