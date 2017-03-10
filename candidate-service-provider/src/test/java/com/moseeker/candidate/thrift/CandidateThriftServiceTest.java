package com.moseeker.candidate.thrift;

import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.candidatedb.tables.records.CandidatePositionRecord;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.candidate.service.CandidateService;
import com.moseeker.thrift.gen.dao.struct.CandidatePositionDO;

import org.apache.thrift.TException;
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
    //@Test
    public void testGlancePosition() throws Exception {
        candidateService.glancePosition(2, 1, 1);
    }
    
    @Test
    public void changeInterestingTest() throws TException {
    		candidateService.changeInteresting(391470, 61106, (byte)1);
    }
} 
