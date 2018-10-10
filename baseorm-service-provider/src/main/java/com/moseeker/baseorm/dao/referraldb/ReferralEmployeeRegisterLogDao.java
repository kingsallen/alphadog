package com.moseeker.baseorm.dao.referraldb;

import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeRegisterLog;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralEmployeeRegisterLogRecord;
import org.joda.time.DateTime;
import org.jooq.Configuration;
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


    /**
     * 查找员工取消认证或者认证的最新一条记录
     * @param employeeId
     * @param register
     * @return
     */
    public ReferralEmployeeRegisterLog getRegisterLogByEmployeeId(int employeeId, int register) {
        ReferralEmployeeRegisterLogRecord referralEmployeeRegisterLogRecord = using(configuration()).selectFrom(REFERRAL_EMPLOYEE_REGISTER_LOG).where(REFERRAL_EMPLOYEE_REGISTER_LOG.EMPLOYEE_ID.eq(employeeId)).
                and(REFERRAL_EMPLOYEE_REGISTER_LOG.REGISTER.eq((byte)register)).
                orderBy(REFERRAL_EMPLOYEE_REGISTER_LOG.ID.desc()).limit(1).fetchOne();

        if (referralEmployeeRegisterLogRecord != null) {
            return referralEmployeeRegisterLogRecord.into(com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeRegisterLog.class);
        } else {
            return null;
        }
    }

}
