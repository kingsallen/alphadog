package com.moseeker.position.thrift;

import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.position.service.fundationbs.PositionATSService;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.PositionATSServices;
import com.moseeker.thrift.gen.position.struct.BatchHandlerJobPostion;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PositionATSServicesImpl implements PositionATSServices.Iface{
    Logger logger= LoggerFactory.getLogger(PositionATSServicesImpl.class);

    @Autowired
    PositionATSService positionATSService;

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

    @Override
    public Response atsUpdatePositionFeature(BatchHandlerJobPostion batchHandlerJobPostion) throws TException {
        try {
            return ResponseUtils.success(positionATSService.atsUpdatePositionFeature(batchHandlerJobPostion));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }
}
