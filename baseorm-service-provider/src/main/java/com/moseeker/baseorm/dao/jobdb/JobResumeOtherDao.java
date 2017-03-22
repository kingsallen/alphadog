package com.moseeker.baseorm.dao.jobdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.jobdb.tables.JobResumeOther;
import com.moseeker.baseorm.db.jobdb.tables.records.JobResumeOtherRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobResumeOtherDO;

/**
* @author xxx
* JobResumeOtherDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class JobResumeOtherDao extends StructDaoImpl<JobResumeOtherDO, JobResumeOtherRecord, JobResumeOther> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = JobResumeOther.JOB_RESUME_OTHER;
   }
}
