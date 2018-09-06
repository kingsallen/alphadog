package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.candidatedb.tables.CandidatePosition;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateRecomRecord;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateRecomRecordRecord;
import com.moseeker.baseorm.util.BeanUtils;

import com.moseeker.thrift.gen.common.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.CandidateRecomRecordSortingDO;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateRecomRecordDO;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jooq.*;
import org.jooq.impl.TableImpl;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.*;

/**
 * Created by jack on 15/02/2017.
 */
@Repository
public class CandidateRecomRecordDao extends JooqCrudImpl<CandidateRecomRecordDO, CandidateRecomRecordRecord> {


    public CandidateRecomRecordDao() {
        super(CandidateRecomRecord.CANDIDATE_RECOM_RECORD, CandidateRecomRecordDO.class);
    }

    public CandidateRecomRecordDao(TableImpl<CandidateRecomRecordRecord> table, Class<CandidateRecomRecordDO> candidateRecomRecordDOClass) {
        super(table, candidateRecomRecordDOClass);
    }

    public void deleteCandidateRecomRecord(int id) throws CURDException {
        CandidateRecomRecordDO p = new CandidateRecomRecordDO();
        p.setId(id);
        this.deleteData(p);
    }

    public List<CandidateRecomRecordDO> listCandidateRecomRecordsForApplied(int userId, int pageNo, int pageSize) {
        List<CandidateRecomRecordDO> candidateRecomRecordDOList = new ArrayList<>();
        SelectHavingStep selectConditionStep = create.select(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.REPOST_USER_ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.RECOM_TIME,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.IS_RECOM,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID)
                .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal((int) (userId))
                        .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID.greaterThan(0)))
                .groupBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                        CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID);
        if (pageNo > 0 && pageSize > 0) {
            selectConditionStep.limit((pageNo - 1) * pageSize, pageSize);
        }
        Result<CandidateRecomRecordRecord> result = selectConditionStep.fetch().into(CandidateRecomRecord.CANDIDATE_RECOM_RECORD);
        if (result != null && result.size() > 0) {
            candidateRecomRecordDOList = BeanUtils.DBToStruct(CandidateRecomRecordDO.class, result);
        }
        return candidateRecomRecordDOList;
    }

    public List<CandidateRecomRecordDO> listCandidateRecomRecordsByPositionSetAndPresenteeId(Set<Integer> positionIdSet, int presenteeId, int pageNo, int pageSize) {
        List<CandidateRecomRecordDO> candidateRecomRecordDOList = new ArrayList<>();
        SelectConditionStep selectConditionStep = create.select(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.REPOST_USER_ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.RECOM_TIME,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.IS_RECOM,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID)
                .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal((int) (presenteeId))
                        .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID.in(positionIdSet)));
        if (pageNo > 0 && pageSize > 0) {
            selectConditionStep.limit((pageNo - 1) * pageSize, pageSize);
        }
        Result<CandidateRecomRecordRecord> result = selectConditionStep.fetch().into(CandidateRecomRecord.CANDIDATE_RECOM_RECORD);
        if (result != null && result.size() > 0) {
            candidateRecomRecordDOList = BeanUtils.DBToStruct(CandidateRecomRecordDO.class, result);
        }
        return candidateRecomRecordDOList;
    }

    public int countAppliedCandidateRecomRecord(int userId) {
        int count = 0;
        Result<Record1<Integer>> result = create
                .select(countDistinct(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                        CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID))
                .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(userId)
                        .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID.greaterThan(0))).fetch();
        if (result != null && result.size() > 0) {
            count = result.get(0).value1();
        }
        return count;
    }

    public int countInterestedCandidateRecomRecord(int userId) {
        int count = 0;
        Result<Record1<Integer>> result = create
                .select(countDistinct(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                        CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID))
                .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.leftJoin(CandidatePosition.CANDIDATE_POSITION)
                        .on(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID
                                .equal(CandidatePosition.CANDIDATE_POSITION.USER_ID))
                        .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID
                                .equal(CandidatePosition.CANDIDATE_POSITION.POSITION_ID)))
                .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal((int) (userId))
                        .and(CandidatePosition.CANDIDATE_POSITION.IS_INTERESTED.equal((byte) 1)))
                .fetch();
        if (result != null && result.size() > 0) {
            count = result.get(0).value1();
        }
        return count;
    }

    public List<CandidateRecomRecordDO> listInterestedCandidateRecomRecord(int userId, int pageNo, int pageSize) {
        List<CandidateRecomRecordDO> candidateRecomRecordDOList = new ArrayList<>();
        SelectHavingStep selectConditionStep = create.select(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.REPOST_USER_ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.RECOM_TIME,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.IS_RECOM,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID)
                .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.leftJoin(CandidatePosition.CANDIDATE_POSITION)
                        .on(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID
                                .equal(CandidatePosition.CANDIDATE_POSITION.USER_ID))
                        .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID
                                .equal(CandidatePosition.CANDIDATE_POSITION.POSITION_ID)))
                .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(userId)
                        .and(CandidatePosition.CANDIDATE_POSITION.IS_INTERESTED.equal((byte) 1)))
                .groupBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                        CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID);

        if (pageNo > 0 && pageSize > 0) {
            selectConditionStep.limit((pageNo - 1) * pageSize, pageSize);
        }
        Result<CandidateRecomRecordRecord> result = selectConditionStep.fetch().into(CandidateRecomRecord.CANDIDATE_RECOM_RECORD);
        if (result != null && result.size() > 0) {
            candidateRecomRecordDOList = BeanUtils.DBToStruct(CandidateRecomRecordDO.class, result);
        }
        return candidateRecomRecordDOList;
    }

    public List<CandidateRecomRecordDO> listCandidateRecomRecord(int postUserId, String clickTime, List<Integer> recoms, List<Integer> positionIdList) {
        List<CandidateRecomRecordDO> candidateRecomRecordDOList = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime dateTime = DateTime.parse(clickTime, format);
        Condition condition = CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(postUserId)
                .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME.greaterOrEqual(new Timestamp(dateTime.getMillis())))
                .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME.lessThan(new Timestamp(dateTime.plusDays(1).getMillis())))
                .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID.in(positionIdList));

        if (recoms != null && recoms.size() > 0) {
            condition = condition.and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.IS_RECOM.in(recoms));
        }

        Result<CandidateRecomRecordRecord> result = create.selectFrom(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                .where(condition)
                .groupBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID,
                        CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID)
                .orderBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME)
                .fetch();
        SelectSeekStep1 tableLike = create.selectFrom(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                .where(condition)
                .groupBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID,
                        CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID)
                .orderBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME);
        logger.info(tableLike.getSQL());
        if (result != null && result.size() > 0) {
            candidateRecomRecordDOList = BeanUtils.DBToStruct(CandidateRecomRecordDO.class, result);
        }
        return candidateRecomRecordDOList;
    }
    public List<CandidateRecomRecordDO> listCandidateRecomRecord(int postUserId, Timestamp time1,Timestamp time2, List<Integer> recoms, List<Integer> positionIdList) {
        List<CandidateRecomRecordDO> candidateRecomRecordDOList = new ArrayList<>();

        Condition condition = CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(postUserId)
                .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME.greaterOrEqual(time1))
                .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME.lessThan(time2))
                .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID.in(positionIdList));

        if (recoms != null && recoms.size() > 0) {
            condition = condition.and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.IS_RECOM.in(recoms));
        }

        Result<CandidateRecomRecordRecord> result = create.selectFrom(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                .where(condition)
                .groupBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID,
                        CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID)
                .orderBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME)
                .fetch();
        SelectSeekStep1 tableLike = create.selectFrom(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                .where(condition)
                .groupBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID,
                        CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID)
                .orderBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME);
        logger.info(tableLike.getSQL());
        if (result != null && result.size() > 0) {
            candidateRecomRecordDOList = BeanUtils.DBToStruct(CandidateRecomRecordDO.class, result);
        }
        return candidateRecomRecordDOList;
    }

    public List<CandidateRecomRecordDO> listCandidateRecomRecordExceptId(int id, int postUserId, String clickTime,
                                                                         List<Integer> recoms, List<Integer> positionIdList) {
        List<CandidateRecomRecordDO> candidateRecomRecordDOList = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime dateTime = DateTime.parse(clickTime, format);
        Condition condition = CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(postUserId)
                .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.ID.notEqual(id))
                .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME.greaterOrEqual(new Timestamp(dateTime.getMillis())))
                .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME.lessThan(new Timestamp(dateTime.plusDays(1).getMillis())))
                .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID.in(positionIdList));
        if (recoms != null && recoms.size() > 0) {
            condition = condition.and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.IS_RECOM.in(recoms));
        }
        SelectOffsetStep selectOffsetStep = create.selectFrom(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                .where(condition)
                .groupBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID,
                        CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID)
                .orderBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME)
                .limit(2);
        logger.info(selectOffsetStep.getSQL());
        Result<CandidateRecomRecordRecord> result = selectOffsetStep.fetch();
        if (result != null && result.size() > 0) {
            candidateRecomRecordDOList = BeanUtils.DBToStruct(CandidateRecomRecordDO.class, result);
        }
        return candidateRecomRecordDOList;
    }

    public int countCandidateRecomRecordCustom(int postUserId, String clickTime, List<Integer> recoms, List<Integer> positionIdList) {
        int count = 0;
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime dateTime = DateTime.parse(clickTime, format);
        Condition condition = CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(postUserId)
                .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME.greaterOrEqual(new Timestamp(dateTime.getMillis())))
                .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME.lessThan(new Timestamp(dateTime.plusDays(1).getMillis())))
                .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID.in(positionIdList));
        if (recoms != null && recoms.size() > 0) {
            condition = condition.and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.IS_RECOM.in(recoms));
        }

        Result<Record1<Integer>> result = create.selectCount().from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                .where(condition)
                .fetch();
        if (result != null && result.size() > 0) {
            count = (int) result.get(0).get(0);
        }
        return count;
    }

    public List<CandidateRecomRecordSortingDO> listCandidateRecomRecordSorting(List<Integer> postUserId, List<Integer> positionIdList) {
        List<CandidateRecomRecordSortingDO> recoms = new ArrayList<>();
        Field<Integer> count = countDistinct(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID);
        Result<Record2<Integer, Integer>> result = create.select(count,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID)
                .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.IS_RECOM.equal(0)
                        .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.in(postUserId))
                        .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID.in(positionIdList)))
                .groupBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID)
                .orderBy(count.desc())
                .fetch();

        if (result != null && result.size() > 0) {
            for (Record2<Integer, Integer> record2 : result) {
                CandidateRecomRecordSortingDO candidateRecomRecordSortingDO = new CandidateRecomRecordSortingDO();
                if (record2.get(0) != null) {
                    candidateRecomRecordSortingDO.setCount((Integer) record2.get(0));
                }
                if (record2.get(1) != null) {
                    candidateRecomRecordSortingDO.setPostUserId(((Integer) record2.get(1)).intValue());
                }
                recoms.add(candidateRecomRecordSortingDO);
            }
        }
        return recoms;
    }

    public int countCandidateRecomRecordDistinctPresentee(int postUserId) {
        int count = 0;
        SelectConditionStep selectConditionStep = create
                .select(countDistinct(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                        CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID))
                .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(postUserId));

        Result<Record1<Integer>> result = selectConditionStep.fetch();
        if (result != null && result.size() > 0) {
            count = (int) result.get(0).get(0);
        }
        return count;
    }

    public List<com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateRecomRecordDO> listCandidateRecomRecordsForAppliedByUserPositions(int userId, List<Integer> positionIdList, int pageNo, int pageSize) {
        List<com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateRecomRecordDO> candidateRecomRecordDOList = new ArrayList<>();
        SelectSeekStep1 selectConditionStep = create.select(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.REPOST_USER_ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.RECOM_TIME,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.IS_RECOM,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID)
                .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.ID
                        .in(select(max(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.ID))
                                .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                                .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(Integer.valueOf(userId))
                                        .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID.greaterThan(0))
                                        .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID.in(positionIdList)))
                                .groupBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                                        CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID)))
                .orderBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.ID.desc());

        if (pageNo > 0 && pageSize > 0) {
            selectConditionStep.limit((pageNo - 1) * pageSize, pageSize);
        }
        Result<CandidateRecomRecordRecord> result = selectConditionStep.fetch().into(CandidateRecomRecord.CANDIDATE_RECOM_RECORD);
        if (result != null && result.size() > 0) {
            candidateRecomRecordDOList = BeanUtils.DBToStruct(com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateRecomRecordDO.class, result);
        }
        return candidateRecomRecordDOList;
    }

    public int countCandidateRecomRecordDistinctPresenteePosition(int postUserId, List<Integer> positionIdList) {
        int count = 0;
        SelectConditionStep selectConditionStep = create
                .select(countDistinct(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                        CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID))
                .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(Integer.valueOf(postUserId))
                        .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID.in(positionIdList)));

        Result<Record1<Integer>> result = selectConditionStep.fetch();
        if (result != null && result.size() > 0) {
            count = (int) result.get(0).get(0);
        }
        return count;
    }

    public int countAppliedCandidateRecomRecordByUserPosition(int userId, List<Integer> positionIdList) {
        int count = 0;
        Result<Record1<Integer>> result = create
                .select(countDistinct(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                        CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID))
                .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(Integer.valueOf(userId))
                        .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID.greaterThan(0))
                        .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID.in(positionIdList)))
                .fetch();
        if (result != null && result.size() > 0) {
            count = result.get(0).value1();
        }
        return count;
    }

    public int countInterestedCandidateRecomRecordByUserPosition(int userId, List<Integer> positionIdList) {
        int count = 0;
        Result<Record1<Integer>> result = create
                .select(countDistinct(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                        CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID))
                .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.leftJoin(CandidatePosition.CANDIDATE_POSITION)
                        .on(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID
                                .equal(CandidatePosition.CANDIDATE_POSITION.USER_ID))
                        .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID
                                .equal(CandidatePosition.CANDIDATE_POSITION.POSITION_ID)))
                .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(Integer.valueOf(userId))
                        .and(CandidatePosition.CANDIDATE_POSITION.IS_INTERESTED.equal((byte) 1))
                        .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID.in(positionIdList)))
                .fetch();
        if (result != null && result.size() > 0) {
            count = result.get(0).value1();
        }
        return count;
    }

    public List<com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateRecomRecordDO> listInterestedCandidateRecomRecordByUserPositions(int userId, List<Integer> positionIdList, int pageNo, int pageSize) {
        List<com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateRecomRecordDO> candidateRecomRecordDOList = new ArrayList<>();
        SelectSeekStep1 selectConditionStep = create.select(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.REPOST_USER_ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.RECOM_TIME,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.IS_RECOM,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID)
                .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.ID
                        .in(select(max(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.ID))
                                .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                                .leftJoin(CandidatePosition.CANDIDATE_POSITION)
                                .on(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID
                                        .equal(CandidatePosition.CANDIDATE_POSITION.USER_ID))
                                .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID
                                        .equal(CandidatePosition.CANDIDATE_POSITION.POSITION_ID))
                                .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(Integer.valueOf(userId)))
                                .and(CandidatePosition.CANDIDATE_POSITION.IS_INTERESTED.equal((byte) 1))
                                .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID.in(positionIdList))
                                .groupBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                                        CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID)))
                .orderBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.ID.desc());

        if (pageNo > 0 && pageSize > 0) {
            selectConditionStep.limit((pageNo - 1) * pageSize, pageSize);
        }
        Result<CandidateRecomRecordRecord> result = selectConditionStep.fetch().into(CandidateRecomRecord.CANDIDATE_RECOM_RECORD);
        if (result != null && result.size() > 0) {
            candidateRecomRecordDOList = BeanUtils.DBToStruct(com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateRecomRecordDO.class, result);
        }
        return candidateRecomRecordDOList;
    }


    public int insertIfNotExist(CandidateRecomRecordRecord recomRecordRecord) {

        Param<Integer> positionIdParam = param(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID.getName(), recomRecordRecord.getPositionId());
        Param<Integer> applicationIdParam = param(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID.getName(), recomRecordRecord.getAppId());
        Param<Timestamp> recomTimeParam = param(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.RECOM_TIME.getName(), new Timestamp(System.currentTimeMillis()));
        Param<Integer> depthParam = param(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.DEPTH.getName(), recomRecordRecord.getDepth());
        Param<String> reasonParam = param(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.RECOM_REASON.getName(), recomRecordRecord.getRecomReason());
        Param<String> mobileParam = param(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.MOBILE.getName(), recomRecordRecord.getMobile());
        Param<Integer> presenteeUserIdParam = param(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID.getName(), recomRecordRecord.getPresenteeUserId());
        Param<Integer> postUserIdParam = param(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID.getName(), recomRecordRecord.getPostUserId());

        create.insertInto(CandidateRecomRecord.CANDIDATE_RECOM_RECORD,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.RECOM_TIME,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.DEPTH,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.RECOM_REASON,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.MOBILE,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID
        ).select(
                select(
                    positionIdParam,
                    applicationIdParam,
                    recomTimeParam,
                    depthParam,
                    reasonParam,
                    mobileParam,
                    presenteeUserIdParam,
                    postUserIdParam
                )
                .whereNotExists(
                        selectOne()
                        .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                        .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.eq(recomRecordRecord.getPostUserId()))
                        .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID.eq(recomRecordRecord.getPresenteeUserId()))
                        .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID.eq(recomRecordRecord.getPositionId()))
                )
        ).execute();

        CandidateRecomRecordRecord recomRecordRecord1 = create.selectFrom(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.eq(recomRecordRecord.getPostUserId()))
                .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID.eq(recomRecordRecord.getPresenteeUserId()))
                .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID.eq(recomRecordRecord.getPositionId()))
                .orderBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.ID.desc())
                .limit(1)
                .fetchOne();

        return recomRecordRecord1.getId();
    }
}