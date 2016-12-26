package com.moseeker.position.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.jobdb.tables.JobOccupation;
import com.moseeker.db.jobdb.tables.records.JobOccupationRecord;
import com.moseeker.position.dao.JobOccupationDao;

/**
 * 自定义职位职能持久层
 * @author wjf
 *
 */
@Repository
public class JobOccupationDaoImpl extends BaseDaoImpl<JobOccupationRecord, JobOccupation> implements JobOccupationDao{

    @Override
    protected void initJOOQEntity() {
        this.tableLike = JobOccupation.JOB_OCCUPATION;
    }

    @Override
    public JobOccupationRecord getJobOccupationRecord(int id) {
    	JobOccupationRecord jobOccupationRecord = null;
        Connection conn = null;
        try {
            if(id > 0) {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

                Condition condition = JobOccupation.JOB_OCCUPATION.ID.equal(id);

                jobOccupationRecord = create.selectFrom(JobOccupation.JOB_OCCUPATION).where(condition).fetchOne();
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
        return jobOccupationRecord;
    }
}
