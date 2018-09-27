package com.moseeker.useraccounts.thrift;

import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.referral.service.ReferralService;
import com.moseeker.thrift.gen.referral.struct.RedPacket;
import com.moseeker.thrift.gen.referral.struct.RedPackets;
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
    public RedPackets getRedPackets(int userId, int pageNum, int pageSize) throws BIZException, TException {
        try {
            com.moseeker.useraccounts.service.impl.vo.RedPackets redPackets
                    = referralService.getRedPackets(userId, pageNum, pageSize);

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
}
