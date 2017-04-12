package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.db.candidatedb.tables.CandidatePosition;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateRecomRecord;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateRecomRecordRecord;
import com.moseeker.baseorm.db.userdb.tables.UserFavPosition;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.dao.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.CandidateRecomRecordDO;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
            SelectConditionStep selectConditionStep = create.select(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.ID,
                    CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID,
                    CandidateRecomRecord.CANDIDATE_RECOM_RECORD.REPOST_USER_ID,
                    CandidateRecomRecord.CANDIDATE_RECOM_RECORD.CLICK_TIME,
                    CandidateRecomRecord.CANDIDATE_RECOM_RECORD.RECOM_TIME,
                    CandidateRecomRecord.CANDIDATE_RECOM_RECORD.IS_RECOM,
                    CandidateRecomRecord.CANDIDATE_RECOM_RECORD.PRESENTEE_USER_ID,
                    CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POSITION_ID)
                    .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                    .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(UInteger.valueOf(userId))
                            .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.APP_ID.greaterThan(0)));
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
            Result<Record1<Integer>> result = create.selectCount()
                    .from(CandidateRecomRecord.CANDIDATE_RECOM_RECORD)
                    .where(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.POST_USER_ID.equal(UInteger.valueOf(userId))
                            .and(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.IS_RECOM.equal(0))).fetch();
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
            Result<Record1<Integer>> result = create.selectCount()
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

            SelectConditionStep selectConditionStep = create.select(CandidateRecomRecord.CANDIDATE_RECOM_RECORD.ID,
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
                            .and(CandidatePosition.CANDIDATE_POSITION.IS_INTERESTED.equal((byte)1)));
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