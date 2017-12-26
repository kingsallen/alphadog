package com.moseeker.position.service.position.base.sync;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.SyncRequestType;
import com.moseeker.position.constants.CheckStrategy;
import com.moseeker.position.service.position.base.config.TransferCheckConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 职位转换检测抽象类
 */
@Component
public class TransferCheckUtil {

    @Autowired
    TransferCheckConfigUtil checkConfigUtil;

    public List<String> checkBeforeTransfer(SyncRequestType requestType, ChannelType channelType, JSONObject jsonForm){
        if(requestType==null || channelType==null || jsonForm==null || jsonForm.isEmpty()){
            return Collections.emptyList();
        }

        Map<String,Map<CheckStrategy,String>> checkStrategy = getCheckStrategy(requestType,channelType);
        if(checkStrategy==null || checkStrategy.isEmpty()){
            return Collections.emptyList();
        }

        List<String> msgs=new ArrayList<>();
        checkStrategy.forEach((param,strategies)->{
            if(!jsonForm.containsKey(param)){
                msgs.add(CheckStrategy.REQUIRED.errorMsg(param));
            }else{
                String value=jsonForm.getString(param);

                strategies.forEach((strategy,strategyVal)->{
                    if(!strategy.check(strategyVal,value)){
                        msgs.add(strategy.errorMsg(value));
                    }
                });
            }
        });

        return Collections.emptyList();
    }



    private Map<String, Map<CheckStrategy, String>> getCheckStrategy(SyncRequestType requestType,ChannelType channelType){
        if(requestType==null || channelType==null){
            return null;
        }
        return checkConfigUtil.getConfig(requestType,channelType);
    }
}
