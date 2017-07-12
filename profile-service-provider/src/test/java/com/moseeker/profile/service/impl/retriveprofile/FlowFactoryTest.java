package com.moseeker.profile.service.impl.retriveprofile;

import com.moseeker.common.constants.ChannelType;
import com.moseeker.profile.config.AppConfig;
import com.moseeker.profile.service.impl.retriveprofile.flows.AliPayRetrievalFlow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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