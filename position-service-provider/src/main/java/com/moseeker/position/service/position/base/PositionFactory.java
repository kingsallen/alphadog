package com.moseeker.position.service.position.base;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.refresh.AbstractRabbitMQParamRefresher;
import com.moseeker.position.service.position.base.sync.verify.PositionSyncVerifier;
import com.moseeker.position.service.position.base.sync.verify.PositionSyncVerifyHandler;
import com.moseeker.position.service.position.base.sync.verify.PositionSyncVerifyReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PositionFactory {

    Logger logger= LoggerFactory.getLogger(PositionFactory.class);

    @Autowired
    private List<AbstractRabbitMQParamRefresher> refreshList=new ArrayList<>();


    public boolean hasRabbitMQParamRefresher(ChannelType channelType){
        for(AbstractRabbitMQParamRefresher refresher:refreshList){
            if(refresher.getChannelType()==channelType){
                return true;
            }
        }
        return false;
    }

    public AbstractRabbitMQParamRefresher getRabbitMQParamRefresher(ChannelType channelType){
        for(AbstractRabbitMQParamRefresher refresher:refreshList){
            if(refresher.getChannelType()==channelType){
                return refresher;
            }
        }
        logger.error("no matched RabbitMQParamRefresher channelType:{}",channelType);
        throw new RuntimeException("no matched RabbitMQParamRefresher");
    }


    @Autowired
    private List<PositionSyncVerifier> handlers;

    public PositionSyncVerifyHandler getVerifyHandlerInstance(ChannelType channelType){
        for(PositionSyncVerifier handler:handlers){
            if(handler.getChannelType()==channelType){
                return handler;
            }
        }
        logger.error("no matched PositionSyncVerifyHandler channelType:{}",channelType);
        throw new RuntimeException("no matched PositionSyncVerifyHandler");
    }

    public PositionSyncVerifyReceiver getVerifyReceiverInstance(ChannelType channelType){
        for(PositionSyncVerifier handler:handlers){
            if(handler.getChannelType()==channelType){
                return handler;
            }
        }
        logger.error("no matched PositionSyncVerifyReceiver channelType:{}",channelType);
        throw new RuntimeException("no matched PositionSyncVerifyReceiver");
    }
}
