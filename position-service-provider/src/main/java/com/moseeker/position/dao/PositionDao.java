package com.moseeker.position.dao;

import java.util.List;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.position.pojo.RecommendedPositonPojo;

public interface PositionDao extends BaseDao<JobPositionRecord> {

    List<RecommendedPositonPojo> getRecommendedPositions(int pid);

    /**
     * 查找职位所在的省份
     * @param positionId 质变编号
     * @return 省份集合
     */
	List<DictCityRecord> getProvincesByPositionID(int positionId);

}
