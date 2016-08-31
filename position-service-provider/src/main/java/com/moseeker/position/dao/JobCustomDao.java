package com.moseeker.position.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.jobdb.tables.records.JobCustomRecord;

/**
 * 职位自定义字段
 *
 * Created by zzh on 16/8/11.
 */
public interface JobCustomDao extends BaseDao<JobCustomRecord>{

    JobCustomRecord getJobCustomRecord(int id);
}
