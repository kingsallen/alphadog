package com.moseeker.position.service.position.job51;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.sync.verify.MobileVeifyHandler;
import com.moseeker.position.service.position.base.sync.verify.PositionSyncVerifier;
import com.moseeker.position.service.position.base.sync.verify.PositionSyncVerifyHandler;
import com.moseeker.position.service.position.base.sync.verify.PositionSyncVerifyReceiver;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.SysBIZException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Job51SyncVerifyHandler implements PositionSyncVerifier<String> {
    Logger logger= LoggerFactory.getLogger(Job51SyncVerifyHandler.class);

    @Resource(type = MobileVeifyHandler.class)
    private PositionSyncVerifyHandler verifyHandler;

    @Resource(type = MobileVeifyHandler.class)
    private PositionSyncVerifyReceiver verifyReceiver;

    @Override
    public void handler(String info) throws BIZException {
        logger.info("51 syncVerifyInfo info:{}",info);
        try {
            verifyHandler.handler(info);
        }catch (BIZException e){
            logger.error("51 syncVerifyInfo BIZException info:{},error:{}",info,e);
            throw e;
        }catch (Exception e){
            logger.error("51 syncVerifyInfo Exception info:{},error:{}",info,e);
            throw new SysBIZException();
        }

    }

    @Override
    public void receive(String param) throws BIZException {
        logger.info("51 verifyHandler param:{}",param);
        try {
            verifyReceiver.receive(param);
        }catch (BIZException e){
            logger.error("51 verifyHandler BIZException param:{},error:{}",param,e);
            throw e;
        }catch (Exception e){
            logger.error("51 syncVerifyInfo Exception param:{},error:{}",param,e);
            throw new SysBIZException();
        }
    }

    @Override
    public ChannelType getChannelType() {
        return ChannelType.JOB51;
    }
}
