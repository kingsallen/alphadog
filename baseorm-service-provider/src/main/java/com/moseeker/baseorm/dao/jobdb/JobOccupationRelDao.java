package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.JobOccupationRel;
import com.moseeker.baseorm.db.jobdb.tables.records.JobOccupationRelRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobOccupationRelDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* JobOccupationRelDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class JobOccupationRelDao extends JooqCrudImpl<JobOccupationRelDO, JobOccupationRelRecord> {

    public JobOccupationRelDao() {
        super(JobOccupationRel.JOB_OCCUPATION_REL, JobOccupationRelDO.class);
    }

    public JobOccupationRelDao(TableImpl<JobOccupationRelRecord> table, Class<JobOccupationRelDO> jobOccupationRelDOClass) {
        super(table, jobOccupationRelDOClass);
    }
}
