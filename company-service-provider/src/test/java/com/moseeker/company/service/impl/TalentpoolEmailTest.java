package com.moseeker.company.service.impl;

import com.moseeker.company.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zztaiwll on 18/4/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class TalentpoolEmailTest {
    @Autowired
    private TalentpoolEmailService talentpoolEmailService;

    @Test
    public void sendInviteEmail(){
        Map<String,String> params=new HashMap<>();
        java.util.List<Integer> userIdList=new ArrayList<>();
        userIdList.add(2191891);
        userIdList.add(2191521);
        userIdList.add(2191513);
        userIdList.add(2191505);
        userIdList.add(2191525);
        List<Integer> positionIdList=new ArrayList<>();
        positionIdList.add(1011417);
        positionIdList.add(1011421);
        positionIdList.add(1011422);
        positionIdList.add(1011423);
        positionIdList.add(1011581);
        int companyId=39978;
        int hrId=82752;
        int flag=0;
        int positionFlag=0;
        int result=talentpoolEmailService.talentPoolSendInviteToDelivyEmail(params,userIdList,positionIdList,companyId,hrId,flag,positionFlag);
        System.out.println(result) ;

    }
    @Test
    public void sendResumeEmail(){

    }
    @Test
    public void positionSendInviteEmail(){

    }
}
