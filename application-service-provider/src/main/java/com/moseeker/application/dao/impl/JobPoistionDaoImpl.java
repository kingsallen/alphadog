package com.moseeker.application.dao.impl;

import com.moseeker.application.dao.JobPositionDao;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.hrdb.tables.HrCompanyAccount;
import com.moseeker.db.hrdb.tables.records.HrCompanyAccountRecord;
import com.moseeker.db.jobdb.tables.JobPosition;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 职位服务实现
 * <p>
 *
 * Created by zzh on 16/5/24.
 */
@Repository
public class JobPoistionDaoImpl extends
		BaseDaoImpl<JobPositionRecord, JobPosition> implements
		JobPositionDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = JobPosition.JOB_POSITION;
	}

	/***
	 * 根据职位ID获取职位信息
	 *
	 * @param positionId 职位ID
	 * @return
     */
	@Override
	public JobPositionRecord getPositionById(int positionId) {
		JobPositionRecord record = null;
		Connection conn = null;
		try {
			if(positionId > 0) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				record = create.selectFrom(JobPosition.JOB_POSITION)
							.where(JobPosition.JOB_POSITION.ID.equal(positionId))
							.limit(1).fetchOne();
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

	/**
	 * 获取当前发布人的hr_company_account的关系company_id
	 * <p>
	 *
	 * @param publisher
	 * @return
     */
	public HrCompanyAccountRecord getHrCompanyAccountByPublisher(int publisher){
		HrCompanyAccountRecord hrCompanyAccountRecord = null;
		Connection conn = null;
		try {
			if(publisher > 0) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				Condition condition = HrCompanyAccount.HR_COMPANY_ACCOUNT.ACCOUNT_ID.equal(publisher);
				hrCompanyAccountRecord = create.selectFrom(HrCompanyAccount.HR_COMPANY_ACCOUNT).
						where(condition).fetchOne();
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
		return hrCompanyAccountRecord;
	}

}
