package com.moseeker.baseorm.dao.referraldb;

import com.moseeker.baseorm.db.referraldb.tables.ReferralRecomEvaluation;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralRecomEvaluationRecord;
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
public class ReferralRecomEvaluationDao extends com.moseeker.baseorm.db.referraldb.tables.daos.ReferralRecomEvaluationDao {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ReferralRecomEvaluationDao(Configuration configuration) {
        super(configuration);
    }

    public int insertIfNotExist(ReferralRecomEvaluationRecord recomRecordRecord) {

        Timestamp now = new Timestamp(System.currentTimeMillis());
        Param<Integer> applicationIdParam = param(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.APP_ID.getName(), recomRecordRecord.getAppId());
        Param<String> mobileParam = param(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.MOBILE.getName(), recomRecordRecord.getMobile());
        Param<Integer> positionIdParam = param(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.POSITION_ID.getName(), recomRecordRecord.getPositionId());
        Param<Integer> presenteeUserIdParam = param(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.PRESENTEE_USER_ID.getName(), recomRecordRecord.getPresenteeUserId());
        Param<Integer> postUserIdParam = param(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.PRESENTEE_USER_ID.getName(), recomRecordRecord.getPostUserId());
        Param<String> recomReasonParam = param(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.RECOM_REASON_TAG.getName(), recomRecordRecord.getRecomReasonTag());
        Param<Byte> relationshipParam = param(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.RELATIONSHIP.getName(), recomRecordRecord.getRelationship());
        Param<String> recomReasonTextParam = param(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.RECOM_REASON_TEXT.getName(), recomRecordRecord.getRecomReasonText());
        using(configuration()).insertInto(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION,
                ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.APP_ID,
                ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.POSITION_ID,
                ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.MOBILE,
                ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.PRESENTEE_USER_ID,
                ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.POST_USER_ID,
                ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.RECOM_REASON_TAG,
                ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.RELATIONSHIP,
                ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.RECOM_REASON_TEXT
        ).select(
                select(
                        applicationIdParam,
                        positionIdParam,
                        mobileParam,
                        presenteeUserIdParam,
                        postUserIdParam,
                        recomReasonParam,
                        relationshipParam,
                        recomReasonTextParam
                )
                        .whereNotExists(
                                selectOne()
                                        .from(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION)
                                        .where(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.POST_USER_ID.eq(recomRecordRecord.getPostUserId()))
                                        .and(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.PRESENTEE_USER_ID.eq(recomRecordRecord.getPresenteeUserId()))
                                        .and(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.POSITION_ID.eq(recomRecordRecord.getPositionId()))
                        )
        ).execute();

        ReferralRecomEvaluationRecord evaluationRecord = using(configuration()).selectFrom(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION)
                .where(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.POST_USER_ID.eq(recomRecordRecord.getPostUserId()))
                .and(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.PRESENTEE_USER_ID.eq(recomRecordRecord.getPresenteeUserId()))
                .and(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.POSITION_ID.eq(recomRecordRecord.getPositionId()))
                .orderBy(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.ID.desc())
                .limit(1)
                .fetchOne();

        return evaluationRecord.getId();
    }

    /**
     * 修改推荐人
     * @param postUserId 历史推荐人
     * @param positionId 职位编号
     * @param referenceId 被推荐人
     * @param id 新推荐人
     */
    public void changePostUserId(int postUserId, int positionId, Integer referenceId, int id) {
        using(configuration()).update(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION)
                .set(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.PRESENTEE_USER_ID, id)
                .where(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.POST_USER_ID.eq(postUserId))
                .and(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.PRESENTEE_USER_ID.eq(referenceId))
                .and(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.POSITION_ID.eq(positionId))
                .andNotExists(
                        selectOne()
                        .from(
                                selectFrom(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION)
                                .where(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.POST_USER_ID.eq(positionId))
                                .and(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.POST_USER_ID.eq(postUserId))
                                .and(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.PRESENTEE_USER_ID.eq(id))
                        )
                )
                .execute();
    }


    public List<ReferralRecomEvaluationRecord> getEvaluationListByUserId(int userId, List<Integer> appidList){
        List<ReferralRecomEvaluationRecord> evaluationRecords = using(configuration())
                .selectFrom(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION)
                .where(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.APP_ID.in(appidList))
                .and(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.PRESENTEE_USER_ID.eq(userId))
                .fetch();
        return evaluationRecords;
    }

    public  ReferralRecomEvaluationRecord fetchByPostPresenteePosition(Integer postUserId, Integer presenteeUserId, Integer positionId) {
        ReferralRecomEvaluationRecord evaluationRecord = using(configuration()).selectFrom(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION)
                .where(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.POST_USER_ID.eq(postUserId))
                .and(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.PRESENTEE_USER_ID.eq(presenteeUserId))
                .and(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.POSITION_ID.eq(positionId))
                .orderBy(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.ID.desc())
                .limit(1)
                .fetchOne();
        return evaluationRecord;
    }

    public List<com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralRecomEvaluation> fetchEvaluationRecordsByAppids(int postUserId, List<Integer> applierIds, List<Integer> pids) {
        return using(configuration())
                .selectFrom(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION)
                .where(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.POST_USER_ID.eq(postUserId))
                .and(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.PRESENTEE_USER_ID.in(applierIds))
                .and(ReferralRecomEvaluation.REFERRAL_RECOM_EVALUATION.POSITION_ID.in(pids))
                .fetchInto(com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralRecomEvaluation.class);
    }
}
