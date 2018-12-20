package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateShareChain;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateShareChainRecord;
import com.moseeker.thrift.gen.common.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateShareChainDO;
import com.moseeker.thrift.gen.referral.struct.ReferralInviteInfo;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
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

    public CandidateShareChainDO getCandidateShareChainById(int id){
        return create.selectFrom(CandidateShareChain.CANDIDATE_SHARE_CHAIN)
                .where(CandidateShareChain.CANDIDATE_SHARE_CHAIN.ID.eq(id))
                .fetchOneInto(CandidateShareChainDO.class);
    }

    public Result<Record2<Integer, Integer>> countEmployeeForward(List<Integer> userIdList,
                                                                  List<Integer> positionIdList,
                                                                  LocalDateTime lastFriday,
                                                                  LocalDateTime currentFriday) {
        return create.select(
                    CandidateShareChain.CANDIDATE_SHARE_CHAIN.ROOT_RECOM_USER_ID,
                    count().as("count")
                )
                .from(CandidateShareChain.CANDIDATE_SHARE_CHAIN)
                .where(CandidateShareChain.CANDIDATE_SHARE_CHAIN.ROOT_RECOM_USER_ID.in(userIdList))
                .and(CandidateShareChain.CANDIDATE_SHARE_CHAIN.POSITION_ID.in(positionIdList))
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

    public Result<Record2<Integer,Integer>> countRepeatRecommend(List<Integer> userIdList, List<Integer> positionIdList,
                                                                 LocalDateTime lastFriday,
                                                                 LocalDateTime currentFriday) {
        return create.select(
                CandidateShareChain.CANDIDATE_SHARE_CHAIN.ROOT_RECOM_USER_ID,
                count()
        )
                .from(CandidateShareChain.CANDIDATE_SHARE_CHAIN)
                .where(CandidateShareChain.CANDIDATE_SHARE_CHAIN.ROOT_RECOM_USER_ID.in(userIdList))
                .and(CandidateShareChain.CANDIDATE_SHARE_CHAIN.POSITION_ID.in(positionIdList))
                .and(CandidateShareChain.CANDIDATE_SHARE_CHAIN.CLICK_TIME.gt(
                        new Timestamp(lastFriday.atZone(ZoneId.systemDefault()).toInstant()
                                .toEpochMilli())))
                .and(CandidateShareChain.CANDIDATE_SHARE_CHAIN.CLICK_TIME.le(
                        new Timestamp(currentFriday.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())))
                .and(CandidateShareChain.CANDIDATE_SHARE_CHAIN.PRESENTEE_USER_ID
                        .notEqual(CandidateShareChain.CANDIDATE_SHARE_CHAIN.RECOM_USER_ID))
                .groupBy(CandidateShareChain.CANDIDATE_SHARE_CHAIN.PRESENTEE_USER_ID,
                        CandidateShareChain.CANDIDATE_SHARE_CHAIN.POSITION_ID,
                        CandidateShareChain.CANDIDATE_SHARE_CHAIN.ROOT_RECOM_USER_ID)
                .having(count().gt(1))
                .fetch();
    }

    public List<CandidateShareChainDO> getRadarCards(int rootUserId, Timestamp startTime, Timestamp endTime) {
        List<CandidateShareChainDO> list = create.selectFrom(CandidateShareChain.CANDIDATE_SHARE_CHAIN)
                .where(CandidateShareChain.CANDIDATE_SHARE_CHAIN.ROOT_RECOM_USER_ID.eq(rootUserId))
                .and(CandidateShareChain.CANDIDATE_SHARE_CHAIN.CLICK_TIME.between(startTime, endTime))
                .and(CandidateShareChain.CANDIDATE_SHARE_CHAIN.DEPTH.ne(0))
                .orderBy(CandidateShareChain.CANDIDATE_SHARE_CHAIN.DEPTH)
                .fetchInto(CandidateShareChainDO.class);
        if(list == null){
            return new ArrayList<>();
        }else {
            return list;
        }
    }

    public CandidateShareChainDO getRecordById(int parentChainId) {
        return create.selectFrom(CandidateShareChain.CANDIDATE_SHARE_CHAIN)
                .where(CandidateShareChain.CANDIDATE_SHARE_CHAIN.ID.eq(parentChainId))
                .fetchOneInto(CandidateShareChainDO.class);
    }

    public List<CandidateShareChainDO> getRadarCards(ReferralInviteInfo inviteInfo) {
        Timestamp tenMinite = new Timestamp(inviteInfo.getTimestamp());
        Timestamp beforeTenMinite = new Timestamp(inviteInfo.getTimestamp() - 1000 * 60 * 10);
        List<CandidateShareChainDO> list = create.selectFrom(CandidateShareChain.CANDIDATE_SHARE_CHAIN)
                .where(CandidateShareChain.CANDIDATE_SHARE_CHAIN.ROOT_RECOM_USER_ID.eq(inviteInfo.getUserId()))
                .and(CandidateShareChain.CANDIDATE_SHARE_CHAIN.POSITION_ID.eq(inviteInfo.getPid()))
                .and(CandidateShareChain.CANDIDATE_SHARE_CHAIN.CLICK_TIME.between(beforeTenMinite, tenMinite))
                .and(CandidateShareChain.CANDIDATE_SHARE_CHAIN.DEPTH.ne(0))
                .orderBy(CandidateShareChain.CANDIDATE_SHARE_CHAIN.DEPTH)
                .fetchInto(CandidateShareChainDO.class);
        if(list == null){
            return new ArrayList<>();
        }else {
            return list;
        }
    }


}
