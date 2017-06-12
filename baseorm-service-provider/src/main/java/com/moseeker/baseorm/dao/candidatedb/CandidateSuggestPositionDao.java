package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateSuggestPosition;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateSuggestPositionRecord;
import com.moseeker.thrift.gen.dao.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.CandidateSuggestPositionDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by jack on 15/02/2017.
 */
@Repository
public class CandidateSuggestPositionDao extends JooqCrudImpl<CandidateSuggestPositionDO, CandidateSuggestPositionRecord> {

    public CandidateSuggestPositionDao() {
        super(CandidateSuggestPosition.CANDIDATE_SUGGEST_POSITION, CandidateSuggestPositionDO.class);
    }

    public CandidateSuggestPositionDao(TableImpl<CandidateSuggestPositionRecord> table, Class<CandidateSuggestPositionDO> candidateSuggestPositionDOClass) {
        super(table, candidateSuggestPositionDOClass);
    }

    public void deleteCandidateShareChain(int id) throws CURDException {
        CandidateSuggestPositionDO p = new CandidateSuggestPositionDO();
        p.setId(id);
        this.deleteData(p);
    }
}
