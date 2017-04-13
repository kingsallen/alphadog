package com.moseeker.baseorm.dao.jobdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.jobdb.tables.JobOccupationRel;
import com.moseeker.baseorm.db.jobdb.tables.records.JobOccupationRelRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobOccupationRelDO;

/**
* @author xxx
* JobOccupationRelDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class JobOccupationRelDao extends StructDaoImpl<JobOccupationRelDO, JobOccupationRelRecord, JobOccupationRel> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = JobOccupationRel.JOB_OCCUPATION_REL;
   }
}
