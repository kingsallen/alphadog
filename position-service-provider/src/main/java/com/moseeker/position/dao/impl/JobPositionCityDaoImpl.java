package com.moseeker.position.dao.impl;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;

import com.moseeker.db.jobdb.tables.JobPositionCity;
import com.moseeker.db.jobdb.tables.records.JobPositionCityRecord;
import com.moseeker.position.dao.JobPositionCityDao;
import org.springframework.stereotype.Repository;


@Repository
public class JobPositionCityDaoImpl extends BaseDaoImpl<JobPositionCityRecord, JobPositionCity> implements JobPositionCityDao {
    @Override
    protected void initJOOQEntity() {
        this.tableLike = JobPositionCity.JOB_POSITION_CITY;
    }
}
