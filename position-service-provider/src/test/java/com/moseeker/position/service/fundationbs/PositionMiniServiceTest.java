package com.moseeker.position.service.fundationbs;

import com.moseeker.position.config.AppConfig;
import com.moseeker.position.pojo.CompanyAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zztaiwll on 18/1/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class PositionMiniServiceTest {

    @Autowired
    private PositionMiniService positionMiniService;

    @Test
    public void getCompanyAccountTest(){
        int accountId=82752;
        CompanyAccount account1=positionMiniService.getAccountInfo(accountId);
        System.out.println(account1.toString());
    }
}
