package com.moseeker.baseorm.dao.jobdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.jobdb.tables.JobPositionCity;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionCityRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionCityDO;

/**
* @author xxx
* JobPositionCityDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class JobPositionCityDao extends StructDaoImpl<JobPositionCityDO, JobPositionCityRecord, JobPositionCity> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = JobPositionCity.JOB_POSITION_CITY;
   }
}
