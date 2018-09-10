package com.moseeker.position.thrift;

import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.position.service.fundationbs.ReferralPositionService;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.ReferralPositionServices;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date: 2018/9/4
 * @Author: JackYang
 */

@Service
public class ReferralPositionServiceImpl implements ReferralPositionServices.Iface {

    @Autowired
    ReferralPositionService referralPositionService;

    @Override
    public void putReferralPositions(List<Integer> pids) throws TException {
        try {
            referralPositionService.putReferralPositions(pids);
        } catch (Exception e) {
            e.printStackTrace();
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public void delReferralPositions(List<Integer> pids) throws TException {
        try {
            referralPositionService.delReferralPositions(pids);
        } catch (Exception e) {
            e.printStackTrace();
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public void updatePointsConfig(int companyId, int flag) throws TException {
        referralPositionService.updatePointsConfig(companyId,flag);
    }

    @Override
    public Response getPointsConfig(int companyId) throws TException {
        return referralPositionService.getPointsConfig(companyId);
    }

}
