package com.moseeker.useraccounts.service.thirdpartyaccount;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.useraccounts.service.thirdpartyaccount.info.ThridPartyAcountEntity;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.useraccounts.config.AppConfig;
import com.moseeker.useraccounts.service.thirdpartyaccount.operation.BindOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ThirdPartyAccountServiceTest {
    @Autowired
    ThirdPartyAccountService service;

    @Autowired
    HRThirdPartyAccountDao thirdPartyAccountDao;

    @Autowired
    ThridPartyAcountEntity thridPartyAcountEntity;

    /*@Mock
    ChaosHandler chaosHandler;*/

    @Autowired
    BindOperation bindOperation;

    /*@Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        chaosHandler= Mockito.mock(ChaosHandler.class);

        ReflectionTestUtils.setField(AopTargetUtils.getTarget(bindOperation), "chaosHandler", chaosHandler);

        HrThirdPartyAccountDO thirdPartyAccount=new HrThirdPartyAccountDO();
        thirdPartyAccount.setBinding((short)10);
        Mockito.when(chaosHandler.bind(thirdPartyAccount,new HashMap<>())).thenReturn(thirdPartyAccount);
    }*/

    @Test
    public void test() throws Exception {
        String str="{" +
                "  \"username\": \"peter62277\"," +
                "  \"appid\": 4," +
                "  \"user_id\": 82694," +
                "  \"channel\": 7," +
                " \"password\": \"123\","+
                " \"ext\": \"123\""+
                "}";
        JSONObject obj=JSON.parseObject(str);
        HrThirdPartyAccountDO thirdPartyAccount= JSON.toJavaObject(obj,HrThirdPartyAccountDO.class);

        //正常流程
        try {
            print(service.bindThirdAccount(obj.getIntValue("user_id"),thirdPartyAccount,false));
        }catch (Exception e){
            e.printStackTrace();
        }

        //测试已存在
        thirdPartyAccount.setUsername("peter62277");
        thirdPartyAccount.setChannel((short)1);
        thirdPartyAccount.setExt("sc930524");
        try {
            print(service.bindThirdAccount(obj.getIntValue("user_id"),thirdPartyAccount,false));
        }catch (Exception e){
            e.printStackTrace();
        }

        //测试同渠道
        thirdPartyAccount.setCompanyId(4);
        thirdPartyAccount.setUsername("18917124545");
        thirdPartyAccount.setChannel((short)2);
        try {
            print(service.bindThirdAccount(82690,thirdPartyAccount,false));
        }catch (Exception e){
            e.printStackTrace();
        }

        //测试正常流程
        /*new Thread(){
            @Override
            public void run() {
                try {
                    print(service.bindThirdAccount(obj.getIntValue("user_id"),thirdPartyAccount,false));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();*/

        //测试redis
        /*new Thread(){
            @Override
            public void run() {
                try {
                    print(service.bindThirdAccount(obj.getIntValue("user_id"),thirdPartyAccount,false));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();*/


        /*new Thread(){
            @Override
            public void run() {
                try {
                    print(service.bindThirdAccount(obj.getIntValue("user_id"),thirdPartyAccount,false));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();*/



    }

    public void print(Object obj){
        System.out.println(JSON.toJSONString(obj));
    }
}
