package com.moseeker.candidate.thrift;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.candidate.service.CandidateService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * CandidateThriftService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Mar 2, 2017</pre>
 */
public class CandidateThriftServiceTest {

    CandidateService.Iface candidateService = ServiceManager.SERVICEMANAGER.getService(CandidateService.Iface.class);

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: glancePosition(int userId, int positionId, int shareChainId)
     */
    @Test
    public void testGlancePosition() throws Exception {
        candidateService.glancePosition(2, 1, 1);
    }


} 
