package com.moseeker.position.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.jobdb.tables.records.JobPositionCityRecord;

import java.util.List;


public interface JobPositionCityDao extends BaseDao<JobPositionCityRecord> {

    /**
     * 通过PID删除job_postion_city
     *
     * @param pids
     */
    void delJobPostionCityByPids(List<Integer> pids);
}
