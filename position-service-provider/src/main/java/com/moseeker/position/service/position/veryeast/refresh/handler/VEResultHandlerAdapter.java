package com.moseeker.position.service.position.veryeast.refresh.handler;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.position.service.position.base.refresh.handler.ResultHandlerAdapter;

/**
 * 一个适配器接口,
 * VeryEast所有结果处理策略都需要实现这个接口
 * 在{com.moseeker.position.service.position.veryeast.refresh.VeryEastParamRefresher}类中会自动注入
 * 自动调用handle方法
 */
public interface VEResultHandlerAdapter extends ResultHandlerAdapter<String> {
    default ChannelType channelType(){
        return ChannelType.VERYEAST;
    }
}
