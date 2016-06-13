package com.moseeker.position.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.jobdb.tables.JobPosition;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.position.dao.JobPositionDao;

@Repository
public class JobPoistionDaoImpl extends
		BaseDaoImpl<JobPositionRecord, JobPosition> implements
		JobPositionDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = JobPosition.JOB_POSITION;
	}

	@Override
	public JobPositionRecord getPositionById(int positionId) {
		JobPositionRecord record = null;
		Connection conn = null;
		try {
			if(positionId > 0) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				Result<JobPositionRecord> result = create.selectFrom(JobPosition.JOB_POSITION)
						.where(JobPosition.JOB_POSITION.ID.equal(positionId))
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
