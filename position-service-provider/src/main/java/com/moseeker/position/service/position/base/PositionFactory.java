package com.moseeker.position.service.position.base;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.refresh.AbstractRabbitMQParamRefresher;
import com.moseeker.position.service.position.base.sync.PositionSyncVerifyHandler;
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
        throw new RuntimeException("no matched RabbitMQParamRefresher");
    }


    @Autowired
    private List<PositionSyncVerifyHandler> handlers;

    public PositionSyncVerifyHandler getVerifyHandlerInstance(ChannelType channelType){
        for(PositionSyncVerifyHandler handler:handlers){
            if(handler.getChannelType()==channelType){
                return handler;
            }
        }
        throw new RuntimeException("no matched PositionSyncVerifyHandler");
    }

}
