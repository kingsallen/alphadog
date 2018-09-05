package com.moseeker.baseorm.dao.referraldb;

import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Author: jack
 * @Date: 2018/9/5
 */
@Repository
public class EmployeeReferralRecordDao extends com.moseeker.baseorm.db.referraldb.tables.daos.EmployeeReferralRecordDao {

    @Autowired
    public EmployeeReferralRecordDao(Configuration configuration) {
        super(configuration);
    }


}
