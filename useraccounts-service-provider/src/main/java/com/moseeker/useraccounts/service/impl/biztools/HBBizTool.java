package com.moseeker.useraccounts.service.impl.biztools;

import com.moseeker.baseorm.db.hrdb.tables.records.HrHbConfigRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbItemsRecord;
import com.moseeker.useraccounts.service.impl.vo.RedPacket;

import java.util.Map;

/**
 * @Author: jack
 * @Date: 2018/9/27
 */
public class HBBizTool {

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
    public static boolean isOpen(byte status) {
        return status == 4 || status == 5 || status == 6 || status == 7 || status == 100 || status == 101
                || status == -1;
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
    public static RedPacket packageRedPacket(HrHbItemsRecord hrHbItemsRecord, Map<Integer, String> candidateNameMap,
                                             Map<Integer, HrHbConfigRecord> configMap, Map<Integer, String> titleMap,
                                             Map<Integer, String> cardNoMap) {
        RedPacket redPacket = new RedPacket();
        redPacket.setId(hrHbItemsRecord.getId());
        redPacket.setCandidateName(candidateNameMap.get(hrHbItemsRecord.getId()));
        redPacket.setCardno(cardNoMap.get(hrHbItemsRecord.getId()));
        HrHbConfigRecord configRecord = configMap.get(hrHbItemsRecord.getHbConfigId());
        if (configRecord != null) {
            redPacket.setType(configRecord.getType());
            redPacket.setName(HBBizTool.getConfigName(configRecord.getType()));
        }
        redPacket.setValue(hrHbItemsRecord.getAmount().doubleValue());
        redPacket.setOpen(HBBizTool.isOpen(hrHbItemsRecord.getStatus()));
        redPacket.setPositionTitle(titleMap.get(hrHbItemsRecord.getBindingId()));
        if (hrHbItemsRecord.getOpenTime() != null) {
            redPacket.setOpenTime(hrHbItemsRecord.getOpenTime().getTime());
        } else {
            redPacket.setOpenTime(hrHbItemsRecord.getUpdateTime().getTime());
        }
        return redPacket;
    }
}
