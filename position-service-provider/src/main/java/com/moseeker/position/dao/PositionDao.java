package com.moseeker.position.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.thrift.gen.position.struct.Position;

import java.util.List;

public interface PositionDao extends BaseDao<JobPositionRecord> {

    List<JobPositionRecord> getRecommendedPositions(int pid);

}
