package com.moseeker.baseorm.dao.jobdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.jobdb.tables.JobPositionTopic;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionTopicRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionTopicDO;

/**
* @author xxx
* JobPositionTopicDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class JobPositionTopicDao extends StructDaoImpl<JobPositionTopicDO, JobPositionTopicRecord, JobPositionTopic> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = JobPositionTopic.JOB_POSITION_TOPIC;
   }
}
