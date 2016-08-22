package com.moseeker.position.dao.impl;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.jobdb.tables.JobPositionExt;
import com.moseeker.db.jobdb.tables.records.JobPositionExtRecord;
import com.moseeker.position.dao.JobPositonExtDao;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 职位副表
 *
 * Created by zzh on 16/8/11.
 */
@Repository
public class JobPositionExtDaoImpl extends BaseDaoImpl<JobPositionExtRecord, JobPositionExt> implements JobPositonExtDao {

    @Override
    protected void initJOOQEntity() {
        this.tableLike = JobPositionExt.JOB_POSITION_EXT;
    }

    @Override
    public JobPositionExtRecord getJobPositonExtRecordByPositionId(int positionId) {
        JobPositionExtRecord jobPositionExtRecord = null;
        Connection conn = null;
        try {
            if(positionId > 0) {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

                Condition condition = JobPositionExt.JOB_POSITION_EXT.PID.equal(positionId);

                jobPositionExtRecord = create.selectFrom(JobPositionExt.JOB_POSITION_EXT).where(condition).fetchOne();
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
        return jobPositionExtRecord;
    }
}
