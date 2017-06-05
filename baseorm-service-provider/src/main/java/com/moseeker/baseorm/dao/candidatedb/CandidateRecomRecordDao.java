package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.db.candidatedb.tables.CandidatePosition;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateRecomRecord;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateRecomRecordRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.dao.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.CandidateRecomRecordDO;
import com.moseeker.thrift.gen.dao.struct.CandidateRecomRecordSortingDO;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jooq.*;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.jooq.impl.DSL.*;

/**
 * Created by jack on 15/02/2017.
 */
@Component
public class CandidateRecomRecordDao extends StructDaoImpl<CandidateRecomRecordDO, CandidateRecomRecordRecord, CandidateRecomRecord> {


    @Override
    protected void initJOOQEntity() {
        this.tableLike = CandidateRecomRecord.CANDIDATE_RECOM_RECORD;
    }

    public void deleteCandidateRecomRecord(int id) throws CURDException {
        CandidateRecomRecordDO p = new CandidateRecomRecordDO();
        p.setId(id);
        this.deleteResource(p);
    }

    public List<CandidateRecomRecordDO> listCandidateRecomRecordsForApplied(int userId, int pageNo, int pageSize) {
        List<CandidateRecomRecordDO> candidateRecomRecordDOList = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            SelectHavingStep selectConditionStep = create.select(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.ID,
                    CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID,
                    CandidateRecomRecord.CANDIDATE_RECOM_RECORD.REPOST_USER_ID,
                    CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME,
                    CandidateRecomRecord.CANDIDATE_RECOM_RECORD.RECOM_TIME,
                    CandidateRecomRecord.CANDIDATE_RECOM_RECORD.IS_RECOM,
                    CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                    CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID)
                    .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                    .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(UInteger.valueOf(userId))
                            .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID.greaterThan(0)))
                    .groupBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                            CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID);
            if(pageNo > 0 && pageSize > 0) {
                selectConditionStep.limit((pageNo-1)*pageSize, pageSize);
            }
            Result<CandidateRecomRecordRecord> result = selectConditionStep.fetch().into(CandidateRecomRecord.CANDIDATE_RECOM_RECORD);
            if(result != null && result.size() > 0) {
                candidateRecomRecordDOList = BeanUtils.DBToStruct(CandidateRecomRecordDO.class, result);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return candidateRecomRecordDOList;
    }

    public List<CandidateRecomRecordDO> listCandidateRecomRecordsByPositionSetAndPresenteeId(Set<Integer> positionIdSet, int presenteeId, int pageNo, int pageSize) {
        List<CandidateRecomRecordDO> candidateRecomRecordDOList = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            SelectConditionStep selectConditionStep = create.select(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.ID,
                    CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID,
                    CandidateRecomRecord.CANDIDATE_RECOM_RECORD.REPOST_USER_ID,
                    CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME,
                    CandidateRecomRecord.CANDIDATE_RECOM_RECORD.RECOM_TIME,
                    CandidateRecomRecord.CANDIDATE_RECOM_RECORD.IS_RECOM,
                    CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                    CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID)
                    .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                    .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(UInteger.valueOf(presenteeId))
                            .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID.in(positionIdSet)));
            if(pageNo > 0 && pageSize > 0) {
                selectConditionStep.limit((pageNo-1)*pageSize, pageSize);
            }
            Result<CandidateRecomRecordRecord> result = selectConditionStep.fetch().into(CandidateRecomRecord.CANDIDATE_RECOM_RECORD);
            if(result != null && result.size() > 0) {
                candidateRecomRecordDOList = BeanUtils.DBToStruct(CandidateRecomRecordDO.class, result);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return candidateRecomRecordDOList;
    }

    public int countAppliedCandidateRecomRecord(int userId) {
        int count = 0;

        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            Result<Record1<Integer>> result = create
                    .select(countDistinct(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                            CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID))
                    .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                    .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(UInteger.valueOf(userId))
                            .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID.greaterThan(0))).fetch();
            if(result != null && result.size() > 0) {
                count = result.get(0).value1();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }

        return count;
    }

    public int countInterestedCandidateRecomRecord(int userId) {
        int count = 0;
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            Result<Record1<Integer>> result = create
                    .select(countDistinct(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                            CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID))
                    .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.leftJoin(CandidatePosition.CANDIDATE_POSITION)
                            .on(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID
                                    .equal(CandidatePosition.CANDIDATE_POSITION.USER_ID))
                            .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID
                                    .equal(CandidatePosition.CANDIDATE_POSITION.POSITION_ID)))
                    .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(UInteger.valueOf(userId))
                            .and(CandidatePosition.CANDIDATE_POSITION.IS_INTERESTED.equal((byte)1)))
                    .fetch();
            if(result != null && result.size() > 0) {
                count = result.get(0).value1();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return count;
    }

    public List<CandidateRecomRecordDO> listInterestedCandidateRecomRecord(int userId, int pageNo, int pageSize) {
        List<CandidateRecomRecordDO> candidateRecomRecordDOList = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

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
                    .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(UInteger.valueOf(userId))
                            .and(CandidatePosition.CANDIDATE_POSITION.IS_INTERESTED.equal((byte)1)))
                    .groupBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID, CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID);
            if(pageNo > 0 && pageSize > 0) {
                selectConditionStep.limit((pageNo-1)*pageSize, pageSize);
            }
            Result<CandidateRecomRecordRecord> result = selectConditionStep.fetch().into(CandidateRecomRecord.CANDIDATE_RECOM_RECORD);
            if(result != null && result.size() > 0) {
                candidateRecomRecordDOList = BeanUtils.DBToStruct(CandidateRecomRecordDO.class, result);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return candidateRecomRecordDOList;
    }

    public List<CandidateRecomRecordDO> listCandidateRecomRecord(int postUserId, String clickTime, List<Integer> recoms) {
        List<CandidateRecomRecordDO> candidateRecomRecordDOList = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

            DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
            DateTime dateTime = DateTime.parse(clickTime, format);
            Condition condition = CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(UInteger.valueOf(postUserId))
                    .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME.greaterOrEqual(new Timestamp(dateTime.getMillis())))
                    .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME.lessThan(new Timestamp(dateTime.plusDays(1).getMillis())));
            if(recoms != null && recoms.size() > 0) {
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
            if(result != null && result.size() > 0) {
                candidateRecomRecordDOList = BeanUtils.DBToStruct(CandidateRecomRecordDO.class, result);
            }

        } catch (SQLException e) {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.rollback();
                }
            } catch (SQLException e1) {
                logger.error(e1.getMessage(), e1);
            }
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return candidateRecomRecordDOList;
    }

    public List<CandidateRecomRecordDO> listCandidateRecomRecordExceptId(int id, int postUserId, String clickTime, List<Integer> recoms) {
        List<CandidateRecomRecordDO> candidateRecomRecordDOList = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

            DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
            DateTime dateTime = DateTime.parse(clickTime, format);
            Condition condition = CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(UInteger.valueOf(postUserId))
                    .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.ID.notEqual(UInteger.valueOf(id)))
                    .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME.greaterOrEqual(new Timestamp(dateTime.getMillis())))
                    .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME.lessThan(new Timestamp(dateTime.plusDays(1).getMillis())));
            if(recoms != null && recoms.size() > 0) {
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
            if(result != null && result.size() > 0) {
                candidateRecomRecordDOList = BeanUtils.DBToStruct(CandidateRecomRecordDO.class, result);
            }

        } catch (SQLException e) {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.rollback();
                }
            } catch (SQLException e1) {
                logger.error(e1.getMessage(), e1);
            }
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return candidateRecomRecordDOList;
    }

    public int countCandidateRecomRecordCustom(int postUserId, String clickTime, List<Integer> recoms) {
        int count = 0;
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

            DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
            DateTime dateTime = DateTime.parse(clickTime, format);
            Condition condition = CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(UInteger.valueOf(postUserId))
                    .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME.greaterOrEqual(new Timestamp(dateTime.getMillis())))
                    .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME.lessThan(new Timestamp(dateTime.plusDays(1).getMillis())));
            if(recoms != null && recoms.size() > 0) {
                condition = condition.and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.IS_RECOM.in(recoms));
            }

            Result<Record1<Integer>> result = create.selectCount().from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                    .where(condition)
                    .fetch();
            if(result != null && result.size() > 0) {
                count = (int) result.get(0).get(0);
            }

        } catch (SQLException e) {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.rollback();
                }
            } catch (SQLException e1) {
                logger.error(e1.getMessage(), e1);
            }
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return count;
    }

    public List<CandidateRecomRecordSortingDO> listCandidateRecomRecordSorting(List<Integer> postUserId) {
        List<CandidateRecomRecordSortingDO> recoms = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

            Field<Integer> count = count(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.ID);
            Result<Record2<Integer, UInteger>> result = create.select(count,
                    CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID)
                    .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                    .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.IS_RECOM.equal(0).and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.in(postUserId)))
                    .groupBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID)
                    .orderBy(count.desc())
                    .fetch();

            if (result != null && result.size() > 0) {
                for(Record2<Integer, UInteger> record2 : result) {
                    CandidateRecomRecordSortingDO candidateRecomRecordSortingDO = new CandidateRecomRecordSortingDO();
                    if(record2.get(0) != null) {
                        candidateRecomRecordSortingDO.setCount((Integer) record2.get(0));
                    }
                    if(record2.get(1) != null) {
                        candidateRecomRecordSortingDO.setPostUserId(((UInteger)record2.get(1)).intValue());
                    }
                    recoms.add(candidateRecomRecordSortingDO);
                }
            }
        } catch (SQLException e) {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.rollback();
                }
            } catch (SQLException e1) {
                logger.error(e1.getMessage(), e1);
            }
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return recoms;
    }

    public int countCandidateRecomRecordDistinctPresentee(int postUserId) {
        int count = 0;
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

            SelectConditionStep selectConditionStep = create
                    .select(countDistinct(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                            CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID))
                    .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                    .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(UInteger.valueOf(postUserId)));

            Result<Record1<Integer>> result = selectConditionStep.fetch();
            if(result != null && result.size() > 0) {
                count = (int) result.get(0).get(0);
            }

        } catch (SQLException e) {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.rollback();
                }
            } catch (SQLException e1) {
                logger.error(e1.getMessage(), e1);
            }
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return count;
    }

    public List<CandidateRecomRecordDO> listCandidateRecomRecordsForAppliedByUserPositions(int userId, List<Integer> positionIdList, int pageNo, int pageSize) {
        List<CandidateRecomRecordDO> candidateRecomRecordDOList = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
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
                                    .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(UInteger.valueOf(userId))
                                            .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID.greaterThan(0))
                                            .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID.in(positionIdList)))
                                    .groupBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                                            CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID)))
                    .orderBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.ID.desc());

            if(pageNo > 0 && pageSize > 0) {
                selectConditionStep.limit((pageNo-1)*pageSize, pageSize);
            }
            Result<CandidateRecomRecordRecord> result = selectConditionStep.fetch().into(CandidateRecomRecord.CANDIDATE_RECOM_RECORD);
            if(result != null && result.size() > 0) {
                candidateRecomRecordDOList = BeanUtils.DBToStruct(CandidateRecomRecordDO.class, result);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return candidateRecomRecordDOList;
    }

    public int countCandidateRecomRecordDistinctPresenteePosition(int postUserId, List<Integer> positionIdList) {
        int count = 0;
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

            SelectConditionStep selectConditionStep = create
                    .select(countDistinct(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                            CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID))
                    .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                    .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(UInteger.valueOf(postUserId))
                            .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID.in(positionIdList)));

            Result<Record1<Integer>> result = selectConditionStep.fetch();
            if(result != null && result.size() > 0) {
                count = (int) result.get(0).get(0);
            }

        } catch (SQLException e) {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.rollback();
                }
            } catch (SQLException e1) {
                logger.error(e1.getMessage(), e1);
            }
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return count;
    }

    public int countAppliedCandidateRecomRecordByUserPosition(int userId, List<Integer> positionIdList) {
        int count = 0;

        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            Result<Record1<Integer>> result = create
                    .select(countDistinct(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                            CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID))
                    .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                    .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(UInteger.valueOf(userId))
                            .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID.greaterThan(0))
                            .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID.in(positionIdList)))
                    .fetch();
            if(result != null && result.size() > 0) {
                count = result.get(0).value1();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }

        return count;
    }

    public int countInterestedCandidateRecomRecordByUserPosition(int userId, List<Integer> positionIdList) {
        int count = 0;
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            Result<Record1<Integer>> result = create
                    .select(countDistinct(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                            CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID))
                    .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.leftJoin(CandidatePosition.CANDIDATE_POSITION)
                            .on(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID
                                    .equal(CandidatePosition.CANDIDATE_POSITION.USER_ID))
                            .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID
                                    .equal(CandidatePosition.CANDIDATE_POSITION.POSITION_ID)))
                    .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(UInteger.valueOf(userId))
                            .and(CandidatePosition.CANDIDATE_POSITION.IS_INTERESTED.equal((byte)1))
                            .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID.in(positionIdList)))
                    .fetch();
            if(result != null && result.size() > 0) {
                count = result.get(0).value1();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return count;
    }

    public List<CandidateRecomRecordDO> listInterestedCandidateRecomRecordByUserPositions(int userId, List<Integer> positionIdList, int pageNo, int pageSize) {
        List<CandidateRecomRecordDO> candidateRecomRecordDOList = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

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
                                    .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(UInteger.valueOf(userId)))
                                    .and(CandidatePosition.CANDIDATE_POSITION.IS_INTERESTED.equal((byte)1))
                                    .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID.in(positionIdList))
                                    .groupBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                                            CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID)))
                    .orderBy(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.ID.desc());

            if(pageNo > 0 && pageSize > 0) {
                selectConditionStep.limit((pageNo-1)*pageSize, pageSize);
            }
            Result<CandidateRecomRecordRecord> result = selectConditionStep.fetch().into(CandidateRecomRecord.CANDIDATE_RECOM_RECORD);
            if(result != null && result.size() > 0) {
                candidateRecomRecordDOList = BeanUtils.DBToStruct(CandidateRecomRecordDO.class, result);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return candidateRecomRecordDOList;
    }
}