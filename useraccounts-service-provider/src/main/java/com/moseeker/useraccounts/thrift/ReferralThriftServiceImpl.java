package com.moseeker.useraccounts.thrift;

import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.referral.service.ReferralService;
import com.moseeker.thrift.gen.referral.struct.*;
import java.util.ArrayList;
import java.util.List;
import com.moseeker.thrift.gen.referral.struct.*;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.service.impl.vo.ActivityVO;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @Author: jack
 * @Date: 2018/9/26
 */
@Service
public class ReferralThriftServiceImpl implements ReferralService.Iface {

    @Autowired
    private com.moseeker.useraccounts.service.ReferralService referralService;

    @Override
    public RedPackets getRedPackets(int userId, int companyId, int pageNum, int pageSize)
            throws BIZException, TException {
        try {
            com.moseeker.useraccounts.service.impl.vo.RedPackets redPackets
                    = referralService.getRedPackets(userId, companyId, pageNum, pageSize);

            RedPackets result = new RedPackets();
            BeanUtils.copyProperties(redPackets, result);
            if (redPackets.getRedpackets() != null && redPackets.getRedpackets().size() > 0) {
                result.setRedpackets(redPackets.getRedpackets().stream().map(redPacket -> {
                    RedPacket redPacketStruct = new RedPacket();
                    BeanUtils.copyProperties(redPacket, redPacketStruct);
                    return redPacketStruct;
                }).collect(Collectors.toList()));
            }
            return result;
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public BonusList getBonus(int userId, int companyId, int pageNum, int pageSize) throws BIZException, TException {
        try {
            com.moseeker.useraccounts.service.impl.vo.BonusList bonusList = referralService.getBonus(userId, companyId,
                    pageNum, pageSize);
            BonusList result = new BonusList();
            BeanUtils.copyProperties(bonusList, result);
            if (bonusList.getBonus() != null && bonusList.getBonus().size() > 0) {
                result.setBonus(bonusList.getBonus().stream().map(bonus -> {
                    Bonus bonus1 = new Bonus();
                    BeanUtils.copyProperties(bonus, bonus1);
                    return bonus1;
                }).collect(Collectors.toList()));
            }
            return result;
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public List<ReferralProfileTab> getReferralProfileList(int userId, int companyId, int hrId) throws BIZException, TException {
        try {
            List<com.moseeker.useraccounts.service.impl.vo.ReferralProfileTab> profileTabList = referralService
                    .getReferralProfileTabList(userId, companyId, hrId);
            List<ReferralProfileTab> result = new ArrayList<>();
            if (!StringUtils.isEmptyList(profileTabList)) {
                result = profileTabList.stream().map(tab -> {
                    ReferralProfileTab profileTab = new ReferralProfileTab();
                    BeanUtils.copyProperties(tab, profileTab);
                    return profileTab;
                }).collect(Collectors.toList());
            }
            return result;
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public void updateActivity(ActivityDTO activityDTO) throws BIZException, TException {
        try {
            ActivityVO activityVO = com.moseeker.baseorm.util.BeanUtils.structToDB(activityDTO, ActivityVO.class);
            referralService.updateActivity(activityVO);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }
}
