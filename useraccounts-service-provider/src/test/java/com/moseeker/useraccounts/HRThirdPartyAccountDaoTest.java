package com.moseeker.useraccounts;

import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.useraccounts.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class HRThirdPartyAccountDaoTest {

    @Autowired
    HRThirdPartyAccountDao thirdPartyAccountDao;

    @Test
    public void test(){
        thirdPartyAccountDao.getThirdPartyAccountByUserId(82752,1);
    }

}