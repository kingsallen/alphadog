package com.moseeker.useraccounts.service.thirdpartyaccount;

import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.useraccounts.config.AppConfig;
import com.moseeker.useraccounts.service.impl.LiePinUserAccountBindHandler;
import org.jooq.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 猎聘用户绑定
 *
 * @author cjm
 * @date 2018-05-28 16:16
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class LPUserAccountBindTest {

    @Autowired
    HRThirdPartyAccountDao dao;

    @Test
    public void testBind() throws Exception {
        HrThirdPartyAccountDO user = new HrThirdPartyAccountDO();
        user.setUsername("mayflower");
        user.setPassword("20180612");
        LiePinUserAccountBindHandler handler = new LiePinUserAccountBindHandler();
        user = handler.bind(user, null);
    }

//    @Test
    public void testGet() throws Exception {

        List<HrThirdPartyAccountDO>  list = dao.getBoundThirdPartyAccountDO(2);
    }

//    @Test
    public void test1(){

    }
}
