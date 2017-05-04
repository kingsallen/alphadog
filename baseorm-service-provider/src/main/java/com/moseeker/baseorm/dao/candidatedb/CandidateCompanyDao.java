package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateCompany;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateCompanyRecord;
import com.moseeker.thrift.gen.dao.struct.CandidateCompanyDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by jack on 15/02/2017.
 */
@Repository
public class CandidateCompanyDao extends JooqCrudImpl<CandidateCompanyDO, CandidateCompanyRecord> {

    public CandidateCompanyDao() {
        super(CandidateCompany.CANDIDATE_COMPANY, CandidateCompanyDO.class);
    }

    public CandidateCompanyDao(TableImpl<CandidateCompanyRecord> table, Class<CandidateCompanyDO> candidateCompanyDOClass) {
        super(table, candidateCompanyDOClass);
    }
}
