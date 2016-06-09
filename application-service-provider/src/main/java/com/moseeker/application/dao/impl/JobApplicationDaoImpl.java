package com.moseeker.application.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.types.UInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.moseeker.application.dao.JobApplicationDao;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.candidatedb.tables.CandidateCompany;
import com.moseeker.db.candidatedb.tables.CandidatePosition;
import com.moseeker.db.jobdb.tables.JobApplication;
import com.moseeker.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.db.profiledb.tables.ProfileBasic;
import com.moseeker.db.profiledb.tables.ProfileProfile;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;

/**
 * 申请服务实现
 * <p>
 *
 * Created by zzh on 16/5/24.
 */
@Repository
public class JobApplicationDaoImpl extends BaseDaoImpl<JobApplicationRecord, JobApplication>
		implements JobApplicationDao {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void initJOOQEntity() {
		this.tableLike = JobApplication.JOB_APPLICATION;
	}

	/**
	 * 判断当前用户是否申请了该职位
	 *
	 * @param userId
	 *            用户ID
	 * @param positionId
	 *            职位ID
	 *
	 * @return true : 申请, false: 没申请过
	 *
	 */
	@Override
	public int getApplicationByUserIdAndPositionId(long userId, long positionId) throws Exception {
		Connection conn = null;
		Integer count = 0;

		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

			Condition condition = JobApplication.JOB_APPLICATION.APPLIER_ID.equal(UInteger.valueOf(userId))
					.and(JobApplication.JOB_APPLICATION.POSITION_ID.equal(UInteger.valueOf(positionId)));

			Record record = create.selectCount().from(JobApplication.JOB_APPLICATION).where(condition).fetchOne();
			count = (Integer) record.getValue(0);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	@Override
	public ProfileBasicRecord getBasicByUserId(int userId) throws Exception {
		ProfileBasicRecord basicRecord = null;
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			Result<Record> result = create.select(ProfileBasic.PROFILE_BASIC.fields()).from(ProfileBasic.PROFILE_BASIC)
					.join(ProfileProfile.PROFILE_PROFILE)
					.on(ProfileProfile.PROFILE_PROFILE.ID.equal(ProfileBasic.PROFILE_BASIC.PROFILE_ID))
					.where(ProfileProfile.PROFILE_PROFILE.USER_ID.equal(UInteger.valueOf(userId))).limit(1).fetch();
			if (result != null && result.size() > 0) {
				basicRecord = (ProfileBasicRecord) result.get(0);
			}
		} catch (SQLException e) {
			logger.error("error", e);
			throw new Exception(e);
		} finally {
			// do nothing
		}
		return basicRecord;
	}

	@Override
	public int getViewNumber(long positionId, long userId) throws Exception {
		int viewNumber = 0;
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			Result<Record1<Integer>> result = create.select(CandidatePosition.CANDIDATE_POSITION.VIEW_NUMBER)
					.from(CandidatePosition.CANDIDATE_POSITION).join(CandidateCompany.CANDIDATE_COMPANY)
					.on(CandidatePosition.CANDIDATE_POSITION.CANDIDATE_COMPANY_ID
							.equal(CandidateCompany.CANDIDATE_COMPANY.ID))
					.where(CandidatePosition.CANDIDATE_POSITION.POSITION_ID.equal((int) positionId))
					.and(CandidateCompany.CANDIDATE_COMPANY.SYS_USER_ID.equal((int) userId)).limit(0).fetch();
			if (result != null && result.size() > 0) {
				viewNumber = result.get(0).value1();
			}
		} catch (SQLException e) {
			conn.rollback();
			logger.error("error", e);
			throw new Exception(e);
		} finally {
			// do nothing
		}
		return viewNumber;
	}

	@Override
	public int saveApplication(JobApplicationRecord jobApplicationRecord) throws Exception {
		int appId = 0;
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			Result<JobApplicationRecord> result = create.selectFrom(JobApplication.JOB_APPLICATION)
					.where(JobApplication.JOB_APPLICATION.POSITION_ID.equal(jobApplicationRecord.getPositionId())
							.and(JobApplication.JOB_APPLICATION.APPLIER_ID.equal(jobApplicationRecord.getApplierId())))
					.fetch();
			if (result != null && result.size() > 0) {

			} else {
				create.attach(jobApplicationRecord);
				jobApplicationRecord.insert();
				appId = jobApplicationRecord.getId().intValue();
			}
		} catch (SQLException e) {
			conn.rollback();
			logger.error("error", e);
			throw new Exception(e);
		} finally {
			// do nothing
		}
		return appId;
	}
}
