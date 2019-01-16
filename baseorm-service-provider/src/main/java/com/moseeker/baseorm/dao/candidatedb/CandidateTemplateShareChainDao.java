package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateTemplateShareChain;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateTemplateShareChainRecord;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateShareChainDO;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateTemplateShareChainDO;
import com.moseeker.thrift.gen.referral.struct.ReferralInviteInfo;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import static com.moseeker.baseorm.db.candidatedb.tables.CandidateTemplateShareChain.CANDIDATE_TEMPLATE_SHARE_CHAIN;

@Repository
public class CandidateTemplateShareChainDao extends JooqCrudImpl<CandidateTemplateShareChainDO, CandidateTemplateShareChainRecord> {


    public CandidateTemplateShareChainDao() {
        super(CANDIDATE_TEMPLATE_SHARE_CHAIN, CandidateTemplateShareChainDO.class);
    }

    public CandidateTemplateShareChainDao(TableImpl<CandidateTemplateShareChainRecord> table, Class<CandidateTemplateShareChainDO> candidateTemplateShareChainDOClass) {
        super(table, candidateTemplateShareChainDOClass);
    }


    public List<CandidateTemplateShareChainDO> getRadarCards(long timestamp) {
        return create.selectFrom(CANDIDATE_TEMPLATE_SHARE_CHAIN)
                .where(CANDIDATE_TEMPLATE_SHARE_CHAIN.SEND_TIME.eq(timestamp))
                .fetchInto(CandidateTemplateShareChainDO.class);
    }

    public void updateHandledRadarCardType(int rootUserId, int endUserId, int positionId, int type) {
        create.update(CANDIDATE_TEMPLATE_SHARE_CHAIN)
                .set(CANDIDATE_TEMPLATE_SHARE_CHAIN.TYPE, (byte)type)
                .where(CANDIDATE_TEMPLATE_SHARE_CHAIN.ROOT_USER_ID.eq(rootUserId))
                .and(CANDIDATE_TEMPLATE_SHARE_CHAIN.PRESENTEE_USER_ID.eq(endUserId))
                .and(CANDIDATE_TEMPLATE_SHARE_CHAIN.POSITION_ID.eq(positionId))
                .and(CANDIDATE_TEMPLATE_SHARE_CHAIN.TYPE.eq((byte)0))
                .execute();
    }

    public void updateTypeBySendTime(ReferralInviteInfo ignoreInfo, int type) {
        create.update(CANDIDATE_TEMPLATE_SHARE_CHAIN)
                .set(CANDIDATE_TEMPLATE_SHARE_CHAIN.TYPE, (byte)type)
                .where(CANDIDATE_TEMPLATE_SHARE_CHAIN.SEND_TIME.eq(ignoreInfo.getTimestamp()))
                .and(CANDIDATE_TEMPLATE_SHARE_CHAIN.ROOT_USER_ID.eq(ignoreInfo.getUserId()))
                .and(CANDIDATE_TEMPLATE_SHARE_CHAIN.POSITION_ID.eq(ignoreInfo.getPid()))
                .and(CANDIDATE_TEMPLATE_SHARE_CHAIN.PRESENTEE_USER_ID.eq(ignoreInfo.getEndUserId()))
                .execute();
    }

    public void updateRadarCardSeekRecomByChainId(int chainId, int seekReferralId) {
        create.update(CANDIDATE_TEMPLATE_SHARE_CHAIN)
                .set(CANDIDATE_TEMPLATE_SHARE_CHAIN.SEEK_REFERRAL_ID, seekReferralId)
                .where(CANDIDATE_TEMPLATE_SHARE_CHAIN.CHAIN_ID.eq(chainId))
                .and(CANDIDATE_TEMPLATE_SHARE_CHAIN.SEEK_REFERRAL_ID.eq(0))
                .execute();
    }

    public void updateHandledTypeByChainIds(List<Integer> shareChainIds, int type) {
        create.update(CANDIDATE_TEMPLATE_SHARE_CHAIN)
                .set(CANDIDATE_TEMPLATE_SHARE_CHAIN.TYPE, (byte)type)
                .where(CANDIDATE_TEMPLATE_SHARE_CHAIN.CHAIN_ID.in(shareChainIds))
                .and(CANDIDATE_TEMPLATE_SHARE_CHAIN.TYPE.eq((byte)0))
                .execute();
    }
}
