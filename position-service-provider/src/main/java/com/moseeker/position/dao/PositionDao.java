package com.moseeker.position.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.position.pojo.RecommendedPositonPojo;
import com.moseeker.thrift.gen.position.struct.Position;
import org.jooq.Record;

import java.util.List;

public interface PositionDao extends BaseDao<JobPositionRecord> {

    List<RecommendedPositonPojo> getRecommendedPositions(int pid);

}
