package com.moseeker.baseorm.dao.referraldb;

import org.jooq.Configuration;
import org.springframework.stereotype.Service;

/**
 * @Date: 2018/9/4
 * @Author: JackYang
 */

@Service
public class HistoryReferralPositionRelDao extends com.moseeker.baseorm.db.referraldb.tables.daos.HistoryReferralPositionRelDao {

    public HistoryReferralPositionRelDao(Configuration configuration) {
        super(configuration);
    }
}
