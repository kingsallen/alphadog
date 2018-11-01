package com.moseeker.useraccounts.service.impl.biztools;

import com.moseeker.baseorm.constant.HBType;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrHbItems;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbConfigRecord;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralEmployeeBonusRecord;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog;
import com.moseeker.baseorm.db.referraldb.tables.records.ReferralPositionBonusStageDetailRecord;
import com.moseeker.entity.Constant.BonusStage;
import com.moseeker.entity.pojos.BonusData;
import com.moseeker.entity.pojos.HBData;
import com.moseeker.entity.pojos.ReferralProfileData;
import com.moseeker.useraccounts.service.impl.vo.Bonus;
import com.moseeker.useraccounts.service.impl.vo.RedPacket;
import com.moseeker.useraccounts.service.impl.vo.ReferralProfileTab;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

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
            case 4 : return "推荐通过初筛红包";
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
     * 封装红包数据
     * @param hrHbItems 红包记录集合
     * @param data 数据对象
     * @return 红包数据
     */
    public static RedPacket packageRedPacket(HrHbItems hrHbItems, HBData data) {
        RedPacket redPacket = new RedPacket();
        redPacket.setId(hrHbItems.getId());
        redPacket.setCardno(data.getCardNoMap().get(hrHbItems.getId()).getCardno());
        HrHbConfigRecord configRecord = data.getConfigMap().get(hrHbItems.getHbConfigId());
        if (configRecord != null) {
            redPacket.setType(configRecord.getType());
            redPacket.setName(HBBizTool.getConfigName(configRecord.getType()));
        }
        if (redPacket.getType() == HBType.Recommend.getValue()) {
            if (data.getRecomPositionTitleMap().get(hrHbItems.getId()) != null) {
                redPacket.setPositionTitle(data.getRecomPositionTitleMap().get(hrHbItems.getId()).getPositionTitle());
                redPacket.setCandidateName(data.getRecomPositionTitleMap().get(hrHbItems.getId()).getCandidateName());
            }
        } else {
            redPacket.setCandidateName(data.getCandidateNameMap().get(hrHbItems.getTriggerWxuserId()));
            redPacket.setPositionTitle(data.getTitleMap().get(hrHbItems.getBindingId()));
        }
        redPacket.setValue(hrHbItems.getAmount().doubleValue());
        redPacket.setOpen(HBBizTool.isOpen(data.getCardNoMap().get(hrHbItems.getId()).getStatus()));

        if (hrHbItems.getOpenTime() != null) {
            redPacket.setOpenTime(hrHbItems.getOpenTime().getTime());
        }
        return redPacket;
    }

    /**
     * 封装奖金数据
     * @param referralEmployeeBonusRecord 奖金记录
     * @param bonusData 数据对象
     * @return 奖金数据
     */
    public static Bonus packageBonus(ReferralEmployeeBonusRecord referralEmployeeBonusRecord, BonusData bonusData) {
        Bonus bonus = new Bonus();

        bonus.setId(referralEmployeeBonusRecord.getId());
        bonus.setValue(new BigDecimal(referralEmployeeBonusRecord.getBonus().doubleValue())
                .divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
        bonus.setOpen((referralEmployeeBonusRecord.getClaim() == 1 && referralEmployeeBonusRecord.getDisable() ==0)
                || referralEmployeeBonusRecord.getDisable() == 1);
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

    /**
     * 封装内推附件数据
     * @param log           内推记录
     * @param profileData   数据对象
     * @return
     */
    public static ReferralProfileTab packageReferralTab(ReferralLog log, ReferralProfileData profileData) {
        ReferralProfileTab tab = new ReferralProfileTab();
        tab.setUploadTime(log.getCreateTime());
        if(profileData.getPositionTitleMap().get(log.getPositionId()) != null){
            tab.setPositionTitle(profileData.getPositionTitleMap().get(log.getPositionId()));
        }

        if(profileData.getEmployeeNameMap().get(log.getEmployeeId())!=null){
            tab.setSender(profileData.getEmployeeNameMap().get(log.getEmployeeId()));
        }

        if(profileData.getProfileIdMap().get(log.getOldReferenceId())!=null){
            Integer profileId = profileData.getProfileIdMap().get(log.getOldReferenceId());
            if(profileData.getAttchmentMap().get(profileId)!=null){
                tab.setId(profileData.getAttchmentMap().get(profileId).getId());
                tab.setFilePath(profileData.getAttchmentMap().get(profileId).getPath());
            }
        }
        return tab;
    }
}
