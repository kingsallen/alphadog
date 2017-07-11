package com.moseeker.profile.service.impl.retriveprofile;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.profile.service.impl.retriveprofile.flows.AliPayRetrievalFlow;

/**
 * profile回收类型工厂
 * Created by jack on 10/07/2017.
 */
public class FlowFactory {

    /**
     * 根据渠道生成对应的profile回收类
     * @param channelType 渠道类型
     * @return 具体的profile回收业务类
     */
    public static RetrievalFlow createRetrieveFlow(ChannelType channelType) {
        RetrievalFlow retrievalFlow = null;
        switch (channelType) {
            case ALIPAY: retrievalFlow = new AliPayRetrievalFlow();break;
        }

        return retrievalFlow;
    }
}
