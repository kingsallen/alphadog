package com.moseeker.position.dao.impl;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.jobdb.tables.JobOccupationRel;
import com.moseeker.db.jobdb.tables.JobPositionCity;
import com.moseeker.db.jobdb.tables.records.JobOccupationRelRecord;
import com.moseeker.position.dao.JobOccupationRelDao;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 自定义职位职能持久层
 *
 * @author wjf
 */
@Repository
public class JobOccupationRelDaoImpl extends BaseDaoImpl<JobOccupationRelRecord, JobOccupationRel> implements JobOccupationRelDao {

    @Override
    protected void initJOOQEntity() {
        this.tableLike = JobOccupationRel.JOB_OCCUPATION_REL;
    }


    @Override
    public void delJobOccupationRelByPids(List<Integer> pids) {
        Connection conn = null;
        try {
            if (pids != null && pids.size() > 0) {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
                Condition condition = JobOccupationRel.JOB_OCCUPATION_REL.PID.in(pids);
                create.deleteFrom(JobOccupationRel.JOB_OCCUPATION_REL).where(condition).execute();
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
    }
}
