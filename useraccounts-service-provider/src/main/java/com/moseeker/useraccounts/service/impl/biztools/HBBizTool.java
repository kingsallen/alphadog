package com.moseeker.useraccounts.service.impl.biztools;

import com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbConfigRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbScratchCardRecord;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralPositionBonusStageDetailRecord;
import com.moseeker.entity.Constant.BonusStage;
import com.moseeker.entity.pojos.BonusData;
import com.moseeker.useraccounts.service.impl.vo.Bonus;
import com.moseeker.useraccounts.service.impl.vo.RedPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author: jack
 * @Date: 2018/9/27
 */
public class HBBizTool {

    private static Logger logger = LoggerFactory.getLogger(HBBizTool.class);
    /**
     * 红包类型名称
     * @param type 红包类型
     * @return 红包名称
     */
    public static String getConfigName(int type) {
        switch (type) {
            case 0 : return "员工认证红包";
            case 1 : return "推荐评价红包";
            case 2 : return "转发被点击红包";
            case 3 : return "转发被申请红包";
            default:
                return "";
        }
    }

    /**
     * 红包是否打开
     * @param status 红包状态
     * @return true 打开 false 未打开
     */
    public static boolean isOpen(Integer status) {
        return status == 1;
    }

    /**
     * 封装红包
     * @param hrHbItemsRecord 红包记录
     * @param candidateNameMap 候选人名称
     * @param configMap 红包类型
     * @param titleMap 职位名称
     * @param cardNoMap 刮刮卡
     * @return 红包数据
     */
    public static RedPacket packageRedPacket(HrHbItems hrHbItemsRecord, Map<Integer, String> candidateNameMap,
                                             Map<Integer, HrHbConfigRecord> configMap, Map<Integer, String> titleMap,
                                             Map<Integer, HrHbScratchCardRecord> cardNoMap) {
        RedPacket redPacket = new RedPacket();
        redPacket.setId(hrHbItemsRecord.getId());
        redPacket.setCandidateName(candidateNameMap.get(hrHbItemsRecord.getTriggerWxuserId().intValue()));
        redPacket.setCardno(cardNoMap.get(hrHbItemsRecord.getId()).getCardno());
        HrHbConfigRecord configRecord = configMap.get(hrHbItemsRecord.getHbConfigId());
        if (configRecord != null) {
            redPacket.setType(configRecord.getType());
            redPacket.setName(HBBizTool.getConfigName(configRecord.getType()));
        }
        redPacket.setValue(hrHbItemsRecord.getAmount().doubleValue());
        redPacket.setOpen(HBBizTool.isOpen(cardNoMap.get(hrHbItemsRecord.getId()).getStatus()));
        redPacket.setPositionTitle(titleMap.get(hrHbItemsRecord.getBindingId()));
        if (hrHbItemsRecord.getOpenTime() != null) {
            redPacket.setOpenTime(hrHbItemsRecord.getOpenTime().getTime());
        } else {
            redPacket.setOpenTime(hrHbItemsRecord.getUpdateTime().getTime());
        }
        return redPacket;
    }

    /**
     * 封装奖金数据
     * @param referralEmployeeBonusRecord
     * @param bonusData
     * @return
     */
    public static Bonus packageBonus(ReferralEmployeeBonusRecord referralEmployeeBonusRecord, BonusData bonusData) {
        Bonus bonus = new Bonus();

        bonus.setId(referralEmployeeBonusRecord.getId());
        bonus.setValue(new BigDecimal(referralEmployeeBonusRecord.getBonus().doubleValue())
                .divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
        bonus.setOpen(referralEmployeeBonusRecord.getClaim() == 1);
        bonusData.getStageDetailMap().get(referralEmployeeBonusRecord.getBonusStageDetailId());
        if (bonusData.getStageDetailMap().get(referralEmployeeBonusRecord.getBonusStageDetailId()) != null) {
            ReferralPositionBonusStageDetailRecord referralPositionBonusStageDetailRecord =
                    bonusData.getStageDetailMap().get(referralEmployeeBonusRecord.getBonusStageDetailId());
            BonusStage bonusStage = BonusStage.instanceFromValue(referralPositionBonusStageDetailRecord.getStageType());
            if (bonusStage != null) {
                bonus.setName(bonusStage.getDescription(bonus.getValue()));
            }
        }
        if (bonusData.getCandidateMap().get(referralEmployeeBonusRecord.getApplicationId()) != null) {
            bonus.setCandidateName(bonusData.getCandidateMap().get(referralEmployeeBonusRecord.getApplicationId()));
        }
        if (bonusData.getPositionTitleMap().get(referralEmployeeBonusRecord.getApplicationId()) != null) {
            bonus.setPositionTitle(bonusData.getPositionTitleMap().get(referralEmployeeBonusRecord.getApplicationId()));
        }

        if (bonusData.getEmploymentDateMap().get(referralEmployeeBonusRecord.getApplicationId()) != null) {
            bonus.setEmploymentDate(bonusData.getEmploymentDateMap().get(referralEmployeeBonusRecord.getApplicationId()));
        }
        bonus.setType(3);
        if (bonus.getValue() >= 0) {
            bonus.setCancel(false);
        } else {
            bonus.setCancel(true);
        }
        return bonus;
    }
}
