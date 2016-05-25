package com.moseeker.application.dao.impl;

import com.moseeker.application.dao.ApplicationDao;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.jobdb.tables.JobApplication;
import com.moseeker.db.jobdb.tables.records.JobApplicationRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * 申请服务实现
 * <p>
 *
 * Created by zzh on 16/5/24.
 */
@Repository
public class ApplicationDaoImpl extends BaseDaoImpl<JobApplicationRecord, JobApplication> implements ApplicationDao {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void initJOOQEntity() {
        this.tableLike = JobApplication.JOB_APPLICATION;
    }

}
