package com.moseeker.baseorm.dao.referraldb;

import com.moseeker.baseorm.db.referraldb.tables.records.ReferralPositionBonusStageDetailRecord;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moseeker.baseorm.db.referraldb.tables.ReferralPositionBonusStageDetail.REFERRAL_POSITION_BONUS_STAGE_DETAIL;
import static org.jooq.impl.DSL.using;

/**
 * @Date: 2018/9/25
 * @Author: JackYang
 */
@Repository
public class ReferralPositionBonusStageDetailDao extends com.moseeker.baseorm.db.referraldb.tables.daos.ReferralPositionBonusStageDetailDao {

    @Autowired
    public ReferralPositionBonusStageDetailDao(Configuration configuration) {
        super(configuration);
    }


    public com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralPositionBonusStageDetail
    fetchByReferralPositionIdAndStageType(Integer positionId, Integer stageType) {
        ReferralPositionBonusStageDetailRecord referralPositionBonusStageDetailRecord = using(configuration())
                .selectFrom(REFERRAL_POSITION_BONUS_STAGE_DETAIL)
                .where(REFERRAL_POSITION_BONUS_STAGE_DETAIL.POSITION_ID.eq(positionId))
                .and(REFERRAL_POSITION_BONUS_STAGE_DETAIL.STAGE_TYPE.eq(stageType))
                .fetchOne();
        if (referralPositionBonusStageDetailRecord != null) {
            return referralPositionBonusStageDetailRecord.into(com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralPositionBonusStageDetail.class);
        } else {
            return null;
        }
    }

    public List<ReferralPositionBonusStageDetailRecord> fetchByIdList(List<Integer> stageIdList) {

        return using(configuration())
                .selectFrom(REFERRAL_POSITION_BONUS_STAGE_DETAIL)
                .where(REFERRAL_POSITION_BONUS_STAGE_DETAIL.ID.in(stageIdList))
                .fetch();
    }
}

