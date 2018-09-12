package com.moseeker.baseorm.dao.referraldb;

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
}
