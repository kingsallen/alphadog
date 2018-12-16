package com.moseeker.baseorm.dao.referraldb;

import com.moseeker.baseorm.db.referraldb.tables.ReferralCompanyConf;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralCompanyConfRecord;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static org.jooq.impl.DSL.using;

/**
 * @Author: jack
 * @Date: 2018/9/8
 */
@Repository
public class ReferralCompanyConfDao extends com.moseeker.baseorm.db.referraldb.tables.daos.ReferralCompanyConfDao {

    @Autowired
    public ReferralCompanyConfDao(Configuration configuration) {
        super(configuration);
    }

    public com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf fetchOneByCompanyId(int companyId) {

        ReferralCompanyConfRecord record = using(configuration())
                .selectFrom(ReferralCompanyConf.REFERRAL_COMPANY_CONF)
                .where(ReferralCompanyConf.REFERRAL_COMPANY_CONF.COMPANY_ID.eq(companyId))
                .fetchOne();
        if (record != null) {
            return record.into(com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf.class);
        } else {
            return null;
        }
    }

    public int insertReferralCompanyConf(com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf companyConf) {

        int record = using(configuration()).insertInto(ReferralCompanyConf.REFERRAL_COMPANY_CONF)
                .columns(ReferralCompanyConf.REFERRAL_COMPANY_CONF.COMPANY_ID, ReferralCompanyConf.REFERRAL_COMPANY_CONF.REFERRAL_KEY_INFORMATION)
                .values(companyConf.getCompanyId(), companyConf.getReferralKeyInformation())
                .onDuplicateKeyUpdate()
                .set(ReferralCompanyConf.REFERRAL_COMPANY_CONF.REFERRAL_KEY_INFORMATION, companyConf.getReferralKeyInformation())
                .execute();
        return record;
    }
}
