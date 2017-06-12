package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionExt;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionExtRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionExtDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* JobPositionExtDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class JobPositionExtDao extends JooqCrudImpl<JobPositionExtDO, JobPositionExtRecord> {

    public JobPositionExtDao() {
        super(JobPositionExt.JOB_POSITION_EXT, JobPositionExtDO.class);
    }

    public JobPositionExtDao(TableImpl<JobPositionExtRecord> table, Class<JobPositionExtDO> jobPositionExtDOClass) {
        super(table, jobPositionExtDOClass);
    }
}
