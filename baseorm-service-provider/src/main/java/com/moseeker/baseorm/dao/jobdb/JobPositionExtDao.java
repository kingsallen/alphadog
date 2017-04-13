package com.moseeker.baseorm.dao.jobdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.jobdb.tables.JobPositionExt;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionExtRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionExtDO;

/**
* @author xxx
* JobPositionExtDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class JobPositionExtDao extends StructDaoImpl<JobPositionExtDO, JobPositionExtRecord, JobPositionExt> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = JobPositionExt.JOB_POSITION_EXT;
   }
}
