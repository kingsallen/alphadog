package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionProfileFilter;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionProfileFilterRecord;
import java.util.List;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 17/12/1.
 */
@Service
public class JobPositionProfileFilterDao extends JooqCrudImpl<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionProfileFilter,JobPositionProfileFilterRecord> {

    public JobPositionProfileFilterDao(){
        super(JobPositionProfileFilter.JOB_POSITION_PROFILE_FILTER, com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionProfileFilter.class);
    }
    public JobPositionProfileFilterDao(TableImpl<JobPositionProfileFilterRecord> table, Class<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionProfileFilter> jobPositionProfileFilterClass) {
        super(table, jobPositionProfileFilterClass);
    }

    public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionProfileFilter> getFilterPositionByfilterIdList(List<Integer> filterIdList){
        List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionProfileFilter> result=create.selectFrom(JobPositionProfileFilter.JOB_POSITION_PROFILE_FILTER)
                .where(JobPositionProfileFilter.JOB_POSITION_PROFILE_FILTER.PFID.in(filterIdList))
                .fetchInto(com.moseeker.baseorm.db.jobdb.tables.pojos.JobPositionProfileFilter.class);
        return result;
    }



}
