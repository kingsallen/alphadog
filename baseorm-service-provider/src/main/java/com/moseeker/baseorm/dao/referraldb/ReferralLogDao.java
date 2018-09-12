package com.moseeker.baseorm.dao.referraldb;

import com.moseeker.baseorm.config.ClaimType;
import com.moseeker.baseorm.db.referraldb.tables.ReferralLog;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralLogRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import org.jooq.Configuration;
import org.jooq.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

import static org.jooq.impl.DSL.*;

/**
 * @Author: jack
 * @Date: 2018/9/6
 */
@Repository
public class ReferralLogDao extends com.moseeker.baseorm.db.referraldb.tables.daos.ReferralLogDao {

    @Autowired
    public ReferralLogDao(Configuration configuration) {
        super(configuration);
    }

    public int createReferralLog(int employeeId, int referenceId, int position, int referralType) {

        Param<Integer> employeeIdParam = param(ReferralLog.REFERRAL_LOG.EMPLOYEE_ID.getName(), employeeId);
        Param<Integer> referenceIdParam = param(ReferralLog.REFERRAL_LOG.REFERENCE_ID.getName(), referenceId);
        Param<Integer> positionIdParam = param(ReferralLog.REFERRAL_LOG.POSITION_ID.getName(), position);
        Param<Timestamp> referralTime = param(ReferralLog.REFERRAL_LOG.REFERRAL_TIME.getName(), new Timestamp(System.currentTimeMillis()));
        Param<Integer> referralTypeParam = param(ReferralLog.REFERRAL_LOG.TYPE.getName(), referralType);

        ReferralLogRecord referralLogRecord = using(configuration()).insertInto(
                ReferralLog.REFERRAL_LOG,
                ReferralLog.REFERRAL_LOG.EMPLOYEE_ID,
                ReferralLog.REFERRAL_LOG.REFERENCE_ID,
                ReferralLog.REFERRAL_LOG.POSITION_ID,
                ReferralLog.REFERRAL_LOG.REFERRAL_TIME,
                ReferralLog.REFERRAL_LOG.TYPE
        ).select(
                select(
                        employeeIdParam,
                        referenceIdParam,
                        positionIdParam,
                        referralTime,
                        referralTypeParam
                ).whereNotExists(
                        selectOne()
                        .from(ReferralLog.REFERRAL_LOG)
                        .where(ReferralLog.REFERRAL_LOG.EMPLOYEE_ID.eq(employeeId))
                        .and(ReferralLog.REFERRAL_LOG.REFERENCE_ID.eq(referenceId))
                        .and(ReferralLog.REFERRAL_LOG.POSITION_ID.eq(position))
                )
        ).returning().fetchOne();
        if (referralLogRecord != null) {
            return referralLogRecord.getId();
        } else {
            return 0;
        }
    }

    /**
     * 认领推荐记录
     * @param id 认领记录编号
     * @param userId 认领人
     * @return true 认领成功 false 认领失败
     */
    public boolean claim(int id, int userId) {
        int execute = using(configuration())
                .update(ReferralLog.REFERRAL_LOG)
                .set(ReferralLog.REFERRAL_LOG.REFERENCE_ID, userId)
                .set(ReferralLog.REFERRAL_LOG.CLAIM, ClaimType.Claimed.getValue())
                .set(ReferralLog.REFERRAL_LOG.CLAIM_TIME, new Timestamp(System.currentTimeMillis()))
                .where(ReferralLog.REFERRAL_LOG.ID.eq(id))
                .and(ReferralLog.REFERRAL_LOG.CLAIM.eq(ClaimType.UnClaim.getValue()))
                .execute();
        return execute == 1;
    }
}
