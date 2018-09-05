package com.moseeker.baseorm.dao.referraldb;

import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Date: 2018/9/4
 * @Author: JackYang
 */

@Service
public class HistoryReferralPositionRelDao extends com.moseeker.baseorm.db.referraldb.tables.daos.HistoryReferralPositionRelDao {

    @Autowired
    public HistoryReferralPositionRelDao(Configuration configuration) {
        super(configuration);
    }
}
