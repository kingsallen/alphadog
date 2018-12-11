package com.moseeker.baseorm.dao.referraldb;

import com.moseeker.baseorm.db.referraldb.tables.ReferralRecomEvaluation;
import com.moseeker.baseorm.db.referraldb.tables.ReferralSeekRecommend;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralRecomEvaluationRecord;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralSeekRecommendRecord;
import java.sql.Timestamp;
import java.util.List;
import org.jooq.Configuration;
import org.jooq.Param;
import static org.jooq.impl.DSL.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Author: jack
 * @Date: 2018/9/6
 */
@Repository
public class ReferralSeekRecommendDao extends com.moseeker.baseorm.db.referraldb.tables.daos.ReferralSeekRecommendDao {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ReferralSeekRecommendDao(Configuration configuration) {
        super(configuration);
    }

    public int insertIfNotExist(ReferralSeekRecommendRecord recomRecordRecord) {

        Timestamp now = new Timestamp(System.currentTimeMillis());
        Param<Integer> positionIdParam = param(ReferralSeekRecommend.REFERRAL_SEEK_RECOMMEND.POSITION_ID.getName(), recomRecordRecord.getPositionId());
        Param<Integer> presenteeUserIdParam = param(ReferralSeekRecommend.REFERRAL_SEEK_RECOMMEND.PRESENTEE_USER_ID.getName(), recomRecordRecord.getPresenteeUserId());
        Param<Integer> postUserIdParam = param(ReferralSeekRecommend.REFERRAL_SEEK_RECOMMEND.PRESENTEE_USER_ID.getName(), recomRecordRecord.getPostUserId());
        using(configuration()).insertInto(ReferralSeekRecommend.REFERRAL_SEEK_RECOMMEND,
                ReferralSeekRecommend.REFERRAL_SEEK_RECOMMEND.POSITION_ID,
                ReferralSeekRecommend.REFERRAL_SEEK_RECOMMEND.PRESENTEE_USER_ID,
                ReferralSeekRecommend.REFERRAL_SEEK_RECOMMEND.POST_USER_ID
        ).select(
                select(
                        positionIdParam,
                        presenteeUserIdParam,
                        postUserIdParam
                )
                        .whereNotExists(
                                selectOne()
                                        .from(ReferralSeekRecommend.REFERRAL_SEEK_RECOMMEND)
                                        .where(ReferralSeekRecommend.REFERRAL_SEEK_RECOMMEND.POST_USER_ID.eq(recomRecordRecord.getPostUserId()))
                                        .and(ReferralSeekRecommend.REFERRAL_SEEK_RECOMMEND.PRESENTEE_USER_ID.eq(recomRecordRecord.getPresenteeUserId()))
                                        .and(ReferralSeekRecommend.REFERRAL_SEEK_RECOMMEND.POSITION_ID.eq(recomRecordRecord.getPositionId()))
                        )
        ).execute();

        ReferralSeekRecommendRecord recommendRecord = using(configuration()).selectFrom(ReferralSeekRecommend.REFERRAL_SEEK_RECOMMEND)
                .where(ReferralSeekRecommend.REFERRAL_SEEK_RECOMMEND.POST_USER_ID.eq(recomRecordRecord.getPostUserId()))
                .and(ReferralSeekRecommend.REFERRAL_SEEK_RECOMMEND.PRESENTEE_USER_ID.eq(recomRecordRecord.getPresenteeUserId()))
                .and(ReferralSeekRecommend.REFERRAL_SEEK_RECOMMEND.POSITION_ID.eq(recomRecordRecord.getPositionId()))
                .orderBy(ReferralSeekRecommend.REFERRAL_SEEK_RECOMMEND.ID.desc())
                .limit(1)
                .fetchOne();

        return recommendRecord.getId();
    }

}
