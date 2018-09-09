package com.moseeker.baseorm.db.referraldb.tables.daos;

import com.moseeker.baseorm.db.referraldb.tables.ReferralCompanyConf;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static org.jooq.impl.DSL.using;



/**
 * @Date: 2018/9/9
 * @Author: JackYang
 */
@Repository
public class ReferralCompanyConfJooqDao extends ReferralCompanyConfDao {

    @Autowired
    public ReferralCompanyConfJooqDao(Configuration configuration) {
        super(configuration);
    }


    public com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf findByCompnayId(Integer companyId){
        com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf result = using(configuration())
                .selectFrom(ReferralCompanyConf.REFERRAL_COMPANY_CONF)
                .where(ReferralCompanyConf.REFERRAL_COMPANY_CONF.COMPANY_ID.equal(companyId)).fetchOneInto(com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralCompanyConf.class);
       return result;
    }
}
