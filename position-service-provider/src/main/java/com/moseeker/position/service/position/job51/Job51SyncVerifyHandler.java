package com.moseeker.position.service.position.job51;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.sync.verify.MobileVeifyHandler;
import com.moseeker.position.service.position.base.sync.verify.PositionSyncVerifier;
import com.moseeker.position.service.position.zhilian.ZhilianSyncVerifyHandler;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.SysBIZException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Job51SyncVerifyHandler implements PositionSyncVerifier<String> {
    Logger logger= LoggerFactory.getLogger(ZhilianSyncVerifyHandler.class);

    @Autowired
    private MobileVeifyHandler mobileVeifyHandler;

    @Override
    public void handler(String info) throws BIZException {
        logger.info("51 syncVerifyInfo info:{}",info);
        try {
            mobileVeifyHandler.handler(info);
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
            mobileVeifyHandler.receive(param);
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
