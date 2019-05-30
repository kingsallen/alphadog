package com.moseeker.baseorm.dao.referraldb;

import com.moseeker.baseorm.db.referraldb.tables.records.ReferralSeekRecommendRecord;
import org.jooq.Configuration;
import org.jooq.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.moseeker.baseorm.db.referraldb.tables.ReferralSeekRecommend.REFERRAL_SEEK_RECOMMEND;
import static org.jooq.impl.DSL.*;

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

    public ReferralSeekRecommendRecord insertIfNotExist(ReferralSeekRecommendRecord recomRecordRecord) {

        Timestamp now = new Timestamp(System.currentTimeMillis());
        Param<Integer> positionIdParam = param(REFERRAL_SEEK_RECOMMEND.POSITION_ID.getName(), recomRecordRecord.getPositionId());
        Param<Integer> presenteeUserIdParam = param(REFERRAL_SEEK_RECOMMEND.PRESENTEE_ID.getName(), recomRecordRecord.getPresenteeId());
        Param<Integer> postUserIdParam = param(REFERRAL_SEEK_RECOMMEND.PRESENTEE_ID.getName(), recomRecordRecord.getPostUserId());
        Param<Integer> originParam = param(REFERRAL_SEEK_RECOMMEND.ORIGIN.getName(),recomRecordRecord.getOrigin());
        using(configuration()).insertInto(REFERRAL_SEEK_RECOMMEND,
                REFERRAL_SEEK_RECOMMEND.POSITION_ID,
                REFERRAL_SEEK_RECOMMEND.PRESENTEE_ID,
                REFERRAL_SEEK_RECOMMEND.POST_USER_ID,
                REFERRAL_SEEK_RECOMMEND.ORIGIN
        ).select(
                select(
                        positionIdParam,
                        presenteeUserIdParam,
                        postUserIdParam,
                        originParam
                )
                        .whereNotExists(
                                selectOne()
                                        .from(REFERRAL_SEEK_RECOMMEND)
                                        .where(REFERRAL_SEEK_RECOMMEND.POST_USER_ID.eq(recomRecordRecord.getPostUserId()))
                                        .and(REFERRAL_SEEK_RECOMMEND.PRESENTEE_ID.eq(recomRecordRecord.getPresenteeId()))
                                        .and(REFERRAL_SEEK_RECOMMEND.POSITION_ID.eq(recomRecordRecord.getPositionId()))
                                        .and(REFERRAL_SEEK_RECOMMEND.ORIGIN.eq(recomRecordRecord.getOrigin()))
                        )
        ).execute();

        ReferralSeekRecommendRecord recommendRecord = using(configuration()).selectFrom(REFERRAL_SEEK_RECOMMEND)
                .where(REFERRAL_SEEK_RECOMMEND.POST_USER_ID.eq(recomRecordRecord.getPostUserId()))
                .and(REFERRAL_SEEK_RECOMMEND.PRESENTEE_ID.eq(recomRecordRecord.getPresenteeId()))
                .and(REFERRAL_SEEK_RECOMMEND.POSITION_ID.eq(recomRecordRecord.getPositionId()))
                .orderBy(REFERRAL_SEEK_RECOMMEND.ID.desc())
                .limit(1)
                .fetchOne();

        return recommendRecord;
    }


    public ReferralSeekRecommendRecord fetchByIdAndPostUserId(int referralId, int postUserId){
        return using(configuration()).selectFrom(REFERRAL_SEEK_RECOMMEND)
                .where(REFERRAL_SEEK_RECOMMEND.ID.eq(referralId))
                .and(REFERRAL_SEEK_RECOMMEND.POST_USER_ID.eq(postUserId))
                .fetchOneInto(ReferralSeekRecommendRecord.class);

    }

    public ReferralSeekRecommendRecord getById(int referralId){
        return using(configuration()).selectFrom(REFERRAL_SEEK_RECOMMEND)
                .where(REFERRAL_SEEK_RECOMMEND.ID.eq(referralId))
                .fetchOneInto(ReferralSeekRecommendRecord.class);

    }

    public List<ReferralSeekRecommendRecord> fetchSeekRecommendByPostUserId(int postUserId, List<Integer> positionIdList){
        return using(configuration()).selectFrom(REFERRAL_SEEK_RECOMMEND)
                .where(REFERRAL_SEEK_RECOMMEND.POST_USER_ID.eq(postUserId))
                .and(REFERRAL_SEEK_RECOMMEND.POSITION_ID.in(positionIdList))
                .and(REFERRAL_SEEK_RECOMMEND.APP_ID.eq(0))
                .fetchInto(ReferralSeekRecommendRecord.class);

    }

    public List<ReferralSeekRecommendRecord> fetchSeekRecommendByPostAndPressentee(int postUserId, List<Integer> positionIdList, List<Integer> presenteeIds){
        return using(configuration()).selectFrom(REFERRAL_SEEK_RECOMMEND)
                .where(REFERRAL_SEEK_RECOMMEND.POST_USER_ID.eq(postUserId))
                .and(REFERRAL_SEEK_RECOMMEND.POSITION_ID.in(positionIdList))
                .and(REFERRAL_SEEK_RECOMMEND.PRESENTEE_ID.in(presenteeIds))
                .and(REFERRAL_SEEK_RECOMMEND.APP_ID.eq(0))
                .fetch();

    }


    public List<ReferralSeekRecommendRecord> fetchSeekRecommendByPost(int postUserId, List<Integer> positionIdList, Set<Integer> presenteeUserIdList){
        return using(configuration()).selectFrom(REFERRAL_SEEK_RECOMMEND)
                .where(REFERRAL_SEEK_RECOMMEND.POST_USER_ID.eq(postUserId))
                .and(REFERRAL_SEEK_RECOMMEND.POSITION_ID.in(positionIdList))
                .and(REFERRAL_SEEK_RECOMMEND.PRESENTEE_ID.notIn(presenteeUserIdList))
                .and(REFERRAL_SEEK_RECOMMEND.APP_ID.eq(0))
                .orderBy(REFERRAL_SEEK_RECOMMEND.RECOMMEND_TIME.desc())
                .fetch();

    }

    public int fetchSeekRecommendByPostCount(int postUserId, List<Integer> positionIdList, Set<Integer> presenteeUserIdList){
        return using(configuration()).selectFrom(REFERRAL_SEEK_RECOMMEND)
                .where(REFERRAL_SEEK_RECOMMEND.POST_USER_ID.eq(postUserId))
                .and(REFERRAL_SEEK_RECOMMEND.POSITION_ID.in(positionIdList))
                .and(REFERRAL_SEEK_RECOMMEND.PRESENTEE_ID.notIn(presenteeUserIdList))
                .and(REFERRAL_SEEK_RECOMMEND.APP_ID.eq(0))
                .fetchCount();

    }
    public int updateReferralSeekRecommendRecordForAppId(int referralId, int appId ){
        return using(configuration()).update(REFERRAL_SEEK_RECOMMEND)
                .set(REFERRAL_SEEK_RECOMMEND.APP_ID, appId)
                .where(REFERRAL_SEEK_RECOMMEND.ID.eq(referralId))
                .execute();

    }

    public int updateReferralSeekRecommendRecordForRecommendTime(int referralId){
        return using(configuration()).update(REFERRAL_SEEK_RECOMMEND)
                .set(REFERRAL_SEEK_RECOMMEND.RECOMMEND_TIME, new Timestamp(new Date().getTime()))
                .where(REFERRAL_SEEK_RECOMMEND.ID.eq(referralId))
                .execute();

    }

    public List<ReferralSeekRecommendRecord> fetchSeekRecommendByPostUserAndPresentee(int postUserId, List<Integer> presenteeUserId){
        return using(configuration()).selectFrom(REFERRAL_SEEK_RECOMMEND)
                .where(REFERRAL_SEEK_RECOMMEND.POST_USER_ID.eq(postUserId))
                .and(REFERRAL_SEEK_RECOMMEND.PRESENTEE_ID.in(presenteeUserId))
                .and(REFERRAL_SEEK_RECOMMEND.APP_ID.eq(0))
                .orderBy(REFERRAL_SEEK_RECOMMEND.RECOMMEND_TIME.desc())
                .fetchInto(ReferralSeekRecommendRecord.class);

    }

    public List<ReferralSeekRecommendRecord> fetchSeekRecommendByAppids(List<Integer> appids){
        return using(configuration()).selectFrom(REFERRAL_SEEK_RECOMMEND)
                .where(REFERRAL_SEEK_RECOMMEND.APP_ID.in(appids))
                .orderBy(REFERRAL_SEEK_RECOMMEND.RECOMMEND_TIME.desc())
                .fetchInto(ReferralSeekRecommendRecord.class);
    }


    public ReferralSeekRecommendRecord fetchByUidAndPresenteeIdAndPid(int employeeId, int presenteeId, int positionId) {
        return using(configuration()).selectFrom(REFERRAL_SEEK_RECOMMEND)
                .where(REFERRAL_SEEK_RECOMMEND.POST_USER_ID.eq(employeeId))
                .and(REFERRAL_SEEK_RECOMMEND.PRESENTEE_ID.eq(presenteeId))
                .and(REFERRAL_SEEK_RECOMMEND.POSITION_ID.eq(positionId))
                .fetchOneInto(ReferralSeekRecommendRecord.class);
    }

}
