package com.moseeker.useraccounts;

import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.email.Email;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.rpccenter.config.ClientConfig;
import com.moseeker.rpccenter.config.RegistryConfig;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.employee.struct.RewardVOPageVO;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService;
import com.moseeker.thrift.gen.useraccounts.struct.*;
import com.moseeker.useraccounts.thrift.UserHrAccountServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.moseeker.useraccounts.service.impl.UserEmployeeServiceImpl;
import org.apache.thrift.TException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;

/**
 * HR账号服务
 * <p>
 * Created by zzh on 16/6/1.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
public class UserHrAccountServiceImplTest {

    public static void main(String[] args) {

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setConnectstr("127.0.0.1:2181");
        registryConfig.setNamespace("services");

        String iface = UserHrAccountService.Iface.class.getName();
        ClientConfig<UserHrAccountService.Iface> clientConfig = new ClientConfig<UserHrAccountService.Iface>();
        clientConfig.setService("com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService");
        clientConfig.setIface(iface);

        UserHrAccountService.Iface userHrAccountService = null;

        try {
            userHrAccountService = clientConfig.createProxy(registryConfig);

            // 添加我感兴趣
            String code = "1234";
//            Response postResource = userHrAccountService.postResource(getUserHrAccount(), code);
            Response postResource = userHrAccountService.userHrAccount(1, 1, 1, 10);

            System.out.println(postResource);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 添加HR账号
     */
    private static UserHrAccount getUserHrAccount() {
        UserHrAccount userHrAccount = new UserHrAccount();
        userHrAccount.setMobile("18610245972");
        userHrAccount.setSource(2);
        return userHrAccount;
    }


    ////@Test
    public void userHrAccount() throws TException {
        UserHrAccountService.Iface user = ServiceManager.SERVICE_MANAGER.getService(UserHrAccountService.Iface.class);
        Response response = user.userHrAccount(0, 1, 1, 10);
        System.out.println(response.getData());
    }

    public UserHrAccountService.Iface service;

    @Before
    public void init() {
        service = ServiceManager.SERVICE_MANAGER.getService(UserHrAccountService.Iface.class);
    }

    //    @Test
    public void testNpsStatus() throws TException {
        HrNpsResult result = service.npsStatus(82690, null, null);
        System.out.println(BeanUtils.convertStructToJSON(result));
    }

    //    @Test
    public void testNpsUpdate() throws TException {
        HrNpsUpdate update = new HrNpsUpdate();
        update.setUser_id(82689);
        update.setIntention(Byte.valueOf("8"));
        HrNpsResult result = service.npsUpdate(update);
        System.out.println(BeanUtils.convertStructToJSON(result));
    }

    //    @Test
    public void testNpsUpdate2() throws TException {
        HrNpsUpdate update = new HrNpsUpdate();
        update.setUser_id(82689);
        update.setUsername("testUserName");
        update.setMobile("testMobile");
        update.setCompany("testCompany");
        HrNpsResult result = service.npsUpdate(update);
        System.out.println(BeanUtils.convertStructToJSON(result));
    }


    @Autowired
    UserHrAccountDao userHrAccountDao;

    @Test
    public void testNpsList() throws Exception {
        HrNpsStatistic result = userHrAccountDao.npsList(null, null, 1, 500);
        System.out.println(BeanUtils.convertStructToJSON(result));
    }


    @Test
    public void testStructToDB() {
        HrThirdPartyAccountDO hrThirdPartyAccountDO = new HrThirdPartyAccountDO();
        hrThirdPartyAccountDO.setChannel(Short.valueOf("2"));
        hrThirdPartyAccountDO.setUsername("fdfdsaf");
        hrThirdPartyAccountDO.setPassword("fdfdsfdpwd");
        hrThirdPartyAccountDO.setBinding(Short.valueOf("1"));
        HrThirdPartyAccountRecord record = BeanUtils.structToDB(hrThirdPartyAccountDO, HrThirdPartyAccountRecord.class);

        record.toString();
    }

    @Autowired
    com.moseeker.useraccounts.service.impl.UserHrAccountService userHrAccountService;

    @Autowired
    UserHrAccountServiceImpl userHrAccountServiceImpl;

    @Test
    public void testBinding() throws Exception {
        HrThirdPartyAccountDO hrThirdPartyAccountDO = new HrThirdPartyAccountDO();
        hrThirdPartyAccountDO.setUsername("fiqb60145062");
        hrThirdPartyAccountDO.setPassword("2892c63f12e0e8849f2a7dd981375331");
        hrThirdPartyAccountDO.setChannel((short) 3);
//        userHrAccountService.bindThirdAccount(82752, hrThirdPartyAccountDO, false);

        Thread.sleep(1000000);
    }

    @Test
    public void testRefresh() throws Exception {
//        userHrAccountService.synchronizeThirdpartyAccount(82752, 66, true);
        HrThirdPartyAccountDO hrThirdPartyAccountDO = new HrThirdPartyAccountDO();
        hrThirdPartyAccountDO.setUsername("xxxxx");
        hrThirdPartyAccountDO.setPassword("xxxxx");
        hrThirdPartyAccountDO.setChannel((short) 2);
//        userHrAccountService.bindThirdAccount(82847, hrThirdPartyAccountDO, true);
    }


    @Autowired
    UserEmployeeDao userEmployeeDao;

    @Autowired
    UserEmployeeServiceImpl userEmployeeService;

    @Test
    public void testUserEmployeeBatch() throws Exception {

        System.out.println(DateUtils.dateToLongTime(new Date()));

        //51350 1

        Query query = new Query.QueryBuilder().where("company_id", 51350).setPageSize(1000).buildQuery();
        List<UserEmployeeStruct> employeeStructs = userEmployeeDao.getDatas(query, UserEmployeeStruct.class);

        String groupName = "test:" + System.currentTimeMillis();

        System.out.println("groupName:" + groupName);

        for (UserEmployeeStruct userEmployeeStruct : employeeStructs) {
            userEmployeeStruct.setCompany_id(1);
            userEmployeeStruct.setId(0);
            userEmployeeStruct.unsetId();
            userEmployeeStruct.setGroupname(groupName);
        }

        System.out.println(DateUtils.dateToLongTime(new Date()));

        UserEmployeeBatchForm batchForm = new UserEmployeeBatchForm();
        batchForm.setAs_task(true);
        batchForm.setCompany_id(1);
        batchForm.setData(employeeStructs);
        batchForm.setDel_not_include(true);

//        int[] result = userEmployeeDao.postPutUserEmployeeBatch(batchForm);

        System.out.println(DateUtils.dateToLongTime(new Date()));

//        Thread.sleep(1000*600);

//        System.out.println(result);
    }

    //    @Test
