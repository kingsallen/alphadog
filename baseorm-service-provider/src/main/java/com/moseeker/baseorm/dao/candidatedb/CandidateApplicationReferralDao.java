package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateApplicationReferral;
import static com.moseeker.baseorm.db.candidatedb.tables.CandidateApplicationReferral.CANDIDATE_APPLICATION_REFERRAL;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateApplicationReferralRecord;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateApplicationPscDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by jack on 15/02/2017.
 */
@Repository
public class CandidateApplicationReferralDao extends JooqCrudImpl<CandidateApplicationReferralDO, CandidateApplicationReferralRecord> {

    public CandidateApplicationReferralDao() {
        super(CANDIDATE_APPLICATION_REFERRAL, CandidateApplicationReferralDO.class);
    }

    public CandidateApplicationReferralDao(TableImpl<CandidateApplicationReferralRecord> table, Class<CandidateApplicationReferralDO> candidateApplicationReferralDOClass) {
        super(table, candidateApplicationReferralDOClass);
    }

    /**
     * 查询申请的链路信息
     * @param applicationId
     * @return
     */
    public CandidateApplicationReferralDO getApplicationPscByApplication(int applicationId) {
        return create.selectFrom(CANDIDATE_APPLICATION_REFERRAL)
                .where(CANDIDATE_APPLICATION_REFERRAL.APPLICATION_ID.eq(applicationId))
                .fetchOneInto(CandidateApplicationPscDO.class);
    }

    public int addDataIgnoreDuplicate(int applicationId, int pscId){
        CandidateApplicationReferral T = CANDIDATE_APPLICATION_REFERRAL;
        return create.insertInto(T)
                .columns(T.APPLICATION_ID,T.PSC_ID)
                .values(applicationId, pscId)
                .onDuplicateKeyIgnore()
                .execute();
    }
}
