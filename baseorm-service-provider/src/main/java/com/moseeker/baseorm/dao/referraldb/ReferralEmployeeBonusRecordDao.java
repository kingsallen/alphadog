package com.moseeker.baseorm.dao.referraldb;

import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Date: 2018/9/27
 * @Author: JackYang
 */
@Repository
public class ReferralEmployeeBonusRecordDao extends com.moseeker.baseorm.db.referraldb.tables.daos.ReferralEmployeeBonusRecordDao {

    @Autowired
    public ReferralEmployeeBonusRecordDao(Configuration configuration) {
        super(configuration);
    }
}
