package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateRemark;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateRemarkRecord;
import com.moseeker.thrift.gen.dao.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateRemarkDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by jack on 15/02/2017.
 */
@Repository
public class CandidateRemarkDao extends JooqCrudImpl<CandidateRemarkDO, CandidateRemarkRecord> {


    public CandidateRemarkDao() {
        super(CandidateRemark.CANDIDATE_REMARK, CandidateRemarkDO.class);
    }

    public CandidateRemarkDao(TableImpl<CandidateRemarkRecord> table, Class<CandidateRemarkDO> candidateRemarkDOClass) {
        super(table, candidateRemarkDOClass);
    }

    public void deleteCandidateRemark(int id) throws CURDException {
        CandidateRemarkDO p = new CandidateRemarkDO();
        p.setId(id);
        this.deleteData(p);
    }
}
