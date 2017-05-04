package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionCityRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionCityDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* JobPositionCityDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class JobPositionCityDao extends JooqCrudImpl<JobPositionCityDO, JobPositionCityRecord> {


    public JobPositionCityDao(TableImpl<JobPositionCityRecord> table, Class<JobPositionCityDO> jobPositionCityDOClass) {
        super(table, jobPositionCityDOClass);
    }
}
