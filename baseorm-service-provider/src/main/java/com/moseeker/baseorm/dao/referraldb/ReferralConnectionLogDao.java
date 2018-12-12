package com.moseeker.baseorm.dao.referraldb;

import com.moseeker.baseorm.db.referraldb.tables.records.ReferralConnectionLogRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.moseeker.baseorm.db.referraldb.tables.ReferralConnectionLog.REFERRAL_CONNECTION_LOG;
/**
 * @author cjm
 * @date 2018-12-12 16:26
 **/
@Repository
public class ReferralConnectionLogDao {

    @Autowired
    private DSLContext create;

    public ReferralConnectionLogRecord insertRecord(ReferralConnectionLogRecord connectionLogRecord) {
        return create.insertInto(REFERRAL_CONNECTION_LOG)
                .values(connectionLogRecord)
        .returning()
        .fetchOne();
    }

    public ReferralConnectionLogRecord fetchByChainId(int chainId) {
        return create.selectFrom(REFERRAL_CONNECTION_LOG)
                .where(REFERRAL_CONNECTION_LOG.CHAIN_ID.eq(chainId))
                .fetchOne();
    }
}
