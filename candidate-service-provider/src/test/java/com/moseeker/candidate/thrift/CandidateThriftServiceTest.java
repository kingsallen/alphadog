package com.moseeker.candidate.thrift;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.candidate.service.CandidateService;
import com.moseeker.thrift.gen.candidate.struct.*;
import org.apache.thrift.TException;
import org.junit.After;
import org.junit.Before;
import java.util.ArrayList;
import java.util.List;

/**
 * CandidateThriftService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Mar 2, 2017</pre>
 */
public class CandidateThriftServiceTest {

    CandidateService.Iface candidateService = ServiceManager.SERVICE_MANAGER.getService(CandidateService.Iface.class);

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }



    /**
     * Method: glancePosition(int userId, int positionId, int shareChainId)
     */
    ////@Test
    public void testGlancePosition() throws Exception {
        candidateService.glancePosition(3502, 24443, 701);
    }
    
//    //@Test
//    public void changeInterestingTest() throws TException {
//    		candidateService.changeInteresting(391470, 61106, (byte)0);
//    }

    ////@Test
    public void testCandidateList() {

        CandidateListParam param = new CandidateListParam();
        param.setCompanyId(39978);
        param.setClickTime("2017-06-01");
        param.setPostUserId(191576);
        List<Integer> recomList = new ArrayList<Integer>(){{
            add(1);
            add(2);
            add(3);
        }};

        param.setRecoms(recomList);
        try {
            List<CandidateList> result = candidateService.candidateList(param);
            if (result != null && result.size() > 0) {
                result.forEach(candidateList -> {
                    System.out.println(candidateList);
                });
            } else {
                System.out.println("no one find!");
            }
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    ////@Test
    public void testGetRecommendations() {
        List<Integer> candidateIdList = new ArrayList<Integer>(){{
            add(14784);
            add(14816);
        }};
        try {
            RecommendResult recommendResult = candidateService.getRecomendations(39978, candidateIdList);
            System.out.println(recommendResult);
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    ////@Test
    public void testRecommend() {
        RecommmendParam param = new RecommmendParam();
        param.setCompanyId(39978);
        param.setCompany("recommend-test");
        param.setClickTime("2017-06-01");
        param.setPostUserId(191576);
        param.setId(14784);
        param.setMobile("15502117047");
        param.setPosition("recommend-position-test");
        param.setRealName("realname-test");
        param.setRecomReason("test");
        try {
            RecommendResult recommendResult = candidateService.recommend(param);
            System.out.println(recommendResult);
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    ////@Test
    public void testRecommend1() {
        RecommmendParam param = new RecommmendParam();
        param.setCompanyId(39978);
        param.setCompany("recommend-test");
        param.setClickTime("2017-02-33 12:12");
        param.setPostUserId(3341);
        param.setId(14349);
        param.setMobile("15502117047");
        param.setPosition("recommend-position-test");
        param.setRealName("realname-test");
        param.setRecomReason("test");
        try {
            RecommendResult recommendResult = candidateService.recommend(param);
            System.out.println(recommendResult);
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    ////@Test
    public void testGetRecommendation() {
        try {
            RecomRecordResult recommendResult = candidateService.getRecommendation(14349, 3341);
            System.out.println(recommendResult);
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    ////@Test
    public void testGetRecommendatorySorting() {
        try {
            SortResult result = candidateService.getRecommendatorySorting(4, 1);
            System.out.println(result);
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    ////@Test
    public void testIgnore() {
        try {
            RecommendResult result = candidateService.ignore(1, 1, 4, "2017-02-21");
            System.out.println(result);
        } catch (TException e) {
            e.printStackTrace();
        }
    }
} 
