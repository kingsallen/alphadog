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
        userIdList.add(1234);
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
        userIdList.add(1234);
        Response res=talentPoolService.batchCancleTalent(hrId,userIdList,companyId);
        System.out.println(res);
    }
    @Test
    public void testAddTag(){
        int hrId=82752;
        int companyId=39978;
        String name="xxxx";
        Response res=talentPoolService.addHrTag(hrId,companyId,name);
        System.out.println(res);

    }

    @Test
    public void testDelTag(){
        int hrId=82752;
        int companyId=39978;
        int tagId =2;
        Response res=talentPoolService.deleteHrTag(hrId,companyId,tagId);
        System.out.println(res);

    }
    @Test
    public void testupdateTag(){
        int hrId=82752;
        int companyId=39978;
        int tagId =1;
        String name="ssss";
        Response res=talentPoolService.updateHrTag(hrId,companyId,tagId,name);
        System.out.println(res);

    }
    @Test
    public void testGetAllHrTag(){
        int hrId=82752;
        int companyId=39978;
        Response res=talentPoolService.getAllHrTag(hrId,companyId,1,100);
        System.out.println(res);

    }
    @Test
    public void testBatchCancleTalentTag(){
        int hrId=82752;
        int companyId=39978;
        Set<Integer> userIdList=new HashSet<>();
        userIdList.add(2191508);
        userIdList.add(2191513);
        userIdList.add(2191525);
        userIdList.add(2191558);
        Set<Integer> tagIdList=new HashSet<>();
        tagIdList.add(1);
        tagIdList.add(2);
        Response res=talentPoolService.cancaleBatchTalentTag(hrId,userIdList,tagIdList,companyId);
        System.out.println(res);

    }
    @Test
    public void testBatchAddTalentTag(){
        int hrId=82752;
        int companyId=39978;
        Set<Integer> userIdList=new HashSet<>();
        userIdList.add(2191508);
        userIdList.add(2191513);
        userIdList.add(2191525);
        userIdList.add(2191558);
        Set<Integer> tagIdList=new HashSet<>();
        tagIdList.add(1);
        tagIdList.add(2);
        Response res=talentPoolService.addBatchTalentTag(hrId,userIdList,tagIdList,companyId);
        System.out.println(res);
    }
    @Test
    public void testAddTalentComment(){
        int hrId=82752;
        int companyId=39978;
        int userId=2191508;
        String content="xxxxxx";
        Response res=talentPoolService.addTalentComment(hrId,companyId,userId,content);
        System.out.println(res);
    }
    @Test
    public void testCancleTalentComment(){
        int hrId=82752;
        int companyId=39978;
        int userId=2191508;
        int tagId=1;
        Response res=talentPoolService.delTalentComment(hrId,companyId,userId,tagId);
        System.out.println(res);
    }

    @Test
    public void testBatchTalentPublic(){
        int hrId=82752;
        int companyId=39978;
        Set<Integer> userIdList=new HashSet<>();
        userIdList.add(2191508);
        userIdList.add(2191513);
        userIdList.add(2191525);
        userIdList.add(2191558);
        Response res=talentPoolService.AddbatchPublicTalent(hrId,companyId,userIdList);
        System.out.println(res);
    }

    @Test
    public void testBatchCancleTalentPublic(){
        int hrId=82752;
        int companyId=39978;
        Set<Integer> userIdList=new HashSet<>();
        userIdList.add(2191508);
        userIdList.add(2191513);
        userIdList.add(2191525);
        userIdList.add(2191558);
        userIdList.add(12234);
        Response res=talentPoolService.cancleBatchPublicTalent(hrId,companyId,userIdList);
        System.out.println(res);
    }

    @Test
    public void getAllPublic(){
        int hrId=82752;
        int companyId=39978;
        Response res=talentPoolService.getCompanyPublic(hrId,companyId);
        System.out.println(res);
    }

    @Test
    public void getAllComment(){
        int userId=2191508;
        int hrId=82752;
        int companyId=39978;
        Response res=talentPoolService.getAllTalentComment(hrId,companyId,userId);
        System.out.println(res);
    }
}
