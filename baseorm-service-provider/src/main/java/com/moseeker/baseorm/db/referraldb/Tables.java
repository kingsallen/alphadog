/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.referraldb;


import com.moseeker.baseorm.db.referraldb.tables.EmployeeReferralRecord;
import com.moseeker.baseorm.db.referraldb.tables.HistoryReferralPositionRel;
import com.moseeker.baseorm.db.referraldb.tables.ReferralCompanyConf;
import com.moseeker.baseorm.db.referraldb.tables.ReferralLog;
import com.moseeker.baseorm.db.referraldb.tables.ReferralPositionRel;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in referraldb
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * 员工内推记录
     */
    public static final EmployeeReferralRecord EMPLOYEE_REFERRAL_RECORD = com.moseeker.baseorm.db.referraldb.tables.EmployeeReferralRecord.EMPLOYEE_REFERRAL_RECORD;

    /**
     * The table <code>referraldb.history_referral_position_rel</code>.
     */
    public static final HistoryReferralPositionRel HISTORY_REFERRAL_POSITION_REL = com.moseeker.baseorm.db.referraldb.tables.HistoryReferralPositionRel.HISTORY_REFERRAL_POSITION_REL;

    /**
     * The table <code>referraldb.referral_company_conf</code>.
     */
    public static final ReferralCompanyConf REFERRAL_COMPANY_CONF = com.moseeker.baseorm.db.referraldb.tables.ReferralCompanyConf.REFERRAL_COMPANY_CONF;

    /**
     * 内推记录
     */
    public static final ReferralLog REFERRAL_LOG = com.moseeker.baseorm.db.referraldb.tables.ReferralLog.REFERRAL_LOG;

    /**
     * The table <code>referraldb.referral_position_rel</code>.
     */
    public static final ReferralPositionRel REFERRAL_POSITION_REL = com.moseeker.baseorm.db.referraldb.tables.ReferralPositionRel.REFERRAL_POSITION_REL;
}
