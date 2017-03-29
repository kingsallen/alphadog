package com.moseeker.application.dao.impl;

import com.moseeker.application.dao.JobApplicationDao;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.historydb.tables.records.HistoryJobApplicationRecord;
import com.moseeker.db.hrdb.tables.records.HrOperationRecordRecord;
import com.moseeker.db.jobdb.tables.JobApplication;
import com.moseeker.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.db.userdb.tables.UserEmployee;
import com.moseeker.db.userdb.tables.UserUser;
import com.moseeker.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

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
     * 根据申请Id获取申请记录
     *
     * @param applicationId
     * @return
     * @throws Exception
     */
    @Override
    public JobApplicationRecord getApplicationById(long applicationId) throws Exception {
        Connection conn = null;
        JobApplicationRecord jobApplicationRecord = null;

        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

            Condition condition = JobApplication.JOB_APPLICATION.ID.equal(UInteger.valueOf(applicationId));

            jobApplicationRecord = create.selectFrom(JobApplication.JOB_APPLICATION).where(condition).fetchOne();

            return jobApplicationRecord;

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
        return null;
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

            Record record = create.selectCount().from(JobApplication.JOB_APPLICATION).where(condition).limit(1)
                    .fetchOne();
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

    /**
     * 保存申请记录
     *
     * @param jobApplicationRecord
     * @return<p>
     *
     * @throws Exception
     */
    @Override
    public int saveApplication(JobApplicationRecord jobApplicationRecord, JobPositionRecord jobPositionRecord) throws Exception {
        int appId = 0;
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

            conn.setAutoCommit(false);
            
            HrOperationRecordRecord hrOperationRecord = null;

            if(jobApplicationRecord.getRecommenderUserId() != null && jobApplicationRecord.getRecommenderUserId().intValue() > 0) {
                boolean existUserEmployee = false;
                UserUserRecord userUserRecord = create.selectFrom(UserUser.USER_USER).where(UserUser.USER_USER.ID.equal(jobApplicationRecord.getRecommenderUserId())).fetchAny();
                if(userUserRecord != null) {
                    UserEmployeeRecord userEmployeeRecord = create.selectFrom(UserEmployee.USER_EMPLOYEE)
                            .where(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.equal(userUserRecord.getId().intValue())
                                    .and(UserEmployee.USER_EMPLOYEE.DISABLE.equal((byte)0))
                                    .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.equal((byte)0))
                                    .and(UserEmployee.USER_EMPLOYEE.STATUS.equal(0)))
                            .fetchAny();
                    if(userEmployeeRecord != null) {
                        existUserEmployee = true;
                    }
                }
                if(!existUserEmployee) {
                    jobApplicationRecord.setRecommenderUserId(UInteger.valueOf(0));
                }

                if(jobApplicationRecord.getApplierId() != null && jobApplicationRecord.getApplierId().intValue() == jobApplicationRecord.getRecommenderUserId().intValue()) {
                    jobApplicationRecord.setRecommenderUserId(UInteger.valueOf(0));
                }
            }

            create.attach(jobApplicationRecord);
            jobApplicationRecord.insert();
            appId = jobApplicationRecord.getId().intValue();

            if(appId > 0){
                hrOperationRecord = getHrOperationRecordRecord(appId, jobApplicationRecord, jobPositionRecord);
                create.attach(hrOperationRecord);
                hrOperationRecord.insert();
            }
            conn.commit();
            conn.setAutoCommit(false);
        } catch (Exception e) {
            conn.rollback();
            logger.error("error", e);
            throw new Exception(e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return appId;
    }
    
    @Override
	public int saveApplicationIfNotExist(JobApplicationRecord jobApplicationRecord,
			JobPositionRecord jobPositionRecord) throws Exception {
    	 int appId = 0;
         Connection conn = null;
         try {
             conn = DBConnHelper.DBConn.getConn();
             DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

             HrOperationRecordRecord hrOperationRecord = null;

             create.attach(jobApplicationRecord);
             //todo 需要做去重处理
             jobApplicationRecord.insert();
             appId = jobApplicationRecord.getId().intValue();

             if(appId > 0){
                 hrOperationRecord = getHrOperationRecordRecord(appId, jobApplicationRecord, jobPositionRecord);
                 create.attach(hrOperationRecord);
                 hrOperationRecord.insert();
             }
             
         } catch (Exception e) {
             conn.rollback();
             logger.error("error", e);
             throw new Exception(e);
         } finally {
             try {
                 if (conn != null && !conn.isClosed()) {
                     conn.close();
                 }
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
         return appId;
	}

    /**
     * 归档申请记录
     *
     * @param jobApplicationRecord
     * @return
     * @throws Exception
     */
    @Override
    public int archiveApplicationRecord(JobApplicationRecord jobApplicationRecord) throws Exception {
        int status = 0;
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();

            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

            HistoryJobApplicationRecord historyJobApplicationRecord = setHistoryJobApplicationRecord(jobApplicationRecord);

            if (historyJobApplicationRecord != null){
                create.attach(historyJobApplicationRecord);
                status = historyJobApplicationRecord.insert();
            }

            if (status > 0){
                create.attach(jobApplicationRecord);
                status = jobApplicationRecord.delete();
            }
        } catch (Exception e) {
            logger.error("error", e);
            throw new Exception(e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

    /**
     * 转换归档申请记录
     *
     * @param jobApplicationRecord
     * @return
     */
    private HistoryJobApplicationRecord setHistoryJobApplicationRecord(JobApplicationRecord jobApplicationRecord){

        HistoryJobApplicationRecord historyJobApplicationRecord = null;

        if (jobApplicationRecord != null) {
            historyJobApplicationRecord = new HistoryJobApplicationRecord();

            historyJobApplicationRecord.setId(jobApplicationRecord.getId());
            historyJobApplicationRecord.setPositionId(jobApplicationRecord.getPositionId());
            historyJobApplicationRecord.setRecommenderId(jobApplicationRecord.getRecommenderId());
            historyJobApplicationRecord.setLApplicationId(jobApplicationRecord.getLApplicationId());
            historyJobApplicationRecord.setUserId(jobApplicationRecord.getApplierId());
            historyJobApplicationRecord.setAtsStatus(UInteger.valueOf(jobApplicationRecord.getAtsStatus()));
            historyJobApplicationRecord.setDisable(UInteger.valueOf(jobApplicationRecord.getDisable()));
            historyJobApplicationRecord.setRoutine(UInteger.valueOf(jobApplicationRecord.getRoutine()));
            historyJobApplicationRecord.setIsViewed(UByte.valueOf(jobApplicationRecord.getIsViewed()));
            historyJobApplicationRecord.setViewCount(UInteger.valueOf(jobApplicationRecord.getViewCount()));
            historyJobApplicationRecord.setNotSuitable(UByte.valueOf(jobApplicationRecord.getNotSuitable()));
            historyJobApplicationRecord.setCompanyId(jobApplicationRecord.getCompanyId());
            historyJobApplicationRecord.setAppTplId(jobApplicationRecord.getAppTplId());
            historyJobApplicationRecord.setProxy(UByte.valueOf(jobApplicationRecord.getProxy()));
            historyJobApplicationRecord.setApplyType(UInteger.valueOf(jobApplicationRecord.getApplyType()));
            historyJobApplicationRecord.setEmailStatus(UInteger.valueOf(jobApplicationRecord.getEmailStatus()));
            historyJobApplicationRecord.setSubmitTime(jobApplicationRecord.getSubmitTime());
            historyJobApplicationRecord.setCreateTime(jobApplicationRecord.get_CreateTime());
            historyJobApplicationRecord.setUpdateTime(jobApplicationRecord.getUpdateTime());
        }
        return historyJobApplicationRecord;
    }

    /**
     * 生成HR操作记录
     *
     * @param appId 当前申请ID
     * @param jobApplicationRecord 当前申请记录
     * @param JobPositonrecord 当前申请职位
     * @return
     */
    private HrOperationRecordRecord getHrOperationRecordRecord(long appId,
                                                               JobApplicationRecord jobApplicationRecord,
                                                               JobPositionRecord JobPositonrecord){
        HrOperationRecordRecord hrOperationRecordRecord = new HrOperationRecordRecord();
        hrOperationRecordRecord.setAdminId(JobPositonrecord.getPublisher().longValue());
        hrOperationRecordRecord.setCompanyId(jobApplicationRecord.getCompanyId().longValue());
        hrOperationRecordRecord.setAppId(appId);
        hrOperationRecordRecord.setOperateTplId(jobApplicationRecord.getAppTplId().intValue());
        return hrOperationRecordRecord;
    }
}
