package com.moseeker.position.thrift;

import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.position.service.fundationbs.PositionATSService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.thirdpartydb.ThirdpartyCompanyChannelConfDO;
import com.moseeker.thrift.gen.position.service.PositionATSServices;
import com.moseeker.thrift.gen.position.struct.BatchHandlerJobPostion;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PositionATSServicesImpl implements PositionATSServices.Iface{
    Logger logger= LoggerFactory.getLogger(PositionATSServicesImpl.class);

    @Autowired
    PositionATSService positionATSService;

    @Override
    public Response getSyncChannel() throws TException {
        try {
            return ResponseUtils.success(positionATSService.getSyncChannel());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public List<ThirdpartyCompanyChannelConfDO> updateCompanyChannelConf(int company_id, List<Integer> channel) throws TException {
        try {
            return positionATSService.updateCompanyChannelConf(company_id,channel);
        } catch (BIZException e){
            logger.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public List<Integer> getCompanyChannelConfByCompanyId(int company_id) throws TException {
        try {
            return positionATSService.getCompanyChannelConfByCompanyId(company_id);
        } catch (BIZException e){
            logger.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
        }
    }

    @Override
    public Response insertGlluePosition(BatchHandlerJobPostion batchHandlerJobPostion) throws TException {
        try {
            return positionATSService.insertGlluePosition(batchHandlerJobPostion);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response updateGlluePosition(BatchHandlerJobPostion batchHandlerJobPostion) throws TException {
        try {
            return positionATSService.updateGlluePosition(batchHandlerJobPostion);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response republishPosition(BatchHandlerJobPostion batchHandlerJobPostion) throws TException {
        try {
            return positionATSService.republishPosition(batchHandlerJobPostion);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response revokeGlluePosition(BatchHandlerJobPostion batchHandlerJobPostion) throws TException {
        try {
            return positionATSService.revokeGlluePosition(batchHandlerJobPostion);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }
}
