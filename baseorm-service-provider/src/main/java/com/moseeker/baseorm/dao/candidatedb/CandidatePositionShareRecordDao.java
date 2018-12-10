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

    /**
     * 查找最近浏览的职位信息
     *
     * @param beRecomUserIds 被推荐人userId
     * @param userId 转发职位的员工userId
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return list
     */
    public List<CandidatePositionShareRecordDO> fetchPositionShareByUserIds(Set<Integer> beRecomUserIds, int userId, Timestamp startTime, Timestamp endTime) {
        return create.selectFrom(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD)
                .where(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.CREATE_TIME.between(startTime, endTime))
                .and(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.PRESENTEE_USER_ID.in(beRecomUserIds))
                .and(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.RECOM_USER_ID.eq(userId))
                .and(CandidatePositionShareRecord.CANDIDATE_POSITION_SHARE_RECORD.SOURCE.eq((byte)0))
                .fetchInto(CandidatePositionShareRecordDO.class);
    }
}
