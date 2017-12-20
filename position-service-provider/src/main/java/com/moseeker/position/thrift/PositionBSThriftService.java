package com.moseeker.position.thrift;

import com.moseeker.common.constants.SyncRequestType;
import com.moseeker.position.constants.ResultMessage;
import com.moseeker.position.service.appbs.PositionBS;
import com.moseeker.position.service.schedule.ThirdPartyPositionParamRefresh;
import com.moseeker.thrift.gen.apps.positionbs.service.PositionBS.Iface;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPositionForm;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionBSThriftService implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PositionBS positionBS;

    @Autowired
    private ThirdPartyPositionParamRefresh refresher;

    /**
     * 同步第三方职位
     */
    @Override
    public Response synchronizePositionToThirdPartyPlatform(ThirdPartyPositionForm position) throws TException {
        try {
            //设置请求端类型为网页端
            position.setRequestType(SyncRequestType.WEB.code());
            return ResultMessage.SUCCESS.toResponse(positionBS.synchronizePositionToThirdPartyPlatform(position));
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

    @Override
    public Response refreshThirdPartyParam() throws BIZException, TException {
        try {
            refresher.refresh();
            return ResultMessage.SUCCESS.toResponse(null);
        }catch (Exception e){
            logger.error("refresh Third Party Param error {}",e.getMessage());
            return ResultMessage.PROGRAM_EXCEPTION.toResponse();
        }

    }
}
