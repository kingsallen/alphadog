package com.moseeker.useraccounts.service.thirdpartyaccount;

import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.useraccounts.config.AppConfig;
import com.moseeker.useraccounts.service.impl.Job58UserAccountBindHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cjm
 * @date 2018-11-22 15:46
 **/
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
public class Job58AccountBindTest {

//    @Autowired
//    Job58UserAccountBindHandler bindHandler;

    @Test
    public void testBind() throws Exception {
        Job58UserAccountBindHandler bindHandler = new Job58UserAccountBindHandler();
        HrThirdPartyAccountDO hrThirdPartyAccount = new HrThirdPartyAccountDO();
        Map<String, String> requestCode = new HashMap<>();
        requestCode.put("ext", "14cdb8893e92c8d752d8e5f13b2f2d59");
        bindHandler.bind(hrThirdPartyAccount, requestCode);
    }

}
