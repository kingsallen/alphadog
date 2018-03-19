package com.moseeker.position.service.position.jobsdb.refresh.handler;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.iface.IChannelType;
import com.moseeker.position.service.position.base.refresh.handler.ResultHandlerAdapter;

/**
 * 一个适配器接口,
 * JOBSDB所有结果处理策略都需要实现这个接口
 * 在{com.moseeker.position.service.position.jobsdb.refresh.JobsDBParamRefresher}类中会自动注入
 * 自动调用handle方法
 */
public interface JobsDBResultHandlerAdapter extends ResultHandlerAdapter<String>,IChannelType {
    default ChannelType getChannelType(){
        return ChannelType.JOBSDB;
    }
}
