package com.moseeker.application.dao.impl;

import com.moseeker.application.dao.JobApplicationDao;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.jobdb.tables.JobApplication;
import com.moseeker.db.jobdb.tables.records.JobApplicationRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
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
public class JobApplicationDaoImpl extends BaseDaoImpl<JobApplicationRecord, JobApplication> implements JobApplicationDao {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void initJOOQEntity() {
        this.tableLike = JobApplication.JOB_APPLICATION;
    }

    /**
     * 判断当前用户是否申请了该职位
     *
     * @param userId 用户ID
     * @param positionId 职位ID
     *
     * @return true : 申请, false: 没申请过
     *
     * */
    @Override
    public int getApplicationByUserIdAndPositionId(long userId, long positionId) throws Exception {
        Connection conn = null;
        Integer count = 0;

        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

            Condition condition = JobApplication.JOB_APPLICATION.APPLIER_ID.equal(UInteger.valueOf(userId)).
                    and(JobApplication.JOB_APPLICATION.POSITION_ID.equal(UInteger.valueOf(positionId)));

            Record record = create.selectCount().from(JobApplication.JOB_APPLICATION).where(condition).fetchOne();
            count = (Integer)record.getValue(0);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);

        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }
}
