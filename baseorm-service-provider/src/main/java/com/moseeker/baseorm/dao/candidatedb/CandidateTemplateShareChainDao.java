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

    public void updateHandledRadarCardTypeByIds(ReferralInviteInfo inviteInfo, int type) {
        create.update(CANDIDATE_TEMPLATE_SHARE_CHAIN)
                .set(CANDIDATE_TEMPLATE_SHARE_CHAIN.TYPE, (byte)type)
                .where(CANDIDATE_TEMPLATE_SHARE_CHAIN.ROOT_USER_ID.eq(inviteInfo.getUserId()))
                .and(CANDIDATE_TEMPLATE_SHARE_CHAIN.PRESENTEE_USER_ID.eq(inviteInfo.getEndUserId()))
                .and(CANDIDATE_TEMPLATE_SHARE_CHAIN.POSITION_ID.eq(inviteInfo.getPid()))
                .and(CANDIDATE_TEMPLATE_SHARE_CHAIN.TYPE.eq((byte)0))
                .execute();
    }
}
