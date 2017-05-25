package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionCity;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionCityRecord;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionCityDO;
import java.util.List;
import org.jooq.Condition;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* JobPositionCityDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class JobPositionCityDao extends JooqCrudImpl<JobPositionCityDO, JobPositionCityRecord> {

    public JobPositionCityDao() {
        super(JobPositionCity.JOB_POSITION_CITY, JobPositionCityDO.class);
    }

    public JobPositionCityDao(TableImpl<JobPositionCityRecord> table, Class<JobPositionCityDO> jobPositionCityDOClass) {
        super(table, jobPositionCityDOClass);
    }
    public void delJobPostionCityByPids(List<Integer> pids) {
        try {
            if (pids != null && pids.size() > 0) {
                Condition condition = JobPositionCity.JOB_POSITION_CITY.PID.in(pids);
                create.deleteFrom(JobPositionCity.JOB_POSITION_CITY).where(condition).execute();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
        }
    }
}
