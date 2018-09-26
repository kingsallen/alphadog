package com.moseeker.baseorm.dao.referraldb;

import com.moseeker.baseorm.db.referraldb.tables.ReferralPositionBonus;
import com.moseeker.baseorm.db.referraldb.tables.ReferralPositionBonusStageDetail;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralPositionBonusRecord;
import com.moseeker.thrift.gen.position.struct.ReferralPositionBonusDO;
import com.moseeker.thrift.gen.position.struct.ReferralPositionBonusStageDetailDO;
import com.moseeker.thrift.gen.position.struct.ReferralPositionBonusVO;
import org.jooq.Configuration;
import org.jooq.Param;
import org.jooq.Record5;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.jooq.impl.DSL.*;

/**
 * @Date: 2018/9/25
 * @Author: JackYang
 */
public class ReferralPositionBonusDao extends com.moseeker.baseorm.db.referraldb.tables.daos.ReferralPositionBonusDao {

    @Autowired
    public ReferralPositionBonusDao(Configuration configuration) {
        super(configuration);
    }


    public Map<Integer, ReferralPositionBonusVO> fetchByPid(List<Integer> pid) {

        Map<Integer, ReferralPositionBonusVO> hasMap = new HashMap<>();
        Result<Record5<Integer, Integer, Integer, Integer, Integer>> result = using(configuration())
                .select(ReferralPositionBonus.REFERRAL_POSITION_BONUS.POSITION_ID, ReferralPositionBonus.REFERRAL_POSITION_BONUS.TOTAL_BONUS,
                        ReferralPositionBonusStageDetail.REFERRAL_POSITION_BONUS_STAGE_DETAIL.STAGE_BONUS, ReferralPositionBonusStageDetail.REFERRAL_POSITION_BONUS_STAGE_DETAIL.STAGE_PROPORTION,
                        ReferralPositionBonusStageDetail.REFERRAL_POSITION_BONUS_STAGE_DETAIL.STAGE_TYPE)
                .from(ReferralPositionBonus.REFERRAL_POSITION_BONUS.innerJoin(ReferralPositionBonusStageDetail.REFERRAL_POSITION_BONUS_STAGE_DETAIL).
                        on(ReferralPositionBonus.REFERRAL_POSITION_BONUS.ID.equal(ReferralPositionBonusStageDetail.REFERRAL_POSITION_BONUS_STAGE_DETAIL.REFERRAL_POSITION_BONUS_ID)))
                .where(ReferralPositionBonus.REFERRAL_POSITION_BONUS.POSITION_ID.in(pid))
                .fetch();

        if (result != null) {
            for (Record5<Integer, Integer, Integer, Integer, Integer> r : result) {
                Integer position_id = r.getValue(ReferralPositionBonus.REFERRAL_POSITION_BONUS.POSITION_ID);
                Integer total_bonus = r.getValue(ReferralPositionBonus.REFERRAL_POSITION_BONUS.TOTAL_BONUS);
                Integer stage_bonus = r.getValue(ReferralPositionBonusStageDetail.REFERRAL_POSITION_BONUS_STAGE_DETAIL.STAGE_BONUS);
                Integer stage_proportaion = r.getValue(ReferralPositionBonusStageDetail.REFERRAL_POSITION_BONUS_STAGE_DETAIL.STAGE_PROPORTION);
                Integer stage_type = r.getValue(ReferralPositionBonusStageDetail.REFERRAL_POSITION_BONUS_STAGE_DETAIL.STAGE_TYPE);

                if (hasMap.containsKey(position_id)) {
                    ReferralPositionBonusVO bonusVO = (ReferralPositionBonusVO) hasMap.get(position_id);

                    ReferralPositionBonusStageDetailDO detailDO = new ReferralPositionBonusStageDetailDO();
                    detailDO.setStage_bonus(stage_bonus);
                    detailDO.setStage_proportion(stage_proportaion);
                    detailDO.setStage_type(stage_type);
                    bonusVO.getData().add(detailDO);
                    hasMap.put(position_id, bonusVO);

                } else {
                    ReferralPositionBonusVO bonusVO = new ReferralPositionBonusVO();
                    ReferralPositionBonusDO bonusDO = new ReferralPositionBonusDO();
                    List<ReferralPositionBonusStageDetailDO> datas = new ArrayList<>();
                    ReferralPositionBonusStageDetailDO detailDO = new ReferralPositionBonusStageDetailDO();

                    bonusDO.setPosition_id(position_id);
                    bonusDO.setTotal_bonus(total_bonus);

                    detailDO.setStage_bonus(stage_bonus);
                    detailDO.setStage_proportion(stage_proportaion);
                    detailDO.setStage_type(stage_type);
                    datas.add(detailDO);

                    bonusVO.setPosition_bonus(bonusDO);
                    bonusVO.setData(datas);

                    hasMap.put(position_id, bonusVO);
                }
            }
            return hasMap;
        } else {

            return new HashMap<>();
        }

    }


    public int createReferralPositionBonus(int positionId, int totalBonus) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Param<Integer> positionIdParam = param(ReferralPositionBonus.REFERRAL_POSITION_BONUS.POSITION_ID.getName(), positionId);
        Param<Integer> totalBonusParam = param(ReferralPositionBonus.REFERRAL_POSITION_BONUS.TOTAL_BONUS.getName(), totalBonus);
        Param<Timestamp> createTimeParam = param(ReferralPositionBonus.REFERRAL_POSITION_BONUS.CREATE_TIME.getName(), now);
        Param<Timestamp> updateTimeParam = param(ReferralPositionBonus.REFERRAL_POSITION_BONUS.UPDATE_TIME.getName(),now);

        ReferralPositionBonusRecord referralPositionBonusRecord = using(configuration()).insertInto(
                ReferralPositionBonus.REFERRAL_POSITION_BONUS,
                ReferralPositionBonus.REFERRAL_POSITION_BONUS.POSITION_ID,
                ReferralPositionBonus.REFERRAL_POSITION_BONUS.TOTAL_BONUS,
                ReferralPositionBonus.REFERRAL_POSITION_BONUS.CREATE_TIME,
                ReferralPositionBonus.REFERRAL_POSITION_BONUS.UPDATE_TIME
                ).select(
                select(
                        positionIdParam,
                        totalBonusParam,
                        createTimeParam,
                        updateTimeParam
                ).whereNotExists(
                        selectOne()
                                .from(ReferralPositionBonus.REFERRAL_POSITION_BONUS)
                                .where(ReferralPositionBonus.REFERRAL_POSITION_BONUS.POSITION_ID.eq(positionId))
                                .and(ReferralPositionBonus.REFERRAL_POSITION_BONUS.TOTAL_BONUS.eq(totalBonus))
                )
        ).returning().fetchOne();
        if (referralPositionBonusRecord != null) {
            return referralPositionBonusRecord.getId();
        } else {
            return 0;
        }
    }
}
