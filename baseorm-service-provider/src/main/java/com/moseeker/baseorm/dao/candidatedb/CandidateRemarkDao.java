package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.db.candidatedb.tables.CandidateRemark;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidateRemarkRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.CandidateRemarkDO;
import org.springframework.stereotype.Component;

/**
 * Created by jack on 15/02/2017.
 */
@Component
public class CandidateRemarkDao extends StructDaoImpl<CandidateRemarkDO, CandidateRemarkRecord, CandidateRemark> {


    @Override
    protected void initJOOQEntity() {
        this.tableLike = CandidateRemark.CANDIDATE_REMARK;
    }

    public void deleteCandidateRemark(int id) throws CURDException {
        CandidateRemarkDO p = new CandidateRemarkDO();
        p.setId(id);
        this.deleteResource(p);
    }
}
