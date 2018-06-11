package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionCity;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionLiepinMapping;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionLiepinMappingRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionLiepinMappingDO;
import org.jooq.Condition;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cjm
 *         JobPositionLiepinMappingDao 实现类
 *         2018-6-6
 */
@Repository
public class JobPositionLiepinMappingDao extends JooqCrudImpl<JobPositionLiepinMappingDO, JobPositionLiepinMappingRecord> {

    public JobPositionLiepinMappingDao() {
        super(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING, JobPositionLiepinMappingDO.class);
    }

    public JobPositionLiepinMappingDao(TableImpl<JobPositionLiepinMappingRecord> table, Class<JobPositionLiepinMappingDO> jobPositionLiepinMappingDOClass) {
        super(table, jobPositionLiepinMappingDOClass);
    }

    public List<JobPositionLiepinMappingDO> getMappingDataByPidAndState(int pid, byte state) {
        Query query = new Query.QueryBuilder()
                .where(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.JOB_ID.getName(), pid)
                .and(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.STATE.getName(), state)
                .buildQuery();
        return getDatas(query);
    }

//    public List<JobPositionLiepinMappingDO> getMappingDataByPid(int pid) {
//        Query query = new Query.QueryBuilder()
//                .where(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.JOB_ID.getName(), pid)
//                .buildQuery();
//        return getDatas(query);
//    }

    public List<JobPositionLiepinMappingDO> getMappingDataByPid(int pid) {
       return create.selectFrom(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING)
               .where(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.JOB_ID.eq(pid))
               .fetchInto(JobPositionLiepinMappingDO.class);
    }


    public List<JobPositionLiepinMappingDO> getMappingDataByTitle(String jobTitle) {
        Query query = new Query.QueryBuilder()
                .where(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.JOB_TITLE.getName(), jobTitle)
//                .and(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.STATE.getName(), 1)
                .buildQuery();
        return getDatas(query);
    }

    public void updateErrMsg(int id, String message) {
        create.update(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.ERR_MSG, message)
                .where(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.ID.eq(id))
                .execute();
    }

    public void updateState(List<Integer> ids, byte state) {
        Condition condition = JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.ID.in(ids);
        create.update(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.STATE, state)
                .where(condition)
                .execute();
    }

    public void updateErrMsgBatch(int positionId, String message) {
        create.update(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.ERR_MSG, message)
                .where(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.JOB_ID.eq(positionId))
                .execute();
    }

    public void updateJobInfoById(Integer id, Integer thirdPositionId, byte state, String errorMsg) {
        create.update(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.ERR_MSG, errorMsg)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.STATE, state)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.LIEPIN_JOB_ID, thirdPositionId)
                .where(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.ID.eq(id))
                .execute();
    }

    public JobPositionLiepinMappingDO getDataByPidAndCityCode(Integer positionId, String cityCode) {
        return create.selectFrom(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING)
                .where(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.JOB_ID.eq(positionId))
                .and(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.CITY_CODE.eq(Integer.parseInt(cityCode)))
                .limit(1)
                .fetchOneInto(JobPositionLiepinMappingDO.class);
    }

    public List<JobPositionLiepinMappingDO> getMappingDataByPidAndCode(int positionId, List<Integer> rePublishCityCodes) {
        if(rePublishCityCodes.isEmpty()){
            return new ArrayList<>();
        }
        return create.selectFrom(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING)
                .where(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.JOB_ID.eq(positionId))
                .and(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.CITY_CODE.in(rePublishCityCodes))
                .fetchInto(JobPositionLiepinMappingDO.class);
    }
}
