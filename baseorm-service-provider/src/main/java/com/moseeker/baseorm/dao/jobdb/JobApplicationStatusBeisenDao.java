package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationStatusBeisenRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationStatusBeisenDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* JobApplicationStatusBeisenDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class JobApplicationStatusBeisenDao extends JooqCrudImpl<JobApplicationStatusBeisenDO, JobApplicationStatusBeisenRecord> {


    public JobApplicationStatusBeisenDao(TableImpl<JobApplicationStatusBeisenRecord> table, Class<JobApplicationStatusBeisenDO> jobApplicationStatusBeisenDOClass) {
        super(table, jobApplicationStatusBeisenDOClass);
    }
}
