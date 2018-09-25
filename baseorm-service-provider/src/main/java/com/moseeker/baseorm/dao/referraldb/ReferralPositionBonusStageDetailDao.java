package com.moseeker.baseorm.dao.referraldb;

import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Date: 2018/9/25
 * @Author: JackYang
 */
public class ReferralPositionBonusStageDetailDao extends com.moseeker.baseorm.db.referraldb.tables.daos.ReferralPositionBonusStageDetailDao {

        @Autowired
        public ReferralPositionBonusStageDetailDao(Configuration configuration) {
            super(configuration);
        }

}

