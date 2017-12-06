package com.moseeker.company.service.impl;

import com.moseeker.company.config.AppConfig;
import com.moseeker.thrift.gen.common.struct.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zztaiwll on 17/12/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class TalentPoolServiceTest {
    @Autowired
    private TalentPoolService talentPoolService;

    @Test
    public void testBatchAddTalent(){
        int hrId=82752;
        int companyId=39978;
        Set<Integer> userIdList=new HashSet<>();
        userIdList.add(2191508);
        userIdList.add(2191513);
        userIdList.add(2191525);
        userIdList.add(2191558);
        Response res=talentPoolService.batchAddTalent(hrId,userIdList,companyId);
        System.out.println(res);
    }
    @Test
    public void testBatchCancleTalent(){
        int hrId=82752;
        int companyId=39978;
        Set<Integer> userIdList=new HashSet<>();
        userIdList.add(2191508);
        userIdList.add(2191513);
        userIdList.add(2191525);
        userIdList.add(2191558);
        Response res=talentPoolService.batchCancleTalent(hrId,userIdList,companyId);
        System.out.println(res);
    }
    @Test
    public void testBatchCancleTalentTag(){
        int hrId=82752;
        int companyId=39978;
    }
    @Test
    public void testBatchAddTalentTag(){
        int hrId=82752;
        int companyId=39978;
    }
    @Test
    public void testAddTalentComment(){
        int hrId=82752;
        int companyId=39978;
    }
    @Test
    public void testCancleTalentComment(){
        int hrId=82752;
        int companyId=39978;
    }

    @Test
    public void testBatchTalentPublic(){
        int hrId=82752;
        int companyId=39978;
    }

    @Test
    public void testBatchCancleTalentPublic(){
        int hrId=82752;
        int companyId=39978;
    }
}
