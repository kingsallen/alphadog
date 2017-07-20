package com.moseeker.profile.service.impl.retriveprofile;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.profile.service.impl.retriveprofile.flow.AliPayRetrievalFlow;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jack on 11/07/2017.
 */
public class FlowFactoryTest {

    @Test
    public void createRetrieveFlow() throws Exception {
        ChannelType channelType = ChannelType.instaceFromInteger(5);
        RetrievalFlow retrievalFlow = FlowFactory.createRetrieveFlow(channelType);
        assertEquals(AliPayRetrievalFlow.class, retrievalFlow.getClass());
    }

}