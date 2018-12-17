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
                .where(REFERRAL_CONNECTION_LOG.ID.eq(chainId))
                .fetchOne();
    }

    public ReferralConnectionLogRecord fetchChainLogRecord(int userId, int endUserId, int positionId) {
        return create.selectFrom(REFERRAL_CONNECTION_LOG)
                .where(REFERRAL_CONNECTION_LOG.POSITION_ID.eq(positionId))
                .and(REFERRAL_CONNECTION_LOG.ROOT_USER_ID.eq(userId))
                .and(REFERRAL_CONNECTION_LOG.END_USER_ID.eq(endUserId))
                .fetchOne();
    }

    public void updateRecord(ReferralConnectionLogRecord connectionLogRecord) {
        create.execute("set names utf8mb4");
        create.attach(connectionLogRecord);
        create.executeUpdate(connectionLogRecord);
    }
}
