package com.moseeker.position.service.position.base.sync.check;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractTransferCheck<T> implements ITransferCheck<T> {
    Map<T,List<String>> errorMsgMap=new ConcurrentHashMap<>();

    public List<String> popErrorMsg(T t){
        if(errorMsgMap.containsKey(t)){
            List<String> result=errorMsgMap.get(t);
            errorMsgMap.remove(t);
            return result;
        }
        return Collections.emptyList();
    }

    public List<String> putErrorMsg(T t,List<String> errorMsg){
        if(errorMsgMap.containsKey(t)){
            List<String> result=errorMsgMap.get(t);
            errorMsgMap.put(t,errorMsg);
            return result;
        }
        return Collections.emptyList();
    }
}
