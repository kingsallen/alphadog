package com.moseeker.application.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.moseeker.application.dao.JobResumeBasicDao;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.jobdb.tables.JobResumeBasic;
import com.moseeker.db.jobdb.tables.records.JobResumeBasicRecord;

/**
 * 申请服务实现
 * <p>
 *
 * Created by zzh on 16/5/24.
 */
@Repository
public class JobResumeBasicDaoImpl extends BaseDaoImpl<JobResumeBasicRecord, JobResumeBasic> implements JobResumeBasicDao {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void initJOOQEntity() {
        this.tableLike = JobResumeBasic.JOB_RESUME_BASIC;
    }

}
