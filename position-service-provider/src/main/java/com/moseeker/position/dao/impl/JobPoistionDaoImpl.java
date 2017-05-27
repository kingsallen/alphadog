package com.moseeker.position.dao.impl;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.db.hrdb.tables.HrCompanyAccount;
import com.moseeker.db.hrdb.tables.records.HrCompanyAccountRecord;
import com.moseeker.db.jobdb.tables.JobPosition;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.position.dao.JobPositionDao;
import com.moseeker.position.pojo.JobPositionPojo;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 职位服务实现
 * <p>
 * <p>
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
        JobPositionRecord jobPositionRecord = null;
        Connection conn = null;
        try {
            if (positionId > 0) {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

                Condition condition = JobPosition.JOB_POSITION.ID.equal(positionId);

                jobPositionRecord = create.selectFrom(JobPosition.JOB_POSITION).where(condition).fetchOne();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            } finally {
                //do nothing
            }
        }
        return jobPositionRecord;
    }

    /***
     * 根据职位ID获取职位信息
     *
     * @param positionId 职位ID
     * @return
     */
    @Override
    public JobPositionPojo getPosition(int positionId) {
        List<JobPositionPojo> jobPositionPojoList = null;
        Connection conn = null;
        try {
            if (positionId > 0) {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

                Condition condition = JobPosition.JOB_POSITION.ID.equal(positionId);

                jobPositionPojoList = create.select().from(JobPosition.JOB_POSITION)
                        .where(condition).fetchInto(JobPositionPojo.class);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            } finally {
                //do nothing
            }
        }
        return jobPositionPojoList != null ? jobPositionPojoList.get(0) : null;
    }

    /**
     * 获取当前发布人的hr_company_account的关系company_id
     * <p>
     *
     * @param publisher
     * @return
     */
    @Override
    public HrCompanyAccountRecord getHrCompanyAccountByPublisher(int publisher) {
        HrCompanyAccountRecord hrCompanyAccountRecord = null;
        Connection conn = null;
        try {
            if (publisher > 0) {
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
                if (conn != null && !conn.isClosed()) {
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

    @Override
    public Integer insertJobPostion(JobPositionRecord jobPositionRecord) {
        Connection conn = null;
        if (jobPositionRecord != null) {
            try {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
                create.attach(jobPositionRecord);
                jobPositionRecord.insert();

            } catch (Exception e) {
                logger.error("error", e);
            } finally {
                try {
                    if (conn != null && !conn.isClosed()) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                } finally {
                    //do nothing
                }

            }
        }
        return jobPositionRecord.getId();
    }

}
