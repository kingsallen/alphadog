package com.moseeker.position.service.position.base.sync;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.SyncRequestType;
import com.moseeker.position.service.position.base.sync.check.AbstractTransferCheck;
import com.moseeker.position.service.position.base.sync.check.ITransferCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 职位转换检测抽象类
 */
@Component
public class TransferCheckUtil {

    @Autowired
    List<AbstractTransferCheck> checkList;

    public List<String> checkBeforeTransfer(SyncRequestType requestType, ChannelType channelType, JSONObject jsonForm){
        if(requestType==null || channelType==null || jsonForm==null || jsonForm.isEmpty()){
            return Collections.emptyList();
        }

        for(AbstractTransferCheck transferCheck:checkList){
            if(transferCheck.getChannelType()==channelType){
                if(transferCheck.containsError(jsonForm)){
                    return transferCheck.getError(jsonForm);
                }
            }
        }
        return Collections.emptyList();
    }

}
