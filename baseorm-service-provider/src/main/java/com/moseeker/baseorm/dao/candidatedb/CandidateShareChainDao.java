package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateShareChain;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateShareChainRecord;
import com.moseeker.thrift.gen.dao.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.CandidateShareChainDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

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
}
