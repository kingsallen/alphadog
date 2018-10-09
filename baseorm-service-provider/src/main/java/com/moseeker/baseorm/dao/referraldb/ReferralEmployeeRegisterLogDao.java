package com.moseeker.baseorm.dao.referraldb;

import com.moseeker.baseorm.db.referraldb.tables.records.ReferralEmployeeRegisterLogRecord;
import org.joda.time.DateTime;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

import static com.moseeker.baseorm.db.referraldb.tables.ReferralEmployeeRegisterLog.REFERRAL_EMPLOYEE_REGISTER_LOG;
import static org.jooq.impl.DSL.using;

/**
 * @Author: jack
 * @Date: 2018/10/9
 */
@Repository
public class ReferralEmployeeRegisterLogDao extends com.moseeker.baseorm.db.referraldb.tables.daos.ReferralEmployeeRegisterLogDao {

    @Autowired
    public ReferralEmployeeRegisterLogDao(Configuration configuration) {
        super(configuration);
    }

    public int addRegisterLog(int employeeId, DateTime currentTime) {

        ReferralEmployeeRegisterLogRecord referralEmployeeRegisterLogRecord = using(configuration())
                .insertInto(REFERRAL_EMPLOYEE_REGISTER_LOG)
                .columns(REFERRAL_EMPLOYEE_REGISTER_LOG.EMPLOYEE_ID, REFERRAL_EMPLOYEE_REGISTER_LOG.REGISTER,
                        REFERRAL_EMPLOYEE_REGISTER_LOG.OPERATE_TIME)
                .values(employeeId, (byte)1, new Timestamp(currentTime.getMillis()))
                .returning()
                .fetchOne();
        return referralEmployeeRegisterLogRecord == null?0:referralEmployeeRegisterLogRecord.getId();
    }

    public int addCancelLog(int employeeId, DateTime currentTime) {
        ReferralEmployeeRegisterLogRecord referralEmployeeRegisterLogRecord = using(configuration())
                .insertInto(REFERRAL_EMPLOYEE_REGISTER_LOG)
                .columns(REFERRAL_EMPLOYEE_REGISTER_LOG.EMPLOYEE_ID, REFERRAL_EMPLOYEE_REGISTER_LOG.REGISTER,
                        REFERRAL_EMPLOYEE_REGISTER_LOG.OPERATE_TIME)
                .values(employeeId, (byte)0, new Timestamp(currentTime.getMillis()))
                .returning()
                .fetchOne();
        return referralEmployeeRegisterLogRecord == null ? 0 : referralEmployeeRegisterLogRecord.getId();
    }
}
