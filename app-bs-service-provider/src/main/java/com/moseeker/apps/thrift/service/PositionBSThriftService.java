package com.moseeker.apps.thrift.service;

import com.moseeker.apps.constants.ResultMessage;

import java.util.List;

import com.moseeker.thrift.gen.common.struct.BIZException;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.apps.service.PositionBS;
import com.moseeker.thrift.gen.apps.positionbs.service.PositionBS.Iface;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;
import com.moseeker.thrift.gen.common.struct.Response;

@Service
public class PositionBSThriftService implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PositionBS positionBS;

    /**
     * 同步第三方职位
     */
    @Override
    public Response synchronizePositionToThirdPartyPlatform(ThirdPartyPositionForm position) throws TException {
        try {
            return positionBS.synchronizePositionToThirdPartyPlatform(position);
        } catch (BIZException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return ResultMessage.PROGRAM_EXCEPTION.toResponse();
        }
    }

    /**
     * 刷新职位
     */
    @Override
    public Response refreshPositionToThirdPartyPlatform(int positionId, int channel) throws TException {
        try {
            return positionBS.refreshPosition(positionId, channel);
        } catch (BIZException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new TException(e);
        }
    }

    @Override
    public Response refreshPositionQXPlatform(List<Integer> positionIds) throws TException {
        // TODO Auto-generated method stub
        try {
            return positionBS.refreshPositionQX(positionIds);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResultMessage.PROGRAM_EXCEPTION.toResponse();
        }

    }
}
