package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionShareTplConf;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionShareTplConfRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionShareTplConfDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* JobPositionShareTplConfDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class JobPositionShareTplConfDao extends JooqCrudImpl<JobPositionShareTplConfDO, JobPositionShareTplConfRecord> {

    public JobPositionShareTplConfDao() {
        super(JobPositionShareTplConf.JOB_POSITION_SHARE_TPL_CONF, JobPositionShareTplConfDO.class);
    }

    public JobPositionShareTplConfDao(TableImpl<JobPositionShareTplConfRecord> table, Class<JobPositionShareTplConfDO> jobPositionShareTplConfDOClass) {
        super(table, jobPositionShareTplConfDOClass);
    }
}
