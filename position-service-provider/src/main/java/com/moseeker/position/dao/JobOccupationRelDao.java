package com.moseeker.position.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.jobdb.tables.records.JobOccupationRelRecord;

import java.util.List;

/**
 * 自定义职位职能持久层
 *
 * @author wjf
 */
public interface JobOccupationRelDao extends BaseDao<JobOccupationRelRecord> {

    void delJobOccupationRelByPids(List<Integer> pids);

}
