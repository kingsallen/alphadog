package com.moseeker.function.thrift.service;

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
    public HrThirdPartyAccountDO binding(HrThirdPartyAccountDO hrThirdPartyAccount) throws TException {
        try {
            return chaosService.bind(hrThirdPartyAccount);
        } catch (BIZException e) {
            throw e;
        } catch (ConnectException e) {
            throw new BIZException(-1, "绑定失败，请稍后再试");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new TException(e);
        }
    }

    @Override
    public HrThirdPartyAccountDO synchronization(HrThirdPartyAccountDO thirdPartyAccount) throws TException {
        try {
            return chaosService.synchronization(thirdPartyAccount);
        } catch (BIZException e) {
            throw e;
        } catch (ConnectException e) {
            throw new BIZException(-1, "刷新失败，请稍后再试");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new TException(e);
        }
    }

    @Override
    public void synchronizePosition(List<ThirdPartyPositionForSynchronizationWithAccount> positions) throws TException {
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

    @Override
    public void refreshPosition(ThirdPartyPositionForSynchronizationWithAccount position) throws TException {
        // TODO Auto-generated method stub
        try {
            chaosService.refreshPosition(position);
        } catch (BIZException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new TException(e);
        }
    }
}
