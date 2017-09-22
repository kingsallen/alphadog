package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.JobPcAdvertisement;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPcAdvertisementRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPcAdvertisementDO;
import org.jooq.impl.TableImpl;

/**
 * Created by zztaiwll on 17/9/20.
 */
public class JobPcAdvertisementDao extends JooqCrudImpl<JobPcAdvertisementDO,JobPcAdvertisementRecord> {
    public JobPcAdvertisementDao(){
        super(JobPcAdvertisement.JOB_PC_ADVERTISEMENT,JobPcAdvertisementDO.class);
    }
    public JobPcAdvertisementDao(TableImpl<JobPcAdvertisementRecord> table, Class<JobPcAdvertisementDO> jobPcAdvertisementDOClass) {
        super(table, jobPcAdvertisementDOClass);
    }
}
