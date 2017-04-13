package com.moseeker.baseorm.dao.jobdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.jobdb.tables.JobApplicationConf;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationConfRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationConfDO;

/**
* @author xxx
* JobApplicationConfDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class JobApplicationConfDao extends StructDaoImpl<JobApplicationConfDO, JobApplicationConfRecord, JobApplicationConf> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = JobApplicationConf.JOB_APPLICATION_CONF;
   }
}
