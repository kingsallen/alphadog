package com.moseeker.function.thrift.service;

import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.function.service.chaos.ChaosServiceImpl;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.foundation.chaos.service.ChaosServices.Iface;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronizationWithAccount;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.util.List;
import java.util.Map;

/**
 * chaos服务对接
 * <p>Company: MoSeeker</P>
 * <p>date: Nov 8, 2016</p>
 * <p>Email: wjf2255@gmail.com</p>
 *
 * @author wjf
 */
@Service
public class ChaosThriftService implements Iface {

    Logger logger = LoggerFactory.getLogger(ChaosThriftService.class);

    @Autowired
    private ChaosServiceImpl chaosService;

    @Override
    public String binding(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) throws TException {
        try {
            return chaosService.bind(hrThirdPartyAccount, extras);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public String bindConfirm(HrThirdPartyAccountDO thirdPartyAccount, Map<String, String> extras, boolean confirm) throws BIZException, TException {
        try {
            return chaosService.bindConfirm(thirdPartyAccount, extras,confirm);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public String bindMessage(HrThirdPartyAccountDO thirdPartyAccount, Map<String, String> extras, String code) throws BIZException, TException {
        try {
            return chaosService.bindMessage(thirdPartyAccount, extras,code);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public void synchronizePosition(List<String> positions) throws TException {
        try {
            chaosService.synchronizePosition(positions);
        } catch (BIZException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new TException(e);
        }
    }

}
