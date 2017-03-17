package com.moseeker.position.dao.impl;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;

import com.moseeker.db.jobdb.tables.JobPositionCity;
import com.moseeker.db.jobdb.tables.records.JobPositionCityRecord;
import com.moseeker.position.dao.JobPositionCityDao;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


@Repository
public class JobPositionCityDaoImpl extends BaseDaoImpl<JobPositionCityRecord, JobPositionCity> implements JobPositionCityDao {
    @Override
    protected void initJOOQEntity() {
        this.tableLike = JobPositionCity.JOB_POSITION_CITY;
    }

    /**
     * 通过PID删除job_postion_city
     *
     * @param pids
     */
    @Override
    public void delJobPostionCityByPids(List<Integer> pids) {
        Connection conn = null;
        try {
            if (pids != null && pids.size() > 0) {
                conn = DBConnHelper.DBConn.getConn();
                DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
                Condition condition = JobPositionCity.JOB_POSITION_CITY.PID.in(pids);
                create.deleteFrom(JobPositionCity.JOB_POSITION_CITY).where(condition).execute();
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
