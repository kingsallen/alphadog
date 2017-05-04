package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationConfRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationConfDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* JobApplicationConfDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class JobApplicationConfDao extends JooqCrudImpl<JobApplicationConfDO, JobApplicationConfRecord> {


    public JobApplicationConfDao(TableImpl<JobApplicationConfRecord> table, Class<JobApplicationConfDO> jobApplicationConfDOClass) {
        super(table, jobApplicationConfDOClass);
    }
}
