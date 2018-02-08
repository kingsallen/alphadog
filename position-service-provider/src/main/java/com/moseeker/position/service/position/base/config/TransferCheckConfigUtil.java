package com.moseeker.position.service.position.base.config;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.SyncRequestType;
import com.moseeker.position.constants.CheckStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class TransferCheckConfigUtil {
    @Autowired
    List<AbstractTransferCheckConfig> checkConfigs;

    public Map<String, Map<CheckStrategy, String>> getConfig(SyncRequestType requestType,ChannelType channelType){
        if(requestType==null || channelType==null){
            return null;
        }
        for(AbstractTransferCheckConfig checkConfig:checkConfigs){
                if(checkConfig.getSyncRequestType()==requestType && checkConfig.getStrategy()!=null){
                    return checkConfig.getStrategy().get(channelType);
                }
        }
        return null;
    }
}
