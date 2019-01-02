package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.candidatedb.tables.CandidatePositionShareRecord;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidatePositionShareRecordRecord;
import com.moseeker.thrift.gen.common.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidatePositionShareRecordDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * Created by jack on 15/02/2017.
 */
@Repository
public class CandidatePositionShareRecordDao extends JooqCrudImpl<CandidatePositionShareRecordDO, CandidatePositionShareRecordRecord> {


    public CandidatePositionShareRecordDao() {
        super(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD, CandidatePositionShareRecordDO.class);
    }

    public CandidatePositionShareRecordDao(TableImpl<CandidatePositionShareRecordRecord> table, Class<CandidatePositionShareRecordDO> candidatePositionShareRecordDOClass) {
        super(table, candidatePositionShareRecordDOClass);
    }

    public void deleteCandidatePositionShareRecord(int id) throws CURDException {
        CandidatePositionShareRecordRecord p = new CandidatePositionShareRecordRecord();
        p.setId(id);
        this.deleteRecord(p);
    }

    public List<CandidatePositionShareRecordDO> fetchPositionShareByShareChainIds(Set<Integer> shareChainIds) {
        return create.selectFrom(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD)
                .where(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.SHARE_CHAIN_ID.in(shareChainIds))
                .groupBy(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.SHARE_CHAIN_ID)
                .orderBy(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.CREATE_TIME.desc())
                .fetchInto(CandidatePositionShareRecordDO.class);

    }
}
