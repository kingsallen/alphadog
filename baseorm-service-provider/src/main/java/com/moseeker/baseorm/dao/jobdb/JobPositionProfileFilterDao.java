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

    public List<JobPositionProfileFilterRecord> getFilterPositionRecordByfilterIdList(List<Integer> filterIdList){
        List<JobPositionProfileFilterRecord> result=create.selectFrom(JobPositionProfileFilter.JOB_POSITION_PROFILE_FILTER)
                .where(JobPositionProfileFilter.JOB_POSITION_PROFILE_FILTER.PFID.in(filterIdList))
                .fetch();
        return result;
    }

    public void deleteFilterPositionRecordList(List<JobPositionProfileFilterRecord> filterRecordList){
        if(filterRecordList != null && filterRecordList.size()>0) {
            for(JobPositionProfileFilterRecord record : filterRecordList) {
                create.deleteFrom(JobPositionProfileFilter.JOB_POSITION_PROFILE_FILTER)
                        .where(JobPositionProfileFilter.JOB_POSITION_PROFILE_FILTER.PFID.in(record.getPfid()))
                        .and(JobPositionProfileFilter.JOB_POSITION_PROFILE_FILTER.PID.in(record.getPid())).execute();
            }
        }
    }

    public void deleteFilterPositionByFilterIdList(List<Integer> filterIdList){
        if(filterIdList != null && filterIdList.size()>0) {
            create.deleteFrom(JobPositionProfileFilter.JOB_POSITION_PROFILE_FILTER)
                    .where(JobPositionProfileFilter.JOB_POSITION_PROFILE_FILTER.PFID.in(filterIdList))
                    .execute();
        }
    }

}
