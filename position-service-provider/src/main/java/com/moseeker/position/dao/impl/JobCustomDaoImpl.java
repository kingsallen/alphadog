package com.moseeker.position.dao.impl;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.jobdb.tables.JobCustom;
import com.moseeker.db.jobdb.tables.records.JobCustomRecord;
import com.moseeker.position.dao.JobCustomDao;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by zzh on 16/8/11.
 */
@Repository
public class JobCustomDaoImpl extends BaseDaoImpl<JobCustomRecord, JobCustom> implements JobCustomDao{

    @Override
    protected void initJOOQEntity() {
        this.tableLike = JobCustom.JOB_CUSTOM;
    }

    @Override
    public JobCustomRecord getJobCustomRecord(int positionId) {
        JobCustomRecord jobCustomRecord = null;
        Connection conn = null;
        try {
            if(positionId > 0) {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

                Condition condition = JobCustom.JOB_CUSTOM.ID.equal(positionId);

                jobCustomRecord = create.selectFrom(JobCustom.JOB_CUSTOM).where(condition).fetchOne();
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
        return jobCustomRecord;
    }
}
