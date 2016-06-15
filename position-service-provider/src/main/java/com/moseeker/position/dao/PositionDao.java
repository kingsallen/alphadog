package com.moseeker.position.dao;

import java.util.List;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.position.pojo.RecommendedPositonPojo;

public interface PositionDao extends BaseDao<JobPositionRecord> {

    List<RecommendedPositonPojo> getRecommendedPositions(int pid);

}
