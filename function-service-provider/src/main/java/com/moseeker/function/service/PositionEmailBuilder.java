package com.moseeker.function.service;

import com.moseeker.common.iface.IChannelType;

import java.util.Map;

public interface PositionEmailBuilder<P> extends IChannelType{
    String divider = "<br/>";

    Map<String,String> message(P position);
}
