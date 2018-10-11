package com.moseeker.baseorm.dao.referraldb;

import org.jooq.Configuration;
import org.jooq.Record2;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moseeker.baseorm.db.jobdb.tables.JobPosition.JOB_POSITION;
import static com.moseeker.baseorm.db.referraldb.tables.ReferralRecomHbPosition.REFERRAL_RECOM_HB_POSITION;
import static org.jooq.impl.DSL.using;

/**
 * @Author: jack
 * @Date: 2018/10/11
 */
@Repository
public class ReferralRecomHbPositionDao extends com.moseeker.baseorm.db.referraldb.tables.daos.ReferralRecomHbPositionDao {

    @Autowired
    public ReferralRecomHbPositionDao(Configuration configuration) {
        super(configuration);
    }

    /**
     * 查找红包与职位名称
     * @param recomItemIdList 红包记录列表
     * @return 红包记录编号和职位名称的集合
     */
    public Result<Record2<Integer, String>> fetchPositionTitle(List<Integer> recomItemIdList) {
        return using(configuration())
                .select(REFERRAL_RECOM_HB_POSITION.HB_ITEM_ID, JOB_POSITION.TITLE)
                .from(REFERRAL_RECOM_HB_POSITION).innerJoin(JOB_POSITION)
                .on(REFERRAL_RECOM_HB_POSITION.POSITION_ID.eq(JOB_POSITION.ID))
                .where(REFERRAL_RECOM_HB_POSITION.HB_ITEM_ID.in(recomItemIdList))
                .fetch();
    }
}
