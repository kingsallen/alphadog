package com.moseeker.baseorm.dao.jobdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.jobdb.tables.JobApplicationStatusBeisen;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationStatusBeisenRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationStatusBeisenDO;

/**
* @author xxx
* JobApplicationStatusBeisenDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class JobApplicationStatusBeisenDao extends StructDaoImpl<JobApplicationStatusBeisenDO, JobApplicationStatusBeisenRecord, JobApplicationStatusBeisen> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = JobApplicationStatusBeisen.JOB_APPLICATION_STATUS_BEISEN;
   }
}
