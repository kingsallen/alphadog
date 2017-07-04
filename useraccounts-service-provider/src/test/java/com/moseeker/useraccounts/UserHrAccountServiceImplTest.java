package com.moseeker.useraccounts;

import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.rpccenter.config.ClientConfig;
import com.moseeker.rpccenter.config.RegistryConfig;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService;
import com.moseeker.thrift.gen.useraccounts.struct.*;
import com.moseeker.useraccounts.config.AppConfig;
import com.moseeker.useraccounts.service.impl.UserEmployeeServiceImpl;
import org.apache.thrift.TException;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * HR账号服务
 * <p>
 * Created by zzh on 16/6/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
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
        UserHrAccountService.Iface user = ServiceManager.SERVICEMANAGER.getService(UserHrAccountService.Iface.class);
        Response response = user.userHrAccount(0, 1, 1, 10);
        System.out.println(response.getData());
    }

    public UserHrAccountService.Iface service;

    @Before
    public void init() {
        service = ServiceManager.SERVICEMANAGER.getService(UserHrAccountService.Iface.class);
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

    //    @Test
    public void testNpsList() throws TException {
        HrNpsStatistic result = service.npsList(null, null, 0, 0);
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

    @Test
    public void testBinding() throws Exception {
        HrThirdPartyAccountDO hrThirdPartyAccountDO = new HrThirdPartyAccountDO();
        hrThirdPartyAccountDO.setUsername("xxxxx");
        hrThirdPartyAccountDO.setPassword("xxxxx");
        hrThirdPartyAccountDO.setChannel((short) 2);
        userHrAccountService.bindThirdAccount(82847, hrThirdPartyAccountDO);
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

        String groupName = "test:"+System.currentTimeMillis();

        System.out.println("groupName:"+groupName);

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

        int[] result = userEmployeeDao.postPutUserEmployeeBatch(batchForm);

        System.out.println(DateUtils.dateToLongTime(new Date()));

//        Thread.sleep(1000*600);

        System.out.println(result);
    }
}
