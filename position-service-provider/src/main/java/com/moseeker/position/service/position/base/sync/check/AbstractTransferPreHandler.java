package com.moseeker.position.service.position.base.sync.check;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.SyncRequestType;
import com.moseeker.common.iface.IChannelType;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;

import java.util.Iterator;
import java.util.Map;

/**
 * 职位同步预处理抽象类
 * @param <F>   职位同步页面表单类
 */
public abstract class AbstractTransferPreHandler<F> implements IChannelType{

    /**
     * 多态预处理方法处理方法
     * @param f     前台表单对象
     * @param moseekerPosition  仟寻职位
     */
    public abstract void handle(F f, JobPositionDO moseekerPosition);

    /**
     * 获取同步请求端类型，ATS，WEB
     * @return 请求端类型
     */
    public abstract SyncRequestType getSyncRequestType();

    /**
     * 获取前台表单对应类型class
     * @return
     */
    public abstract Class<F> getFormClass();

    /**
     * 核心预处理方法，转换,调用多态处理方法
     * @param f
     * @param moseekerPosition
     */
    public void handle(JSONObject f, JobPositionDO moseekerPosition){
        F temp =f.toJavaObject(getFormClass());

        handle(temp,moseekerPosition);

        // 对temp的修改要同时对应到对f的修改
        Iterator<Map.Entry<String, Object>> it = JSON.parseObject(JSON.toJSONString(temp)).entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String,Object> entry = it.next();
            f.put(entry.getKey(),entry.getValue());
        }
    }
}
