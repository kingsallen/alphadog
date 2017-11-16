package com.moseeker.useraccounts.service.thirdpartyaccount;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.util.AopTargetUtils;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.useraccounts.config.AppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ThirdPartyAccountServiceTest {
    @Autowired
    ThirdPartyAccountService service;

    HrThirdPartyAccountDO thirdPartyAccount;

    @Mock
    ThirdPartyAccountSynctor synctor;

    @Before
    public void init() throws Exception {
        synctor= Mockito.mock(ThirdPartyAccountSynctor.class);

        ReflectionTestUtils.setField(AopTargetUtils.getTarget(service), "thirdPartyAccountSynctor", synctor);

        Mockito.when(synctor.bindThirdPartyAccount(thirdPartyAccount,null,false)).thenReturn(thirdPartyAccount);
    }

    @Test
    public void test() throws Exception {
        String str="{" +
                "  \"username\": \"18917124545\"," +
                "  \"appid\": 4," +
                "  \"user_id\": 82690," +
                "  \"member_name\": null," +
                "  \"channel\": 3" +
                "}";
        JSONObject obj=JSON.parseObject(str);
        thirdPartyAccount= JSON.toJavaObject(obj,HrThirdPartyAccountDO.class);

        service.bindThirdAccount(obj.getIntValue("user_id"),thirdPartyAccount,false);


    }
}
