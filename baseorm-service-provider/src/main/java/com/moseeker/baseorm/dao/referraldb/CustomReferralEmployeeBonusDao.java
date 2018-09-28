package com.moseeker.baseorm.dao.referraldb;

import com.moseeker.baseorm.db.referraldb.tables.daos.ReferralEmployeeBonusRecordDao;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Author: jack
 * @Date: 2018/9/28
 */
@Repository
public class CustomReferralEmployeeBonusDao extends ReferralEmployeeBonusRecordDao {

    @Autowired
    public CustomReferralEmployeeBonusDao(Configuration configuration) {
        super(configuration);
    }


}
