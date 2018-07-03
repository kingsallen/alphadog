package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionCity;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionLiepinMapping;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionLiepinMappingRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionLiepinMappingDO;
import org.joda.time.DateTime;
import org.jooq.Condition;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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

    public List<JobPositionLiepinMappingDO> getMappingDataByPid(int pid) {
       return create.selectFrom(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING)
               .where(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.POSITION_ID.eq(pid))
               .fetchInto(JobPositionLiepinMappingDO.class);
    }


    public List<JobPositionLiepinMappingDO> getMappingDataByTitleAndUserId(String jobTitle, Integer liepinUserId, List<Integer> cityCodesIntList, Integer positionId) {
        Query query = new Query.QueryBuilder()
                .where(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.JOB_TITLE.getName(), jobTitle)
                .and(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.LIEPIN_USER_ID.getName(), liepinUserId)
                .and(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.POSITION_ID.getName(), positionId)
                .and(new com.moseeker.common.util.query.Condition(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.CITY_CODE.getName(), cityCodesIntList, ValueOp.IN))
                .buildQuery();
        return getDatas(query);
    }

    public void updateErrMsg(int id, String message) {
        create.update(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.ERR_MSG, message)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.UPDATE_TIME, new Timestamp(System.currentTimeMillis()))
                .where(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.ID.eq(id))
                .execute();
    }

    public void updateState(List<Integer> ids, byte state) {
        Condition condition = JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.ID.in(ids);
        create.update(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.STATE, state)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.UPDATE_TIME, new Timestamp(System.currentTimeMillis()))
                .where(condition)
                .execute();
    }

    public void updateState(int id, byte state) {
        create.update(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.STATE, state)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.UPDATE_TIME, new Timestamp(System.currentTimeMillis()))
                .where(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.ID.eq(id))
                .execute();
    }

    public void updateErrMsgBatch(List<Integer> ids, String message) {
        create.update(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.ERR_MSG, message)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.UPDATE_TIME, new Timestamp(System.currentTimeMillis()))
                .where(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.ID.in(ids))
                .execute();
    }

    public void updateErrMsgBatch(Integer positionId, String message) {
        create.update(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.ERR_MSG, message)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.UPDATE_TIME, new Timestamp(System.currentTimeMillis()))
                .where(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.POSITION_ID.eq(positionId))
                .execute();
    }

    public void updateJobInfoById(Integer id, Integer thirdPositionId, byte state, String errorMsg) {
        create.update(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.ERR_MSG, errorMsg)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.STATE, state)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.LIEPIN_JOB_ID, thirdPositionId)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.UPDATE_TIME, new Timestamp(System.currentTimeMillis()))
                .where(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.ID.eq(id))
                .execute();
    }

    public List<JobPositionLiepinMappingDO> getDownShelfMappingDataByPidAndCode(int positionId, List<Integer> rePublishCityCodes) {
        if(rePublishCityCodes.isEmpty()){
            return new ArrayList<>();
        }
        return create.selectFrom(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING)
                .where(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.POSITION_ID.eq(positionId))
                .and(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.CITY_CODE.in(rePublishCityCodes))
                .and(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.STATE.eq((byte)0))
                .fetchInto(JobPositionLiepinMappingDO.class);
    }

    public List<JobPositionLiepinMappingDO> getMappingDataByUserId(int userId) {
        return create.select(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.ID,
                JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.POSITION_ID,
                JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.LIEPIN_JOB_ID)
                .from(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING)
                .where(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.LIEPIN_USER_ID.eq(userId))
                .and(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.STATE.eq((byte)1))
                .fetchInto(JobPositionLiepinMappingDO.class);
    }

    public List<JobPositionLiepinMappingDO> getValidMappingDataByPid(int id, byte state) {
        return create.selectFrom(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING)
                .where(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.POSITION_ID.eq(id))
                .and(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.STATE.eq(state))
                .fetchInto(JobPositionLiepinMappingDO.class);
    }

    public int updateStateAndJobId(List<Integer> republishIdList, byte state, Integer positionId) {
        return create.update(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.POSITION_ID, positionId)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.STATE, state)
                .set(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.UPDATE_TIME, new Timestamp(System.currentTimeMillis()))
                .where(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.ID.in(republishIdList))
                .execute();
    }

    public List<JobPositionLiepinMappingDO> getMappingDataByPidAndCityCodes(int positionId, List<Integer> cityCodes) {
        return create.selectFrom(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING)
                .where(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.POSITION_ID.eq(positionId))
                .and(JobPositionLiepinMapping.JOB_POSITION_LIEPIN_MAPPING.CITY_CODE.in(cityCodes))
                .fetchInto(JobPositionLiepinMappingDO.class);
    }
}
