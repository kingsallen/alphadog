package com.moseeker.application.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.moseeker.application.dao.JobResumeOtherDao;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.jobdb.tables.JobResumeOther;
import com.moseeker.db.jobdb.tables.records.JobResumeOtherRecord;

/**
 * 自定义简历 - 申请副本 服务实现
 * <p>
 *
 * Created by zzh on 16/5/24.
 */
@Repository
public class JobResumeOtherDaoImpl extends BaseDaoImpl<JobResumeOtherRecord, JobResumeOther> implements JobResumeOtherDao {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void initJOOQEntity() {
        this.tableLike = JobResumeOther.JOB_RESUME_OTHER;
    }

}
