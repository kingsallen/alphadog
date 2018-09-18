package com.moseeker.position.thrift;

import com.moseeker.position.service.fundationbs.ReferralPositionService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.ReferralPositionServices;
import com.moseeker.thrift.gen.position.struct.ReferralPositionUpdateDataDO;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Date: 2018/9/4
 * @Author: JackYang
 */

@Service
public class ReferralPositionServiceImpl implements ReferralPositionServices.Iface {

    @Autowired
    ReferralPositionService referralPositionService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void putReferralPositions(ReferralPositionUpdateDataDO dataDO) throws TException {
        try {
            referralPositionService.putReferralPositions(dataDO);
        } catch (BIZException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void delReferralPositions(ReferralPositionUpdateDataDO dataDO) throws TException {
        try {
            referralPositionService.delReferralPositions(dataDO);
        } catch (BIZException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
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
