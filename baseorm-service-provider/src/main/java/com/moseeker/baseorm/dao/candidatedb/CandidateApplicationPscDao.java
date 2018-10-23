package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateApplicationPsc;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateApplicationPscRecord;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateApplicationPscDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by jack on 15/02/2017.
 */
@Repository
public class CandidateApplicationPscDao extends JooqCrudImpl<CandidateApplicationPscDO, CandidateApplicationPscRecord> {

    public CandidateApplicationPscDao() {
        super(CandidateApplicationPsc.CANDIDATE_APPLICATION_PSC, CandidateApplicationPscDO.class);
    }

    public CandidateApplicationPscDao(TableImpl<CandidateApplicationPscRecord> table, Class<CandidateApplicationPscDO> candidateApplicationPscDOClass) {
        super(table, candidateApplicationPscDOClass);
    }

    /**
     * 查询申请的链路信息
     * @param applicationId
     * @return
     */
    public CandidateApplicationPscDO getApplicationPscByApplication(int applicationId) {
        return create.selectFrom(CandidateApplicationPsc.CANDIDATE_APPLICATION_PSC)
                .where(CandidateApplicationPsc.CANDIDATE_APPLICATION_PSC.APPLICATION_ID.eq(applicationId))
                .fetchOneInto(CandidateApplicationPscDO.class);
    }

    public int addDataIgnoreDuplicate(int applicationId, int pscId){
        CandidateApplicationPsc T = CandidateApplicationPsc.CANDIDATE_APPLICATION_PSC;
        return create.insertInto(T)
                .columns(T.APPLICATION_ID,T.PSC_ID)
                .values(applicationId, pscId)
                .onDuplicateKeyIgnore()
                .execute();
    }
}
