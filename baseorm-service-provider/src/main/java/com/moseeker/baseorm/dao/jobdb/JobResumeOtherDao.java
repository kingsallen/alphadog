package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.records.JobResumeOtherRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobResumeOtherDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* JobResumeOtherDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class JobResumeOtherDao extends JooqCrudImpl<JobResumeOtherDO, JobResumeOtherRecord> {


    public JobResumeOtherDao(TableImpl<JobResumeOtherRecord> table, Class<JobResumeOtherDO> jobResumeOtherDOClass) {
        super(table, jobResumeOtherDOClass);
    }
}
