package com.moseeker.useraccounts.thrift;

import com.alibaba.fastjson.JSON;
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
import com.moseeker.useraccounts.service.ReferralRadarService;
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

    @Autowired
    private ReferralRadarService radarService;

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
    public void employeeReferralReason(int postUserId, int positionId, int referralId, List<String> referralReasons, byte relationship, String recomReasonText) throws BIZException, TException {
        try{
            referralService.employeeReferralReason(postUserId, positionId,  referralId, referralReasons, relationship, recomReasonText);
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

    @Override
    public String getRadarCards(ReferralCardInfo cardInfo) throws BIZException, TException {
        try {
            return radarService.getRadarCards(cardInfo.getCompanyId(), cardInfo);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public String inviteApplication(ReferralInviteInfo inviteInfo) throws BIZException, TException {
        try {
            return radarService.inviteApplication(inviteInfo.getCompanyId(), inviteInfo);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public String ignoreCurrentViewer(ReferralInviteInfo ignoreInfo) throws BIZException, TException {
        try {
            radarService.ignoreCurrentViewer(ignoreInfo.getCompanyId(), ignoreInfo);
            return "success";
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public String connectRadar(ConnectRadarInfo radarInfo) throws BIZException, TException {
        try {
            RadarConnectResult result = radarService.connectRadar(radarInfo.getCompanyId(), radarInfo);
            return JSON.toJSONString(result);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public String checkEmployee(CheckEmployeeInfo checkInfo) throws BIZException, TException {
        try {
            return radarService.checkEmployee(checkInfo.getCompanyId(), checkInfo);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public void saveTenMinuteCandidateShareChain(ReferralCardInfo cardInfo) throws BIZException, TException {
        try {
            radarService.saveTenMinuteCandidateShareChain(cardInfo.getCompanyId(), cardInfo);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public String getProgressByOne(ReferralProgressQueryInfo progressQuery) throws BIZException, TException {
        try {
            return radarService.getProgressByOne(progressQuery.getCompanyId(), progressQuery);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public String getProgressBatch(ReferralProgressInfo progressInfo) throws BIZException, TException {
        try {
            return radarService.getProgressBatch(progressInfo.getCompanyId(), progressInfo);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public String progressQueryKeyword(ReferralProgressInfo progressInfo) throws BIZException, TException {
        try {
            return radarService.progressQueryKeyword(progressInfo.getCompanyId(), progressInfo);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public void employeeReferralRecomEvaluation(int postUserId, int positionId, int presenteeId, List<String> referralReasons, byte relationship, String recomReasonText) throws BIZException, TException {
        try {
            referralService.employeeReferralRecomEvaluation(postUserId, positionId, presenteeId, referralReasons, relationship, recomReasonText);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public int checkSeekReferral(int userId, int presenteeId, int positionId, int companyId, int psc) throws BIZException, TException {
        try {
            return radarService.checkSeekReferral(companyId, userId, presenteeId, positionId, psc);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

}
