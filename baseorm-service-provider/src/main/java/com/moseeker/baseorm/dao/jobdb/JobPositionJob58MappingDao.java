package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionJob58Mapping;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionJob58MappingRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionJob58MappingDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * @author cjm
 * @date 2018-11-29 11:10
 **/
@Repository
public class JobPositionJob58MappingDao extends JooqCrudImpl<JobPositionJob58MappingDO, JobPositionJob58MappingRecord> {

    public JobPositionJob58MappingDao() {
        super(JobPositionJob58Mapping.JOB_POSITION_JOB58_MAPPING, JobPositionJob58MappingDO.class);
    }

    public JobPositionJob58MappingDao(TableImpl<JobPositionJob58MappingRecord> table, Class<JobPositionJob58MappingDO> jobPositionJob58MappingDOClass) {
        super(table, jobPositionJob58MappingDOClass);
    }


}