//    @Transactional
    public void addReawrdTest() {
        try {
            userHrAccountServiceImpl.addEmployeeReward(658112, 100, 100, "加积分");
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    //    @Test
    public void getEmployeeRewardsTest() {
        try {
            RewardVOPageVO rewardVOPageVO = userHrAccountServiceImpl.getEmployeeRewards(658112, 39978, 10, 1);
            System.out.println(BeanUtils.convertStructToJSON(rewardVOPageVO));
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    //    @Test
//    @Transactional
    public void removeEmployeeTest() {
        try {
            Assert.assertTrue(userHrAccountServiceImpl.delEmployee(Arrays.asList(3352, 3353)));
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    //    @Test
//    @Transactional
    public void unbindTest() {
        try {
            Assert.assertTrue(userHrAccountServiceImpl.unbindEmployee(Arrays.asList(3394, 658112, 3402)));
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSendEmail() throws Exception {
        System.out.println("start");
        List<String> recipients = new ArrayList<>();
        recipients.add("wengjianfei@moseeker.com");
        String subject = "测试邮件";
        StringBuilder content = new StringBuilder();
        content.append("<b style=\"color:blue;text-decoration:underline\">【简历邮箱】：").append("cv_").append(1234567).append("@test.com").append("</b>");
        content.append("<b style=\"color:red\">（手动发布该职位时，请一定将该邮箱填写在简历回收邮箱中）</b>").append("<br/>");
        Email.EmailBuilder emailBuilder = new Email.EmailBuilder(recipients.subList(0, 1));
        emailBuilder.addCCList(recipients.subList(1, recipients.size()));
        emailBuilder.setSubject(subject);
        emailBuilder.setContent(content.toString());
        Email email = emailBuilder.build();
        email.send(3, new Email.EmailListener() {
            @Override
            public void success() {
                System.out.println("email send messageDelivered");
            }

            @Override
            public void failed(Exception e) {
                System.out.println("发送职位同步刷新错误的邮件失败了:EmailTO:" + recipients + ":Title:" + subject.toString() + ":Message:" + content.toString());
            }
        });

        Thread.sleep(100000);
    }

}
