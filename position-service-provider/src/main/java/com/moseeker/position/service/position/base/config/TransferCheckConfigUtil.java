package com.moseeker.position.service.position.base.config;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.constants.CheckStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class TransferCheckConfigUtil {
    @Autowired
    List<AbstractTransferCheckConfig> checkConfigs;

    public Map<ChannelType, Map<String, Map<CheckStrategy, String>>> getConfig(){
        for(AbstractTransferCheckConfig checkConfig:checkConfigs){
                return null;
        }
        return null;
    }
}
