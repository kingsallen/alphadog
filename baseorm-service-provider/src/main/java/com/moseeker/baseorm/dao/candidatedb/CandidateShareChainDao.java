package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateShareChain;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateShareChainRecord;
import com.moseeker.thrift.gen.common.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateShareChainDO;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.jooq.impl.DSL.count;

/**
 * Created by jack on 15/02/2017.
 */
@Repository
public class CandidateShareChainDao extends JooqCrudImpl<CandidateShareChainDO, CandidateShareChainRecord> {

    public CandidateShareChainDao() {
        super(CandidateShareChain.CANDIDATE_SHARE_CHAIN, CandidateShareChainDO.class);
    }

    public CandidateShareChainDao(TableImpl<CandidateShareChainRecord> table, Class<CandidateShareChainDO> candidateShareChainDOClass) {
        super(table, candidateShareChainDOClass);
    }

    public void deleteCandidateShareChain(int id) throws CURDException {
        CandidateShareChainDO p = new CandidateShareChainDO();
        p.setId(id);
        this.deleteData(p);
    }

    public Result<Record2<Integer, Integer>> countEmployeeForward(List<Integer> userIdList, LocalDateTime lastFriday,
                                                                  LocalDateTime currentFriday) {
        return create.select(
                    CandidateShareChain.CANDIDATE_SHARE_CHAIN.ROOT_RECOM_USER_ID,
                    count(CandidateShareChain.CANDIDATE_SHARE_CHAIN.ID).as("count")
                )
                .from(CandidateShareChain.CANDIDATE_SHARE_CHAIN)
                .where(CandidateShareChain.CANDIDATE_SHARE_CHAIN.ROOT_RECOM_USER_ID.in(userIdList))
                .and(CandidateShareChain.CANDIDATE_SHARE_CHAIN.CLICK_TIME.gt(
                        new Timestamp(lastFriday.atZone(ZoneId.systemDefault()).toInstant()
                        .toEpochMilli())))
                .and(CandidateShareChain.CANDIDATE_SHARE_CHAIN.CLICK_TIME.le(
                        new Timestamp(currentFriday.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())))
                .and(CandidateShareChain.CANDIDATE_SHARE_CHAIN.PRESENTEE_USER_ID
                        .notEqual(CandidateShareChain.CANDIDATE_SHARE_CHAIN.RECOM_USER_ID))
                .groupBy(CandidateShareChain.CANDIDATE_SHARE_CHAIN.ROOT_RECOM_USER_ID)
                .fetch();
    }

    public Result<Record2<Integer,Integer>> countRepeatRecommend(List<Integer> userIdList, LocalDateTime lastFriday,
                                                                 LocalDateTime currentFriday) {
        return create.select(
                CandidateShareChain.CANDIDATE_SHARE_CHAIN.ROOT_RECOM_USER_ID,
                count(CandidateShareChain.CANDIDATE_SHARE_CHAIN.ID).as("count")
        )
                .from(CandidateShareChain.CANDIDATE_SHARE_CHAIN)
                .where(CandidateShareChain.CANDIDATE_SHARE_CHAIN.ROOT_RECOM_USER_ID.in(userIdList))
                .and(CandidateShareChain.CANDIDATE_SHARE_CHAIN.CLICK_TIME.gt(
                        new Timestamp(lastFriday.atZone(ZoneId.systemDefault()).toInstant()
                                .toEpochMilli())))
                .and(CandidateShareChain.CANDIDATE_SHARE_CHAIN.CLICK_TIME.le(
                        new Timestamp(currentFriday.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())))
                .and(CandidateShareChain.CANDIDATE_SHARE_CHAIN.PRESENTEE_USER_ID
                        .notEqual(CandidateShareChain.CANDIDATE_SHARE_CHAIN.RECOM_USER_ID))
                .groupBy(CandidateShareChain.CANDIDATE_SHARE_CHAIN.RECOM_USER_ID,
                        CandidateShareChain.CANDIDATE_SHARE_CHAIN.PRESENTEE_USER_ID)
                .fetch();
    }

}
