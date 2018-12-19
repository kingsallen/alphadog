package com.moseeker.useraccounts.thrift;

import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.referral.service.ReferralService;
import com.moseeker.thrift.gen.referral.struct.*;
import com.moseeker.thrift.gen.referral.struct.Bonus;
import com.moseeker.thrift.gen.referral.struct.BonusList;
import com.moseeker.thrift.gen.referral.struct.ContactPushInfo;
import com.moseeker.thrift.gen.referral.struct.RedPacket;
import com.moseeker.thrift.gen.referral.struct.RedPackets;
import com.moseeker.thrift.gen.referral.struct.ReferralProfileTab;
import com.moseeker.thrift.gen.referral.struct.ReferralReasonInfo;
import com.moseeker.useraccounts.service.impl.vo.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<ReferralReasonInfo> getReferralReason(int userId, int companyId, int hrId) throws BIZException, TException {
        try {
            List<com.moseeker.useraccounts.service.impl.vo.ReferralReasonInfo> result = referralService.getReferralReasonInfo(userId, companyId, hrId);
            List<ReferralReasonInfo> referralReasonInfos =new ArrayList<>();
            if(!StringUtils.isEmptyList(result)) {
                referralReasonInfos = result.stream().map(m -> {
                    ReferralReasonInfo info = new ReferralReasonInfo();
                    BeanUtils.copyProperties(m, info);
                    return info;
                }).collect(Collectors.toList());
            }
            return referralReasonInfos;
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public void handerKeyInformationStatus(int companyId, int keyInformation) throws BIZException, TException {
        try {
            referralService.handerKeyInformationStatus(companyId, keyInformation);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public int fetchKeyInformationStatus(int companyId) throws BIZException, TException {
        try{
            return referralService.fetchKeyInformationStatus(companyId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public void addUserSeekRecommend(int userId, int postUserId, int positionId, int origin) throws BIZException, TException {
        try{
            referralService.addReferralSeekRecommend(userId, postUserId, positionId, origin);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public void employeeReferralReason(int userId, int positionId, int referralId, List<String> referralReasons, byte relationship, String recomReasonText, int postUserId) throws BIZException, TException {
        try{
            referralService.employeeReferralReason(userId, positionId, postUserId, referralId, referralReasons, relationship, recomReasonText);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public ContactPushInfo fetchSeekRecommend(int referralId, int postUserId) throws BIZException, TException {
        try{
            com.moseeker.useraccounts.service.impl.vo.ContactPushInfo result = referralService.fetchSeekRecommend(referralId, postUserId);
            ContactPushInfo info = new ContactPushInfo();
            BeanUtils.copyProperties(result, info);
            return info;
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }


}
