package com.moseeker.position.service.position.base.refresh;

import com.moseeker.common.constants.ChannelType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RefresherFactory {

    Logger logger= LoggerFactory.getLogger(RefresherFactory.class);

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

}
