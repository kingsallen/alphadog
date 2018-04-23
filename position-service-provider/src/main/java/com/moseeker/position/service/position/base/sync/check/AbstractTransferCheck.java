package com.moseeker.position.service.position.base.sync.check;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractTransferCheck<T> implements ITransferCheck<T> {
    private Map<T,List<String>> errorMsgMap=new ConcurrentHashMap<>();

    protected static final String CHECK_ERROR = "检查职位同步错误！";

    //获取前台表单对应类型class
    public abstract Class<T> getFormClass();

    /**
     * 将json类型的表单数据转换成对应的渠道表单类再传给各渠道实现类
     * @param jsonForm
     * @return
     */
    public boolean containsError(JSONObject jsonForm, JobPositionDO moseekerPosition){
        return containsError(jsonForm.toJavaObject(getFormClass()),moseekerPosition);
    }

    public List<String> getError(JSONObject jsonForm, JobPositionDO moseekerPosition){
        List<String> errorMsg;
        errorMsg = getError(jsonForm.toJavaObject(getFormClass()),moseekerPosition);

        return errorMsg;
    }

    @Override
    public boolean containsError(T t, JobPositionDO moseekerPosition) {
        return !StringUtils.isEmptyList(getError(t,moseekerPosition));
    }

    /*public List<String> popErrorMsg(JSONObject jsonForm){
        T t = jsonForm.toJavaObject(getFormClass());

        if(errorMsgMap.containsKey(t)){
            List<String> result=errorMsgMap.get(t);
            errorMsgMap.remove(t);
            return result;
        }
        return Collections.emptyList();
    }

    protected List<String> putErrorMsg(T t,List<String> errorMsg){
        if(errorMsgMap.containsKey(t)){
            List<String> result=errorMsgMap.get(t);
            errorMsgMap.put(t,errorMsg);
            return result;
        }
        return Collections.emptyList();
    }*/
}
