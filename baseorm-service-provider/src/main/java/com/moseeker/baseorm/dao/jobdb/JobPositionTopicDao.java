package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionTopic;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionTopicRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionTopicDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* JobPositionTopicDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class JobPositionTopicDao extends JooqCrudImpl<JobPositionTopicDO, JobPositionTopicRecord> {


    public JobPositionTopicDao() {
        super(JobPositionTopic.JOB_POSITION_TOPIC, JobPositionTopicDO.class);
    }

    public JobPositionTopicDao(TableImpl<JobPositionTopicRecord> table, Class<JobPositionTopicDO> jobPositionTopicDOClass) {
        super(table, jobPositionTopicDOClass);
    }
}
