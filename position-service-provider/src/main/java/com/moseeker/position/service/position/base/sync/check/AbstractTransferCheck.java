package com.moseeker.position.service.position.base.sync.check;

import com.alibaba.fastjson.JSON;
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
        return containsError(JSON.parseObject(jsonForm.toJSONString(),getFormClass()),moseekerPosition);
    }

    public List<String> getError(JSONObject jsonForm, JobPositionDO moseekerPosition){
        List<String> errorMsg;
        errorMsg = getError(JSON.parseObject(jsonForm.toJSONString(),getFormClass()),moseekerPosition);

        return errorMsg;
    }

    @Override
    public boolean containsError(T t, JobPositionDO moseekerPosition) {
        return !StringUtils.isEmptyList(getError(t,moseekerPosition));
    }
}
