package com.moseeker.position.dao.impl;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.db.jobdb.tables.JobPosition;
import com.moseeker.position.dao.PositionDao;
import org.springframework.stereotype.Repository;

/**
 * Created by chendi on 5/25/16.
 */
@Repository
public class PositionDaoImpl extends BaseDaoImpl<JobPositionRecord, JobPosition> implements PositionDao {

    @Override
    protected void initJOOQEntity() {
        this.tableLike = JobPosition.JOB_POSITION;
    }
}
