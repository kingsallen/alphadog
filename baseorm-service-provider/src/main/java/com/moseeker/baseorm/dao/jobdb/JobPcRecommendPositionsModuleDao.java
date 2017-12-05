package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.JobPcRecommendPositionsModule;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPcRecommendPositionsModuleRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPcRecommendPositionsModuleDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 17/9/20.
 */
@Service
public class JobPcRecommendPositionsModuleDao extends JooqCrudImpl<JobPcRecommendPositionsModuleDO,JobPcRecommendPositionsModuleRecord> {
    public JobPcRecommendPositionsModuleDao(){
        super(JobPcRecommendPositionsModule.JOB_PC_RECOMMEND_POSITIONS_MODULE,JobPcRecommendPositionsModuleDO.class);
    }
    public JobPcRecommendPositionsModuleDao(TableImpl<JobPcRecommendPositionsModuleRecord> table, Class<JobPcRecommendPositionsModuleDO> jobPcRecommendPositionsModuleDOClass) {
        super(table, jobPcRecommendPositionsModuleDOClass);
    }
}
